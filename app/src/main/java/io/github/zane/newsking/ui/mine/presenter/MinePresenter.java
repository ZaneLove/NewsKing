package io.github.zane.newsking.ui.mine.presenter;

import com.llf.basemodel.utils.AppInfoUtil;
import com.llf.basemodel.utils.DownloadUtil;
import com.llf.basemodel.utils.JsonUtils;
import com.llf.basemodel.utils.LogUtil;
import com.llf.basemodel.utils.OkHttpUtils;

import io.github.zane.newsking.App;
import io.github.zane.newsking.entity.ApplicationEntity;
import io.github.zane.newsking.ui.mine.contact.MineContract;

/**
 * Created by Zane on 2016/3/21.
 */

public class MinePresenter implements MineContract.Presenter {
    private MineContract.View mView;

    public MinePresenter(MineContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkUpdate(String url) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("版本更新" + response);
                ApplicationEntity entity = JsonUtils.deserialize(response, ApplicationEntity.class);
                if (AppInfoUtil.getVersionCode(App.instance) < Integer.parseInt(entity.getVersion())) {
                    DownloadUtil.downloadApk(App.instance, entity.getInstall_url(), entity.getName(), entity.getChangelog(), "xiuqu");
                } else {
                    mView.retureResult("当前已是最新版本");
                }
            }

            @Override
            public void onFailure(Exception e) {
                mView.retureResult(e.getMessage());
            }
        });
    }
}
