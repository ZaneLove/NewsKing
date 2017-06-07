package io.github.zane.newsking.ui.girl.contract;

import android.content.Context;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

import java.util.List;

import io.github.zane.newsking.entity.JcodeEntity;

/**
 * Created by Zane on 2016/3/28.
 */

public interface GirlContract {
    interface View extends BaseView {
        void returnData(List<JcodeEntity> datas);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);

        void addRecord(Context context, JcodeEntity entity);
    }
}
