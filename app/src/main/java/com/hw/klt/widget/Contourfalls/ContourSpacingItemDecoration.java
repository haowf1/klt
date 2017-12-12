package com.hw.klt.widget.Contourfalls;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hao on 17-8-12.
 */

public class ContourSpacingItemDecoration extends RecyclerView.ItemDecoration{
    private static final String TAG = ContourSpacingItemDecoration.class.getName();

    public static int DEFAULT_SPACING = 64;
    private int mSpacing;

    public ContourSpacingItemDecoration(){
        this(DEFAULT_SPACING);
    }

    public ContourSpacingItemDecoration(int spacing){
        mSpacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (!(parent.getLayoutManager() instanceof  ContourFallsLayoutManager)){
            throw new IllegalArgumentException(String.format("The %s must be used with a %s",TAG,ContourFallsLayoutManager.TAG));
        }

        final ContourFallsLayoutManager layoutManager = (ContourFallsLayoutManager) parent.getLayoutManager();

        int childIndex = parent.getChildAdapterPosition(view);
        if (childIndex == RecyclerView.NO_POSITION) return;

        outRect.top = 0;
        outRect.left = 0;
        outRect.right = mSpacing;
        outRect.bottom = mSpacing;

        if (isTopChild(childIndex, layoutManager)) {
            outRect.top = mSpacing;
        }

        if (isLeftChild(childIndex, layoutManager)) {
            outRect.left = mSpacing;
        }
    }


    private static boolean isTopChild(int position, ContourFallsLayoutManager layoutManager) {
        boolean isFirstViewHeader = layoutManager.isFirstViewHeader();
        if (isFirstViewHeader && position == ContourFallsLayoutManager.HEAD_POSTION) {
            return true;
        } else if (isFirstViewHeader && position > ContourFallsLayoutManager.HEAD_POSTION) {
            // Decrement position to factor in existence of header
            position -= 1;
        }

        final ContourSizeCalculator sizeCalculator = layoutManager.getSizeCalculator();
        return sizeCalculator.getRowForChildPosition(position) == 0;
    }

    private static boolean isLeftChild(int position,ContourFallsLayoutManager layoutManager){
        boolean isFirstViewHeader = layoutManager.isFirstViewHeader();
        if (isFirstViewHeader && position == ContourFallsLayoutManager.HEAD_POSTION){
            return true;
        }else if (isFirstViewHeader && position > ContourFallsLayoutManager.HEAD_POSTION){
            position -= 1;
        }
        final ContourSizeCalculator sizeCalculator = layoutManager.getSizeCalculator();
        int rowForPosition = sizeCalculator.getRowForChildPosition(position);
        return sizeCalculator.getFirstChildPositionForRow(rowForPosition) == position;
    }
}
