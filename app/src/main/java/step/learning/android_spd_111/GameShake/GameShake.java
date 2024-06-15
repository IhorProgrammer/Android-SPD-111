package step.learning.android_spd_111.GameShake;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.LinkedList;

import step.learning.android_spd_111.GameActivity;
import step.learning.android_spd_111.R;
import step.learning.android_spd_111.onSwipeListener;

public class GameShake {

    private final Handler handler = new Handler();

    public void setMoveDirection(Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    private Direction moveDirection;

    public Direction getMoveDirection() {
        return moveDirection;
    }

    private final GameShakeShake gameShakeShake;
    private final GameShakeField gameShakeField;
    private final GameShakeFood gameShakeFood;

    AppCompatActivity activity;
    private Animation opacityAnimation;

    private GameActivityEnum gameActivityEnum = GameActivityEnum.stop;

    public GameActivityEnum getGameActivityEnum() {
        return gameActivityEnum;
    }

    private int gameSpeed = 1;

    public GameShake(int fieldColor, int shakeColor, LinearLayout field, AppCompatActivity activity) {
        gameShakeShake = new GameShakeShake(shakeColor);
        gameShakeField = new GameShakeField(fieldColor);
        gameShakeFood = new GameShakeFood();
        gameShakeField.initField( field, activity );
        this.activity = activity;
        opacityAnimation = AnimationUtils.loadAnimation (this.activity, R.anim.opacity);

        start();

    }

    private void changeFoodPosition() {
        Vector2 foodPositionTemp = gameShakeFood.getFoodPosition();
        if( foodPositionTemp != null )
            gameShakeField.getGameField(foodPositionTemp.x, foodPositionTemp.y).setText( "" );
        do {
            foodPositionTemp = gameShakeFood.changeFoodPosition( GameShakeField.FIELD_WIDTH, GameShakeField.FIELD_HEIGHT );
        } while (gameShakeShake.contains( foodPositionTemp ) );


        gameShakeField.getGameField(foodPositionTemp.x, foodPositionTemp.y).setText( GameShakeFood.food );
        gameShakeField.getGameField(foodPositionTemp.x, foodPositionTemp.y).startAnimation( opacityAnimation );

    }

    private void newGame() {
        gameShakeField.clear();
        gameShakeShake.clear();

        gameShakeShake.add(7, 10 );
        gameShakeShake.add(7, 10 );

        refreshShake();
        changeFoodPosition();

        moveDirection = Direction.top;
        gameActivityEnum = GameActivityEnum.run;
        this.step();
    }

    private void refreshShake() {
        gameShakeShake.forEach( v -> {
            gameShakeField.getGameField(v.x, v.y).setBackgroundColor( gameShakeShake.getShakeColor() );
        } );
    }

    private void step() {
        if(gameActivityEnum != GameActivityEnum.run) return;

        Vector2 removed = gameShakeShake.step( moveDirection );

        Vector2 head = gameShakeShake.getHead();
        if( head.x <= 0 || GameShakeField.FIELD_WIDTH <= head.x ||
            head.y <= 0 || GameShakeField.FIELD_HEIGHT <= head.y
        ) {
           stop();
           return;
        }
        Vector2 foodPosition = gameShakeFood.getFoodPosition();

        if( head.equals(foodPosition) ) {
            this.changeFoodPosition();
            gameShakeShake.add( removed.x, removed.y );
        } else {
            gameShakeField.clearGameField( removed.x, removed.y);
        }

        refreshShake();
        gameSpeed = gameShakeShake.size();
        handler.postDelayed(this::step, Math.round( 1000 - (900/14) * Math.min(gameSpeed, 14)) );
    }

    public int getScore() {
        return gameShakeShake.size() - 2;
    }

    public void stop() {
        gameActivityEnum = GameActivityEnum.over;
        new AlertDialog
                .Builder( activity )
                .setTitle( "Game over" )
                .setMessage( "Play one more" )
                .setCancelable( false )
                .setPositiveButton("Yes", ((dialog, which) -> { newGame(); }))
                .setNegativeButton("No", ((dialog, which) -> { activity.finish(); }))
                .show();
    }

    public void pause() {
        switch (gameActivityEnum) {
            case over: case stop:
                break;
            default:
                gameActivityEnum = GameActivityEnum.pause;
                break;
        }
    }

    public void start() {
        switch (gameActivityEnum) {
            case pause:
                gameActivityEnum = GameActivityEnum.run;
                step();
                break;
            case stop:
                newGame();
                break;
        }
    }


}

enum GameActivityEnum {
    pause,
    run,
    stop,
    over,
}
