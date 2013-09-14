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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;


public class CollectionProvider extends ContentProvider{

	public static final String AUTHORITY = "com.bmarohnic.java2week2.collectionprovider";
	
	public static class DealData implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.bmarohnic.java2week2.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.bmarohnic.java2week2.item";
		
		// Define the table columns
		public static final String RESTAURANT_COLUMN = "restaurant";
		public static final String DEAL_COLUMN = "dealTitle";
		public static final String CITY_COLUMN = "city";
		
		public static final String[] PROJECTION = {"_Id", RESTAURANT_COLUMN, DEAL_COLUMN, CITY_COLUMN};
		
		private DealData() {};
		
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;
	public static final int ITEMS_RESTAURANT = 3;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/#", ITEMS_ID);
		uriMatcher.addURI(AUTHORITY, "items/restaurant/*", ITEMS_RESTAURANT);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch(uriMatcher.match(uri)) {
		case ITEMS:
			return DealData.CONTENT_TYPE;
			
		case ITEMS_ID:
		case ITEMS_RESTAURANT:
			return DealData.CONTENT_ITEM_TYPE;
			
		}
		
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		int numberOfObjects = 0;
		MatrixCursor result = new MatrixCursor(DealData.PROJECTION);
		String restaurant;
		String dealTitle;
		String city;
		JSONObject jo;
		
		String JSONString = FileStuff.readStringFile(getContext(), "JSONData.txt");
		JSONArray inputArray = null;
		
		try {
			inputArray = new JSONArray(JSONString);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		numberOfObjects = inputArray.length();
		Log.i("CollectionProvider", "The number of objects is: " + numberOfObjects);
		
		switch(uriMatcher.match(uri)) {
		case ITEMS:
			
			for (int i = 0; i < numberOfObjects; i++)
			{
				try {
					jo = inputArray.getJSONObject(i);
					restaurant = jo.getString("name");
					dealTitle = jo.getString("dealTitle");
					city = jo.getString("city");
					
					result.addRow(new Object[] { i + 1, restaurant, dealTitle, city});
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			break;
			
		case ITEMS_ID:
			
			String itemId = uri.getLastPathSegment();
			Log.i("queryId", itemId);
			
			int index;
			
			try {
				index = Integer.parseInt(itemId);
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
				Log.e("query", "index format error");
				break;
			}
			
			if (index <= 0 || index > numberOfObjects){
				Log.e("query", "index out of range for " + uri.toString());
				break;
			}
			
			jo = null;
			try {
				jo = inputArray.getJSONObject(index - 1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			restaurant = null;
			try {
				restaurant = jo.getString("name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dealTitle = null;
			try {
				dealTitle = jo.getString("dealTitle");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			city = null;
			try {
				city = jo.getString("city");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result.addRow(new Object[] { index, restaurant, dealTitle, city});
			
			break;
			
		case ITEMS_RESTAURANT:
			
			String restName = uri.getLastPathSegment().toString();
			
			
			for (int i = 0; i < numberOfObjects; i++)
			{
				try {
					jo = inputArray.getJSONObject(i);
					restaurant = jo.getString("name");
					dealTitle = jo.getString("dealTitle");
					city = jo.getString("city");
					
					if (restaurant.contentEquals(restName))
					{
						result.addRow(new Object[] { i + 1, restaurant, dealTitle, city});
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			break;
			
			default:
				Log.e("query", "invalid uri = " + uri.toString());
			
		}
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	
}
