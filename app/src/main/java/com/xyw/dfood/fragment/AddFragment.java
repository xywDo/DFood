package com.xyw.dfood.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xyw.dfood.R;
import com.xyw.dfood.activity.MainActivity;
import com.xyw.dfood.biz.FoodBiz;

/**
 * Created by pc on 2015/4/25.
 */
public class AddFragment extends Fragment {
    private View mainView;
    private Spinner spinner;
    private EditText etName,etD;
    private Button btn;
    private ImageView ivImage;
    private MainActivity act;
    private String imagePath;
    private TextView tvTitle;
    private FoodBiz biz;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what == 11){
                Toast.makeText(act,"发布成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(act,"发布失败",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_add_food,null);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        act = (MainActivity) getActivity();
        biz = new FoodBiz(mHandler,act);
        initViews();
        initData();
    }

    private void initData() {
        ivImage.setOnClickListener(clickListener);
        tvTitle.setText("发布美食");
        btn.setOnClickListener(clickListener);
        String[] typeStr = new String[FoodFragment.typeList.size()];
        for (int i = 0;i<typeStr.length;i++){
            typeStr[i] = FoodFragment.typeList.get(i).getType();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(act,android.R.layout.simple_list_item_1,typeStr);
        spinner.setAdapter(adapter);
        spinner.setEnabled(true);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             if(view == ivImage){
                 selectImage();
             }else if(view == btn){
                 biz.addFood(etName.getText().toString(),FoodFragment.typeList.get(spinner.getSelectedItemPosition()),etD.getText().toString(),imagePath);
             }
        }
    };

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,110);
    }

    private void initViews() {
        ivImage = (ImageView) mainView.findViewById(R.id.f_image);
        etName = (EditText) mainView.findViewById(R.id.f_name);
        etD = (EditText) mainView.findViewById(R.id.f_d);
        btn = (Button) mainView.findViewById(R.id.f_btn);
        spinner = (Spinner) mainView.findViewById(R.id.f_type);
        tvTitle = (TextView) mainView.findViewById(R.id.title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                    ContentResolver cr = act.getContentResolver();
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        ivImage.setImageBitmap(bitmap);
                        String[] projs={MediaStore.Images.Media.DATA};
                        Cursor cursor=cr.query(uri,projs,null,null,null);
                        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        imagePath=cursor.getString(column_index);
                        Toast.makeText(act,"dsd===:"+imagePath,Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(act).setTitle("提示")
                .setMessage("选择图片失败").setPositiveButton("确定", null).create();
        dialog.show();
    }
}
