package top.trumeet.redirectstorage;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trumeet on 2017/9/8.
 * 重定向 {@link Environment#getExternalStorageDirectory()}
 * 结果的工具类，以防第三方SDK（尤其阿里系）乱改SD卡和读取用户数据。
 *
 * 需要时使用 {@link RedirectStorage#enable(String)} 启用
 * 不需要时使用 {@link RedirectStorage#disable()} 禁用
 * 如果需要获取没有重定向的真实目录，请使用 {@link RedirectStorage#getRealPath()}
 *
 * 特别感谢 little_cup 提供核心代码，我仅仅做了个简单封装和修改
 * @author Trumeet
 */

@SuppressWarnings("unused")
public class RedirectStorage {
    /**
     * 安装并启用。
     * @param pathSuffix 合并到原目录后面的路径。比如说要重定向到
     *                   SD卡/ABC，那么传递 ABC。
     */
    public static void enable (String pathSuffix) {
        try {
            invokeEnvironmentSdcardMethod(pathSuffix);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 禁用，不会卸载，只是禁用它
     */
    public static void disable () {
        try {
            UserEnvironmentWrapper wrapper =
                    getInstalledWrapper();
            if (wrapper != null)
                wrapper.setEnable(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取真实目录，比如需要保存图片等
     * @return 真实存储卡目录
     */
    @NonNull
    public static File getRealPath () {
        try {
            UserEnvironmentWrapper wrapper = getInstalledWrapper();
            if (wrapper != null)
                return wrapper.getRealExternalStorageDirectory();
            return Environment.getExternalStorageDirectory();
        } catch (Exception e) {
            e.printStackTrace();
            return Environment.getExternalStorageDirectory();
        }
    }

    public static boolean isEnable () {
        try {
            UserEnvironmentWrapper wrapper = getInstalledWrapper();
            return wrapper != null && wrapper.isEnable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取需要反射的 Field
     * @return Field
     */
    private static Field getCurrentUserField ()
            throws NoSuchMethodException, ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException{
        Class envClazz = Class.forName(android.os.Environment.class.getName());
        android.os.Environment.getExternalStorageDirectory();
        Field sCurrentUserField = envClazz.getDeclaredField("sCurrentUser");
        sCurrentUserField.setAccessible(true);
        return sCurrentUserField;
    }

    /**
     * 获取已安装的 Wrapper
     * @return 已安装的 Wrapper
     */
    @Nullable
    private static UserEnvironmentWrapper getInstalledWrapper ()
    throws NoSuchMethodException, ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException{
        return getInstalledWrapper(getCurrentUserField());
    }

    /**
     * 获取已安装的 Wrapper
     * @param field CurrentUserField
     * @return 已安装的 Wrapper
     */
    private static UserEnvironmentWrapper getInstalledWrapper (Field field)
            throws IllegalAccessException {
        Environment.UserEnvironment o = (Environment.UserEnvironment) field.get(null);
        return o == null || !(o instanceof UserEnvironmentWrapper)
                ? null : (UserEnvironmentWrapper) o;
    }

    /**
     * 判断是否已安装
     * @return 是否安装
     */
    public static boolean isInstall () {
        try {
            return getInstalledWrapper() != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeEnvironmentSdcardMethod(String target)
            throws NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field sCurrentUserField = getCurrentUserField();
        UserEnvironmentWrapper wrapper = getInstalledWrapper(sCurrentUserField);
        if (wrapper != null) {
            // Update current wrapper
            wrapper.setCustomPath(target);
            wrapper.setEnable(true);
        } else {
            // Install new wrapper
            Environment.UserEnvironment o = (Environment.UserEnvironment) sCurrentUserField
                    .get(null);
            int user = 0;
            try {
                Field mUserIdField = o.getClass().getDeclaredField("mUserId");
                mUserIdField.setAccessible(true);
                user = mUserIdField.getInt(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sCurrentUserField.set(null, new UserEnvironmentWrapper(o,
                    user
                    , target));
        }
    }

    private static class UserEnvironmentWrapper extends Environment.UserEnvironment {

        private final Environment.UserEnvironment mBase;
        private String mCustomPath;
        private boolean mEnable = true;

        public boolean isEnable() {
            return mEnable;
        }

        public void setEnable(boolean mEnable) {
            this.mEnable = mEnable;
        }

        public String getCustomPath() {
            return mCustomPath;
        }

        public void setCustomPath(String mCustomPath) {
            this.mCustomPath = mCustomPath;
        }

        public UserEnvironmentWrapper(Environment.UserEnvironment ue,
                                      int userId, String customPath) {
            super(userId);
            mBase = ue;
            mCustomPath = customPath;
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
}
