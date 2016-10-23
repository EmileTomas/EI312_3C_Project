package com.example.console.Fragment.GestureFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.console.Activity.MainActivity;
import com.example.console.Activity.SelectableButton;
import com.example.console.Activity.SelectableButtonList;
import com.example.console.Adapter.ClickTabsPager;
import com.example.console.R;

import java.util.ArrayList;

public class GesturesFragment extends Fragment implements OnClickListener {
    private SelectableButton btn_gesture_simple;
    private SelectableButton btn_gesture_flexible;
    private SelectableButton btn_gesture_path;
    private SelectableButtonList buttonList;

    private GestureSimpleFragment gesture_simple_frag;
    private GestureFlexibleFragment gesture_flexible_frag;
    private GesturePathFragment gesture_path_frag;

    ClickTabsPager clickTabsPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gesturesfragment, container, false);
        initialize_widgets(view);
        initializeWidgetsClickListener();
        initializeClickTabsPager();
        clickTabsPager.setItemSelected(btn_gesture_simple.getId());
        return view;
    }

    private void initialize_widgets(View view) {
        btn_gesture_simple = (SelectableButton) view.findViewById(R.id.btn_gesture_simple);
        btn_gesture_flexible = (SelectableButton) view.findViewById(R.id.btn_gesture_flexible);
        btn_gesture_path = (SelectableButton) view.findViewById(R.id.btn_gesture_path);
        gesture_simple_frag = new GestureSimpleFragment();
        gesture_flexible_frag = new GestureFlexibleFragment();
        gesture_path_frag = new GesturePathFragment();
    }


    private void initializeWidgetsClickListener() {
        btn_gesture_simple.setOnClickListener(this);
        btn_gesture_flexible.setOnClickListener(this);
        btn_gesture_path.setOnClickListener(this);
    }

    private void initializeClickTabsPager() {
        buttonList = new SelectableButtonList(btn_gesture_simple, btn_gesture_flexible, btn_gesture_path);
        buttonList.setSelectedColor(R.color.white).setUnselectedColor(R.color.sub_theme);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(gesture_simple_frag);
        fragments.add(gesture_flexible_frag);
        fragments.add(gesture_path_frag);

        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        clickTabsPager = new ClickTabsPager(buttonList, fragments, mainActivity.getSupportFragmentManager(),R.id.gesture_content);
    }

    @Override
    public void onClick(View view) {
        clickTabsPager.setItemSelected(view.getId());
    }


}
