package com.xyw.dfood.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.Model.User;
import com.xyw.dfood.R;
import com.xyw.dfood.activity.MainActivity;
import com.xyw.dfood.activity.RegisterActivity;
import com.xyw.dfood.biz.UserBiz;
import com.xyw.dfood.utils.ImageLoader;

/**
 * Created by pc on 2015/4/25.
 */
public class UserFragment extends Fragment {
    private View mainView,loginView,infoView;
    private EditText etName,etPwd;
    private TextView tvName,tvMyFood,tvCollect,toRegister,tvTitle;
    private MainActivity act;
    private ImageView iv;
    private UserBiz biz;
    private ImageLoader imageLoader;
    public static User loginUser;
    private Button btnLogin,backLogin;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    initData();
                    act.removeLoad();
                    break;
                case 100:
                    Toast.makeText(act,(String)message.obj,Toast.LENGTH_SHORT).show();
                    act.removeLoad();
                    break;
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_login,null);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        act = (MainActivity) getActivity();
        biz =new UserBiz(mHandler,act);
        imageLoader = new ImageLoader(act);
        initViews();
        initData();
    }

    private void initData() {
        tvTitle.setText("用户");
        if(loginUser == null){
            loginView.setVisibility(View.VISIBLE);
            infoView.setVisibility(View.GONE);
        }else{
            loginView.setVisibility(View.GONE);
            infoView.setVisibility(View.VISIBLE);
            imageLoader.loadImage(loginUser.getImageUrl(),iv);
            tvName.setText(loginUser.getName());
            tvCollect.setText(loginUser.getConllect_food().size()+" 个收藏的美食");
            tvMyFood.setText(loginUser.getFoods().size()+" 个发布的美食");
        }
        btnLogin.setOnClickListener(clickListener);
        toRegister.setOnClickListener(clickListener);
        backLogin.setOnClickListener(clickListener);
        tvCollect.setOnClickListener(clickListener);
        tvMyFood.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.user_login:
                    act.showLoad();
                    biz.login(etName.getText().toString(),etPwd.getText().toString());
                    break;
                case R.id.to_register:
                    act.startActivity(new Intent(act, RegisterActivity.class));
                    break;
                case R.id.user_un_login:
                    loginUser = null;
                    initData();
                    break;
                case R.id.r_collect:
                    break;
                case R.id.r_my_food:
                    break;
            }
        }
    };

    private void initViews() {
        loginView = mainView.findViewById(R.id.layout_login);
        infoView = mainView.findViewById(R.id.layout_userinfo);
        etName = (EditText) mainView.findViewById(R.id.u_name);
        etPwd = (EditText) mainView.findViewById(R.id.u_pwd);
        toRegister = (TextView) mainView.findViewById(R.id.to_register);
        btnLogin = (Button) mainView.findViewById(R.id.user_login);
        tvTitle = (TextView) mainView.findViewById(R.id.title);
        tvName = (TextView) mainView.findViewById(R.id.r_name);
        tvCollect = (TextView) mainView.findViewById(R.id.r_collect);
        tvMyFood = (TextView) mainView.findViewById(R.id.r_my_food);
        iv = (ImageView) mainView.findViewById(R.id.r_iv);
        backLogin = (Button) mainView.findViewById(R.id.user_un_login);
    }
}
