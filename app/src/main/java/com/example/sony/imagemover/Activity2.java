package com.example.sony.imagemover;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        int ROWS = 3;
        int COLUMNS = 2;

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableLayout.setShrinkAllColumns(true);

        int item = 1;
        for (int i = 0; i < ROWS; i++) {
            TableRow table = new TableRow(this);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            table.setLayoutParams(layoutParams);

            for (int j = 0; j < COLUMNS; j++) {
                LinearLayout ll = new LinearLayout(this);
                ll.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                final ImageView imageView = new ImageView(this);
                imageView.setId(item);
                imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.cell_shape));
                Drawable itemImg = ResourcesCompat.getDrawable(getResources(), getResources()
                        .getIdentifier("img"+item, "drawable", getPackageName()), null);
                imageView.setImageDrawable(itemImg);
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setOnClickListener(this);
                table.addView(imageView, j);
                item++;
            }

            tableLayout.addView(table, i);
        }
    }

    @Override
    public void onClick(View view) {
        ImageView imageView = (ImageView) findViewById(view.getId());
        imageView.setPadding(10, 10, 10, 10);
    }
}
