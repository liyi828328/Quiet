package perseverance.li.quiet.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import perseverance.li.quiet.R;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/27 11:21
 * ---------------------------------------------------------------
 * Describe:
 * 自定义ImageView，重新计算计算宽高
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/27 11 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WelfareImageView extends ImageView {

    /**
     * 宽度固定，高度 = 宽度 * mImageViewRatio
     */
    private static final int WIDTH_HEIGHT = 1;
    /**
     * 高度固定，宽度 = 高度 * mImageViewRatio
     */
    private static final int HEIGHT_WIDTH = 2;
    private static final float DEFAULT_RATIO = 1.0f;
    private int mRadioType;
    private float mImageViewRatio;

    public WelfareImageView(Context context) {
        super(context);
    }

    public WelfareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WelfareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public WelfareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WelfareImageView);
        mRadioType = typedArray.getInteger(R.styleable.WelfareImageView_imageRatioType,
                WIDTH_HEIGHT);
        mImageViewRatio = typedArray.getFloat(R.styleable.WelfareImageView_imageRatio, DEFAULT_RATIO);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRadioType > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (mRadioType == WIDTH_HEIGHT && width > 0) {
                height = (int) ((float) width * mImageViewRatio);
            } else if (mRadioType == HEIGHT_WIDTH && height > 0) {
                width = (int) ((float) height * mImageViewRatio);
            }
            this.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
