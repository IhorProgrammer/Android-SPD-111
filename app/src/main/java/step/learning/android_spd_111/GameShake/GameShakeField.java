package step.learning.android_spd_111.GameShake;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import step.learning.android_spd_111.GameActivity;
import step.learning.android_spd_111.R;

public class GameShakeField{
    public static final int FIELD_WIDTH = 16;
    public static final int FIELD_HEIGHT = 24;

    private TextView[][] gameField;
    private int fieldColor;

    public int getFieldColor() {
        return fieldColor;
    }

    public TextView getGameField(int i, int j) {
        return gameField[i][j];
    }
    public void setFieldColor(int i, int j, int color) {
        gameField[i][j].setBackgroundColor(color);
    }
    public void clearGameField(int i, int j) {
        gameField[i][j].setBackgroundColor(fieldColor);
    }

    public GameShakeField(int fieldColor) {
        this.fieldColor = fieldColor;
    }

    public void initField(LinearLayout field, Context context){
        LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tvLayoutParams.setMargins(4,4,4,4);
        tvLayoutParams.weight = 1f;

        LinearLayout.LayoutParams rowLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        rowLayoutParams.weight = 1f;

        gameField = new TextView[FIELD_WIDTH][FIELD_HEIGHT];

        for (int i = 0; i < FIELD_HEIGHT; i++) {
            LinearLayout row = new LinearLayout( context );
            row.setOrientation( LinearLayout.HORIZONTAL );
            row.setLayoutParams( rowLayoutParams );

            for (int j = 0; j < FIELD_WIDTH; j++) {
                TextView tv = new TextView( context );
                tv.setBackgroundColor( fieldColor );
                tv.setLayoutParams( tvLayoutParams );
                row.addView( tv );
                gameField[j][i] = tv;

            }
            field.addView(row);
        }


    }

    public void clear() {
        for (TextView[] r: gameField) {
            for (TextView c: r) {
                c.setBackgroundColor( fieldColor );
            }
        }
    }

}
