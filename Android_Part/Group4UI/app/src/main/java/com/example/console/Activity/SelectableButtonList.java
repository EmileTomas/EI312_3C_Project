package com.example.console.Activity;

import com.example.console.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/23.
 */
public class SelectableButtonList{
    private ArrayList<SelectableButton> buttonList=new ArrayList<>();

    public SelectableButtonList(SelectableButton... buttons){
        for(SelectableButton element: buttons){
            buttonList.add(element);
        }
    }

    public int setSelectedAndGetIndex(int selected_button_id){
        SelectableButton element;
        int index=-1;
        for(int i=0;i<buttonList.size();++i){
            element=buttonList.get(i);
            if(element.getId()==selected_button_id){
                element.setSelected();
                index=i;
            }
            else
                element.setUnselected();
        }
        return index;
    }

    public SelectableButtonList setSelectedColor(int color_id){
        for(SelectableButton element: buttonList)
            element.setSelectedColor(color_id);
        return this;
    }
    public SelectableButtonList setUnselectedColor(int color_id){
        for(SelectableButton element: buttonList)
            element.setUnselectedColor(color_id);
        return this;
    }

    public ArrayList<SelectableButton> getButtonList() {
        return buttonList;
    }

    public SelectableButtonList setButtonList(ArrayList<SelectableButton> buttonList) {
        this.buttonList = buttonList;
        return this;
    }

}
