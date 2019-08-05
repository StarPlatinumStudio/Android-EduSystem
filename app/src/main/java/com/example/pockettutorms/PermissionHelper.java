package com.example.pockettutorms;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Hashtable;
import java.util.Map;

/**
 * Android6.0以上权限申请工具类
 */
public class PermissionHelper {

    /** 权限申请通过之后执行的runnable */
    private static Map<Integer, Runnable> mRunnableOnPermissionGranted = new Hashtable<>();
    /** 权限申请不通过之后执行的runnable */
    private static Map<Integer, Runnable> mRunnableOnPermissionDenied = new Hashtable<>();
    private static int mRequestCode = 0;

    /**
     * 判断指定权限是否已经授权
     * @param permissions 详情见：{@link android.Manifest.permission} 如：android.Manifest.permission.xxx
     */
    public static boolean isPermissionGranted(Context context, String... permissions) {
        // 这里规定所有权限均通过才算通过
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在某个权限下运行某些代码
     * 注意：必须在AndroidManifest.xml中先声明需要申请的权限（有些不需要声明，有些必须声明，所以建议全部声明）。
     * @param permissions      android.Manifest.permission.xxx
     * @param actionOnGranted 在权限被授权的情况运行的代码
     * @param actionOnDenied  在权限被拒绝的情况下运行的代码
     */
    public static void runOnPermissionGranted(Activity activity, Runnable actionOnGranted, Runnable actionOnDenied, String... permissions) {
        if (isPermissionGranted(activity, permissions)) {
            new Handler(Looper.getMainLooper()).post(actionOnGranted);
        } else {
            mRunnableOnPermissionGranted.put(mRequestCode, actionOnGranted);
            mRunnableOnPermissionDenied.put(mRequestCode, actionOnDenied);
            ActivityCompat.requestPermissions(activity, permissions, mRequestCode);
            mRequestCode++;
        }
    }

    /**
     * 在 {@link Activity#onRequestPermissionsResult(int, String[], int[])} 中调用。
     * 如：
     * <pre>
     * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     *      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     *      PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
     * }
     * </pre>
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRunnableOnPermissionGranted.containsKey(requestCode)) {
            // 这里规定全部权限都通过才算通过
            boolean grant = true;
            // 在A申请权限，然后马上跳转到B，则grantResults.length=0
            if (grantResults.length == 0) grant = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    grant = false;
                }
            }
            if (grant) {
                new Handler(Looper.getMainLooper()).post(mRunnableOnPermissionGranted.get(requestCode));
            } else {
                new Handler(Looper.getMainLooper()).post(mRunnableOnPermissionDenied.get(requestCode));
            }
            mRunnableOnPermissionGranted.remove(mRequestCode);
            mRunnableOnPermissionDenied.remove(mRequestCode);
        }
    }
}
