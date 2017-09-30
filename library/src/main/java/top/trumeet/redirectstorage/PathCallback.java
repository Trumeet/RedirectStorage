package top.trumeet.redirectstorage;

import java.io.File;

/**
 * Created by Trumeet on 2017/9/30.
 * Callback to modify path
 * @author Trumeet
 */

public interface PathCallback {
    /**
     * 当需要修改目录时调用，您可以返回修改过的 File。
     * 当 Disabled 或获取真实路径的时候它不会被调用。
     * @param original Framework 获得的 File
     * @return 你修改的 File
     */
    File onModify (File original);
}
