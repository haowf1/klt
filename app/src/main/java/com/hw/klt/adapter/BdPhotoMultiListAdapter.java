package com.hw.klt.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.hw.klt.R;
import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.util.DefIconFactory;
import com.hw.klt.util.ImageLoader;
import com.hw.klt.widget.Contourfalls.ContourSizeCalculator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 17-8-9.
 */

public class BdPhotoMultiListAdapter extends BaseQuickAdapter<BaiduPictureInfo.BDPhotoData> implements ContourSizeCalculator.SizeCalculatorDelegate {

    public ArrayList<Double> mImageAspectRatios = new ArrayList<Double>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_list_bdphoto;
    }

    public BdPhotoMultiListAdapter(Context context) {
        super(context);
    }
    @Override
    public double aspectRatioForIndex(int index) {
        // Precaution, have better handling for this in greedo-layout
        if (index >= getItemCount()) return 1.0;
        if (index < mImageAspectRatios.size()){
            return mImageAspectRatios.get(index);
        }
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, BaiduPictureInfo.BDPhotoData item) {
        ImageView bdphoto = holder.getView(R.id.bdphoto);
        ImageLoader.loadCenterCrop(mContext, item.getThumbURL(), bdphoto, DefIconFactory.provideIcon());
//        Picasso.with(mContext)
//                .load(item.getThumbURL())
//                .into(bdphoto);
    }

    public void calculateImageAspectRatios() {
        List<BaiduPictureInfo.BDPhotoData> phtotodata = getData();
        for (int i = 0; i < phtotodata.size(); i++) {
            double item = ((double) (phtotodata.get(i).getWidth() )/ ((double) phtotodata.get(i).getHeight()));
            Log.d("BdPhotoMultiListAdapter",""+item);
            mImageAspectRatios.add(item);
        }
    }


}
