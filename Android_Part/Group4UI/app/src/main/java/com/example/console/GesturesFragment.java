package com.example.console;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.console.Activity.MainActivity;

public class GesturesFragment extends android.support.v4.app.Fragment{
	private Button btn_gesture_simple;
	private Button btn_gesture_flexible;
	private Button btn_gesture_path;
	
	private GestureSimpleFragment gesture_simple_frag;
	private GestureFlexibleFragment gesture_flexible_frag;
	private GesturePathFragment gesture_path_frag;
	private MainActivity mainActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.gesturesfragment, container, false);
		mainActivity = (MainActivity) getActivity();
		btn_gesture_simple = (Button)view.findViewById(R.id.btn_gesture_simple);
		btn_gesture_flexible = (Button)view.findViewById(R.id.btn_gesture_flexible);
		btn_gesture_path = (Button)view.findViewById(R.id.btn_gesture_path);
		
		setDefaultFragment();
		btn_gesture_simple.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_gesture_simple.setTextColor(getResources().getColor(R.color.subtab));
				btn_gesture_simple.setBackgroundColor(getResources().getColor(R.color.background));
				btn_gesture_flexible.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_flexible.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_gesture_path.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_path.setBackgroundColor(getResources().getColor(R.color.subtab));
				FragmentManager fm = mainActivity.getSupportFragmentManager();
				android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
				gesture_simple_frag = new GestureSimpleFragment();
				transaction.replace(R.id.id_gesture_content, gesture_simple_frag);
				transaction.commit();
			}
		});
		
		btn_gesture_flexible.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_gesture_simple.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_simple.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_gesture_flexible.setTextColor(getResources().getColor(R.color.subtab));
				btn_gesture_flexible.setBackgroundColor(getResources().getColor(R.color.background));
				btn_gesture_path.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_path.setBackgroundColor(getResources().getColor(R.color.subtab));
				FragmentManager fm =mainActivity.getSupportFragmentManager();
				android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
				gesture_flexible_frag = new GestureFlexibleFragment();
				transaction.replace(R.id.id_gesture_content, gesture_flexible_frag);
				transaction.commit();
			}
		});
		
		btn_gesture_path.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				btn_gesture_simple.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_simple.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_gesture_flexible.setTextColor(getResources().getColor(R.color.white));
				btn_gesture_flexible.setBackgroundColor(getResources().getColor(R.color.subtab));
				btn_gesture_path.setTextColor(getResources().getColor(R.color.subtab));
				btn_gesture_path.setBackgroundColor(getResources().getColor(R.color.background));
				FragmentManager fm = mainActivity.getSupportFragmentManager();
				android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
				gesture_path_frag = new GesturePathFragment();
				transaction.replace(R.id.id_gesture_content, gesture_path_frag);
				transaction.commit();
			}
		});
		return view;
	}
	
	private void setDefaultFragment(){
		FragmentManager fm = mainActivity.getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
		gesture_simple_frag = new GestureSimpleFragment();
		transaction.replace(R.id.id_gesture_content, gesture_simple_frag);
		transaction.commit();
	}
}
