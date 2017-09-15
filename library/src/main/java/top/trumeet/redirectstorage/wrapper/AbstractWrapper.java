package top.trumeet.redirectstorage.wrapper;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trumeet on 2017/9/15.
 * @author Trumeet
 */

public abstract class AbstractWrapper extends Environment.UserEnvironment {
    public static AbstractWrapper getWrapper (Environment.UserEnvironment base,
                                              String customPath,
                                              Integer userId) {
        checkNonNull(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNonNull(userId);
            return new WrapperMM(base,
                    userId, customPath);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            checkNonNull(userId);
            return new WrapperKK(base,
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

    public final File[] convertDirs (File... dirs) {
        if (dirs == null || dirs.length == 0)
            return dirs;
        List<File> list = new ArrayList<>(dirs.length);
        for (File file : dirs) {
            list.add(new File(file.getAbsolutePath() + mCustomPath));
        }
        return list.toArray(new File[list.size()]);
    }
}
