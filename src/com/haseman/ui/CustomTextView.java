package com.haseman.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView{

	boolean selfChange = false;
	int colorArray[] = new int[]{Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.DKGRAY};
	
	public CustomTextView(Context context, AttributeSet attributeSet, int defSytle){
		super(context, attributeSet, defSytle);
	}
	public CustomTextView(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}
	public CustomTextView(Context context) {
		super(context);
	}
	
	protected void onTextChanged(CharSequence text, int start, int before, int after ){
		//Keep the view from getting into an infitie loop
		if(selfChange){
			selfChange = false;
			return;
		}
		selfChange=true;
		
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		builder.clearSpans();
		ForegroundColorSpan colorSpan;
		int color;
		for(int i=0; i < text.length(); i++){
			//pick the next color
			color = colorArray[i%colorArray.length];
			//Create the color span
			colorSpan = new ForegroundColorSpan(color);
			//Add the color span for this char
			builder.setSpan(colorSpan, i, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		setText(builder);
	}

}

