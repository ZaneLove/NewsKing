package io.github.zane.newsking.ui.video.presenter;

import com.llf.basemodel.utils.JsonUtils;
import com.llf.basemodel.utils.LogUtil;
import com.llf.basemodel.utils.OkHttpUtils;

import io.github.zane.newsking.entity.VideoEntity;
import io.github.zane.newsking.ui.video.contract.VideoContract;


/**
 * Created by Zane on 2016/4/11.
 * 浏览器能访问接口，客户端禁止访问是请求头的问题，需要加请求头
 */

public class VideoPresenter implements VideoContract.Presenter{
    private VideoContract.View mView;

    public VideoPresenter(VideoContract.View view){
        this.mView = view;
    }
    @Override
    public void start() {

    }

    @Override
    public void loadData(String url) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
           public void onSuccess(String response) {
                LogUtil.d("视频列表" + response);
                VideoEntity entity = JsonUtils.deserialize(response,VideoEntity.class);
                mView.returnData(entity.getTag());
            }

            @Override
            public void onFailure(Exception e) {
                mView.showErrorTip(e.getMessage());
            }
        });
    }
}
