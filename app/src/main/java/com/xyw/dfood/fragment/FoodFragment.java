package com.xyw.dfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.Model.Food;
import com.xyw.dfood.Model.FoodType;
import com.xyw.dfood.R;
import com.xyw.dfood.activity.FoodInfoActivity;
import com.xyw.dfood.activity.MainActivity;
import com.xyw.dfood.adapter.FoodAdapter;
import com.xyw.dfood.adapter.MyFoodAdapter;
import com.xyw.dfood.biz.FoodBiz;
import com.xyw.dfood.listview.SimpleFooter;
import com.xyw.dfood.listview.SimpleHeader;
import com.xyw.dfood.listview.ZrcListView;

import java.util.List;
/**
 * Created by pc on 2015/4/24.
 */
public class FoodFragment extends Fragment{
private View mainView;
    private TextView tvTitle;
    private ZrcListView listView;
    private BaseAdapter adapter = null;
    private FoodBiz biz;
    private MainActivity act;
    private List<Food> list;
    private int listViewStatus = 3;
    private int mod;
    public static int stMod;
    private View searchL;
    private EditText etSearch;
    private Spinner spSearch;
    private Button btnSearch;
    public static List<FoodType> typeList;
    private String[] typeStr;
    private int page = 1;
    private Handler mHandelr = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(listViewStatus == 3) {
                        list = (List<Food>) msg.obj;
                        if(mod==0){
                            adapter = new FoodAdapter(list, getActivity().getLayoutInflater());
                        }else{
                            adapter = new MyFoodAdapter(mHandelr,list, getActivity());
                        }
                        listView.setAdapter(adapter);
                        page = 2;
                    }else if(listViewStatus == 1){
                        list.clear();
                        list.addAll( (List<Food>) msg.obj);
                        adapter.notifyDataSetChanged();
                        listView.setRefreshSuccess("刷新成功");
                        page = 2;
                    }else if(listViewStatus == 2){
                        list.addAll( (List<Food>) msg.obj);
                        adapter.notifyDataSetChanged();
                        listView.startLoadMore();
                        page++;
                    }else if(listViewStatus == 4){
                        list.clear();
                        list.addAll( (List<Food>) msg.obj);
                        adapter.notifyDataSetChanged();
                        page = 2;
                    }
                    listViewStatus=0;
                    act.removeLoad();
                    break;
                case 1:
                    typeList = (List<FoodType>) msg.obj;
                    typeStr = new String[typeList.size()+1];
                    typeStr[0] = "全部";
                    for (int i = 1;i<typeStr.length;i++){
                        typeStr[i] = typeList.get(i-1).getType();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(act,android.R.layout.simple_list_item_1,typeStr);
                    spSearch.setAdapter(adapter);
                    spSearch.setEnabled(true);
                    break;
                case 100:
                   if(listViewStatus == 1){
                        listView.setRefreshFail("刷新成功");
                    }else if(listViewStatus == 2){
                        listView.stopLoadMore();
                    }
                    listViewStatus=0;
                    break;
                case 1004:
                    act.removeLoad();
                    Toast.makeText(act,"没有美食",Toast.LENGTH_SHORT).show();
                    break;
                case 1008:
                    Toast.makeText(act,"删除成功",Toast.LENGTH_SHORT).show();
                    break;
                case 1002:
                    Toast.makeText(act,"删除失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mod = stMod;
        mainView  = inflater.inflate(R.layout.fragment_food,null);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        act = (MainActivity)getActivity();
        biz = new FoodBiz(mHandelr,getActivity());
        initView();
        initData();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(listViewStatus == 0) {
                listViewStatus = 4;
                if (spSearch.getSelectedItemPosition() == 0) {
                    biz.findFoodByStr(1, 5, etSearch.getText().toString());
                } else {
                    biz.findFoodByStrType(1, 5, etSearch.getText().toString(), typeList.get(spSearch.getSelectedItemPosition() - 1).getId());
                }
                etSearch.clearFocus();
            }
        }
    };

    private void initData() {
        float density = getResources().getDisplayMetrics().density;
        if(mod == 0) {
            btnSearch.setOnClickListener(clickListener);
        }
        listView.setFirstTopOffset((int) (50 * density));
        SimpleHeader header = new SimpleHeader(act);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        SimpleFooter footer = new SimpleFooter(act);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        listView.startLoadMore();

        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                if (listViewStatus == 0) {
                    listViewStatus = 1;
                    if(mod == 0) {
                        if(spSearch.getSelectedItemPosition()==0){
                            biz.findFoodByStr(1,5,etSearch.getText().toString());
                        }else{
                            biz.findFoodByStrType(1,5,etSearch.getText().toString(),typeList.get(spSearch.getSelectedItemPosition()-1).getId());
                        }
                    }else{
                        biz.getFoodsByUser(1,5,UserFragment.loginUser.getId());
                    }
                } else {
                    listView.setRefreshFail("等待其他操作");
                }
            }
        });

        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                if (listViewStatus == 0) {
                    listViewStatus = 2;
                    if (mod == 0) {
                        biz.getFoods(page, 5);
                    } else {
                        biz.getFoodsByUser(page, 5,UserFragment.loginUser.getId());
                    }
                } else {
                    listView.stopLoadMore();
                }
            }
        });
        if(mod == 0) {
            tvTitle.setText(act.getResources().getText(R.string.food_title));
            biz.findType();
            biz.getFoods(1, 5);
            act.showLoad();
        }else{
            tvTitle.setText("我的美食");
            if(UserFragment.loginUser == null){
                Toast.makeText(act,"请先登录",Toast.LENGTH_SHORT).show();
                act.setShowView(1);
            }else{
                biz.getFoodsByUser(1,5,UserFragment.loginUser.getId());
                act.showLoad();
            }
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(act, FoodInfoActivity.class);
            intent.putExtra("food",list.get(i));
            act.startActivityForResult(intent, 0);
        }
    };

    private void initView() {
        tvTitle = (TextView) mainView.findViewById(R.id.title);
        listView  = (ZrcListView) mainView.findViewById(R.id.food_list);
        searchL = mainView.findViewById(R.id.search_ll);
        if(mod == 0){
            etSearch = (EditText) mainView.findViewById(R.id.search_info);
            spSearch = (Spinner) mainView.findViewById(R.id.search_type);
            btnSearch = (Button) mainView.findViewById(R.id.search_btn);
            spSearch.setEnabled(false);
        }else{
            searchL.setVisibility(View.GONE);
        }
    }

    public void showFoodByC(Intent intent){
        String key = intent.getStringExtra("key");
        String value = intent.getStringExtra("value");
        if(key!=null){
            listViewStatus = 1;
            if(key.equals("type")){
                biz.getFoodsByType(1,5,Integer.parseInt(value));
            }else if(key.equals("user")){
                biz.getFoodsByUser(1,5,Integer.parseInt(value));
            }
        }
    }

    public void update(){
        if(UserFragment.loginUser == null){
            Toast.makeText(act,"请先登录",Toast.LENGTH_SHORT).show();
            act.setShowView(1);
        }else{
            act.showLoad();
            biz.getFoodsByUser(1,5,UserFragment.loginUser.getId());
        }
    }
}
