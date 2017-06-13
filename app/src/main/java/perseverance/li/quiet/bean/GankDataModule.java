package perseverance.li.quiet.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import perseverance.li.quiet.bean.BaseGankData;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/6 15:54
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/6 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class GankDataModule {

    @SerializedName("results")
    public ArrayList<BaseGankData> results;
}
