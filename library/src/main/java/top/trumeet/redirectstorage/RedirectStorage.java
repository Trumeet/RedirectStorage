package top.trumeet.redirectstorage;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

import top.trumeet.redirectstorage.wrapper.AbstractWrapper;

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
    private static final String TAG = RedirectStorage.class.getSimpleName();

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
            AbstractWrapper wrapper =
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
    public static File getRealPath () {
        try {
            AbstractWrapper wrapper = getInstalledWrapper();
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
            AbstractWrapper wrapper = getInstalledWrapper();
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
    private static AbstractWrapper getInstalledWrapper ()
    throws NoSuchMethodException, ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException{
        return getInstalledWrapper(getCurrentUserField());
    }

    /**
     * 获取已安装的 Wrapper
     * @param field CurrentUserField
     * @return 已安装的 Wrapper
     */
    private static AbstractWrapper getInstalledWrapper (Field field)
            throws IllegalAccessException {
        Environment.UserEnvironment o = (Environment.UserEnvironment) field.get(null);
        return o == null || !(o instanceof AbstractWrapper)
                ? null : (AbstractWrapper) o;
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
        AbstractWrapper wrapper = getInstalledWrapper(sCurrentUserField);
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
            AbstractWrapper abstractWrapper = AbstractWrapper.getWrapper(o,
                    target, user);
            if (abstractWrapper == null) {
                Log.e(TAG, "Can not create wrapper, it looks like not support your ROM: " +
                        Build.VERSION.SDK_INT);
                return;
            }
            sCurrentUserField.set(null, abstractWrapper);
        }
    }
}
