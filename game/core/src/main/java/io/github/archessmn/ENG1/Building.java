package io.github.archessmn.ENG1;

import com.badlogic.gdx.graphics.Texture;

public class Building {
    private Texture texture;
    private float x, y;

    public Building(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public Building(Texture texture) {
        this.texture = texture;
        this.x = 0;
        this.y = 0;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
