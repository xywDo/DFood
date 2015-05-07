package com.xyw.dfood.utils;

import android.content.Context;

import org.springframework.http.ResponseEntity;

public class AsyncSubmit <E> implements Runnable  {

	private AsyncSubmitCallBacks bizImpl;
	private int request;
	private String url;
	private int methods;
    private Context context;
    private Object o;
    private Class<E> clazz;

    public AsyncSubmit(Class<E> clazz,Context context,String url,int request,AsyncSubmitCallBacks bizImpl){
        this.methods = 0;
        this.context = context;
        this.url = url;
        this.request = request;
        this.bizImpl = bizImpl;
        this.clazz = clazz;
    }
    public AsyncSubmit(Class<E> clazz,Context context,String url,int request,AsyncSubmitCallBacks bizImpl,Object o){
        this.methods = 1;
        this.context = context;
        this.url = url;
        this.request = request;
        this.bizImpl = bizImpl;
        this.o = o;
        this.clazz = clazz;
    }

	@Override
	public void run() {
        ResponseEntity<E> result = null;
			try {
				if (methods == 0) {
					result = HttpUtils.get(context,url,clazz);
				} else if (methods == 1) {
                    result = HttpUtils.post(context, url,  o,clazz);
                }
			} catch (Exception e) {
				result = null;
			}
		bizImpl.callBacks(request, result);
	}

	public interface AsyncSubmitCallBacks {
		public void callBacks(int request, ResponseEntity result);
	}
}
