package org.literacyapp.appstore.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.literacyapp.appstore.MainActivity;
import org.literacyapp.appstore.R;
import org.literacyapp.appstore.util.ApkLoader;
import org.literacyapp.appstore.util.ChecksumHelper;
import org.literacyapp.appstore.util.ConnectivityHelper;
import org.literacyapp.appstore.util.DeviceInfoHelper;
import org.literacyapp.appstore.util.EnvironmentSettings;
import org.literacyapp.appstore.util.JsonLoader;
import org.literacyapp.appstore.util.UserPrefsHelper;
import org.literacyapp.model.gson.admin.ApplicationGson;
import org.literacyapp.model.gson.admin.ApplicationVersionGson;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DownloadApplicationsAsyncTask extends AsyncTask<Object, Integer, List<ApplicationGson>> {

    private Logger logger = Logger.getLogger(getClass());

    private Context context;

    public DownloadApplicationsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<ApplicationGson> doInBackground(Object... objects) {
        logger.info("doInBackground");

        boolean isServerReachable = ConnectivityHelper.isServerReachable(context);
        logger.info("isServerReachable: " + isServerReachable);
        if (!isServerReachable) {
            logger.warn(context.getString(R.string.server_is_not_reachable));
        } else {
            // Download List of applications
            String url = EnvironmentSettings.getBaseRestUrl() + "/admin/application/list" +
                    "?deviceId=" + DeviceInfoHelper.getDeviceId(context) +
                    "&checksum=" + ChecksumHelper.getChecksum(context) +
                    "&locale=" + UserPrefsHelper.getLocale(context) +
                    "&deviceModel=" + DeviceInfoHelper.getDeviceModel(context) +
                    "&osVersion=" + Build.VERSION.SDK_INT +
                    "&applicationId=" + DeviceInfoHelper.getApplicationId(context) +
                    "&appVersionCode=" + DeviceInfoHelper.getAppVersionCode(context);
            String jsonResponse = JsonLoader.loadJson(url);
            logger.info("jsonResponse: " + jsonResponse);
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (!"success".equals(jsonObject.getString("result"))) {
                    logger.warn("Download failed");
                } else {
                    JSONArray jsonArrayApplications = jsonObject.getJSONArray("applications");
                    // TODO
                }
            } catch (JSONException e) {
                logger.error(null, e);
            }

//            Type type = new TypeToken<List<ApplicationGson>>(){}.getType();
//            List<ApplicationGson> applicationGsonList = new Gson().fromJson(jsonResponse, type);
//            logger.info("applicationGsonList.size(): " + applicationGsonList.size());
//            int counter = 0;
//            for (ApplicationGson applicationGson : applicationGsonList) {
//                logger.info("applicationGson.getPackageName(): " + applicationGson.getPackageName());
//
//                ApplicationVersionGson applicationVersionGson = applicationGson.getApplicationVersions().get(0);
//
//                // Install/update application
//                PackageManager packageManager = context.getPackageManager();
//                try {
//                    PackageInfo packageInfo = packageManager.getPackageInfo(applicationGson.getPackageName(), PackageManager.GET_ACTIVITIES);
//                    logger.info("The application is already installed: " + applicationGson.getPackageName());
//                    // Check if a newer version is available for download
//                    logger.info("packageInfo.versionCode: " + packageInfo.versionCode);
//                    logger.info("Newest version available for download: " + applicationVersionGson.getVersionCode());
//                    if (packageInfo.versionCode < applicationVersionGson.getVersionCode()) {
//                        // Download the APK and install it
//                        downloadAndInstallApk(applicationVersionGson);
//                    }
//                } catch (PackageManager.NameNotFoundException e) {
//                    logger.info("The application is not installed: " + applicationGson.getPackageName());
//                    // Download the APK file and install it
//                    downloadAndInstallApk(applicationVersionGson);
//                }
//
//                publishProgress(++counter * 100 / applicationGsonList.size());
//            }

            // Update time of last synchronization
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putLong(MainActivity.PREF_LAST_SYNCHRONIZATION, Calendar.getInstance().getTimeInMillis()).commit();
        }

        return null;
    }

    private void downloadAndInstallApk(ApplicationVersionGson applicationVersionGson) {
        logger.info("downloadAndInstallApk");

        String fileUrl = EnvironmentSettings.getBaseUrl() + applicationVersionGson.getFileUrl() +
                "?deviceId=" + DeviceInfoHelper.getDeviceId(context) +
                "&checksum=" + ChecksumHelper.getChecksum(context) +
                "&locale=" + UserPrefsHelper.getLocale(context) +
                "&deviceModel=" + DeviceInfoHelper.getDeviceModel(context) +
                "&osVersion=" + Build.VERSION.SDK_INT +
                "&applicationId=" + DeviceInfoHelper.getApplicationId(context) +
                "&appVersionCode=" + DeviceInfoHelper.getAppVersionCode(context);
        logger.info("fileUrl: " + fileUrl);

        String fileName = applicationVersionGson.getApplication().getPackageName() + "-" + applicationVersionGson.getVersionCode() + ".apk";
        logger.info("fileName: " + fileName);

        File apkFile = ApkLoader.loadApk(fileUrl, fileName, context);
        logger.info("apkFile: " + apkFile);
        if ((apkFile == null) || !apkFile.exists()) {
            logger.error("APK download failed: " + fileUrl);
        } else {
            String command = "pm install -r " + apkFile.getAbsolutePath();
            logger.info("command: " + command);
            try {
                Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
                process.waitFor();
                // TODO: log process output

                String startCommand = applicationVersionGson.getStartCommand();
                if (!TextUtils.isEmpty(startCommand)) {
                    logger.info("startCommand: " + startCommand);
                    // TODO
                }
            } catch (IOException e) {
                logger.error("IOException: " + command, e);
            } catch (InterruptedException e) {
                logger.error("InterruptedException: " + command, e);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... percentage) {
        logger.info("onProgressUpdate");
        super.onProgressUpdate(percentage);

        int percentCompleted = percentage[0];
        logger.info("percentCompleted: " + percentCompleted + "%");
        Toast.makeText(context, "percentCompleted: " + percentCompleted + "%", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(List<ApplicationGson> applicationGsonList) {
        logger.info("onPostExecute");
        super.onPostExecute(applicationGsonList);

        Toast.makeText(context, "Synchronization complete!", Toast.LENGTH_SHORT).show();
    }
}
