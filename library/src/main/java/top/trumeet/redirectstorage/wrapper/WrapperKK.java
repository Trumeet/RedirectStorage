package top.trumeet.redirectstorage.wrapper;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by Trumeet on 2017/9/15.
 * @author Trumeet
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
class WrapperKK extends AbstractWrapper {
    WrapperKK(Environment.UserEnvironment ue, int userId, String customPath) {
        super(ue, userId, customPath);
    }

    @Override
    public File getRealExternalStorageDirectory() {
        return null;
    }

    private File convert (File dir) {
        if (!mEnable)
            return dir;
        return new File(convertDirs(dir)[0].getAbsolutePath());
    }

    private File[] convert (File[] dirs) {
        if (!mEnable)
            return dirs;
        return convertDirs(dirs);
    }

    @Deprecated
    public File getExternalStorageDirectory() {
        return convert(mBase.getExternalStorageDirectory());
    }

    @Deprecated
    public File getExternalStoragePublicDirectory(String type) {
        return buildExternalStoragePublicDirs(type)[0];
    }

    public File[] getExternalDirsForVold() {
        return convert(mBase.getExternalDirsForVold());
    }

    public File[] getExternalDirsForApp() {
        return convert(mBase.getExternalDirsForApp());
    }

    public File getMediaDir() {
        return convert(mBase.getMediaDir());
    }

    public File[] buildExternalStoragePublicDirs(String type) {
        return convert(mBase.buildExternalStoragePublicDirs(type));
    }

    public File[] buildExternalStorageAndroidDataDirs() {
        return convert(mBase.buildExternalStorageAndroidDataDirs());
    }

    public File[] buildExternalStorageAndroidObbDirs() {
        return convert(mBase.buildExternalStorageAndroidObbDirs());
    }

    public File[] buildExternalStorageAppDataDirs(String packageName) {
        return convert(mBase.buildExternalStorageAppDataDirs(packageName));
    }

    public File[] buildExternalStorageAppDataDirsForVold(String packageName) {
        return convert(mBase.buildExternalStorageAppDataDirsForVold(packageName));
    }

    public File[] buildExternalStorageAppMediaDirs(String packageName) {
        return convert(mBase.buildExternalStorageAppMediaDirs(packageName));
    }

    public File[] buildExternalStorageAppMediaDirsForVold(String packageName) {
        return convert(mBase.buildExternalStorageAppMediaDirsForVold(packageName));
    }

    public File[] buildExternalStorageAppObbDirs(String packageName) {
        return convert(mBase.buildExternalStorageAppObbDirs(packageName));
    }

    public File[] buildExternalStorageAppObbDirsForVold(String packageName) {
        return convert(mBase.buildExternalStorageAppObbDirsForVold(packageName));
    }

    public File[] buildExternalStorageAppFilesDirs(String packageName) {
        return convert(mBase.buildExternalStorageAppFilesDirs(packageName));
    }

    public File[] buildExternalStorageAppCacheDirs(String packageName) {
        return convert(mBase.buildExternalStorageAppCacheDirs(packageName));
    }
}
