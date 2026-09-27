package com.example.trybubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class BubbleView extends AppCompatImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size = 100;
    private int delay = 100;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();
    private Bitmap bubbleImage;

    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        bubbleImage = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.images5_circle
        );
        testBubbles();
        setOnTouchListener(this);
    }

    private Runnable r = new Runnable() {
        @Override
         public void run() {
            for(Bubble b : bubbleList)
                b.update();
            invalidate();
        }
         };

    @Override
        protected void onDraw(Canvas canvas) {
            for (Bubble b : bubbleList) {
                b.draw(canvas);
                h.postDelayed(r, delay);
            }
        }

        public void testBubbles() {
            for(int n = 0; n < 10; n++) {
                int x = rand.nextInt(600);
                int y = rand.nextInt(600);

                int s = rand.nextInt(size) + size;
                //bubbleList.add( new Bubble(x, y, s) );
                bubbleList.add(new Bubble(x, y, s, bubbleImage));
            }
            invalidate();
        }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    private class Bubble {

        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 1;
        private Bitmap image;
        private boolean growing = true;
        public Bubble(int newX, int newY, int newSize, Bitmap newImage){
            x = newX;
            y = newY;
            size = newSize;
            image = newImage;
            //color = Color.BLUE;
            /*
            color = Color.argb(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256) );

                    */
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;


        }
        public void draw(Canvas canvas){
            /*
            myPaint.setColor(color);

            canvas.drawOval(x - size/2, y - size/2,
                    x + size/2, y + size/2, myPaint);
                    */

            // Draw colored circle around image
            myPaint.setColor(Color.GREEN);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(20);

            canvas.drawCircle(
                    x,
                    y,
                    size / 2 + 5,
                    myPaint
            );

            canvas.drawBitmap(
                    image,
                    null,
                    new android.graphics.Rect(
                            x - size / 2,
                            y - size / 2,
                            x + size / 2,
                            y + size / 2
                    ),
                    myPaint
            );



        }
        public void update(){
            x += xspeed;
            y += yspeed;
            if (x - size/2 <= 0 || x + size/2 >= getWidth())
                xspeed = -xspeed;
            if (y - size/2 <= 0 || y + size/2 >= getHeight())
                yspeed = -yspeed;

            // Grow or shrink
            if (growing) {
                size++;
            } else {
                size--;
            }

            // Change direction when large
            if (size >= 200) {
                growing = false;
            }

            // Change direction when small
            if (size <= 100) {
                growing = true;
            }
        }

    }
}
