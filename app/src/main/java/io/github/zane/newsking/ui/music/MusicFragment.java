package io.github.zane.newsking.ui.music;

import com.llf.basemodel.base.BaseFragment;

import io.github.zane.newsking.R;

/**
 * Created by Zane on 2016/4/2.
 * 音乐播放器实例
 */
public class MusicFragment extends BaseFragment {

    public static MusicFragment getInstance() {
        MusicFragment musicFragment = new MusicFragment();
        return musicFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void lazyFetchData() {

    }
}