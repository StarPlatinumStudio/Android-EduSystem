package com.example.pockettutorms;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {

    /**
     * 在某个权限下运行某些代码
     * 注意：必须在AndroidManifest.xml中先声明需要申请的权限（有些不需要声明，有些必须声明，所以建议全部声明）。
     * @param permissions     android.Manifest.permission.xxx
     * @param actionOnGranted 在权限被授权的情况运行的代码
     * @param actionOnDenied  在权限被拒绝的情况下运行的代码
     */
    public static void runOnPermissionGranted(Activity activity, Runnable actionOnGranted, Runnable actionOnDenied, String... permissions) {
        PermissionHelper.runOnPermissionGranted(activity, actionOnGranted, actionOnDenied, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
