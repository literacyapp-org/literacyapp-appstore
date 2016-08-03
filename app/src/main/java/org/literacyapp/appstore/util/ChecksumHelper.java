package org.literacyapp.appstore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.apache.log4j.Logger;
import org.literacyapp.appstore.PasswordActivity;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumHelper {

    private static Logger logger = Logger.getLogger(ChecksumHelper.class);

    public static String getChecksum(Context context) {
        String checksum = null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String password = sharedPreferences.getString(PasswordActivity.PREF_PASSWORD, null);
        if (TextUtils.isEmpty(password)) {
            throw new RuntimeException("Password needs to be set before calling this method");
        } else {
            String deviceId = DeviceInfoHelper.getDeviceId(context);
            String input = deviceId + password;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] md5AsBytes = messageDigest.digest(input.getBytes("UTF-8"));
                checksum = new BigInteger(1, md5AsBytes).toString(16);
            } catch (NoSuchAlgorithmException e) {
                logger.error(null, e);
            } catch (UnsupportedEncodingException e) {
                logger.error(null, e);
            }
        }

        return checksum;
    }
}
