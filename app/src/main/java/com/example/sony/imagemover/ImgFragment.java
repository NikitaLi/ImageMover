package com.example.sony.imagemover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImgFragment extends Fragment implements View.OnClickListener {

    private final String ARG_ID = "id";

    private boolean mIsSelected = false;
    int mCurImgId = 0;

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
        int img = getImageByID(getCurrentFragmentId());
        mImageView.setImageResource(img);
        mImageView.setOnClickListener(this);
        return inflateView;
    }

    // TODO: разнести
    @Override
    public void onClick(View view) {

        Activity2 act2 = (Activity2) getActivity();

        int currentFragmentId = getCurrentFragmentId();

        if (mIsSelected) {
            deselect();
            act2.saveText(act2.PREV_IMG_ID, 0);
        } else {
            select();
            if (act2.loadText(act2.PREV_IMG_ID) == 0) {
                act2.saveText(act2.PREV_IMG_ID, currentFragmentId);
            } else {
                if (mCurImgId == 0) {
                    mCurImgId = currentFragmentId;
                }

                act2 = (Activity2) getActivity();
                ImgFragment prevFragment = act2.getPreviousFragment();

                int previous_img_id;
                if (prevFragment.mCurImgId == 0) {
                    previous_img_id = act2.loadText(act2.PREV_IMG_ID);
                } else {
                    previous_img_id = prevFragment.mCurImgId;
                }

                int imgPrevious = getImageByID(previous_img_id);
                int imgCurrent = getImageByID(mCurImgId);

                mImageView.setImageResource(imgPrevious);

                prevFragment.mImageView.setImageResource(imgCurrent);
                prevFragment.mCurImgId = mCurImgId;
                prevFragment.deselect();
                deselect();

                int count = act2.loadText(act2.MOVES_COUNT);
                act2.saveText(act2.MOVES_COUNT, ++count);
                act2.saveToMovingHistory(
                        act2.loadText(act2.MOVES_COUNT),
                        act2.loadText(act2.PREV_IMG_ID),
                        currentFragmentId
                );

                mCurImgId = previous_img_id;
                act2.saveText(act2.PREV_IMG_ID, 0);
            }
        }
    }

    public int getCurrentFragmentId() {
        return getArguments().getInt(ARG_ID);
    }

    public void select() {
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mIsSelected = true;
    }

    public void deselect() {
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTransparent));
        mIsSelected = false;
    }

    // TODO: унести в Utils
    public int getImageByID(int id) {
        return getResources()
                .getIdentifier(
                        "img" + id,
                        "drawable",
                        getActivity().getPackageName());
    }

    public void setScaleTypeForImage(String scaleType) {
        mImageView.setScaleType(ImageView.ScaleType.valueOf(scaleType));
    }
}