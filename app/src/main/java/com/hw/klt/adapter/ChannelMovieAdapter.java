package com.hw.klt.adapter;

import android.content.Context;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.hw.klt.R;
import com.hw.klt.logcal.table.MovieTypeInfo;

import java.util.List;

/**
 * Created by hao on 2017/12/8.
 */

public class ChannelMovieAdapter  extends BaseQuickAdapter<MovieTypeInfo> {

    public ChannelMovieAdapter(Context context) {
        super(context);
    }

    public ChannelMovieAdapter(Context context, List<MovieTypeInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_manage;
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieTypeInfo item) {
        holder.setText(R.id.tv_channel_name, item.getName());
    }
}
