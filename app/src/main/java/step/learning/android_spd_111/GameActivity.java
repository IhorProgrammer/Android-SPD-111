package step.learning.android_spd_111;

import android.graphics.Color;
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

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Random;

import step.learning.android_spd_111.GameShake.Direction;
import step.learning.android_spd_111.GameShake.GameShake;

public class GameActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private GameShake gameShake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        int fieldColor = getResources().getColor(R.color.game_field, getTheme());
        int shakeColor = getResources().getColor(R.color.game_shake, getTheme());
        LinearLayout field = findViewById(R.id.game_field);
        gameShake = new GameShake(fieldColor, shakeColor, field, this);

        findViewById(R.id.main).setOnTouchListener( new onSwipeListener(this) {
            @Override
            public void onSwipeBottom() {
                if( gameShake.getMoveDirection() != Direction.top ) gameShake.setMoveDirection(Direction.bottom);
            }

            @Override
            public void onSwipeLeft() {
                if( gameShake.getMoveDirection() != Direction.right ) gameShake.setMoveDirection(Direction.left);

            }

            @Override
            public void onSwipeRight() {
                if( gameShake.getMoveDirection() != Direction.left ) gameShake.setMoveDirection(Direction.right);

            }

            @Override
            public void onSwipeTop() {
                if( gameShake.getMoveDirection() != Direction.bottom ) gameShake.setMoveDirection(Direction.top);
            }
        } );
        scoreChange();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameShake.start();

    }
    @Override
    protected void onPause() {
        super.onPause();
        gameShake.pause();
    }

    protected void scoreChange() {
       TextView tv = ((TextView)findViewById(R.id.game_score));
       tv.setText( Integer.toString(gameShake.getScore()) );
       handler.postDelayed(this::scoreChange, 100 );
    }

}