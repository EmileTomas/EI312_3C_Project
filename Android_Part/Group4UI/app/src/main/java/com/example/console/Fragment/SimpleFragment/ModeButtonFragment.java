package com.example.console.Fragment.SimpleFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.console.Pub;
import com.example.console.R;

import java.util.ArrayList;

// 对于Direction 其实可以继承ImageView 简化代码，但是对于继承后的强制转化还不熟悉，并且组合相对于继承会有更好的拓展性，就选用组合了
public class ModeButtonFragment extends android.support.v4.app.Fragment {
    private DirectionButton up_arrow;
    private DirectionButton left_arrow;
    private DirectionButton right_arrow;
    private DirectionButton down_arrow;
    private DirectionButton stop;
    private ArrayList<DirectionButton> buttonList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_button, container, false);

        initialize_directionButtons(view);
        initialize_button_list(up_arrow, left_arrow, right_arrow, down_arrow, stop);
        set_click_listener();   //此函数未解耦

        return view;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //TODO now it's visible to user
        } else {
            Log.d("Debuggggg", "onPause: aaaaa");
            //do some stuff
        }
    }

    private void initialize_directionButtons(View view) {
        up_arrow = new DirectionButton(
                (ImageView) view.findViewById(R.id.btn_up_arrow),
                R.drawable.up,
                R.drawable.up_grey);
        left_arrow = new DirectionButton(
                (ImageView) view.findViewById(R.id.btn_left_arrow),
                R.drawable.left,
                R.drawable.left_grey);
        right_arrow = new DirectionButton(
                (ImageView) view.findViewById(R.id.btn_right_arrow),
                R.drawable.right,
                R.drawable.right_grey);
        down_arrow = new DirectionButton (
                (ImageView) view.findViewById(R.id.btn_down_arrow),
                R.drawable.down,
                R.drawable.down_grey);
        stop =  new DirectionButton (
                (ImageView) view.findViewById(R.id.btn_center),
                R.drawable.stop,
                R.drawable.stop_grey);
    }

    private void initialize_button_list( DirectionButton ... directionButtons) {
        buttonList = new ArrayList<>();
        for (DirectionButton element : directionButtons) {
            buttonList.add(element);
        }
    }

    private void set_click_listener() {
        up_arrow.getImageView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                set_direction(up_arrow.getImageViewId(),buttonList);
                Pub.sendMessage("u");
            }
        });
        left_arrow.getImageView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                set_direction(left_arrow.getImageViewId(),buttonList);
                Pub.sendMessage("l");
            }
        });
        right_arrow.getImageView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                set_direction(right_arrow.getImageViewId(),buttonList);
                Pub.sendMessage("r");
            }
        });
        down_arrow.getImageView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                set_direction(down_arrow.getImageViewId(),buttonList);
                Pub.sendMessage("d");
            }
        });
        stop.getImageView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                set_direction(stop.getImageViewId(),buttonList);
                Pub.sendMessage("s");
            }
        });

    }

    private void set_direction(int id,ArrayList<DirectionButton> directionButtons) {
        for(DirectionButton element :directionButtons){
            if(element.getImageViewId()==id){
                element.setSelected();
            }
            else
                element.setUnselected();
        }
    }
}
