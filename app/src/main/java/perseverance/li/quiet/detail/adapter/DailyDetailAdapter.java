package perseverance.li.quiet.detail.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseRVAdapter;
import perseverance.li.quiet.base.BaseRViewHolder;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.util.GankApi;
import perseverance.li.quiet.util.GlideUtil;
import perseverance.li.quiet.widget.WelfareImageView;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/27 09:38
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/27 09 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyDetailAdapter extends BaseRVAdapter<BaseGankData> {

    public DailyDetailAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public void convert(BaseRViewHolder vh, BaseGankData baseGankData) {
        LinearLayout content = vh.findViewById(R.id.dailt_content);
        content.removeAllViews();
        String type = baseGankData.type;
        if (GankApi.DATA_TYPE_WELFARE.equals(type)) {
            FrameLayout welfareLayout = (FrameLayout) LayoutInflater.from(mActivity).inflate(R.layout.daily_detail_welfare_item, null);
            WelfareImageView welfareItem = (WelfareImageView) welfareLayout.findViewById(R.id.welfare_image);
            GlideUtil.displayUrl(mActivity, welfareItem, baseGankData.url);
            content.addView(welfareLayout);
        } else {
            View textViewLayout = LayoutInflater.from(mActivity).inflate(R.layout.daily_detail_text_item, null);
            TextView titleView = (TextView) textViewLayout.findViewById(R.id.title);
            TextView authorView = (TextView) textViewLayout.findViewById(R.id.author);
            TextView typeView = (TextView) textViewLayout.findViewById(R.id.type);

            titleView.setText(baseGankData.desc);
            authorView.setText(baseGankData.who);
            int typeColor = mActivity.getResources().getColor(android.R.color.holo_blue_light, null);
            if (GankApi.DATA_TYPE_ANDROID.equals(type)) {
                typeColor = mActivity.getResources().getColor(android.R.color.holo_red_light, null);
            } else if (GankApi.DATA_TYPE_IOS.equals(type)) {
                typeColor = mActivity.getResources().getColor(android.R.color.holo_green_light, null);
            } else if (GankApi.DATA_TYPE_JS.equals(type)) {
                typeColor = mActivity.getResources().getColor(android.R.color.holo_orange_light, null);
            }
            typeView.setTextColor(typeColor);
            typeView.setText(baseGankData.type);
            content.addView(textViewLayout);
        }
    }

    @Override
    public int getItemResId() {
        return R.layout.daily_detail_item;
    }
}
