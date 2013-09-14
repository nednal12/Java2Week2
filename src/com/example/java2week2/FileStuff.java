/*
 * project		Java2Week2
 * 
 * package		com.example.java2week2
 * 
 * @author		Brent Marohnic
 * 
 * date			Sep 14, 2013
 */
package com.example.java2week2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class FileStuff {
	
	/**
	 * Store string file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param content the content
	 * @return the boolean
	 */
	public static Boolean storeStringFile(Context context, String filename, String content){
		try{
			FileOutputStream fos = null;
			
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			
			fos.write(content.getBytes());
			fos.close();
		} catch (IOException e) {
			Log.e("Write Error", filename);
		}
		
		return true;
	}
	
	public static String readStringFile(Context context, String filename) {
		
		
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer fileContent = new StringBuffer("");
		byte[] buffer = new byte[1024];
			
			try {
				int length = -1;
				while ((length = fis.read(buffer)) != -1) {
					fileContent.append(new String(buffer));
				}
				if(length == 0){
					Log.i("MEANINGLESS", "Eliminating 'Variable Not Used' warning!");
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			      
			return fileContent.toString();
		
	}
	
}


