package perseverance.li.quiet.base;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import perseverance.li.quiet.R;
import perseverance.li.quiet.util.GlideUtil;
import perseverance.li.quiet.util.PermissionUtil;
import perseverance.li.quiet.util.ToastUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/27 17:29
 * ---------------------------------------------------------------
 * Describe:
 * Activity 基类
 * <p>
 * 初始化顺序  init > initToolbar > initView
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/27 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    private static final String TAG = "BaseActivity";
    /**
     * 权限请求Code
     */
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 101;
    protected T mPresenter;
    protected Toolbar mToolbar;
    protected ActionBar mActionbar;

    /**
     * 获取所绑定的presenter
     *
     * @return
     */
    public abstract T getPresenter();

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * ActionBar 返回键监听处理
     */
    protected abstract void onMenuHome();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        checkPermission();
        init();
        initToolbar();
        initView();
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.create(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onMenuHome();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        ToastUtil.hideToast();
        GlideUtil.clearGlideMemory(this);
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        int version = Build.VERSION.SDK_INT;
        Log.d(TAG, "sdk version: " + version);
        if (version < Build.VERSION_CODES.M) {
            Log.d(TAG, "sdk version < 23 , Do not check permissions");
            return;
        }
        List<String> permissionList = new ArrayList<>();
        //storage
        if (!PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionList.size() > 0) {
            requestPermissions(permissionList.toArray(new String[permissionList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            boolean result = true;
            List<String> needPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    result = false;
                    String permission = permissions[i];
                    if (!shouldShowRequestPermissionRationale(permission)) {
                        Log.d(TAG, "need permission permission: " + permission + " size: " + needPermissionList.size());
                        needPermissionList.add(permission);
                    }
                }
            }
            Log.d(TAG, "need permission list size: " + needPermissionList.size());
            if (needPermissionList.size() > 0) {
                showMessageOKCancel();
                return;
            }
            Log.d(TAG, "permissions result: " + result);
            if (!result) {
                ToastUtil.showShort(this, R.string.check_permission_result_toast);
                finish();
            }
        }
    }

    /**
     * 弹出授权对话框
     */
    private void showMessageOKCancel() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.need_permission_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
