package io.github.zane.newsking.ui.girl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.llf.basemodel.commonwidget.CircleImageView;
import com.llf.basemodel.recycleview.BaseViewHolder;
import com.llf.basemodel.utils.ImageLoaderUtils;

import java.util.List;

import io.github.zane.newsking.R;
import io.github.zane.newsking.entity.JcodeEntity;

/**
 * Created by Zane on 2016/3/19.
 * 发现的适配器，分为两种样式
 */

public class GirlAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private List<JcodeEntity> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;
    private static final String HOST = "http://www.jcodecraeer.com";

    public GirlAdapter(List<JcodeEntity> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_jcode_no_image, parent, false));
        } else if (viewType == ITEM_HASIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_jcode_has_image, parent, false));
        } else {
            footerView = LayoutInflater.from(mContext).inflate(viewFooter, parent, false);
            return new BaseViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (!(viewFooter != 0 && position == getItemCount() - 1)) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }

            int type = getItemViewType(position);
            JcodeEntity item = datas.get(position);
            if (type == ITEM_HASIMAGE) {
                ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.cover), HOST + item.getImgUrl());
            }
            holder.setText(R.id.title, item.getTitle());
            holder.setText(R.id.content, item.getContent());
            holder.setText(R.id.author, item.getAuthor());
            holder.setText(R.id.seeNum, item.getWatch());
            holder.setText(R.id.commentNum, item.getComments());
            holder.setText(R.id.loveNum, item.getLike());
            ImageLoaderUtils.loadingImg(mContext, (CircleImageView) holder.getView(R.id.avatar), HOST + item.getAuthorImg());
        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (viewFooter != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_HASIMAGE;
        if (viewFooter != 0 && position == getItemCount() - 1) {
            type = TYPE_FOOTER;
            return type;
        }
        if (TextUtils.isEmpty(datas.get(position).getImgUrl())) {
            type = ITEM_NOIMAGE;
        } else {
            type = ITEM_HASIMAGE;
        }
        return type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterVisible(int visible) {
        footerView.setVisibility(visible);
    }

    //设置点击事件
    public void setOnItemClickLitener(OnItemClickListener mLitener) {
        mOnItemClickListener = mLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
