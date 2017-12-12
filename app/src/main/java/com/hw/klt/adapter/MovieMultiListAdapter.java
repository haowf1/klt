package com.hw.klt.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseMultiItemQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.flyco.labelview.LabelView;
import com.hw.klt.R;
import com.hw.klt.adapter.item.NewsMultiItem;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.bean.NewsInfo;
import com.hw.klt.bean.VideoInfo;
import com.hw.klt.config.RetrofitService;
import com.hw.klt.ktlActivity.home.New.NewsArticleActivity;
import com.hw.klt.ktlActivity.home.New.PhotoSetActivity;
import com.hw.klt.ktlActivity.home.New.SpecialActivity;
import com.hw.klt.ktlActivity.home.movie.MovieActivity;
import com.hw.klt.util.DefIconFactory;
import com.hw.klt.util.ImageLoader;
import com.hw.klt.util.ListUtils;
import com.hw.klt.util.MovieUtils;
import com.hw.klt.util.NewsUtils;
import com.hw.klt.util.StringUtils;
import com.hw.klt.widget.RippleView;

import java.util.List;

/**
 * Created by hao on 2017/12/6.
 */

public class MovieMultiListAdapter extends BaseMultiItemQuickAdapter<VideoMultiItem> {

    public MovieMultiListAdapter(Context context, List<VideoMultiItem> data) {
        super(context, data);
    }

    public MovieMultiListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void attachItemType() {
        addItemType(VideoMultiItem.ITEM_TYPE_MOVIE, R.layout.adapter_news_list);
//        addItemType(VideoMultiItem.ITEM_TYPE_PHOTO_SET, R.layout.adapter_news_photo_set);
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoMultiItem item) {
        switch (item.getItemType()) {
            case NewsMultiItem.ITEM_TYPE_NORMAL:
                _handleNewsNormal(holder, item.getVideoBean());
                break;
            case NewsMultiItem.ITEM_TYPE_PHOTO_SET:
//                _handleNewsPhotoSet(holder, item.getNewsBean());
                break;
        }
    }

    /**
     * 处理正常的新闻
     *
     * @param holder
     * @param item
     */
    private void _handleNewsNormal(final BaseViewHolder holder, final VideoInfo item) {
        ImageView newsIcon = holder.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(mContext, item.getVideoposter(), newsIcon, DefIconFactory.provideIcon());
        holder.setText(R.id.tv_title, item.getVideoName())
                .setText(R.id.tv_source,"")
                .setText(R.id.tv_time, item.getVideoSourceType());
        // 设置标签
        if (item.getSkipType()!=null&& MovieUtils.isMovieSet(item.getSkipType())) {
            LabelView labelView = holder.getView(R.id.label_view);
            labelView.setVisibility(View.VISIBLE);
            labelView.setText("专题");
        } else {
            holder.setVisible(R.id.label_view, false);
        }
        // 波纹效果
        RippleView rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(RetrofitService.MOVIE_DATA+item.getVideodetiale());
                intent.setData(content_url);
                mContext.startActivity(intent);
            }
        });
    }

   /* *//**
     * 处理图片的新闻
     *
     * @param holder
     * @param item
     *//*
    private void _handleNewsPhotoSet(BaseViewHolder holder, final NewsInfo item) {
        ImageView[] newsPhoto = new ImageView[3];
        newsPhoto[0] = holder.getView(R.id.iv_icon_1);
        newsPhoto[1] = holder.getView(R.id.iv_icon_2);
        newsPhoto[2] = holder.getView(R.id.iv_icon_3);
        holder.setVisible(R.id.iv_icon_2, false).setVisible(R.id.iv_icon_3, false);
        ImageLoader.loadCenterCrop(mContext, item.getImgsrc(), newsPhoto[0], DefIconFactory.provideIcon());
        if (!ListUtils.isEmpty(item.getImgextra())) {
            for (int i = 0; i < Math.min(2, item.getImgextra().size()); i++) {
                newsPhoto[i + 1].setVisibility(View.VISIBLE);
                ImageLoader.loadCenterCrop(mContext, item.getImgextra().get(i).getImgsrc(),
                        newsPhoto[i + 1], DefIconFactory.provideIcon());
            }
        }
        holder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_source, StringUtils.clipNewsSource(item.getSource()))
                .setText(R.id.tv_time, item.getPtime());
        // 波纹效果
        RippleView rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                PhotoSetActivity.launch(mContext, item.getPhotosetID());
            }
        });
    }*/
}
