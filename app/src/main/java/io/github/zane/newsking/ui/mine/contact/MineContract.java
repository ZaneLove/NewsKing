package io.github.zane.newsking.ui.mine.contact;

import com.llf.basemodel.base.BasePresenter;
import com.llf.basemodel.base.BaseView;

/**
 * Created by Zane on 2016/3/26.
 *
 */

public interface MineContract {
    interface View extends BaseView {
        void retureResult(String result);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
    }
}
