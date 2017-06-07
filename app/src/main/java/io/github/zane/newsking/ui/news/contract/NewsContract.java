package io.github.zane.newsking.ui.news.contract;

import com.llf.basemodel.base.BaseModel;
import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

import java.util.List;

import io.github.zane.newsking.entity.NewsEntity;
import io.github.zane.newsking.ui.news.NewsModel;

/**
 * Created by Zane on 2016/3/25.
 * 头条契约类
 */

public interface NewsContract {
    interface View extends BaseView {
        void returnData(List<NewsEntity> datas);
    }

    interface Presenter extends BasePresenter {
        void loadData(int type, int page);
    }

    interface Model extends BaseModel {
        void loadData(String url, int type, NewsModel.OnLoadFirstDataListener listener);
    }
}
