package com.example.console.Fragment.SimpleFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.console.Activity.MainActivity;
import com.example.console.Adapter.TabsPagerAdapter;
import com.example.console.R;

import java.util.ArrayList;

public class SimpleFragment extends android.support.v4.app.Fragment{
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private ArrayList<android.support.v4.app.Fragment> list_fragment;
	private ArrayList<String> list_tab;
	private TabsPagerAdapter tabsPagerAdapter;
	/*
	private Button btn_mode_button;
	private Button btn_mode_gravity;
	private Button btn_mode_voice;
	*/
	private MainActivity mainActivity;
	private ModeButtonFragment mode_button_fragment;
	private ModeGravityFragment mode_gravity_fragment;
	private ModeVoiceFragment mode_voice_fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_simple, container, false);
		mainActivity = (MainActivity) getActivity();

		tabLayout = (TabLayout) view.findViewById(R.id.simple_frag_tablayout);
		viewPager = (ViewPager) view.findViewById(R.id.simple_frag_pager);

		mode_button_fragment = new ModeButtonFragment();
		mode_gravity_fragment = new ModeGravityFragment();
		mode_voice_fragment = new ModeVoiceFragment();

		list_fragment = new ArrayList<>();
		list_fragment.add(mode_button_fragment);
		list_fragment.add(mode_gravity_fragment);
		list_fragment.add(mode_voice_fragment);

		list_tab = new ArrayList<>();
		list_tab.add("Button");
		list_tab.add("Gravity");
		list_tab.add("Voice");

		//set tabs
		tabLayout.addTab(tabLayout.newTab().setText(list_tab.get(0)));
		tabLayout.addTab(tabLayout.newTab().setText(list_tab.get(1)));
		tabLayout.addTab(tabLayout.newTab().setText(list_tab.get(2)));

		//set Adapter for ViewPager
		tabsPagerAdapter = new TabsPagerAdapter(mainActivity.getSupportFragmentManager(),list_fragment, list_tab);
		viewPager.setAdapter(tabsPagerAdapter);
		viewPager.setOffscreenPageLimit(3);

		//TabLayout加载viewpager
		tabLayout.setupWithViewPager(viewPager, true);

		return view;
	}
}
