package com.xyw.dfood.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyw.dfood.Model.Comment;
import com.xyw.dfood.Model.Food;
import com.xyw.dfood.Model.FoodType;
import com.xyw.dfood.fragment.UserFragment;
import com.xyw.dfood.utils.AsyncSubmit;
import com.xyw.dfood.utils.HttpUtils;
import com.xyw.dfood.utils.PathCreater;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pc on 2015/4/24.
 */
public class FoodBiz implements AsyncSubmit.AsyncSubmitCallBacks {
    private Handler mHandler;
    private Context context;
    public FoodBiz(Handler mHandler,Context context){
        this.mHandler = mHandler;
        this.context = context;
    }

    public void getFoods(int index,int size){
        String url = PathCreater.getInstance(context).getBasepath()+"food/getFoods/page/"+index+"/"+size;
        new Thread(new AsyncSubmit<Food[]>(Food[].class,context,url,0,this)).start();
    }

    public void getFoodsByUser(int index,int size,int uid){
        String url = PathCreater.getInstance(context).getBasepath()+"food/getFoodsUser/page/"+index+"/"+size+"?uid="+ uid;
        new Thread(new AsyncSubmit<Food[]>(Food[].class,context,url,0,this)).start();
    }

    public void getFoodsByType(int index,int size,int tid){
        String url = PathCreater.getInstance(context).getBasepath()+"food/getFoodsType/page/"+index+"/"+size+"?tid="+ tid;
        new Thread(new AsyncSubmit<Food[]>(Food[].class,context,url,0,this)).start();
    }

    public void collect(int uid,int fid,boolean coll){
        String url ;
        if(coll){
             url = PathCreater.getInstance(context).getBasepath()+"user/addIntrest?uid="+uid+"&fid="+fid;
        }else{
            url = PathCreater.getInstance(context).getBasepath()+"user/removeIntrest?uid="+uid+"&fid="+fid;
        }
        new Thread(new AsyncSubmit<Boolean>(Boolean.class,context,url,1,this)).start();
    }

    public void isCollect(int uid,int fid){
        String url = PathCreater.getInstance(context).getBasepath()+"user/IsTrest?uid="+uid+"&fid="+fid;
        new Thread(new AsyncSubmit<Boolean>(Boolean.class,context,url,2,this)).start();
    }

    public void findType(){
        String url = PathCreater.getInstance(context).getBasepath()+"foodType/getTypes";
        new Thread(new AsyncSubmit<FoodType[]>(FoodType[].class,context,url,3,this)).start();
    }

    public void findFoodByStr(int index,int size,String str){
        String url = PathCreater.getInstance(context).getBasepath()+"food/getFoodStr/page/"+index+"/"+size+"?str="+str;
        new Thread(new AsyncSubmit<Food[]>(Food[].class,context,url,4,this)).start();
    }

    public void findFoodByStrType(int index,int size,String str,int tid){
        String url = PathCreater.getInstance(context).getBasepath()+"food/getFoodTS/page/"+index+"/"+size+"?str="+str+"&tid="+tid;
        new Thread(new AsyncSubmit<Food[]>(Food[].class,context,url,4,this)).start();
    }

    public void findComment(int fid){
        String url = PathCreater.getInstance(context).getBasepath()+"comment/getFComments/"+fid;
        new Thread(new AsyncSubmit<Comment[]>(Comment[].class,context,url,5,this)).start();
    }
    public void DeleteComment(int id){
        String url=PathCreater.getInstance(context).getBasepath()+"comment/delComment?id="+id;
        new Thread(new AsyncSubmit<Boolean>(Boolean.class,context,url,6,this)).start();
    }
    public void DelFood(int id){
        String url=PathCreater.getInstance(context).getBasepath()+"food/delFood?id="+id;
        new Thread(new AsyncSubmit<Boolean>(Boolean.class,context,url,6,this)).start();
    }
    public void addFood(final String name,final FoodType type,final String d,final String imagePath){
        new Thread(){
            @Override
            public void run() {
                try {
                    String url = PathCreater.getInstance(context).getBasepath() + "food/addFood";
                    MultiValueMap<String,Object> formData=new LinkedMultiValueMap<String,Object>();
                    FileSystemResource file=new FileSystemResource(imagePath);
                    Food food=new Food();
                    food.setUser(UserFragment.loginUser);
                    food.setFoodType(type);
                    food.setName(name);
                    food.setDescription(d);
                    ObjectMapper objectMapper=new ObjectMapper();
                    String foodJson ="";
                    try {
                        foodJson=objectMapper.writeValueAsString(food);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    formData.add("file",file);
                    formData.add("foodJson", foodJson);
                    HttpHeaders httpHeaders=new HttpHeaders();
                    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                    HttpEntity<MultiValueMap<String,Object>> requestEntity=new HttpEntity<>(formData,httpHeaders);
                    RestTemplate restTemplate=new RestTemplate(HttpUtils.getResquestFactory());
                    ResponseEntity<Boolean> responseEntity=restTemplate.exchange(url, HttpMethod.POST,requestEntity,Boolean.class);
                    Message msg=mHandler.obtainMessage();
                    if(responseEntity.getBody()){
                        msg.what=11;
                    }else{
                        msg.what=100;
                    }
                    msg.sendToTarget();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                mHandler.sendEmptyMessage(100);
            }
        }.start();
    }

    @Override
    public void callBacks(int request, ResponseEntity result) {
        if(result.getBody()!=null){
            Object o =   result.getBody();
            if(o == null){
                mHandler.sendEmptyMessage(100);
                return;
            }
            if(request == 0){
                List x=Arrays.asList(((ResponseEntity<Food[]>)result).getBody());
                List<Food> list = new ArrayList<Food>(x);
                Message msg = mHandler.obtainMessage();
                msg.obj = list;
                msg.what = 0;
                msg.sendToTarget();
            }else if(request == 1){
                Boolean boo = ((ResponseEntity<Boolean>)result).getBody();
                if(boo) {
                    mHandler.sendEmptyMessage(0);
                }else{
                    mHandler.sendEmptyMessage(100);
                }
            }else if(request == 2){
                Boolean boo = ((ResponseEntity<Boolean>)result).getBody();
                if(boo) {
                    mHandler.sendEmptyMessage(1);
                }else{
                    mHandler.sendEmptyMessage(101);
                }
            }else if(request == 3){
                List<FoodType> list = new ArrayList<FoodType>(Arrays.asList(((ResponseEntity<FoodType[]>)result).getBody()));
                Message msg = mHandler.obtainMessage();
                msg.obj = list;
                msg.what = 1;
                msg.sendToTarget();
            }else if(request == 4){
                List<Food> list = new ArrayList<Food>(Arrays.asList(((ResponseEntity<Food[]>)result).getBody()));
                Message msg = mHandler.obtainMessage();
                msg.obj = list;
                msg.what = 0;
                msg.sendToTarget();
            }else if(request == 5){
                List<Comment> list = new ArrayList<Comment>(Arrays.asList(((ResponseEntity<Comment[]>)result).getBody()));
                Message msg = mHandler.obtainMessage();
                msg.obj = list;
                msg.what = 0;
                msg.sendToTarget();
            }else if(request==6){
                boolean deleteYes=((ResponseEntity<Boolean>)result).getBody();
                if(deleteYes){
                    mHandler.sendEmptyMessage(1008);
                }else{
                    mHandler.sendEmptyMessage(1002);
                }
            }
        }else{
                Message msg=mHandler.obtainMessage();
                msg.what=1004;
                msg.sendToTarget();
        }
    }
}
