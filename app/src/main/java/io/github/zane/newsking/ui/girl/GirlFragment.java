package io.github.zane.newsking.ui.girl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.llf.basemodel.base.BaseFragment;
import com.llf.basemodel.commonactivity.WebViewActivity;
import com.llf.basemodel.recycleview.DefaultItemDecoration;
import com.llf.basemodel.recycleview.EndLessOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.zane.newsking.R;
import io.github.zane.newsking.entity.JcodeEntity;
import io.github.zane.newsking.ui.girl.adapter.GirlAdapter;
import io.github.zane.newsking.ui.girl.contract.GirlContract;
import io.github.zane.newsking.ui.girl.presenter.GirlPresenter;

/**
 * Created by Zane on 2016/3/25.
 * 发现
 */

public class GirlFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, GirlContract.View {
    public static GirlFragment getInstance() {
        GirlFragment girlFragment = new GirlFragment();
        return girlFragment;
    }

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private GirlAdapter mAdapter;
    private List<JcodeEntity> jcodes = new ArrayList<>();
    private GirlPresenter mPresenter;
    private int pageIndex = 1;

    private static final String HOST = "http://www.jcodecraeer.com";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl;
    }

    @Override
    protected void initView() {
        mPresenter = new GirlPresenter(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(getActivity()));
        mAdapter = new GirlAdapter(jcodes,getActivity());
        mAdapter.addFooterView(R.layout.layout_footer);
        mAdapter.setOnItemClickLitener(new GirlAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.addRecord(getActivity(), jcodes.get(position));
                WebViewActivity.lanuch(getActivity(), HOST + jcodes.get(position).getDetailUrl());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                pageIndex++;
                mAdapter.setFooterVisible(View.VISIBLE);
                mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
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
        mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        jcodes.clear();
        mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
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
    public void returnData(List<JcodeEntity> datas) {
        if (pageIndex == 1) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.setFooterVisible(View.GONE);
        }
        jcodes.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        jcodes.clear();
    }
}
