package io.github.zane.newsking.ui.news.presenter;

import com.llf.basemodel.utils.LogUtil;
import com.llf.basemodel.utils.OkHttpUtils;

import io.github.zane.newsking.api.Apis;
import io.github.zane.newsking.entity.NewsDetialEntity;
import io.github.zane.newsking.tools.NewsJsonUtils;
import io.github.zane.newsking.ui.news.contract.NewsDetailContract;

/**
 * Created by Zane on 2016/3/23.
 *
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter{
    private NewsDetailContract.View mView;

    public NewsDetailPresenter(NewsDetailContract.View mView){
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadContent(final String s) {
        mView.showLoading();
        String detailUrl = getDetailUrl(s);
        OkHttpUtils.get(detailUrl, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("新闻详情" + response);
                mView.stopLoading();
                NewsDetialEntity newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, s);
                mView.showContent(newsDetailBean.getBody());
            }

            @Override
            public void onFailure(Exception e) {
                mView.stopLoading();
            }
        });
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Apis.NEW_DETAIL);
        sb.append(docId).append(Apis.END_DETAIL_URL);
        return sb.toString();
    }
}
