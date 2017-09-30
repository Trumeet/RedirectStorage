package top.trumeet.redirectstorage.wrapper;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import top.trumeet.redirectstorage.PathCallback;

/**
 * Created by Trumeet on 2017/9/15.
 * Wrapper for marshmallow+
 */

@TargetApi(Build.VERSION_CODES.M)
class WrapperMM extends AbstractWrapper {

    WrapperMM(Environment.UserEnvironment ue, int userId, PathCallback callback) {
        super(ue, userId, callback);
    }

    @Override
    public File[] getExternalDirs() {
        return buildExternalDirs(mEnable);
    }

    private File[] buildExternalDirs (boolean mEnable) {
        File[] dirs = mBase.getExternalDirs();
        if (!mEnable)
            return dirs;
        return convertDirs(dirs);
    }

    public File getRealExternalStorageDirectory () {
        return buildExternalDirs(false)[0];
    }

    @Override
    public File getExternalStorageDirectory() {
        return mBase.getExternalStorageDirectory();
    }

    @Override
    public File getExternalStoragePublicDirectory(String type) {
        return mBase.getExternalStoragePublicDirectory(type);
    }

    @Override
    public File[] buildExternalStoragePublicDirs(String type) {
        return mBase.buildExternalStoragePublicDirs(type);
    }

    @Override
    public File[] buildExternalStorageAndroidDataDirs() {
        return mBase.buildExternalStorageAndroidDataDirs();
    }

    @Override
    public File[] buildExternalStorageAndroidObbDirs() {
        return mBase.buildExternalStorageAndroidObbDirs();
    }

    @Override
    public File[] buildExternalStorageAppDataDirs(String packageName) {
        return mBase.buildExternalStorageAppDataDirs(packageName);
    }

    @Override
    public File[] buildExternalStorageAppMediaDirs(String packageName) {
        return mBase.buildExternalStorageAppMediaDirs(packageName);
    }

    @Override
    public File[] buildExternalStorageAppObbDirs(String packageName) {
        return mBase.buildExternalStorageAppObbDirs(packageName);
    }

    @Override
    public File[] buildExternalStorageAppFilesDirs(String packageName) {
        return mBase.buildExternalStorageAppFilesDirs(packageName);
    }

    @Override
    public File[] buildExternalStorageAppCacheDirs(String packageName) {
        return mBase.buildExternalStorageAppCacheDirs(packageName);
    }
}