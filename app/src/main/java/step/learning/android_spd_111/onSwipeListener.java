package step.learning.android_spd_111;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class onSwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public onSwipeListener(Context context) {
        this.gestureDetector = new GestureDetector(context, new SwipeGestureListener());
    }
    public onSwipeListener(Context context, int min_distance, int min_velocity) {
        this.gestureDetector = new GestureDetector(context, new SwipeGestureListener(min_distance, min_velocity));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    private final class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

        private final int MIN_DISTANCE;
        private final int MIN_VELOCITY;

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }

        public SwipeGestureListener(int min_distance, int min_velocity) {
            this.MIN_DISTANCE = min_distance;
            this.MIN_VELOCITY = min_velocity;
        }

        public SwipeGestureListener()  {
            this(70, 70);
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            boolean isDespatced = false;
            if (e1 != null) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //горизонтальний
                    if (Math.abs(distanceX) > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {
                        if (distanceX > 0) {
                            //право
                            onSwipeRight();
                            isDespatced = true;
                        } else {
                            //ліво
                            isDespatced = true;
                            onSwipeLeft();
                        }
                    }
                } else {
                    // вертикальний
                    if (Math.abs(distanceY) > MIN_DISTANCE && Math.abs(velocityY) > MIN_VELOCITY) {
                        if (distanceY < 0) {
                            //вверх
                            isDespatced = true;
                            onSwipeTop();

                        } else  {
                            //вниз
                            isDespatced = true;
                            onSwipeBottom();
                        }
                    }
                }
            }
            return isDespatced;

        }
    }
}
