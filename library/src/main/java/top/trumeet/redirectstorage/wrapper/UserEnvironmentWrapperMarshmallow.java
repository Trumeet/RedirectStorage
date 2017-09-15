package top.trumeet.redirectstorage.wrapper;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trumeet on 2017/9/15.
 * Wrapper for marshmallow+
 */

class UserEnvironmentWrapperMarshmallow extends AbstractWrapper {

    UserEnvironmentWrapperMarshmallow(Environment.UserEnvironment ue, int userId, String customPath) {
        super(ue, userId, customPath);
    }

    @Override
    public File[] getExternalDirs() {
        return buildExternalDirs(mEnable);
    }

    private File[] buildExternalDirs (boolean mEnable) {
        File[] dirs = mBase.getExternalDirs();
        if (!mEnable)
            return dirs;
        if (dirs == null || dirs.length == 0)
            return dirs;
        List<File> list = new ArrayList<>(dirs.length);
        for (File file : dirs) {
            list.add(new File(file.getAbsolutePath() + mCustomPath));
        }
        return list.toArray(new File[list.size()]);
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