package ru.dpankratov.projects.rpg.mydraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

public class MyGame extends View implements View.OnTouchListener {

    private Paint paint = new Paint(); //Кисть
    private Random random = new Random();
    private int viewWidth;
    private int viewHeight;

    private Bitmap pic;
    private Bitmap sprite;
    private Bitmap explosion;
    float touchX, touchY;

    float myShipX, myShipY;

    float mySpriteX, mySpriteY;

    String myAction = "None";

    boolean fire = false;
    boolean redraw = false;



    public MyGame(Context context) {
        super(context);
        setOnTouchListener(this);
        pic = BitmapFactory.decodeResource(getResources(), R.mipmap.ship1);
        sprite = BitmapFactory.decodeResource(getResources(), R.mipmap.images);
        explosion = BitmapFactory.decodeResource(getResources(), R.mipmap.explosion);
        myShipX = 0;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        myShipY = viewHeight - pic.getHeight();
        mySpriteY = 0;
        mySpriteX = random.nextFloat() * viewWidth;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (redraw || mySpriteY >= viewHeight) {
            if (redraw) canvas.drawBitmap(explosion, sprite.getWidth(), sprite.getHeight(), paint);
            mySpriteX = random.nextFloat() * viewWidth;
            mySpriteY = 0;
            redraw = false;
        }

        canvas.drawBitmap(sprite, mySpriteX, mySpriteY, paint);
        canvas.drawBitmap(pic, myShipX, myShipY, paint);
        mySpriteY += 5;

        if (fire) {
            fire = false;
            canvas.drawLine(myShipX + pic.getWidth() / 2, myShipY, myShipX + pic.getWidth()/2, 0, paint);
            if (myShipX + pic.getWidth() / 2 > mySpriteX && myShipX + pic.getWidth() / 2 < mySpriteX + sprite.getWidth()) {
                redraw = true;
                canvas.drawBitmap(explosion, sprite.getWidth(), sprite.getHeight(), paint);
            }
        }
        invalidate();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        if (touchX > viewWidth - pic.getWidth()) {
            myShipX = viewWidth - pic.getWidth();
        } else {
            myShipX = touchX;
        }
        //myShipY = touchY;
        fire = true;

        /*switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                myAction = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                myAction = "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                myAction = "ACTION_MOVE";
                break;
        }*/
        return false;
    }
}
