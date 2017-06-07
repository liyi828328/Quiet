package perseverance.li.quiet.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseGankData;
import perseverance.li.quiet.home.model.DailyModule;
import perseverance.li.quiet.home.model.DailyModule.DailyResults;
import perseverance.li.quiet.util.Constant;
import perseverance.li.quiet.util.GlideUtil;
import perseverance.li.quiet.home.adapter.DailyDataAdapter.DailyDataViewHolder;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/2 14:06
 * ---------------------------------------------------------------
 * Describe:
 * 每日数据Adapter
 *
 * TODO：后续需要抽象
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/2 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyDataAdapter extends RecyclerView.Adapter<DailyDataViewHolder> implements View.OnClickListener {

    private static final int POS_TAG = R.id.icon_id;
    private Activity mActivity;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private List<DailyModule> mDailyModules;

    public DailyDataAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void setDataList(List<DailyModule> dailyModules) {
        this.mDailyModules = dailyModules;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public DailyDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.home_daily_item, parent, false);
        DailyDataViewHolder viewHolder = new DailyDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DailyDataViewHolder holder, int position) {
        DailyModule dailyModule = mDailyModules.get(position);
        DailyResults results = dailyModule.results;
        //load image
        ArrayList<BaseGankData> welfareDatas = results.welfareData;
        if (welfareDatas != null && welfareDatas.size() > 0) {
            BaseGankData gankData = welfareDatas.get(0);
            if (gankData != null) {
                String imageUrl = gankData.url;
                if (!TextUtils.isEmpty(imageUrl)) {
                    GlideUtil.displayUrl(mActivity, holder.mIconView, imageUrl, R.mipmap.img_default_gray);
                }
            }
        }
        //set title
        ArrayList<BaseGankData> videoDatas = results.videoData;
        if (videoDatas != null && videoDatas.size() > 0) {
            BaseGankData gankData = videoDatas.get(0);
            if (gankData != null) {
                holder.mTitle.setText(gankData.desc);
                try {
                    //set date
                    SimpleDateFormat sdf = new SimpleDateFormat(Constant.DAILY_DATE_FORMAT);
                    holder.mDate.setText(sdf.format(gankData.publishedAt));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //set item click
        holder.itemView.setTag(POS_TAG, position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mDailyModules != null ? mDailyModules.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag(POS_TAG));
        }
    }

    class DailyDataViewHolder extends ViewHolder {

        private ImageView mIconView;
        private TextView mTitle;
        private TextView mDate;

        public DailyDataViewHolder(View itemView) {
            super(itemView);
            mIconView = (ImageView) itemView.findViewById(R.id.icon_id);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }

    }
}
