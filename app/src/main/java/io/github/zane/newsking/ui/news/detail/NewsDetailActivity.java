package io.github.zane.newsking.ui.news.detail;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.llf.basemodel.base.BaseActivity;
import com.llf.basemodel.utils.ImageLoaderUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import io.github.zane.newsking.R;
import io.github.zane.newsking.entity.NewsEntity;
import io.github.zane.newsking.ui.news.contract.NewsDetailContract;
import io.github.zane.newsking.ui.news.presenter.NewsDetailPresenter;

/**
 * Created by Zane on 2016/3/24.
 * 新闻详情
 */

public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View{
    @Bind(R.id.ivImage)
    ImageView mIvImage;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.htNewsContent)
    HtmlTextView mHtNewsContent;

    private NewsEntity mEntity;//详情数据
    private NewsDetailContract.Presenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mPresenter = new NewsDetailPresenter(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //通过 NavigationDrawer 打开关闭 抽屉---返回
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });

        mEntity = (NewsEntity) getIntent().getSerializableExtra("news");
        mCollapsingToolbar.setTitle(mEntity.getTitle());
        ImageLoaderUtils.loadingImg(getApplicationContext(), mIvImage, mEntity.getImgsrc());
        mPresenter.loadContent(mEntity.getDocid());
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorHint(msg);
    }

    @Override
    public void showContent(String s) {
        mHtNewsContent.setHtmlFromString(s, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
