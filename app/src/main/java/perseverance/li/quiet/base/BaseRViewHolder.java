package perseverance.li.quiet.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/9 11:33
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/9 11 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class BaseRViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseRViewHolder";
    private View mItemView;
    /**
     * 缓存view
     */
    private SparseArray<View> mViewList;

    public BaseRViewHolder(View itemView) {
        super(itemView);
        this.mViewList = new SparseArray<>();
        this.mItemView = itemView;
    }

    /**
     * 通过id获取view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = mViewList.get(viewId);
        Log.d(TAG, "BaseRViewHolder findViewById view: " + view);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViewList.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 返回item的view对象
     *
     * @return
     */
    public View getConverView() {
        return mItemView;
    }
}
