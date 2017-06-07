package io.github.zane.newsking.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.llf.basemodel.base.BaseActivity;

import butterknife.Bind;
import io.github.zane.newsking.R;

/**
 * Created by Zane on 2016/3/24.
 * 设置
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setContentInsetStartWithNavigation(0);
    }
}
