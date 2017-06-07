package io.github.zane.newsking.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.llf.basemodel.base.BaseFragment;
import com.llf.basemodel.commonwidget.CircleImageView;
import com.llf.basemodel.dialog.ShareDialog;
import com.llf.basemodel.utils.ImageLoaderUtils;
import com.llf.basemodel.utils.LogUtil;
import com.llf.photopicker.ImgSelConfig;
import com.llf.photopicker.PickPhotoActivity;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.zane.newsking.R;
import io.github.zane.newsking.constant.AppConfig;
import io.github.zane.newsking.ui.mine.contact.MineContract;
import io.github.zane.newsking.ui.mine.presenter.MinePresenter;

/**
 * Created by Zane on 2016/3/25.
 * 我的
 */

public class MineFragment extends BaseFragment implements MineContract.View, IUiListener, ShareDialog.OneShare {
    @Bind(R.id.avatar)
    CircleImageView mAvatar;

    private static final int CHANGE_AVATAIR = 1;
    private MineContract.Presenter mPresenter;
    private Tencent mTencent;
    private IWXAPI iwxapi;

    public static MineFragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mPresenter = new MinePresenter(this);
        mTencent = Tencent.createInstance(AppConfig.APP_ID_QQ, getActivity());
        iwxapi = WXAPIFactory.createWXAPI(getActivity(), AppConfig.APP_ID_WEIXIN, false);
        iwxapi.registerApp(AppConfig.APP_ID_WEIXIN);
    }

    @Override
    protected void lazyFetchData() {

    }

    @OnClick({R.id.setting, R.id.attention, R.id.track, R.id.share, R.id.night, R.id.service, R.id.update, R.id.reply, R.id.avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.attention:
                startActivity(AttentionActivity.class);
                break;
            case R.id.track:
                startActivity(TrackActivity.class);
                break;
            case R.id.share:
                ShareDialog.show(getActivity(), this);
                break;
            case R.id.night:
                showToast("夜间模式");
                break;
            case R.id.service:
                showToast("客服中心");
                break;
            case R.id.update:
                mPresenter.checkUpdate("http://api.fir.im/apps/latest/58f87d50959d6904280005a3?api_token=9f2408863ff25abccca986e5d4d9d6ba");
                break;
            case R.id.reply:
                showToast("反馈");
                break;
            case R.id.avatar:
                PickPhotoActivity.startActivity(this, new ImgSelConfig.Builder().multiSelect(false).build(), CHANGE_AVATAIR);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e("回调");
        if (requestCode == CHANGE_AVATAIR && resultCode == Activity.RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(PickPhotoActivity.INTENT_RESULT);
            ImageLoaderUtils.loadingImg(getActivity(), mAvatar, result.get(0));
        }

        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
//        Luban.get(getActivity())
//                .load(File)                     //传人要压缩的图片
//                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
//                .setCompressListener(new OnCompressListener() { //设置回调
//
//                    @Override
//                    public void onStart() {
//                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                    }
//                    @Override
//                    public void onSuccess(File file) {
//                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // TODO 当压缩过去出现问题时调用
//                    }
//                }).launch();    //启动压缩
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorHint(msg);
    }

    @Override
    public void retureResult(String result) {
        showToast(result);
    }

    @Override
    public void onComplete(Object o) {
        showToast("qq分享成功");
    }

    @Override
    public void onError(UiError uiError) {
        showToast("qq分享出错" + uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        showToast("qq分享取消");
    }

    @Override
    public void weixinShare() {
        showToast("微信分享");
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "https://fir.im/6s7z";
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = "出大事了";
        msg.description = "这里有个好强大的app";
        Bitmap thump = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thump, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        iwxapi.sendReq(req);
    }

    @Override
    public void qqShare() {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "出大事了");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "这里有个好强大的app");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://fir.im/6s7z");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://avatar.csdn.net/B/0/1/1_new_one_object.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "秀趣");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(getActivity(), params, this);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
