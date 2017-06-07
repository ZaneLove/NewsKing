package io.github.zane.newsking.ui.video.contract;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

import java.util.List;

import io.github.zane.newsking.entity.VideoEntity;

/**
 * Created by Zane on 2016/4/1.
 *
 */

public interface VideoContract {
    interface View extends BaseView {
        void returnData(List<VideoEntity.V9LG4CHORBean> datas);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);
    }
}
