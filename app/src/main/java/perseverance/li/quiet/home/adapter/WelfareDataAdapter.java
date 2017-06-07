package perseverance.li.quiet.home.adapter;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseGankData;
import perseverance.li.quiet.home.adapter.WelfareDataAdapter.WelfareDataViewHolder;
import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/6 19:56
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/6 19 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WelfareDataAdapter extends RecyclerView.Adapter<WelfareDataViewHolder>
        implements View.OnClickListener {

    private static final int POS_TAG = R.id.welfare_image;
    private Activity mActivity;
    /**
     * 使用map来缓存view高度，防止view高度被重复变换导致闪屏
     */
    private Map<String, Integer> mHeights = new HashMap<>();
    private List<BaseGankData> mWelfareDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public WelfareDataAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void setDataList(List<BaseGankData> welfareDatas) {
        this.mWelfareDatas = welfareDatas;
        getRandomHeight();
    }

    @Override
    public WelfareDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.home_welfare_item, parent, false);
        WelfareDataViewHolder viewHolder = new WelfareDataViewHolder(view);
        return viewHolder;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(WelfareDataViewHolder holder, int position) {
        BaseGankData item = mWelfareDatas.get(position);
        if (item != null) {
            String url = item.url;
            ImageView view = holder.mWelfareImage;
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = mHeights.get(url);
            view.setLayoutParams(params);
            GlideUtil.displayUrl(mActivity, view, url, R.mipmap.img_default_gray);
        }

        //set item click
        holder.itemView.setTag(POS_TAG, position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mWelfareDatas != null ? mWelfareDatas.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag(POS_TAG));
        }
    }

    /**
     * 随机item的高度
     */
    private void getRandomHeight() {
        if (mWelfareDatas != null && mWelfareDatas.size() > 0) {
            for (int i = 0; i < mWelfareDatas.size(); i++) {
                BaseGankData baseGankData = mWelfareDatas.get(i);
                String url = baseGankData.url;
                if (url != null && !mHeights.containsKey(url)) {
                    mHeights.put(url, (int) (200 + Math.random() * 1000));
                }
            }
        }
    }

    class WelfareDataViewHolder extends RecyclerView.ViewHolder {

        private ImageView mWelfareImage;

        public WelfareDataViewHolder(View itemView) {
            super(itemView);
            mWelfareImage = (ImageView) itemView.findViewById(R.id.welfare_image);
        }
    }
}
