package com.example.sony.imagemover;

import android.app.FragmentManager;
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

    private boolean isSelected = false;
    private int cur_img_id = 0;

    private LinearLayout mLinearLayout;
    private ImageView mImageView;

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
        mLinearLayout = (LinearLayout) inflateView.findViewById(R.id.background);

        mImageView = (ImageView) inflateView.findViewById(R.id.this_image);
        Drawable img = getImageByID(getArguments().getInt(ARG_ID));
        mImageView.setImageDrawable(img);
        mImageView.setOnClickListener(this);
        return inflateView;
    }

    @Override
    public void onClick(View view) {

        int currentFragmentId = getArguments().getInt(ARG_ID);

        if (isSelected) {
            deselect();
            Activity2.prevImgID = 0;
        } else {
            select();
            if (Activity2.prevImgID == 0) {
                Activity2.prevImgID = currentFragmentId;
            }
            else {
                if (cur_img_id == 0) {
                    cur_img_id = currentFragmentId;
                }

                FragmentManager mFragmentManager = getFragmentManager();
                ImgFragment prevFragment = (ImgFragment) mFragmentManager.findFragmentByTag(Integer.toString(Activity2.prevImgID));

                int previous_img_id;
                if (prevFragment.cur_img_id == 0) {
                    previous_img_id = Activity2.prevImgID;
                }
                else {
                    previous_img_id = prevFragment.cur_img_id;
                }

                Drawable imgPrevious = getImageByID(previous_img_id);
                Drawable imgCurrent = getImageByID(cur_img_id);

                mImageView.setImageDrawable(imgPrevious);

                prevFragment.mImageView.setImageDrawable(imgCurrent);
                prevFragment.cur_img_id = cur_img_id;
                prevFragment.deselect();

                deselect();
                cur_img_id = previous_img_id;
                Activity2.prevImgID = 0;
            }
        }
    }

    public Drawable getImageByID(int ID) {
        return ResourcesCompat.getDrawable(getResources(), getResources()
                        .getIdentifier(
                                "img"+ID,
                                "drawable",
                                MainActivity.PACKAGE_NAME),
                null
        );
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