package com.xiaoxuan.broadcastsample.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xiaoxuan on 2015/6/15.
 */
public class Tools {

    public static void ToolsToast(Context context,String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
