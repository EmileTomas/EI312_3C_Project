package com.example.console.Fragment.SimpleFragment;

import android.widget.ImageView;

import com.example.console.Interface.Selectable;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DirectionButton implements Selectable {
    private ImageView imageView;
    private int unselected_image_id;
    private int selected_image_id;

    DirectionButton(ImageView imageView, int unselected_image_id, int selected_image_id) {
        this.imageView=imageView;
        this.unselected_image_id = unselected_image_id;
        this.selected_image_id = selected_image_id;
    }

    @Override
    public void setSelected() {
        imageView.setImageResource(selected_image_id);
    }

    @Override
    public void setUnselected() {
        imageView.setImageResource(unselected_image_id);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    public int getImageViewId(){
        return this.imageView.getId();
    }
}
