package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.archessmn.ENG1.Util.GridUtils;
import io.github.archessmn.ENG1.World;

public abstract class Building {
    public float x;
    public float y;

    public float width;
    public float height;

    public enum Type {
        GYM,
        HALLS,
        LECTURE_HALL,
        OFFICES,
        PIAZZA
    }

    public Type buildingType;

    public Rectangle bounds;

    public Sprite sprite;

    private World world;

    boolean clicked = false;

    public Building(World world, Type buildingType, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.world = world;

        this.buildingType = buildingType;

        this.bounds = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setCenter(float x, float y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    public void draw(SpriteBatch batch) {
        sprite.setX(this.x);
        sprite.setY(this.y);

        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return this.bounds.set(this.x, this.y, this.width, this.height);
    }

    public Building makeCopy() {
        return new UnknownBuilding(this.world, this.buildingType, this.x, this.y + 60, this.width, this.height);
    }

    public Vector2 getGridCoords() {
        return GridUtils.getGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    public void snapToGrid() {
        Vector2 gridCoords = getGridCoords();
        this.setCenter(gridCoords.x, gridCoords.y);
    }
}

