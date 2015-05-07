package com.xyw.dfood.entityDaoImpl;

import android.content.Context;

import com.xyw.dfood.Model.User;
import com.xyw.dfood.entityDao.UserDao;
import com.xyw.dfood.utils.PathCreater;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Do on 2015/4/23.
 */
public class UserDaoImpl implements UserDao {
    private Context context;
    public UserDaoImpl(Context context){
        this.context=context;
    }
    @Override
    public boolean registUser(User user) {
        String url= PathCreater.getInstance(context).getBasepath()+"user/addUser";
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity=new HttpEntity<User>(user,headers);
        ResponseEntity<Boolean> out=restTemplate.exchange(url, HttpMethod.POST,entity,Boolean.class);
        return out.getBody();
    }

    @Override
    public boolean login(User user) {
        String url= PathCreater.getInstance(context).getBasepath()+"user/login";
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity=new HttpEntity<User>(user,headers);
        ResponseEntity<Boolean> out=restTemplate.exchange(url, HttpMethod.POST,entity,Boolean.class);
        return out.getBody();
    }

    @Override
    public List<User> getEntitys() {
        String url= PathCreater.getInstance(context).getBasepath()+"user/getAllUser";
        HttpHeaders requestHeaders=new HttpHeaders();
        List<MediaType> acceptableMediaTypes=new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<?> requestHttpEntity=new HttpEntity<Object>(requestHeaders);
        RestTemplate res=new RestTemplate();
        res.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<User[]> responseEntity=res.exchange(url, HttpMethod.GET,requestHttpEntity,User[].class);
        List<User> list= Arrays.asList(responseEntity.getBody());
        return list;
    }

    @Override
    public boolean deleteEntity(int id) {
        return false;
    }

    @Override
    public User getEntity(int id) {
        String url= PathCreater.getInstance(context).getBasepath()+"user/getUser?+id="+id;
        HttpHeaders requestHeaders=new HttpHeaders();
        List<MediaType> acceptableMediaTypes=new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<?> requestHttpEntity=new HttpEntity<Object>(requestHeaders);
        RestTemplate res=new RestTemplate();
        res.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<User> responseEntity=res.exchange(url, HttpMethod.GET,requestHttpEntity,User.class);
        User user= responseEntity.getBody();
        return user;
    }

    @Override
    public User UpdateEntity(User user) {
        String url= PathCreater.getInstance(context).getBasepath()+"user/updateUser";
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestHttpEntity=new HttpEntity<User>(user,headers);
        ResponseEntity<User> responseEntity=restTemplate.exchange(url, HttpMethod.GET,requestHttpEntity,User.class);
        User user1= responseEntity.getBody();
        return user1;
    }
}
