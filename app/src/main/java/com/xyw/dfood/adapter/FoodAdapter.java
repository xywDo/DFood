package com.xyw.dfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xyw.dfood.Model.Food;
import com.xyw.dfood.R;
import com.xyw.dfood.activity.FoodInfoActivity;
import com.xyw.dfood.utils.ImageLoader;
import com.xyw.dfood.utils.PathCreater;

import java.util.List;

/**
 * Created by pc on 2015/4/24.
 */
public class FoodAdapter extends BaseAdapter {
    private List<Food> list;
    private LayoutInflater inlfater;
    private ImageLoader imageLoader =null;

    public FoodAdapter(List<Food> list,LayoutInflater inlfater){
        imageLoader = new ImageLoader(inlfater.getContext());
        this.list = list;
        this.inlfater = inlfater;
    }

    private class ViewHolder{
        public ImageView iv;
        public TextView name;
        public RatingBar xingxing;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view == null){
            vh = new ViewHolder();
            view = inlfater.inflate(R.layout.list_item_food,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv);
            vh.name = (TextView) view.findViewById(R.id.name);
            vh.xingxing = (RatingBar) view.findViewById(R.id.star_count);
            view.setOnClickListener(onClickListener);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        vh.name.setTag(list.get(i));
        vh.iv.setImageResource(R.drawable.def_image);
        if(list.get(i).getImageUrl()!=null && list.get(i).getImageUrl().length()>0) {
            imageLoader.loadImage(PathCreater.getInstance(inlfater.getContext()).getBasepath1()+list.get(i).getImageUrl(), vh.iv);
        }
        vh.name.setText(list.get(i).getName());
        vh.xingxing.setRating(list.get(i).getScore());
        return view;
    }

    private View.OnClickListener onClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Food index = (Food)((ViewHolder)view.getTag()).name.getTag();
            Intent intent = new Intent(inlfater.getContext(),FoodInfoActivity.class);
            intent.putExtra("food",index);
            ((Activity)inlfater.getContext()).startActivityForResult(intent,1);
        }
    };
}
