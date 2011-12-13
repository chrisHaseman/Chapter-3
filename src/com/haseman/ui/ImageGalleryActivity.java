package com.haseman.ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageGalleryActivity extends Activity{

	public static final String EXTRA_URLS = "urls";
	public static final String EXTRA_INDEX = "currentImage";
	
	private Bitmap bitmap;
	private ImageDownloader id;
	private int curIndex;
	private String urls[];
	
//	public void onCreate(Bundle extra){
//		try{
//			super.onCreate(extra);
//			//Thread.sleep(10000);
//			URL url = new URL("http://wanderingoak.net/bridge.png");
//			HttpURLConnection httpCon = (HttpURLConnection)url.openConnection();
//			if(httpCon.getResponseCode() != 200)
//				throw new Exception("Failed to connect");
//			InputStream is = httpCon.getInputStream();
//			Bitmap bitmap = BitmapFactory.decodeStream(is);
//			ImageView iv = (ImageView)findViewById(R.id.main_image);
//			if(iv!=null)
//				iv.setImageBitmap(bitmap);
//		}catch(Exception e){
//			Log.e("ImageFetching","Didn't work!",e);
//		}
//	}
	@Override
	public void onCreate(Bundle extras){
		super.onCreate(extras);
		setContentView(R.layout.image_layout_linear);
		
		urls = getIntent().getExtras().getStringArray(EXTRA_URLS);
		curIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
		
		View button = findViewById(R.id.next);
		if(button!= null)
			button.setOnClickListener(mClickListener);
		button = findViewById(R.id.prev);
		if(button!=null)
			button.setOnClickListener(mClickListener);
			
		TextView tv = (TextView)findViewById(R.id.url_view);
		if(tv!=null)
			tv.setText(urls[curIndex]);
		
		ImageView iv = (ImageView)findViewById(R.id.main_image);
		iv.setOnClickListener(mClickListener);
		
//		bitmap = (Bitmap)getLastNonConfigurationInstance();
//		if(bitmap != null){
//			iv.setImageBitmap(bitmap);
//			return;
//		}
		
		id = new ImageDownloader();
		id.execute(urls[curIndex]);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(id!=null){
			id.cancel(true);
		}
	}
	
	private class ImageDownloader extends AsyncTask<String, Integer, Bitmap>{

		@Override
		protected void onPreExecute(){
			//Setup is done here
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{ 
				URL url = new URL(params[0]);
				HttpURLConnection httpCon = (HttpURLConnection)url.openConnection();
				if(httpCon.getResponseCode() != 200)
					throw new Exception("Failed to connect");
				InputStream is = httpCon.getInputStream();

				return BitmapFactory.decodeStream(is);
			}catch(Exception e){
				Log.e("Image","Failed to load image",e);
			}
			return null;
		} 
		
		protected void onPostExecute(Bitmap img){
			bitmap = img;
			ImageView iv = (ImageView)findViewById(R.id.main_image);
			if(iv!=null && img!=null){
				iv.setImageBitmap(img);
			}
			else
				Toast.makeText(ImageGalleryActivity.this, "Failed to download image", Toast.LENGTH_LONG).show();
		}
		protected void onCancelled(){
		}
	}
	public Object onRetainNonConfigurationInstance(){
		super.onRetainNonConfigurationInstance();
		if(bitmap != null)
			return bitmap;
		return null;
	}
	private void showHideButtons(){
		View bv = findViewById(R.id.button_bar);
		if(bv!= null){
			if(bv.getVisibility() == View.INVISIBLE)
				bv.setVisibility(View.VISIBLE);
			else if(bv.getVisibility() == View.VISIBLE)
				bv.setVisibility(View.INVISIBLE);
		}
		return;
	}
	View.OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.next){
				curIndex++;
				if(curIndex >= urls.length)
					curIndex = 0;
			}
			else if(v.getId() == R.id.prev){
				curIndex--;
				if(curIndex < 0)
					curIndex = urls.length-1;
			}
			else if(v.getId() ==R.id.main_image){
				showHideButtons();
				return;
			}
			else
				return;
			
			Intent i = new Intent(ImageGalleryActivity.this, ImageGalleryActivity.class);
			i.putExtra(EXTRA_URLS, urls);
			i.putExtra(EXTRA_INDEX, curIndex);
			startActivity(i);
			finish();
		}
	};
}
