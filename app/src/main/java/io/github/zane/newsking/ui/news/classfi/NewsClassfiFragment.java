package io.github.zane.newsking.ui.news.classfi;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.llf.basemodel.base.BaseFragment;
import com.llf.basemodel.image.BigImagePagerActivity;
import com.llf.basemodel.recycleview.BaseViewHolder;
import com.llf.basemodel.recycleview.DefaultItemDecoration;
import com.llf.basemodel.recycleview.EndLessOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.github.zane.newsking.R;
import io.github.zane.newsking.api.Apis;
import io.github.zane.newsking.entity.NewsEntity;
import io.github.zane.newsking.ui.news.NewsFragment;
import io.github.zane.newsking.ui.news.adapter.NewsAdapter;
import io.github.zane.newsking.ui.news.contract.NewsContract;
import io.github.zane.newsking.ui.news.detail.NewsDetailActivity;
import io.github.zane.newsking.ui.news.presenter.NewsPresenter;

/**
 * Created by Zane on 2016/3/25.
 * 新闻头条
 */

public class NewsClassfiFragment extends BaseFragment implements NewsContract.View, SwipeRefreshLayout.OnRefreshListener {
    public static NewsClassfiFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsClassfiFragment fragment = new NewsClassfiFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private NewsAdapter mAdapter;
    private NewsContract.Presenter mPresenter;
    private int pageIndex = 0;
    private int type = NewsFragment.ONE;
    private List<NewsEntity> newDatas = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private Animator spruceAnimator;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_classfi;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt("type");
    }

    @Override
    protected void initView() {
        mPresenter = new NewsPresenter(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(getActivity()));
        mAdapter = new NewsAdapter(newDatas, getActivity());
        mAdapter.addFooterView(R.layout.layout_footer);
        mAdapter.setOnItemClickLitener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BaseViewHolder viewHolder) {
                if (newDatas.get(position).getImgextra() == null) {
                    NewsEntity entity = newDatas.get(position);
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("news", entity);
                    /**
                     * 这边会有崩溃问题出现
                     */
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                                    viewHolder.getView(R.id.ivNews), getString(R.string.transition_news_img));
//                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                    startActivity(intent);
                } else {
                    images.clear();
                    for (int i = 0; i < newDatas.get(position).getImgextra().size(); i++) {
                        images.add(newDatas.get(position).getImgextra().get(i).getImgsrc());
                    }
                    BigImagePagerActivity.startImagePagerActivity(getActivity(), images, 0);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                pageIndex += Apis.PAZE_SIZE;
                mAdapter.setFooterVisible(View.VISIBLE);
                mPresenter.loadData(type, pageIndex);
            }
        });
    }

    @Override
    protected void lazyFetchData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.loadData(type, pageIndex);
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
    public void returnData(List<NewsEntity> datas) {
        if (pageIndex == 0) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.setFooterVisible(View.GONE);
        }
        newDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        newDatas.clear();
        mPresenter.loadData(type, pageIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        newDatas.clear();
        images.clear();
    }

    public void slideToTop(){
        mRecyclerView.smoothScrollToPosition(0);
    }
}
