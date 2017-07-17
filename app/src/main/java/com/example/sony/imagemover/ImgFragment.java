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

    // FIXME: Поля не приватные
    boolean mIsSelected = false;
    int mCurImgId;

    private LinearLayout mLinearLayout;
    // FIXME: Поле не приватное
    ImageView mImageView;

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
        int img = getImageById(getCurrentFragmentId());
        mCurImgId = getCurrentFragmentId();
        mImageView.setImageResource(img);
        mImageView.setOnClickListener(this);
        return inflateView;
    }

    @Override
    public void onClick(View view) {
        Activity2 act2 = (Activity2) getActivity();
        // FIXME: Реализацию методов getSelectedFragment и swapImages лучше вынести интерфейс,
        // FIXME: чтобы фрагмент не знал ничего об активити и работал только с интерфейсом
        if (act2.getSelectedFragment() != null) {
            if (mIsSelected) {
                deselect();
            } else {
                act2.swapImages(getCurrentFragmentId());
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
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mIsSelected = true;
    }

    public void deselect() {
        mLinearLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTransparent));
        mIsSelected = false;
    }

    public int getImageById(int id) {
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