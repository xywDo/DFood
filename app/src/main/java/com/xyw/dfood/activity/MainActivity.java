package com.xyw.dfood.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.xyw.dfood.R;
import com.xyw.dfood.base.BaseFragmentActivity;
import com.xyw.dfood.fragment.AddFragment;
import com.xyw.dfood.fragment.FoodFragment;
import com.xyw.dfood.fragment.MenuFragment;
import com.xyw.dfood.fragment.UserFragment;

/**
 * Created by pc on 2015/4/24.
 */
public class MainActivity extends BaseFragmentActivity {

    private MenuFragment menuFragment;
    private FoodFragment foodFragment;
    private UserFragment userFragment;
    private AddFragment addFragment;
    private boolean isBack;
    private FoodFragment myFoodFragment;

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            isBack = false;
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBack = false;
        menuFragment = new MenuFragment();
        setMenu();
        setShowView(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            toggleView();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isBack) {
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 2000);
                isBack = true;
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void setMenu() {
        FragmentTransaction fTransaction = getSupportFragmentManager()
                .beginTransaction();
        fTransaction.add(R.id.layout_menu, menuFragment, "menuFragment");
        fTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (foodFragment != null) {
            transaction.hide(foodFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
        if(myFoodFragment != null){
            transaction.hide(myFoodFragment);
        }
        if(addFragment != null){
            transaction.hide(addFragment);
        }
    }

    public void setShowView(int index) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (foodFragment == null) {
                    FoodFragment.stMod = 0;
                    foodFragment = new FoodFragment();
                    transaction.add(R.id.layout_sliding, foodFragment);
                } else {
                    transaction.show(foodFragment);
                }
                break;
            case 1:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.layout_sliding, userFragment);
                } else {
                    transaction.show(userFragment);
                }
                break;
            case 2:
                if (myFoodFragment == null) {
                    FoodFragment.stMod = 1;
                    myFoodFragment = new FoodFragment();
                    transaction.add(R.id.layout_sliding, myFoodFragment);
                } else {
                    myFoodFragment.update();
                    transaction.show(myFoodFragment);
                }
                break;
            case 3:
                if (addFragment == null) {
                    addFragment = new AddFragment();
                    transaction.add(R.id.layout_sliding, addFragment);
                } else {
                    transaction.show(addFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == 1){
                foodFragment.showFoodByC(data);
            }
        }else if(requestCode == 120){
            addFragment.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
