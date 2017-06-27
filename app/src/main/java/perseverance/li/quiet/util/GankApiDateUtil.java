package perseverance.li.quiet.util;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/2 15:37
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/2 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class GankApiDateUtil {

    private static final String TAG = "GankApiDateUtil";
    /**
     * 一天的时长
     */
    private static final long ONE_DAY = DateUtils.DAY_IN_MILLIS;

    /**
     * 下拉刷新的页数
     */
    private int mCurrentPagePosition;

    /**
     * 设置的初始日期
     */
    private Calendar mCalendar;

    public GankApiDateUtil(Calendar calendar) {
        this.mCalendar = calendar;
    }

    /**
     * 根据当前刷新的页数来往下计算日期
     *
     * @param position
     */
    public void setCurrentPagePosition(int position) {
        this.mCurrentPagePosition = position;
    }

    /**
     * 获取年
     *
     * @return
     */
    public int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public int getMonth() {
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public int getDay() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取每次刷新需要的天数
     *
     * @return
     */
    public List<GankApiDateUtil> getListTime() {
        List<GankApiDateUtil> listTime = new ArrayList<>();
        for (int i = 0; i < GankApi.DEFAULT_DAILY_SIZE; i++) {
            long time = this.mCalendar.getTimeInMillis() -
                    ((mCurrentPagePosition - 1) * GankApi.DEFAULT_DAILY_SIZE * ONE_DAY) - i * ONE_DAY;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            GankApiDateUtil date = new GankApiDateUtil(c);
            listTime.add(date);
        }
        return listTime;
    }
}
