package step.learning.android_spd_111.GameShake;

import android.os.Handler;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.function.Consumer;

import step.learning.android_spd_111.GameActivity;

public class GameShakeShake {
    private LinkedList<Vector2> shake = new LinkedList<Vector2>();

    public int getShakeColor() {
        return shakeColor;
    }

    public Vector2 getHead() {
        return shake.getFirst();
    }

    private int shakeColor;

    public int size() {
        return shake.size();
    }

    public GameShakeShake(int shakeColor) {
        this.shakeColor = shakeColor;
    }

    public void clear() {
        shake.clear();
    }

    public void add(int x, int y) {
        shake.add( new Vector2(x, y) );
    }

    public Vector2 step(Direction direction) {
        Vector2 tail = shake.getLast();
        Vector2 head = shake.getFirst();
        Vector2 newHead = new Vector2( head.x, head.y );

        switch (direction) {
            case top:
                newHead.y -= 1;
                break;
            case bottom:
                newHead.y += 1;
                break;
            case left:
                newHead.x -= 1;
                break;
            case right:
                newHead.x += 1;
                break;
        }

        shake.addFirst( newHead );
        shake.remove( tail );
        return tail;
    }

    public void forEach(Consumer<Vector2> function) {
        shake.forEach(function);
    }

    public boolean contains(Vector2 vector) {
        return shake.contains(vector);
    }

}
