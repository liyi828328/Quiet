package perseverance.li.quiet.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 16-5-30 10:22
 * ---------------------------------------------------------------
 * Describe:
 * 权限工具类
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 16-5-30 10 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class PermissionUtil {

    private static final String TAG = "PermissionUtil";

    /**
     * 检查某项权限是否授权
     *
     * @param context
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        if (context == null || TextUtils.isEmpty(permission)) {
            return false;
        }
        Log.d(TAG, "check permission : " + permission);
        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(permission, context.getPackageName());
        return hasPerm == PackageManager.PERMISSION_GRANTED;
    }
}
