package perseverance.li.quiet.base;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/27 10:00
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/27 10 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BasePresenter<T extends IBaseView> {

    private static final String TAG = "BasePresenter";
    private T mView;
    private CompositeDisposable mCompositeDisposable;

    /**
     * 创建
     *
     * @param view
     */
    public void create(T view) {
        attachView(view);
        mCompositeDisposable = new CompositeDisposable();
    }

    private void attachView(T view) {
        this.mView = view;
    }

    private void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    /**
     * 返回对应的view的接口
     *
     * @return
     */
    public T getBaseView() {
        return mView;
    }

    /**
     * 管理observer订阅
     *
     * @param disposable
     */
    public void addDispoable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    /**
     * 清除observer订阅
     */
    public void clearDispoable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * Activity退出，销毁引用等，防止内存泄露
     */
    public void destory() {
        detachView();
        //将所有的observer取消订阅
        mCompositeDisposable.clear();
        mCompositeDisposable = null;

        Log.d(TAG, "BasePresenter destory mCompositeDisposable: " + mCompositeDisposable);
    }

}
