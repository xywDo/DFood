package com.xyw.dfood;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.xyw.dfood.Model.User;
import com.xyw.dfood.utils.PathCreater;

import org.apache.http.Header;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class UserService {
//	private static Gson gson;
//	static{
//		gson=new Gson();
//	}
	public static List<User> getUserList() throws IOException{
//		URL url=new URL("http://192.168.0.111:8080/test_ssh/user/getAllUser");
//		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//		conn.setConnectTimeout(6000);
//		conn.setRequestMethod("GET");
//		if(conn.getResponseCode()==200){
//			InputStream in=conn.getInputStream();
//			byte[] byteCode=StreaMTool.read(in);
//			String s=new String(byteCode,"UTF-8");
//			if(s!=null){
//				Gson json=new Gson();
//				List<User> list=json.fromJson(s,new TypeToken<List<User>>(){}.getType());
//				if(list!=null){
//					return list;
//				}
//			}
//			return null;
//		}
		return null;
		
	}
	public static boolean PostData(User user,Activity activity) throws IOException{
//		URL url=new URL("http://192.168.0.104:8080/test_ssh/user/addUser");
//		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//		conn.setConnectTimeout(6000);
//		conn.setRequestMethod("POST");
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
//		conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Charset", "utf-8");
//		String s=gson.toJson(user);
//		OutputStream ou=conn.getOutputStream();
//		ou.write(s.getBytes());
//		ou.flush();
//		ou.close();
//		if(conn.getResponseCode()==200){
//
//			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String result="";
//			String readLine=null;
//			Toast.makeText(activity, "成功了", Toast.LENGTH_SHORT).show();
//			while((readLine=bufferedReader.readLine())!=null){
//				result+=readLine;
//			}
//			bufferedReader.close();
//			conn.disconnect();
//			return true;
//		}
//		Toast.makeText(activity,"Return Code:"+conn.getResponseCode(),Toast.LENGTH_SHORT).show();
//		Toast.makeText(activity, "shibai功了", Toast.LENGTH_SHORT).show();
        String url= PathCreater.getInstance(activity).getBasepath()+"/user/addUser";
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity=new HttpEntity<User>(user,headers);
        ResponseEntity<Boolean> out=restTemplate.exchange(url, HttpMethod.POST,entity,Boolean.class);
        if(out.getBody()){
            return true;
        }
		return false;
		
	}
    public static List<User> getUsers(Context context){
        String url=PathCreater.getInstance(context).getBasepath()+"user/getAllUser";
        HttpHeaders requestHeaders=new HttpHeaders();
        List<MediaType> acceptableMediaTypes=new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<?> requestHttpEntity=new HttpEntity<Object>(requestHeaders);
        RestTemplate res=new RestTemplate();
        res.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<User[]> responseEntity=res.exchange(url,HttpMethod.GET,requestHttpEntity,User[].class);
        List<User> list= Arrays.asList(responseEntity.getBody());
        return list;
    }
}
