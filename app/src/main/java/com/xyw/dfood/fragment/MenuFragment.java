package com.xyw.dfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyw.dfood.R;
import com.xyw.dfood.activity.MainActivity;
import com.xyw.dfood.base.BaseFragmentActivity;
import com.xyw.dfood.view.MenuLinearLayout;

public class MenuFragment extends Fragment implements MenuLinearLayout.MenuLLOnClick {

	private View fragmentView;
	private MenuLinearLayout llMs,llYh,llWdMs,llAdd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_menu, null);
		return fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initDatas();
	}

	private void initDatas() {
        llMs.setMlloc(this);
        llMs.setRes("美食",
                R.drawable.youjiantou_selector);
        llYh.setMlloc(this);
        llYh.setRes("用户",
                R.drawable.youjiantou_selector);
        llWdMs.setMlloc(this);
        llWdMs.setRes("我的美食",
                R.drawable.youjiantou_selector);
        llAdd.setMlloc(this);
        llAdd.setRes( "发布美食",
                R.drawable.youjiantou_selector);
	}

	private void initViews() {
        llMs = (MenuLinearLayout) fragmentView.findViewById(R.id.ll_ms);
        llYh =  (MenuLinearLayout) fragmentView.findViewById(R.id.ll_yh);
        llWdMs = (MenuLinearLayout) fragmentView.findViewById(R.id.ll_wd_yh);
        llAdd = (MenuLinearLayout) fragmentView.findViewById(R.id.ll_add);
	}

	@Override
	public void menuOnClick(MenuLinearLayout view) {
		if (view == llMs) {
			((MainActivity) getActivity()).setShowView(0);
		}else if(view == llYh){
            ((MainActivity) getActivity()).setShowView(1);
        }
        if(view == llWdMs){
            ((MainActivity) getActivity()).setShowView(2);
        }
        if(view == llAdd){
			if(UserFragment.loginUser==null){
				((MainActivity) getActivity()).setShowView(1);
			}else
            ((MainActivity) getActivity()).setShowView(3);
        }
		((BaseFragmentActivity) getActivity()).toggleView();
	}
}
