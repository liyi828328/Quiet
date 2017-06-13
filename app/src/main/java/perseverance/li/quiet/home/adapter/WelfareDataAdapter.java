package perseverance.li.quiet.home.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import perseverance.li.quiet.R;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.base.BaseRVAdapter;
import perseverance.li.quiet.base.BaseRViewHolder;
import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/6 19:56
 * ---------------------------------------------------------------
 * Describe:
 * 福利数据Adapter
 * <p>
 * TODO：后续需要抽象
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/6 19 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WelfareDataAdapter extends BaseRVAdapter<BaseGankData> {

    private static final String TAG = "WelfareDataAdapter";
    private Activity mActivity;
    /**
     * 使用map来缓存view高度，防止view高度被重复变换导致闪屏
     */
    private Map<String, Integer> mHeights = new HashMap<>();

    public WelfareDataAdapter(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    @Override
    public void convert(BaseRViewHolder vh, BaseGankData baseGankData) {
        if (baseGankData != null) {
            ImageView welfareImage = vh.findViewById(R.id.welfare_image);
            String url = baseGankData.url;
            ViewGroup.LayoutParams params = welfareImage.getLayoutParams();
            params.height = mHeights.get(url);
            welfareImage.setLayoutParams(params);
            GlideUtil.displayUrl(mActivity, welfareImage, url, R.mipmap.img_default_gray);
        }
    }

    @Override
    public int getItemResId() {
        return R.layout.home_welfare_item;
    }

    @Override
    public void setDataList(List<BaseGankData> list) {
        super.setDataList(list);
        getRandomHeight();
    }

    /**
     * 随机item的高度
     */
    private void getRandomHeight() {
        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                BaseGankData baseGankData = mDataList.get(i);
                String url = baseGankData.url;
                if (url != null && !mHeights.containsKey(url)) {
                    mHeights.put(url, (int) (200 + Math.random() * 1000));
                }
            }
        }
    }
}
