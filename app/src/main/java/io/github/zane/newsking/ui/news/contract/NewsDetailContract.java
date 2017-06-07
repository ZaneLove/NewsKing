package io.github.zane.newsking.ui.news.contract;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

/**
 * Created by Zane on 2016/3/23.
 */

public interface NewsDetailContract {
    interface View extends BaseView {
        void showContent(String s);
    }

    interface Presenter extends BasePresenter {
        void loadContent(String s);
    }
}
