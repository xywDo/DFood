package com.xyw.dfood.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xyw.dfood.Model.User;
import com.xyw.dfood.fragment.UserFragment;
import com.xyw.dfood.utils.AsyncSubmit;
import com.xyw.dfood.utils.PathCreater;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pc on 2015/4/25.
 */
public class UserBiz implements AsyncSubmit.AsyncSubmitCallBacks {
private Handler mHandler;
private Context context;
public UserBiz(Handler mHandler,Context context){
        this.mHandler = mHandler;
        this.context = context;
        }

    public void login(String name,String pwd){
        if(name==null|| name.length() == 0||pwd == null || pwd.length()==0){
            Message msg = mHandler.obtainMessage();
            msg.obj = "用户名或者密码不能为空！";
            msg.what = 100;
            msg.sendToTarget();
            return;
        }
        String url = PathCreater.getInstance(context).getBasepath()+"user/login";
        User user = new User();
        user.setName(name);
        user.setPassWord(pwd);
        new Thread(new AsyncSubmit<User>(User.class,context,url,1,this,user)).start();
    }

    public void register(String name,String pwd){
        if(name==null|| name.length() == 0||pwd == null || pwd.length()==0){
            Message msg = mHandler.obtainMessage();
            msg.obj = "用户名或者密码不能为空！";
            msg.what = 100;
            msg.sendToTarget();
            return;
        }
        String url = PathCreater.getInstance(context).getBasepath()+"user/addUser";
        User user = new User();
        user.setName(name);
        user.setPassWord(pwd);
        new Thread(new AsyncSubmit<Boolean>(Boolean.class,context,url,0,this,user)).start();
    }

    @Override
    public void callBacks(int request, ResponseEntity result) {
        if(result!=null){
            if(request == 1){
                User user = ((ResponseEntity<User>)result).getBody();
                if(user!=null) {
                    UserFragment.loginUser = user;
                    mHandler.sendEmptyMessage(0);
                }else{
                    mHandler.sendEmptyMessage(100);
                }
            }else if(request == 0){
                Boolean boo = ((ResponseEntity<Boolean>)result).getBody();
                if(boo) {
                    mHandler.sendEmptyMessage(0);
                }else{
                    mHandler.sendEmptyMessage(100);
                }
            }
        }else{
            Message msg = mHandler.obtainMessage();
            msg.obj = "系统错误！";
            msg.what = 100;
            msg.sendToTarget();
        }
    }
}
