package com.example.console;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GestureFlexibleFragment extends android.support.v4.app.Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		Log.d("fuck","GestureFlexibleFragment");
		
		return inflater.inflate(R.layout.gestureflexiblefragment, container, false);
	}
}
