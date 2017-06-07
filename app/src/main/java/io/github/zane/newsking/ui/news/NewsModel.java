package io.github.zane.newsking.ui.news;

import com.llf.basemodel.utils.LogUtil;
import com.llf.basemodel.utils.OkHttpUtils;

import java.util.List;

import io.github.zane.newsking.api.Apis;
import io.github.zane.newsking.entity.NewsEntity;
import io.github.zane.newsking.tools.NewsJsonUtils;
import io.github.zane.newsking.ui.news.contract.NewsContract;

/**
 * Created by Zane on 2016/3/25.
 *
 */

public class NewsModel implements NewsContract.Model{
    @Override
    public void loadData(String url, final int type, final OnLoadFirstDataListener listener) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("新闻列表" + response);
                List<NewsEntity> dataBeans = NewsJsonUtils.readJsonDataBeans(response, getID(type));
                listener.onSuccess(dataBeans);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news data",e);
            }
        });
    }

    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.ONE:
                id = Apis.TOP_ID;
                break;
            case NewsFragment.TWO:
                id = Apis.NBA_ID;
                break;
            case NewsFragment.THREE:
                id = Apis.CAR_ID;
                break;
            case NewsFragment.FOUR:
                id = Apis.JOKE_ID;
                break;
            default:
                id = Apis.TOP_ID;
                break;
        }
        return id;
    }

    public interface OnLoadFirstDataListener{
        void  onSuccess(List<NewsEntity> list);
        void  onFailure(String str, Exception e);
    }

}
