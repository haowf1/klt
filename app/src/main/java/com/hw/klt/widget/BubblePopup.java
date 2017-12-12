package com.hw.klt.widget;

import android.content.Context;
import android.view.View;

import com.flyco.dialog.widget.popup.base.BaseBubblePopup;

public class BubblePopup extends BaseBubblePopup<BubblePopup> {

    public BubblePopup(Context context, View wrappedView) {
        super(context, wrappedView);
    }

    @Override
    public View onCreateBubbleView() {
        return null;
    }
}
