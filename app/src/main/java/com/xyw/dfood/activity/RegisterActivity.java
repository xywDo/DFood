package com.xyw.dfood.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.R;
import com.xyw.dfood.biz.UserBiz;
import com.xyw.dfood.view.LoadDialog;

/**
 * Created by pc on 2015/4/25.
 */
public class RegisterActivity extends Activity {
    private TextView tvTitle;
    private Button btn;
    private UserBiz biz;
    private EditText etName, etPwd;
    public LoadDialog loadDialog;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 0){
                Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                removeLoad();
                finish();
            }else{
                Toast.makeText(RegisterActivity.this,(String)message.obj,Toast.LENGTH_SHORT).show();
                removeLoad();
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        biz = new UserBiz(mHandler,this);
        loadDialog = new LoadDialog(this);
        initViews();
        initData();
    }

    private void initData() {
        tvTitle.setText("注册");
        btn.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showLoad();
            biz.register(etName.getText().toString(),etPwd.getText().toString());
        }
    };

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.title);
        btn = (Button) findViewById(R.id.user_register);
        etName = (EditText) findViewById(R.id.u_name);
        etPwd = (EditText) findViewById(R.id.u_pwd);
    }

    public void showLoad() {
        if (loadDialog != null && !loadDialog.dialogShowing()) {
            loadDialog.loadDialog();
        }
    }

    public void removeLoad() {
        if (loadDialog != null && loadDialog.dialogShowing()) {
            loadDialog.removeDialog();
        }
    }
}
