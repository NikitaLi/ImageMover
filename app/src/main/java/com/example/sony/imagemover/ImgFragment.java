package com.example.sony.imagemover;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImgFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_ID = "id";

    private int id;
    private boolean isSelected = false;

    private LinearLayout mLinearLayout;
    private ImageView mImageView;

    public ImgFragment newInstance(int id) {
        ImgFragment fragment = new ImgFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout inflateView = (LinearLayout) inflater.inflate(R.layout.img_fragment, container, false);
        mLinearLayout = (LinearLayout) inflateView.findViewById(R.id.background);
        mImageView = (ImageView) inflateView.findViewById(R.id.this_image);
        Drawable itemImg = ResourcesCompat.getDrawable(getResources(), getResources()
                .getIdentifier("img"+id, "drawable", MainActivity.PACKAGE_NAME), null);
        mImageView.setImageDrawable(itemImg);
        mImageView.setOnClickListener(this);
        return inflateView;
    }

    @Override
    public void onClick(View view) {
        if (isSelected) {
            deselect();
        } else {
            select();
        }
    }

    public void select() {
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        isSelected = true;
    }

    public void deselect() {
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTransparent));
        isSelected = false;
    }
}