package perseverance.li.quiet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import perseverance.li.quiet.R;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/16 14:56
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/16 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BaseToolbarActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected ActionBar mActionbar;

    /**
     * ActionBar 返回键监听处理
     */
    protected abstract void onMenuHome();

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolbar();
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

}
