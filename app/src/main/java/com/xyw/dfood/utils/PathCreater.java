package com.xyw.dfood.utils;

import android.content.Context;

import com.xyw.dfood.R;

/**
 * Created by Do on 2015/4/1.
 */
public class PathCreater {
    private static PathCreater sta_pathCreater=null;
    private Context context;
    private PathCreater(Context context){
        this.context=context;
    }
    public static PathCreater getInstance(Context xontext){
        if(sta_pathCreater==null){
            return new PathCreater(xontext);
        }
        return sta_pathCreater;
    }
    public String getBasepath(){
        String url=context.getApplicationContext().getResources().getString(R.string.basePath);
        return url;
    }
    public String getBasepath1(){
        String url=context.getApplicationContext().getResources().getString(R.string.basePath1);
        return url;
    }
}
