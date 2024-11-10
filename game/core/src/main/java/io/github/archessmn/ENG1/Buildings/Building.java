package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.archessmn.ENG1.Util.GridCoordTuple;
import io.github.archessmn.ENG1.Util.GridUtils;
import io.github.archessmn.ENG1.World;

/**
 * Base class for each building type ({@link io.github.archessmn.ENG1.Buildings.Building.Type}),
 * stores information about the building and provides utility classes for interacting with it.
 */
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

    /**
     * Defines the type and, by inference, the {@link Use}
     * and sprite of a building.
     */
    public enum Type {
        GYM, HALLS, LECTURE_HALL, OFFICES, PIAZZA
    }

    /**
     * The use of a building, inferred from its {@link Type}
     */
    public enum Use {
        SLEEP, LEARN, EAT, RECREATION
    }

    public Type buildingType;

    public Rectangle bounds;

    private final Sprite sprite;
    private Sprite unbuiltSprite;
    private final World world;

    /**
     * Initialises a new building.
     * @param world The {@link World} that the building is a part of.
     * @param buildingType The {@link Type} of the building.
     * @param x The X coordinate to place the building at.
     * @param y The Y coordinate to place the building at.
     * @param width Width of the building.
     * @param height Height of the building.
     * @param timeUntilBuilt Time until the building is marked as built.
     * @param built Whether the building should be marked as built upon creation.
     */
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

    /**
     * Draws the building into the game using the provided {@link SpriteBatch}.
     * @param batch The {@link SpriteBatch} to use to draw the building.
     */
    public void draw(SpriteBatch batch) {
        if (timeUntilBuilt > 0 && !built) {
            unbuiltSprite.setPosition(x, y);
            unbuiltSprite.draw(batch);
            return;
        }
        sprite.setPosition(this.x, this.y);

        sprite.draw(batch);
    }

    /**
     * Usually called once per frame to run any updates the building may need,
     * such as reducing time until built.
     * @param deltaTime The amount of time to advance by.
     */
    public void tick(float deltaTime) {
        this.setX(MathUtils.clamp(this.x, 0, world.width - this.width));
        this.setY(MathUtils.clamp(this.y, 0, world.height - this.height));

        if (placed && timeUntilBuilt >= 0) this.timeUntilBuilt -= deltaTime;
        if (timeUntilBuilt <= 0) built = true;
    }

    /**
     * Usually called once per frame to run any updates the building may need,
     * such as reducing time until built. Uses Gdx.graphics.getDeltaTime() to
     * get the amount of time to advance by.
     */
    public void tick() {
        tick(Gdx.graphics.getDeltaTime());
    }

    /**
     * Called when placing the building into the world, will check if the building
     * overlaps and cancel if it does, returning false. Will snap the building to
     * the grid and update its grid coordinates to match its position.
     * @return true if placing the building was successful.
     */
    public boolean place() {
        if (world.doesBuildingOverlap(this)) {
            return false;
        }
        this.snapToGrid();
        GridCoordTuple gridCoord = GridUtils.getGridCoords(this.x, this.y);
        this.gridX = gridCoord.x;
        this.gridY = gridCoord.y;

        this.placed = true;

        return true;
    }

    /**
     * Used to update the position of the building in the world.
     * @param x The X position to use
     * @param y The Y position to use
     */
    public void setCenter(float x, float y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    /**
     * Sets the X position of the building
     * @param x The X position to use
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the Y position of the building
     * @param y The Y position to use
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Calculates and returns the bounding box of the building.
     * @return The bounding box {@link Rectangle} of the building.
     */
    public Rectangle getBounds() {
        return this.bounds.set(this.x, this.y, this.width, this.height);
    }

    /**
     * Makes an un-built copy of the current building type
     * @return A copy of the building.
     */
    public Building makeCopy() {
        return new Building(this.world, this.buildingType, this.x, this.y + 60, this.width, this.height, this.initialBuildTime, false);
    }

    /**
     * Get the raw coordinates of the grid square the building would
     * snap to, relative to the entire viewport.
     * For example the bottom left grid position would be (0.0, 480).
     * @return A {@link Vector2} of the position on the grid
     */
    public Vector2 getRawGridCoords() {
        return GridUtils.getRawGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    /**
     * Get the raw coordinates of the grid square the building would
     * snap to, relative to the grid.
     * For example, the top left grid position would be (0, 8).
     * @return A {@link GridCoordTuple} of the grid position
     */
    public GridCoordTuple getGridCoords() {
        return GridUtils.getGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    /**
     * Snaps the building to the grid.
     */
    public void snapToGrid() {
        Vector2 gridCoords = getRawGridCoords();
        this.setCenter(gridCoords.x, gridCoords.y);
    }

    /**
     * Get the use of the building, inferred from its {@link Type}.
     * @return The {@link Use} of the building.
     */
    public Use getBuildingUse() {
        return switch (buildingType) {
            case GYM -> Use.RECREATION;
            case HALLS -> Use.SLEEP;
            case LECTURE_HALL, OFFICES -> Use.LEARN;
            case PIAZZA -> Use.EAT;
        };
    }
}
