package com.example.sony.imagemover;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImgFragment extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private ImageView imageView;

    private final String ARG_ID = "id";

    private boolean isSelected = false;

    private int curImgId;

    private Listener listener;

    interface Listener {
        ImgFragment getSelectedFragment();
        void swapImages(int currentFragmentId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ImgFragment newInstance(int id) {
        ImgFragment fragment = new ImgFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout inflateView = (LinearLayout) inflater.inflate(R.layout.img_fragment, container, false);
        linearLayout = (LinearLayout) inflateView.findViewById(R.id.background);

        imageView = (ImageView) inflateView.findViewById(R.id.this_image);
        int img = getImageById(getCurrentFragmentId());
        setCurrentImgId(getCurrentFragmentId());
        setImageResource(img);
        imageView.setOnClickListener(this);
        return inflateView;
    }

    @Override
    public void onClick(View view) {
        if (listener.getSelectedFragment() != null) {
            if (isSelected) {
                deselect();
            } else {
                listener.swapImages(getCurrentFragmentId());
            }
        }
        else {
            select();
        }
    }

    public int getCurrentFragmentId() {
        return getArguments().getInt(ARG_ID);
    }

    public void select() {
        linearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        isSelected = true;
    }

    public void deselect() {
        linearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTransparent));
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getImageById(int id) {
        return getResources()
                .getIdentifier(
                        "img" + id,
                        "drawable",
                        getActivity().getPackageName());
    }

    public int getCurrentImgId() {
        return curImgId;
    }

    public void setCurrentImgId(int curImgId) {
        this.curImgId = curImgId;
    }

    void setImageResource(@DrawableRes int resId) {
        imageView.setImageResource(resId);
    }

    public void setScaleTypeForImage(String scaleType) {
        imageView.setScaleType(ImageView.ScaleType.valueOf(scaleType));
    }
}