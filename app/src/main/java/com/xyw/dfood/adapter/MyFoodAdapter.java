package com.xyw.dfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.xyw.dfood.Model.Food;
import com.xyw.dfood.R;
import com.xyw.dfood.activity.FoodInfoActivity;
import com.xyw.dfood.activity.MainActivity;
import com.xyw.dfood.biz.FoodBiz;
import com.xyw.dfood.utils.ImageLoader;
import com.xyw.dfood.utils.PathCreater;

import java.util.List;

/**
 * Created by Do on 2015/5/6.
 */
public class MyFoodAdapter extends BaseSwipeAdapter{
    private List<Food> list;
    private ImageLoader imageLoader =null;
    private Activity activity;
    private SwipeLayout swipeLayout;
    private LinearLayout singleItem;
    private FoodBiz foodBiz;
    public MyFoodAdapter(Handler mHandelr,List<Food> list,Activity activity){
        imageLoader=new ImageLoader(activity);
        this.activity=activity;
        this.list=list;
        foodBiz=new FoodBiz(mHandelr,activity);
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_list;
    }
    private class ViewHolder{
        public ImageView iv;
        public TextView name;
        public RatingBar xingxing;
    }
    @Override
    public View generateView(int position, ViewGroup parent) {
        ViewGroup mContainer = (ViewGroup) activity.getLayoutInflater().inflate(
                R.layout.layout_sliding, null);
        View v=activity.getLayoutInflater().inflate(R.layout.list_item_food1,null);
        swipeLayout=(SwipeLayout)v.findViewById(R.id.swipe_list);
        swipeLayout.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(50).playOn(layout.findViewById(R.id.delete_d));
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        ViewHolder vh = new ViewHolder();
        singleItem=(LinearLayout)swipeLayout.findViewById(R.id.food_item);
        vh.iv = (ImageView) swipeLayout.findViewById(R.id.iv);
        vh.name = (TextView) swipeLayout.findViewById(R.id.name);
        vh.xingxing = (RatingBar) swipeLayout.findViewById(R.id.star_count);
        swipeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE){

                }
                return false;
            }
        });
//      singleItem.setOnClickListener(onClickListener);
        singleItem.setTag(vh);
        vh.name.setTag(list.get(position));
        vh.iv.setImageResource(R.drawable.def_image);
        if(list.get(position).getImageUrl()!=null && list.get(position).getImageUrl().length()>0) {
            imageLoader.loadImage(PathCreater.getInstance(activity).getBasepath1()+list.get(position).getImageUrl(), vh.iv);
        }
        vh.name.setText(list.get(position).getName());
        vh.xingxing.setRating(list.get(position).getScore());
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "click delete", Toast.LENGTH_SHORT).show();
                foodBiz.DelFood(list.get(position).getId());
                swipeLayout.close();
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private View.OnClickListener onClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Food index = (Food)((ViewHolder)view.getTag()).name.getTag();
            Intent intent = new Intent(activity,FoodInfoActivity.class);
            intent.putExtra("food",index);
            activity.startActivityForResult(intent, 1);
        }
    };
}
