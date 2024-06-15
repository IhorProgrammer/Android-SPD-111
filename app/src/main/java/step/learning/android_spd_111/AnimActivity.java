package step.learning.android_spd_111;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnimActivity extends AppCompatActivity {

    private Animation opacityAnimation;
    private Animation sizeAnimation;
    private Animation size2Animation;
    private Animation arcAnimation;
    private Animation bellAnimation;
    private Animation moveAnimation;
    private AnimationSet comboAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        opacityAnimation = AnimationUtils.loadAnimation (this, R.anim.opacity);
        findViewById(R.id.anim_opacity_block).setOnClickListener(this::opacityClick);

        sizeAnimation = AnimationUtils.loadAnimation (this, R.anim.size);
        findViewById(R.id.anim_size_block).setOnClickListener(this::sizeClick);

        size2Animation = AnimationUtils.loadAnimation (this, R.anim.size2);
        findViewById(R.id.anim_size2_block).setOnClickListener(this::size2Click);

        arcAnimation = AnimationUtils.loadAnimation (this, R.anim.arc);
        findViewById(R.id.anim_arc_block).setOnClickListener(this::arcClick);

        bellAnimation = AnimationUtils.loadAnimation (this, R.anim.bell);
        findViewById(R.id.anim_bell_block).setOnClickListener(this::bellClick);

        moveAnimation = AnimationUtils.loadAnimation (this, R.anim.move);
        findViewById(R.id.anim_move_block).setOnClickListener(this::moveClick);


        comboAnimation = new AnimationSet(false);
        comboAnimation.addAnimation( opacityAnimation );
        comboAnimation.addAnimation( size2Animation );
        findViewById(R.id.anim_combo_block).setOnClickListener(this::comboClick);

    }


    private void opacityClick(View view) {
        view.startAnimation( opacityAnimation );
    }
    private void sizeClick(View view) {
        view.startAnimation( sizeAnimation );
    }

    private void size2Click(View view) {
        view.startAnimation( size2Animation );
    }

    private void arcClick(View view) {
        view.startAnimation( arcAnimation );
    }
    private void bellClick(View view) {
        view.startAnimation( bellAnimation );
    }

    private boolean isMovePlaying = false;
    private void moveClick(View view) {
        if(isMovePlaying) {
            view.clearAnimation();
            isMovePlaying = false;
        } else {
            view.startAnimation (bellAnimation);
            isMovePlaying = true;
        }
    }

    private void comboClick(View view) {
        view.startAnimation( comboAnimation );
    }
}