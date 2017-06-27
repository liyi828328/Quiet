package perseverance.li.quiet.home.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import perseverance.li.quiet.R;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.base.BaseRVAdapter;
import perseverance.li.quiet.base.BaseRViewHolder;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.bean.DailyModule.DailyResults;
import perseverance.li.quiet.util.Constant;
import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/2 14:06
 * ---------------------------------------------------------------
 * Describe:
 * 每日数据Adapter
 * <p>
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/2 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyDataAdapter extends BaseRVAdapter<DailyModule> {

    private String mCurrentDate;

    public DailyDataAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public int getItemResId() {
        return R.layout.home_daily_item;
    }

    @Override
    public void convert(BaseRViewHolder vh, DailyModule dailyModule) {
        DailyResults results = dailyModule.results;
        ImageView iconView = vh.findViewById(R.id.icon_id);
        TextView title = vh.findViewById(R.id.title);
        TextView date = vh.findViewById(R.id.date);
        //load image
        ArrayList<BaseGankData> welfareDatas = results.welfareData;
        if (welfareDatas != null && welfareDatas.size() > 0) {
            BaseGankData gankData = welfareDatas.get(0);
            if (gankData != null) {
                String imageUrl = gankData.url;
                if (!TextUtils.isEmpty(imageUrl)) {
                    GlideUtil.displayUrl(mActivity, iconView, imageUrl);
                }
            }
        }
        //set title
        ArrayList<BaseGankData> videoDatas = results.videoData;
        if (videoDatas != null && videoDatas.size() > 0) {
            BaseGankData gankData = videoDatas.get(0);
            if (gankData != null) {
                title.setText(gankData.desc);
                try {
                    //set date
                    SimpleDateFormat sdf = new SimpleDateFormat(Constant.DAILY_DATE_FORMAT);
                    mCurrentDate = sdf.format(gankData.publishedAt);
                    date.setText(mCurrentDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDate() {
        return mCurrentDate;
    }
}
