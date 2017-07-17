package com.example.sony.imagemover;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Activity2 extends AppCompatActivity implements ImgFragment.Listener, View.OnClickListener {

    private Spinner spinner;

    private FragmentManager fragmentManager;

    private SharedPreferences pref;

    private int movesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        spinner = (Spinner) findViewById(R.id.scaleTypeSpinner);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 1; i <= 6; i++) {
            int resId = getResources().getIdentifier("frame" + i, "id", getPackageName());
            ImgFragment fragment = new ImgFragment().newInstance(i);
            fragment.setListener(this);
            fragmentTransaction.add(resId, fragment, Integer.toString(i));
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        Button mBtnStop = (Button) findViewById(R.id.stop_button);
        mBtnStop.setOnClickListener(this);
        setScaleType();
    }

    @Override
    public void onClick(View v) {
        goToMainActivityAndShowHistory();
    }

    @Override
    public void onBackPressed() {
        goToMainActivityAndShowHistory();
    }

    private void setScaleType() {

        final String[] scaleTypeVariants = getResources().getStringArray(R.array.scale_type_variants);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.scale_type_variants,
                        android.R.layout.simple_spinner_item
                );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 1; j <= 6; j++) {
                    ImgFragment fragment = (ImgFragment) fragmentManager.findFragmentByTag(Integer.toString(j));
                    fragment.setScaleTypeForImage(scaleTypeVariants[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Nullable
    @Override
    public ImgFragment getSelectedFragment() {
        for (int j = 1; j <= 6; j++) {
            ImgFragment fragment = (ImgFragment) fragmentManager.findFragmentByTag(Integer.toString(j));
            if (fragment.isSelected()) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public void swapImages(int currentFragmentId) {
        ImgFragment selectedFragment = getSelectedFragment();
        ImgFragment currentFragment = (ImgFragment) fragmentManager.findFragmentByTag(Integer.toString(currentFragmentId));

        int selectedImgId = selectedFragment.getCurrentImgId();
        int currentImgId = currentFragment.getCurrentImgId();

        int imgSelected = selectedFragment.getImageById(selectedImgId);
        int imgCurrent = currentFragment.getImageById(currentImgId);

        selectedFragment.setImageResource(imgCurrent);
        currentFragment.setImageResource(imgSelected);

        selectedFragment.setCurrentImgId(currentImgId);
        currentFragment.setCurrentImgId(selectedImgId);

        movesCount++;
        saveToMovingHistory(
                movesCount,
                selectedFragment.getCurrentFragmentId(),
                currentFragmentId
        );

        selectedFragment.deselect();
    }

    private void goToMainActivityAndShowHistory() {
        Intent intent = new Intent();
        intent.putExtra(Constants.MOVING_HISTORY, getMovingHistory());
        setResult(RESULT_OK, intent);
        finish();
    }

    void saveToMovingHistory(int movesCount, int firstFragmentId, int lastFragmentId) {
        pref = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
        Editor ed = pref.edit();

        ArrayList<String> history = getMovingHistory();
        history.add(
                Integer.toString(movesCount)
                + ", " + Integer.toString(firstFragmentId)
                + ", " + Integer.toString(lastFragmentId)
        );

        Set<String> set = new HashSet<>();
        set.addAll(history);
        ed.putStringSet(Constants.MOVING_HISTORY, set)
                .apply();
    }

    ArrayList<String> getMovingHistory() {
        pref = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
        Set<String> set = pref.getStringSet(Constants.MOVING_HISTORY, new HashSet<String>());
        return new ArrayList<>(set);
    }

// TODO: сделать так, чтобы история отображалась отсортированной
//    ArrayList<String> sortMovingHistory(ArrayList<String> history) {
//        String[] tempHistory = history.toArray(new String[0]);
//        for (String item : tempHistory) {
//            String order = StringUtils.substringBefore(item, ",");
//        }
//        Arrays.sort(tempHistory);
//        return new ArrayList<>(Arrays.asList(tempHistory));
//    }
}
