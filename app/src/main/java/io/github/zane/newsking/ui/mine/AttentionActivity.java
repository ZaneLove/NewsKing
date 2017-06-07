package io.github.zane.newsking.ui.mine;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.llf.basemodel.utils.GuiUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.zane.newsking.R;

/**
 * Created by Zane on 2016/3/26.
 * 关注
 */

public class AttentionActivity extends AppCompatActivity {
    @Bind(R.id.container)
    RelativeLayout mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        ButterKnife.bind(this);

        mContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealShow();
                }
            }
        });
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, mContainer,
                0, R.color.colorPrimary,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        GuiUtils.animateRevealHide(
                this, mContainer,
                0, R.color.colorPrimary,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    // 默认回退
    private void defaultBackPressed() {
        super.onBackPressed();
    }
}
