package com.example.java2week2;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebStuff {
	
	static Boolean _conn = false;
	static String _connectionType = "Unavailable";
	
	/**
	 * Gets the connection type.
	 *
	 * @param context the context
	 * @return the connection type
	 */
	public static String getConnectionType(Context context){
		netInfo(context);
		return _connectionType;
		
	}
	
	/**
	 * Gets the connection status.
	 *
	 * @param context the context
	 * @return the connection status
	 */
	public static Boolean getConnectionStatus(Context context){
		netInfo(context);
		return _conn;
	}
	
	
	/**
	 * Net info.
	 *
	 * @param context the context
	 */
	private static void netInfo(Context context){
		ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
		
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni != null){
			if(ni.isConnected()){
				_connectionType = ni.getTypeName();
				_conn = true;
				
			}
		}
	}
	
	/**
	 * Gets the uRL string response.
	 *
	 * @param url the url
	 * @return the uRL string response
	 */
	public static String getURLStringResponse(URL url){
		String response = "";
		
		try{
			URLConnection conn = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
			
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			
			while((bytesRead = bin.read(contentBytes)) != -1){
				Log.i("getURLStringReponse", "Called");
				response = new String(contentBytes, 0, bytesRead);
				responseBuffer.append(response);
			}
			return responseBuffer.toString();
		} catch(Exception e){
			Log.e("URL Response Error", "getURLStringResponse");
			
		}
		
		return response;
	}
}

