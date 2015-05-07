package com.xyw.dfood.utils;

import android.content.Context;
import android.os.Build;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2015/4/24.
 */
public class HttpUtils {
    private static final int CONNECT_TIMEOUT = 15000;// 30秒
    private static final int READ_TIMEOUT = 8000;// 15秒
    private static SimpleClientHttpRequestFactory simpleFactory;
    private static HttpComponentsClientHttpRequestFactory httpComponentsFactory;
    static{
        simpleFactory=new SimpleClientHttpRequestFactory();
        simpleFactory.setReadTimeout(READ_TIMEOUT);
        simpleFactory.setConnectTimeout(CONNECT_TIMEOUT);
        httpComponentsFactory=new HttpComponentsClientHttpRequestFactory();
        httpComponentsFactory.setConnectTimeout(CONNECT_TIMEOUT);
        httpComponentsFactory.setReadTimeout(READ_TIMEOUT);
    }
    public static ClientHttpRequestFactory getResquestFactory() {
        // 当Android版本大于2.3时
        if (Build.VERSION.SDK_INT >= 9) {
            return simpleFactory;
        }
        return httpComponentsFactory;
    }
    public static <E> ResponseEntity<E> get(Context context,String url,Class<E> clazz){
        HttpHeaders requestHeaders=new HttpHeaders();
        List<MediaType> acceptableMediaTypes=new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<?> requestHttpEntity=new HttpEntity<Object>(requestHeaders);
        RestTemplate restTemplate=new RestTemplate(getResquestFactory());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<E> responseEntity=restTemplate.exchange(url, HttpMethod.GET,requestHttpEntity,clazz);
        return responseEntity;
    }

    public static <E> ResponseEntity<E> post(Context context,String url,Object o,Class<E> clazz){
        HttpHeaders requestHeaders=new HttpHeaders();
        List<MediaType> acceptableMediaTypes=new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<?> requestHttpEntity=new HttpEntity<Object>(o,requestHeaders);
        RestTemplate restTemplate=new RestTemplate(getResquestFactory());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<E> responseEntity=restTemplate.exchange(url, HttpMethod.POST,requestHttpEntity,clazz);
        return responseEntity;
    }
    /**
     * ƴ��rest����URL��ע�������˳��
     */
    public static String spliceRestURL(String prefix, Object... params) {
        StringBuilder builder = new StringBuilder(prefix);
        for (Object p : params) {
            builder.append("/").append(p);
        }
        return builder.toString();
    }
}
