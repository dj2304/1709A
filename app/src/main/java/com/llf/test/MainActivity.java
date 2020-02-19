package com.llf.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomImageView mCustomImageView;

    private int[][] mCustomArray= {{24,71,184,231},
            {401,71,561,231},
            {220,253,380,413}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mCustomImageView = findViewById(R.id.custom);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
    }

    private void initData(){
        try{
            List<Rect> rectList = new ArrayList<>();

            for(int[] intArray : mCustomArray){
                Rect rect = new Rect();
                rect.left = intArray[0];
                rect.top = intArray[1];
                rect.right = intArray[2];
                rect.bottom= intArray[3];

                rectList.add(rect);
            }
            mCustomImageView.setClickableRects(rectList);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                initData();
                break;
            case R.id.btn_read:
                List<Rect> rectList = mCustomImageView.getClickedRects();
                if(rectList != null && rectList.size() > 0){
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(getString(R.string.string_read_start));

                    for(Rect rect : rectList){
                        stringBuffer.append(rect.top)
                                .append("-")
                                .append(rect.left)
                                .append("-")
                                .append(rect.right)
                                .append("-")
                                .append(rect.bottom)
                                .append("\n");
                    }
                    stringBuffer.append(getString(R.string.string_read_end));

                    Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, getString(R.string.string_read_no_data), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:
                mCustomImageView.clearShownRects();
                break;
            default:
                break;
        }
    }
}
