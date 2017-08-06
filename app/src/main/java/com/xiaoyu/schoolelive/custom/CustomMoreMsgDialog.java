package com.xiaoyu.schoolelive.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by NeekChaw on 2017-07-18.
 */
public class CustomMoreMsgDialog extends Dialog {
    public CustomMoreMsgDialog(@NonNull Context context) {
        super(context);
    }

    public CustomMoreMsgDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomMoreMsgDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
