package com.example.console;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public  class  RotateTextView  extends  TextView {
	public  RotateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected  void  onDraw(Canvas canvas) {
		canvas.rotate(-90,getMeasuredWidth()/2,getMeasuredHeight()/2);
		super.onDraw(canvas);
	}

}
