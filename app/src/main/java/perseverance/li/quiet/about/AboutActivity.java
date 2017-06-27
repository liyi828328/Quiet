package perseverance.li.quiet.about;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseToolbarActivity;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/27 20:04
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/27 20 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class AboutActivity extends BaseToolbarActivity {

    @Override
    protected void onMenuHome() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.about_activity;
    }
}
