package com.llf.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView implements View.OnTouchListener {
    private List<Rect> mRectClickableList;
    private LinkedHashMap<Integer, Rect> mRectClickedMap;
    private Paint mPaint;
    private Context mContext;
    private float mX_Down, mY_Down, mScaleX = -1F, mScaleY;

    public CustomImageView(Context context) {
        super(context);
        init(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mRectClickableList = new ArrayList<>();
        mRectClickedMap = new LinkedHashMap<>();
        mPaint = new Paint();

        setOnTouchListener(this);
    }

    public void setClickableRects(List<Rect> rects){
        if(mRectClickableList==null){
            mRectClickableList = new ArrayList<>();
        }

        mRectClickableList.clear();

        for(Rect rect : rects){
            Rect rectNew = new Rect();
            rectNew.top = (int)(rect.top * mScaleY);
            rectNew.bottom = (int)(rect.bottom * mScaleY);
            rectNew.left = (int)(rect.left * mScaleX);
            rectNew.right = (int)(rect.right * mScaleX);
            mRectClickableList.add(rectNew);
        }
    }

    public void clearShownRects(){
        if(mRectClickedMap!=null){
            mRectClickedMap.clear();
        }

        invalidate();
    }

    public List<Rect> getClickedRects(){
        List<Rect> mRectClickedList = new ArrayList<>();
        for(Map.Entry<Integer, Rect> entry : mRectClickedMap.entrySet()) {
            mRectClickedList.add(entry.getValue());
        }
        return mRectClickedList;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mX_Down = event.getX();
            mY_Down = event.getY();
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            if(mRectClickableList == null || mRectClickableList.size() == 0){
                Toast.makeText(mContext, mContext.getString(R.string.click_with_no_rect), Toast.LENGTH_SHORT).show();
                return true;
            }

            float x_Up = event.getX();
            float y_Up = event.getY();

            if(Math.abs(mX_Down - x_Up) < 20 && Math.abs(mY_Down - y_Up) < 20 ){
                for(int i = 0; i < mRectClickableList.size(); i++){
                    Rect rect= mRectClickableList.get(i);
                    if(x_Up > rect.left &&
                            x_Up < rect.right &&
                            y_Up > rect.top &&
                            y_Up < rect.bottom){

                        if(mRectClickedMap.containsKey(i)){
                            mRectClickedMap.remove(i);
                        }else{
                            mRectClickedMap.put(i, rect);
                        }

                        invalidate();

                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        Bitmap bitmapBolder = ((BitmapDrawable) mContext.getDrawable(R.mipmap.bg)).getBitmap();
        int widthBitmap = bitmapBolder.getWidth();
        int heightBitmap = bitmapBolder.getHeight();

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int windowWidth = dm.widthPixels;
        float scale = (float)windowWidth / widthBitmap;
        if(mScaleX > 0){
            return;
        }

        int heightAfter = (int)(heightBitmap * scale);
        if(heightAfter > dm.heightPixels){
            heightAfter = dm.heightPixels;
        }

        mScaleX = scale;
        mScaleY = (float) heightAfter / heightBitmap;
        setMeasuredDimension(windowWidth, heightAfter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmapBolder = ((BitmapDrawable) mContext.getDrawable(R.mipmap.bolder)).getBitmap();

        int i = 0;

        for(Map.Entry<Integer, Rect> entry : mRectClickedMap.entrySet()) {
            Rect rect = entry.getValue();

            Rect srcRect = new Rect(0, 0,
                    (int)(bitmapBolder.getWidth()),
                    (int)(bitmapBolder.getHeight()));
            canvas.drawBitmap(bitmapBolder, srcRect, rect, mPaint);

            Bitmap bitmapNum = null;
            if(i == 0){
                bitmapNum = ((BitmapDrawable) mContext.getDrawable(R.mipmap.num_1)).getBitmap();
            }else if(i == 1){
                bitmapNum = ((BitmapDrawable) mContext.getDrawable(R.mipmap.num_2)).getBitmap();
            }else if(i == 2){
                bitmapNum = ((BitmapDrawable) mContext.getDrawable(R.mipmap.num_3)).getBitmap();
            }
            Rect srcRectNum = new Rect(0, 0,
                    (int)(bitmapNum.getWidth() * mScaleX),
                    (int)(bitmapNum.getHeight() * mScaleY));

            Rect descRectNum = new Rect(rect.left, rect.top,
                    rect.left + bitmapNum.getWidth(), rect.top + bitmapNum.getHeight());
            canvas.drawBitmap(bitmapNum, srcRectNum, descRectNum, mPaint);

            i++;
        }
    }
}
