package com.xyw.dfood.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.Model.Food;
import com.xyw.dfood.R;
import com.xyw.dfood.biz.FoodBiz;
import com.xyw.dfood.fragment.UserFragment;
import com.xyw.dfood.utils.ImageLoader;
import com.xyw.dfood.utils.PathCreater;
import com.xyw.dfood.view.CheckableFrameLayout;
import com.xyw.dfood.view.LoadDialog;

/**
 * Created by pc on 2015/4/24.
 */
public class FoodInfoActivity extends Activity implements View.OnClickListener {
    private Food food;
    private ImageView iv;
    private CheckableFrameLayout collect;
    private TextView tvName,tvUser,tvDescription,tvCollect,tvTitle;
    private Button tvType;
    private Button tvComment;
    private ImageLoader imageLoader;
    private boolean isCollect = false;
    private FoodBiz biz;
    private ImageView collect_image;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 1){
                isCollect = true;
                collect.setChecked(true);
                collect.setOnClickListener(FoodInfoActivity.this);
            }else if(message.what == 101){
                isCollect = false;
                collect.setChecked(false);
                collect.setOnClickListener(FoodInfoActivity.this);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);
        food = (Food) getIntent().getSerializableExtra("food");
        imageLoader = new ImageLoader(this);
        biz = new FoodBiz(mHandler,this);
        initViews();
        initData();
    }

    private void initData() {
        tvTitle.setText("美食详情");
        imageLoader.loadImage(PathCreater.getInstance(this).getBasepath1()+food.getImageUrl(),iv);
        tvName.setText(food.getName());
        tvUser.setText(food.getUser().getName());
        tvDescription.setText(food.getDescription());
        tvCollect.setText(food.getCollect_count()+" 次");
        tvType.setText(food.getFoodType().getType());
        tvUser.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        if(UserFragment.loginUser==null){
            collect.setVisibility(View.GONE);
        }else{
            biz.isCollect(UserFragment.loginUser.getId(),food.getId());
        }
    }

    private void initViews() {
        iv = (ImageView) findViewById(R.id.iv);
        tvName = (TextView) findViewById(R.id.food_name);
        tvUser = (TextView) findViewById(R.id.user_name);
        tvDescription = (TextView) findViewById(R.id.food_description);
        tvCollect = (TextView) findViewById(R.id.food_collect);
        tvComment = (Button) findViewById(R.id.get_comments);
        tvType = (Button) findViewById(R.id.food_type);
        collect = (CheckableFrameLayout) findViewById(R.id.add_schedule_button);
        tvTitle = (TextView) findViewById(R.id.title);
        collect_image = (ImageView) collect.findViewById(R.id.add_schedule_icon);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_name:
                Intent intent = new Intent();
                intent.putExtra("key","user");
                intent.putExtra("value",""+food.getUser().getId());
                setResult(1,intent);
                this.finish();
                break;
            case R.id.food_type:
                Intent intent1 = new Intent();
                intent1.putExtra("key","type");
                intent1.putExtra("value",""+food.getFoodType().getId());
                setResult(1,intent1);
                this.finish();
                break;
            case R.id.get_comments:
                Intent i = new Intent(this,CommentActivity.class);
                i.putExtra("food",food);
                startActivity(i);
                break;
            case R.id.add_schedule_button:
                if(collect.isChecked()){
                    collect_image.setImageResource(R.drawable.add_schedule_button_icon_checked);
                }else{
                    collect_image.setImageResource(R.drawable.add_schedule_button_icon_unchecked);
                }
                collect.setChecked(!collect.isChecked());
                break;
        }
    }

    @Override
    protected void onStop() {
        if(UserFragment.loginUser!=null && collect.isChecked() != isCollect){
            biz.collect(UserFragment.loginUser.getId(),food.getId(),collect.isChecked());
        }
        setResult(0);
        super.onStop();
    }
}
