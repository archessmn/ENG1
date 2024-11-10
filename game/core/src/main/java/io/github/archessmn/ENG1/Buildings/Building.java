package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.archessmn.ENG1.Util.GridCoordTuple;
import io.github.archessmn.ENG1.Util.GridUtils;
import io.github.archessmn.ENG1.World;

public class Building {
    public float x;
    public float y;

    public int gridX;
    public int gridY;

    public final int id;

    public float width;
    public float height;

    public float initialBuildTime;
    public float timeUntilBuilt;

    private boolean placed = false;
    private boolean built;

    public enum Type {
        GYM,
        HALLS,
        LECTURE_HALL,
        OFFICES,
        PIAZZA
    }

    public enum Use {
        SLEEP,
        LEARN,
        EAT,
        RECREATION
    }

    public Type buildingType;

    public Rectangle bounds;

    private final Sprite sprite;
    private Sprite unbuiltSprite;
    private final World world;

    public Building(World world, Type buildingType, float x, float y, float width, float height, float timeUntilBuilt, boolean built) {
        this.id = world.buildings.size - 1;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.initialBuildTime = timeUntilBuilt;
        this.timeUntilBuilt = timeUntilBuilt;

        this.built = built;

        if (timeUntilBuilt > 0) {
            this.unbuiltSprite = new Sprite(world.assetManager.get("construction.png", Texture.class));
            this.unbuiltSprite.setSize(width, height);
        }

        String spriteFileName = switch (buildingType) {
            case GYM -> "gym.png";
            case HALLS -> "halls.png";
            case LECTURE_HALL -> "lecturehall.png";
            case OFFICES -> "offices.png";
            case PIAZZA -> "piazza.png";
        };
        this.sprite = new Sprite(world.assetManager.get(spriteFileName, Texture.class));
        this.sprite.setSize(width, height);

        this.world = world;

        this.buildingType = buildingType;

        this.bounds = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void draw(SpriteBatch batch) {
        if (timeUntilBuilt > 0 && !built) {
            unbuiltSprite.setPosition(x, y);
            unbuiltSprite.draw(batch);
            return;
        }
        sprite.setPosition(this.x, this.y);

        sprite.draw(batch);
    }

    public void tick(float deltaTime) {
        this.setX(MathUtils.clamp(this.x, 0, world.width - this.width));
        this.setY(MathUtils.clamp(this.y, 0, world.height - this.height));

        if (placed && timeUntilBuilt >= 0) this.timeUntilBuilt -= deltaTime;
        if (timeUntilBuilt <= 0) built = true;
    }

    public boolean place() {
        if (world.doesBuildingOverlap(this)) {
            return false;
        }
        this.snapToGrid();
        GridCoordTuple gridCoord = GridUtils.getGridCoords(this.x, this.y);
        this.gridX = gridCoord.x;
        this.gridY = gridCoord.y;

        this.placed = true;

        return placed;
    }

    public void setCenter(float x, float y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return this.bounds.set(this.x, this.y, this.width, this.height);
    }

    public Building makeCopy() {
        return new Building(this.world, this.buildingType, this.x, this.y + 60, this.width, this.height, this.initialBuildTime, false);
    }

    public Vector2 getRawGridCoords() {
        return GridUtils.getRawGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    public GridCoordTuple getGridCoords() {
        return GridUtils.getGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    public void snapToGrid() {
        Vector2 gridCoords = getRawGridCoords();
        this.setCenter(gridCoords.x, gridCoords.y);
    }

    public Use getBuildingUse() {
        return switch (buildingType) {
            case GYM -> Use.RECREATION;
            case HALLS -> Use.SLEEP;
            case LECTURE_HALL, OFFICES -> Use.LEARN;
            case PIAZZA -> Use.EAT;
        };
    }
}
