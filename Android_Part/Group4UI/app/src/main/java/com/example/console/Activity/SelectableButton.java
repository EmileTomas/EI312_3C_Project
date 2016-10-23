package com.example.console.Activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.console.Interface.Selectable;
import com.example.console.R;

/**
 * Created by Administrator on 2016/10/22.
 */
public class SelectableButton extends Button implements Selectable {
    private int selected_color = R.color.white;
    private int unselected_color = R.color.sub_theme;
    private int selected_image;
    private int unselected_image;

    public SelectableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelected() {
        setTextColor(ContextCompat.getColor(getContext(), selected_color));
        if (selected_image != 0)
            setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(), selected_image), null, null);
    }

    @Override
    public void setUnselected() {
        setTextColor(ContextCompat.getColor(getContext(), unselected_color));
        if (unselected_image != 0)
            setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getContext(), unselected_image), null, null);
    }


    public int getSelectedColor() {
        return selected_color;
    }

    public SelectableButton setSelectedColor(int selected_color) {
        this.selected_color = selected_color;
        return this;
    }

    public int getUnselectedColor() {
        return unselected_color;
    }

    public SelectableButton setUnselectedColor(int unselected_color) {
        this.unselected_color = unselected_color;
        return this;
    }

    public int getSelectedImage() {
        return selected_image;
    }

    public SelectableButton setSelectedImage(int selected_image) {
        this.selected_image = selected_image;
        return this;
    }

    public int getUnselectedImage() {
        return unselected_image;
    }

    public SelectableButton setUnselected_image(int unselected_image) {
        this.unselected_image = unselected_image;
        return this;
    }


}
