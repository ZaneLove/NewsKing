package io.github.zane.newsking.ui.news.presenter;


import java.util.List;

import io.github.zane.newsking.api.Apis;
import io.github.zane.newsking.entity.NewsEntity;
import io.github.zane.newsking.ui.news.NewsFragment;
import io.github.zane.newsking.ui.news.NewsModel;
import io.github.zane.newsking.ui.news.contract.NewsContract;

/**
 * Created by Zane on 2016/3/25.
 * 新闻Presenter
 */

public class NewsPresenter implements NewsContract.Presenter, NewsModel.OnLoadFirstDataListener {
    private NewsContract.View view;
    private NewsContract.Model model;

    public NewsPresenter(NewsContract.View view) {
        this.view = view;
        this.model = new NewsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(int type, int page) {
        String url = getUrl(type, page);
        model.loadData(url, type, this);
    }

    @Override
    public void onSuccess(List<NewsEntity> list) {
        view.returnData(list);
    }

    @Override
    public void onFailure(String str, Exception e) {
        view.showErrorTip("您已进入没有网络的二次元");
    }

    private String getUrl(int type, int page) {
        StringBuilder sb=new StringBuilder();
        switch (type){
            case NewsFragment.ONE:
                sb.append(Apis.TOP_URL).append(Apis.TOP_ID);
                break;
            case NewsFragment.TWO:
                sb.append(Apis.COMMON_URL).append(Apis.NBA_ID);
                break;
            case NewsFragment.THREE:
                sb.append(Apis.COMMON_URL).append(Apis.CAR_ID);
                break;
            case NewsFragment.FOUR:
                sb.append(Apis.COMMON_URL).append(Apis.JOKE_ID);
                break;
            default:
                sb.append(Apis.TOP_URL).append(Apis.TOP_ID);
                break;
        }
        sb.append("/").append(page).append(Apis.END_URL);
        return sb.toString();
    }
}
