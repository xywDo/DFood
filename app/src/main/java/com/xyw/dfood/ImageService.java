package com.xyw.dfood;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageService {
	public static Bitmap getImage(String path) throws IOException{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(6000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode()==200){
			InputStream inStream=conn.getInputStream();
			Bitmap bitmap=BitmapFactory.decodeStream(inStream);
			inStream.close();
			return bitmap;
		}
		return null;
	}
}
