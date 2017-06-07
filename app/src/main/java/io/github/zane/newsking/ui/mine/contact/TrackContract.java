package io.github.zane.newsking.ui.mine.contact;

import android.content.Context;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

import java.util.List;

import io.github.zane.newsking.entity.JcodeEntity;

/**
 * Created by Zane on 2016/3/31.
 */

public interface TrackContract {
    interface View extends BaseView {
        void returnData(List<JcodeEntity> datas);
        void retureResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void loadData(Context context);
        void deleteData(Context context, String title);
    }
}
