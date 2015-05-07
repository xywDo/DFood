package com.xyw.dfood.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.Model.Comment;
import com.xyw.dfood.Model.Food;
import com.xyw.dfood.R;
import com.xyw.dfood.adapter.CommentAdapter;
import com.xyw.dfood.biz.FoodBiz;
import com.xyw.dfood.listview.SimpleFooter;
import com.xyw.dfood.listview.SimpleHeader;
import com.xyw.dfood.listview.ZrcListView;

import java.util.List;

/**
 * Created by pc on 2015/4/25.
 */
public class CommentActivity extends Activity {
    private TextView tvTitle;
    private ListView listView;
    private int listViewStatus;
    private List<Comment> list;
    private CommentAdapter adapter;
    private Food food;
    private FoodBiz biz;
    private Handler mHandler  = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 0){
                list = (List<Comment>) message.obj;
                adapter = new CommentAdapter(list,CommentActivity.this);
                listView.setAdapter(adapter);
            }else{
                Toast.makeText(CommentActivity.this,"获得评论失败",Toast.LENGTH_SHORT).show();
                finish();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        food = ((Food) getIntent().getSerializableExtra("food"));
        biz = new FoodBiz(mHandler,this);
        initViews();
        initData();
    }

    private void initData() {
        biz.findComment(food.getId());
        tvTitle.setText("评论");
    }
    public FoodBiz getBiz(){
        return biz;
    }
    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.list_view);
    }
}
