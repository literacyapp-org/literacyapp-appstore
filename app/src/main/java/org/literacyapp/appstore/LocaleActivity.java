package org.literacyapp.appstore;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.log4j.Logger;
import org.literacyapp.appstore.util.RootHelper;
import org.literacyapp.model.enums.Locale;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LocaleActivity extends AppCompatActivity {

    public static final String PREF_LOCALE = "pref_locale";

    private Logger logger = Logger.getLogger(getClass());

    private Spinner mSpinnerLocale;

    private Button mButtonLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger.info("onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locale);

        mSpinnerLocale = (Spinner) findViewById(R.id.spinnerLocale);
        mButtonLocale = (Button) findViewById(R.id.buttonLocale);
    }

    @Override
    protected void onStart() {
        logger.info("onStart");
        super.onStart();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Locale locale : Locale.values()) {
            arrayAdapter.add(locale.toString());
        }

        mSpinnerLocale.setAdapter(arrayAdapter);

        mButtonLocale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.info("onClick");

                String localeAsString = mSpinnerLocale.getSelectedItem().toString();
                logger.info("localeAsString: " + localeAsString);
                Locale locale = Locale.valueOf(localeAsString);

                // Obtain permission to change system configuration
                boolean isSuccessConfigPermission = RootHelper.runAsRoot(new String[] {
                        "pm grant org.literacyapp.appstore android.permission.CHANGE_CONFIGURATION"
                });
                logger.info("isSuccessConfigPermission: " + isSuccessConfigPermission);
                if (!isSuccessConfigPermission) {
                    finish();
                    return;
                }

                // Set locale of device
                java.util.Locale deviceLocale = new java.util.Locale(locale.getLanguage());
                logger.info("deviceLocale: " + deviceLocale);
                try {
                    Class activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

                    Method defaultMethod = activityManagerNativeClass.getMethod("getDefault");
                    defaultMethod.setAccessible(true);
                    Object activityManagerNative = defaultMethod.invoke(activityManagerNativeClass);

                    Method configurationMethod = activityManagerNativeClass.getMethod("getConfiguration");
                    configurationMethod.setAccessible(true);

                    Configuration configuration = (Configuration) configurationMethod.invoke(activityManagerNative);
                    Class configurationClass = configuration.getClass();
                    Field field = configurationClass.getField("userSetLocale");
                    field.setBoolean(configuration, true);

                    configuration.locale = deviceLocale;

                    Method updateConfigurationMethod = activityManagerNativeClass.getMethod("updateConfiguration", Configuration.class);
                    updateConfigurationMethod.setAccessible(true);
                    updateConfigurationMethod.invoke(activityManagerNative, configuration);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString(PREF_LOCALE, locale.toString()).commit();
                    Toast.makeText(getApplicationContext(), "Changing locale of device to: " + deviceLocale, Toast.LENGTH_LONG).show();
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    logger.error(null, e);
                }

                finish();
            }
        });
    }
}
