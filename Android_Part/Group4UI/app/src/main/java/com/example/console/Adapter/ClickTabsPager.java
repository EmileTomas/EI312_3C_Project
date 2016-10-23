package com.example.console.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.console.Activity.SelectableButton;
import com.example.console.Activity.SelectableButtonList;
import com.example.console.Interface.ItemSelectable;
import com.example.console.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/23.
 */
public class ClickTabsPager implements ItemSelectable {
    private SelectableButtonList buttons;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private int contentID;


    public ClickTabsPager(SelectableButtonList buttons, ArrayList<Fragment> fragments, FragmentManager fragmentManager, int contentID) {
        this.buttons = buttons;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.contentID = contentID;

    }


    public void setItemSelected(int button_id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int index = buttons.setSelectedAndGetIndex(button_id);
        Fragment fragment = fragments.get(index);

        hideFragments(fragmentTransaction);
        if (fragmentManager.findFragmentById(fragment.getId()) == null)
            fragmentTransaction.add(contentID, fragment);
        else
            fragmentTransaction.show(fragment);

        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction ft) {
        for (Fragment element : fragments) {
            if(fragmentManager.findFragmentById(element.getId()) != null)
                ft.hide(element);
        }
    }

}
