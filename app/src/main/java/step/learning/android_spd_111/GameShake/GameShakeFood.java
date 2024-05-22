package step.learning.android_spd_111.GameShake;

public class GameShakeFood {
    public static final String food = new String( Character.toChars( 0x1F34E ) );
    private Vector2 foodPosition;


    public Vector2 getFoodPosition() {
        return foodPosition;
    }

    public Vector2 changeFoodPosition(int maxX, int maxY) {
        foodPosition = Vector2.random(maxX, maxY);
        return foodPosition;
    }

}
