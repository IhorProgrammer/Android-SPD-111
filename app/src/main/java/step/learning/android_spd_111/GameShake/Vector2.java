package step.learning.android_spd_111.GameShake;

import java.util.Random;

import step.learning.android_spd_111.GameActivity;

public class Vector2 {
    int x;
    int y;

    private static final Random random = new Random();

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 random(int maxX, int maxY) {
        return new Vector2( random.nextInt(maxX), random.nextInt(maxY) );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return  this.x == vector2.x && this.y == vector2.y;
    }
}
