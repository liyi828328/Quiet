package perseverance.li.quiet.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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
public class GankDataModule implements Serializable {

    @SerializedName("results")
    public ArrayList<BaseGankData> results;
}
