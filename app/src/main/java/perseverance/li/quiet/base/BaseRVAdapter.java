package perseverance.li.quiet.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/9 10:43
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/9 10 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseRViewHolder> {

    protected List<T> mDataList;
    protected Activity mActivity;
    protected OnRecyclerViewItemClickListener mOnItemClickListener;

    public BaseRVAdapter(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 布局转换
     *
     * @param vh
     * @param t
     */
    public abstract void convert(BaseRViewHolder vh, T t);

    /**
     * 获取item id
     *
     * @return
     */
    public abstract int getItemResId();

    @Override
    public BaseRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(getItemResId(), parent, false);
        BaseRViewHolder viewHolder = new BaseRViewHolder(view);
        setItemClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRViewHolder holder, int position) {
        convert(holder, mDataList.get(position));
    }

    /**
     * 设置item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setDataList(List<T> list) {
        this.mDataList = list;
    }

    /**
     * 根据id获取数据
     *
     * @param position
     * @return
     */
    public T getDataByPosition(int position) {
        return mDataList.get(position);
    }

    /**
     * 设置item监听
     *
     * @param viewHolder
     */
    protected void setItemClickListener(final BaseRViewHolder viewHolder) {
        viewHolder.getConverView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }
}
