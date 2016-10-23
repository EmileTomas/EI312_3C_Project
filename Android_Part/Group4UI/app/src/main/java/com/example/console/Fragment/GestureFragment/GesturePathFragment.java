package com.example.console.Fragment.GestureFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.console.R;

public class GesturePathFragment extends android.support.v4.app.Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.d("fuck","GesturePathFragment");
		return inflater.inflate(R.layout.gesturepathfragment, container, false);
	}
}
