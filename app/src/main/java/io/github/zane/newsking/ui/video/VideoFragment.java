package io.github.zane.newsking.ui.video;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.llf.basemodel.base.BaseFragment;
import com.llf.basemodel.commonwidget.CircleImageView;
import com.llf.basemodel.recycleview.BaseAdapter;
import com.llf.basemodel.recycleview.BaseViewHolder;
import com.llf.basemodel.recycleview.EndLessOnScrollListener;
import com.llf.basemodel.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.zane.newsking.R;
import io.github.zane.newsking.api.Apis;
import io.github.zane.newsking.entity.VideoEntity;
import io.github.zane.newsking.ui.video.contract.VideoContract;
import io.github.zane.newsking.ui.video.presenter.VideoPresenter;

/**
 * Created by Zane on 2017/4/15.
 * 视频
 */

public class VideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, VideoContract.View {
    public static VideoFragment getInstance() {
        VideoFragment videoFragment = new VideoFragment();
        return videoFragment;
    }

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private BaseAdapter mAdapter;
    private List<VideoEntity.V9LG4CHORBean> videos = new ArrayList<>();
    private int pageIndex = 0;
    private VideoContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        mPresenter = new VideoPresenter(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BaseAdapter<VideoEntity.V9LG4CHORBean>(getActivity(), R.layout.item_video, videos) {
            @Override
            public void convert(BaseViewHolder viewHolder, VideoEntity.V9LG4CHORBean item) {
                viewHolder.setText(R.id.title, item.getTitle());
                viewHolder.setText(R.id.source, item.getVideosource());
                ImageView imageView = viewHolder.getView(R.id.cover);
                CircleImageView circleImageView = viewHolder.getView(R.id.topicImg);
                ImageLoaderUtils.loadingImg(getActivity(), imageView, item.getCover());
                ImageLoaderUtils.loadingImg(getActivity(), circleImageView, item.getTopicImg());
            }
        };
        mAdapter.addFooterView(R.layout.layout_footer);
        mAdapter.setOnItemClickLitener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BaseViewHolder viewHolder) {
                VideoDetailActivity.launch(getActivity(), videos.get(position).getMp4_url(), videos.get(position).getTitle(), pageIndex);
            }

            @Override
            public void onItemLongClick(int position) {
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                pageIndex++;
                mAdapter.setFooterVisible(View.VISIBLE);
                mPresenter.loadData(Apis.HOST + Apis.Video + Apis.VIDEO_HOT_ID + "/n/" + pageIndex * 10 + "-10.html");
            }
        });
    }

    @OnClick(R.id.floatBtn)
    public void onViewClicked() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected void lazyFetchData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.loadData(Apis.HOST + Apis.Video + Apis.VIDEO_HOT_ID + "/n/" + pageIndex + "-10.html");
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        videos.clear();
        mPresenter.loadData(Apis.HOST + Apis.Video + Apis.VIDEO_HOT_ID + "/n/" + pageIndex + "-10.html");
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
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void returnData(List<VideoEntity.V9LG4CHORBean> datas) {
        if (pageIndex == 0) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.setFooterVisible(View.GONE);
        }
        videos.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        videos.clear();
    }
}
