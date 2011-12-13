package com.haseman.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UiDemoActivity extends Activity implements OnClickListener {
	
	@Override
	public void onClick(View v) {
		String urls[] = new String[]{"http://wanderingoak.net/shuttle.jpg","http://wanderingoak.net/unseen.jpeg", "http://wanderingoak.net/bridge.jpg"};
		Intent i = new Intent("com.haseman.showImageUrls");
		i.putExtra(ImageGalleryActivity.EXTRA_URLS, urls);
		i.putExtra(ImageGalleryActivity.EXTRA_INDEX, 0);
		startActivity(i);
		finish();
		
	}
	private View.OnClickListener mClickListenerTwo = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//Do button two stuff here
			
		}
	};
	
	
	
//	/** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_layout_absolute);
//        
//    }
    
    //Custom Text View code
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	CustomTextView ctv = new CustomTextView(getApplicationContext());
    	ctv.setText("Look at all my different colors!");
    	setContentView(ctv);

    	View v = findViewById(R.id.launch_button);
    	v.setOnClickListener(this);
    }
	
}