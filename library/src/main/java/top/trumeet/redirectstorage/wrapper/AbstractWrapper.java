package top.trumeet.redirectstorage.wrapper;

import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by Trumeet on 2017/9/15.
 */

public abstract class AbstractWrapper extends Environment.UserEnvironment {
    public static AbstractWrapper getWrapper (Environment.UserEnvironment base,
                                              String customPath,
                                              Integer userId) {
        checkNonNull(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNonNull(userId);
            return new UserEnvironmentWrapperMarshmallow(base,
                    userId, customPath);
        }
        return null;
    }

    private static void checkNonNull (Object object) {
        if (object == null)
            throw new NullPointerException();
    }

    final Environment.UserEnvironment mBase;
    String mCustomPath;
    boolean mEnable = true;

    public AbstractWrapper(Environment.UserEnvironment ue,
                                             int userId, String customPath) {
        super(userId);
        mBase = ue;
        mCustomPath = customPath;
    }

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

    public abstract File getRealExternalStorageDirectory ();
}
