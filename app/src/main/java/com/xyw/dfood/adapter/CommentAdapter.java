package com.xyw.dfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.xyw.dfood.Model.Comment;
import com.xyw.dfood.R;
import com.xyw.dfood.activity.CommentActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by pc on 2015/4/25.
 */
public class CommentAdapter extends BaseSwipeAdapter {
    private List<Comment> list;
    private CommentActivity mContext;
    private SwipeLayout swipeLayout;
    public CommentAdapter(List<Comment> list,CommentActivity context){
        this.list = list;
        this.mContext = context;
    }

    private class ViewHolder{
        public TextView tvName,tvDetail,tvDate;
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
        return i;
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder vh = null;
//        if(view == null){
//            vh = new ViewHolder();
//            view = inflater.inflate(R.layout.list_item_comment,null);
//            vh.tvName = (TextView) view.findViewById(R.id.u_name);
//            vh.tvDetail = (TextView) view.findViewById(R.id.detail);
//            vh.tvDate = (TextView) view.findViewById(R.id.date);
//            view.setTag(vh);
//        }else{
//            vh = (ViewHolder) view.getTag();
//        }
//        vh.tvName.setText(list.get(i).getUser().getName());
//        vh.tvDetail.setText(list.get(i).getDetatils());
//        vh.tvDate.setText(list.get(i).getDate());
//        return view;
//    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_list;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.list_comment_util,null);
        swipeLayout= (SwipeLayout) v.findViewById(R.id.swipe_list);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(50).playOn(layout.findViewById(R.id.delete_d));
            }
        });
        return v;
    }

    @Override
    public void fillValues(final int position, View convertView) {
            TextView text_name=(TextView)convertView.findViewById(R.id.user_name);
            TextView text_date=(TextView)convertView.findViewById(R.id.comment_date);
            TextView text_content=(TextView)convertView.findViewById(R.id.comment_content);
            text_name.setText(list.get(position).getUser().getName());
            text_date.setText(list.get(position).getDate());
            text_content.setText(list.get(position).getDetatils());
            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                    mContext.getBiz().DeleteComment(list.get(position).getId());
                    swipeLayout.close();
                }
            });
    }
}
