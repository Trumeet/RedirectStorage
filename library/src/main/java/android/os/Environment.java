package android.os;

import java.io.File;

/**
 * Created by Trumeet on 2017/9/8.
 * Just to pass compile
 * @author Trumeet
 */

public class Environment {
    public Environment() {
        throw new RuntimeException("Stub!");
    }

    public static File getRootDirectory() {
        throw new RuntimeException("Stub!");
    }

    public static File getDataDirectory() {
        throw new RuntimeException("Stub!");
    }

    public static File getExternalStorageDirectory() {
        throw new RuntimeException("Stub!");
    }

    public static File getExternalStoragePublicDirectory(String type) {
        throw new RuntimeException("Stub!");
    }

    public static File getDownloadCacheDirectory() {
        throw new RuntimeException("Stub!");
    }

    public static String getExternalStorageState() {
        throw new RuntimeException("Stub!");
    }

    /** @deprecated */
    @Deprecated
    public static String getStorageState(File path) {
        throw new RuntimeException("Stub!");
    }

    public static String getExternalStorageState(File path) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isExternalStorageRemovable() {
        throw new RuntimeException("Stub!");
    }

    public static boolean isExternalStorageRemovable(File path) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isExternalStorageEmulated() {
        throw new RuntimeException("Stub!");
    }

    public static boolean isExternalStorageEmulated(File path) {
        throw new RuntimeException("Stub!");
    }

    public static class UserEnvironment {
        private final int mUserId;

        /**
         * For Lollipop +
         * @param userId uid
         */
        public UserEnvironment(int userId) {
            mUserId = userId;
        }
        public File[] getExternalDirs() {
            throw new RuntimeException("Stub!");
        }
        @Deprecated
        public File getExternalStorageDirectory() {
            throw new RuntimeException("Stub!");
        }
        @Deprecated
        public File getExternalStoragePublicDirectory(String type) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStoragePublicDirs(String type) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAndroidDataDirs() {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAndroidObbDirs() {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAppDataDirs(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAppMediaDirs(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAppObbDirs(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAppFilesDirs(String packageName) {
            throw new RuntimeException("Stub!");
        }
        public File[] buildExternalStorageAppCacheDirs(String packageName) {
            throw new RuntimeException("Stub!");
        }
    }
}
