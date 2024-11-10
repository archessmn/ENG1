package io.github.archessmn.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Buildings.Building;
import io.github.archessmn.ENG1.Util.GridCoordTuple;
import io.github.archessmn.ENG1.Util.GridUtils;

import java.util.HashMap;

/**
 * Class used to store information about the world and the buildings in it.
 */
public class World {
    public FitViewport viewport;

    public AssetManager assetManager;

    public ShapeRenderer shapeRenderer;
    public ShapeRenderer gridRenderer;

    public SpriteBatch batch;

    public BitmapFont font;

    public Integer width, height;

    public Array<Building> buildings;

    public HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();

    /**
     * Initialises an empty world and loads assets.
     * @param VIEWPORT_WIDTH Width to create the viewport
     * @param VIEWPORT_HEIGHT Height to create the viewport
     * @param worldWidth Width to use for the usable world space
     * @param worldHeight Height to use for the usable world space
     */
    public World(Integer VIEWPORT_WIDTH, Integer VIEWPORT_HEIGHT, Integer worldWidth, Integer worldHeight) {
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.width = worldWidth;
        this.height = worldHeight;

        assetManager = new AssetManager();

        assetManager.load("gym.png", Texture.class);
        assetManager.load("halls.png", Texture.class);
        assetManager.load("lecturehall.png", Texture.class);
        assetManager.load("offices.png", Texture.class);
        assetManager.load("piazza.png", Texture.class);
        assetManager.load("construction.png", Texture.class);
        assetManager.load("missing_texture.png", Texture.class);

        shapeRenderer = new ShapeRenderer();
        gridRenderer = new ShapeRenderer();

        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (0.05f * Gdx.graphics.getHeight());
        font = generator.generateFont(parameter);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        generator.dispose();

        buildings = new Array<>();

        assetManager.finishLoading();
    }

    /**
     * Draws the grid using {@link GridUtils}
     */
    public void drawGrid() {
        GridUtils.drawGrid(gridRenderer);
    }

    /**
     * Adds a building to the world building store and returns its location in the store
     * @param building Building to add to the world
     * @return The index of the building in the world store
     */
    public int addBuilding(Building building) {
        buildings.add(building);
        return buildings.size - 1;
    }

    /**
     * Run the tick() method on each building in the world building store
     * and update the counts for buildings of each use.
     */
    public void tickBuildings() {

        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        for (Building building : buildings) {
            buildingUseCounts.put(building.getBuildingUse(), buildingUseCounts.get(building.getBuildingUse()) + 1);
            building.tick();
        }
    }

    /**
     * Draw all the buildings into the world.
     */
    public void drawBuildings() {
        for (Building building : buildings) building.draw(batch);
    }

    /**
     * Get a building from the world building store
     * @param id The ID of a building (its index).
     * @return The building with the given ID / index
     */
    public Building getBuilding(Integer id) {
        return buildings.get(id);
    }

    /**
     * Utility method to check if a building overlaps with any others in the world
     * after being snapped to the grid based on its current location
     * @param id ID / index of the building in the world building store
     * @return true if the building overlaps with another, else false
     */
    public boolean doesBuildingOverlap(Integer id) {
        Building overlapBuilding = getBuilding(id);

        return doesBuildingOverlap(overlapBuilding);
    }

    /**
     * Utility method to check if a building overlaps with any others in the world
     * after being snapped to the grid based on its current location
     * @param overlapBuilding The building to check for overlaps with others
     * @return true if the building overlaps with another, else false
     */
    public boolean doesBuildingOverlap(Building overlapBuilding) {
        GridCoordTuple gridCoords = overlapBuilding.getGridCoords();

        for (Building building : buildings) {
            if (building.id != overlapBuilding.id) {
                if (building.gridX == gridCoords.x && building.gridY == gridCoords.y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Dispose of various renderers used by the world
     */
    public void dispose() {
        shapeRenderer.dispose();
        gridRenderer.dispose();
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
