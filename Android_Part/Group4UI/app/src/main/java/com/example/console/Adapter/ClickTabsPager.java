package com.example.console.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.console.Activity.MainActivity;
import com.example.console.Activity.SelectableButton;
import com.example.console.Activity.SelectableButtonList;
import com.example.console.Interface.ItemSelectable;
import com.example.console.MyApplication;
import com.example.console.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/23.
 */
public class ClickTabsPager implements ItemSelectable {
    private SelectableButtonList buttons;
    private ArrayList<String> fragments;
    private FragmentManager fragmentManager;
    private Context context;
    private int contentID;


    public ClickTabsPager(SelectableButtonList buttons, ArrayList<String> fragments,FragmentManager fragmentManager, int contentID) {
        this.buttons = buttons;
        this.fragments = fragments;
        this.fragmentManager=fragmentManager;
        this.contentID = contentID;
        this.context= MyApplication.getContext();
    }


    public void setItemSelected(int button_id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int index = buttons.setSelectedAndGetIndex(button_id);

        Log.d("Debuggg",  fragments.get(index));

        Fragment fragment = Fragment.instantiate(context, fragments.get(index));
        fragmentTransaction.replace(contentID,fragment);
        fragmentTransaction.commit();
    }
}
