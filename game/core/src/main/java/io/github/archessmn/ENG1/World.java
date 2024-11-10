package io.github.archessmn.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
 * {@link com.badlogic.gdx.ApplicationListener}
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

    public void drawGrid() {
        GridUtils.drawGrid(gridRenderer);
    }

    public int addBuilding(Building building) {
        buildings.add(building);
        return buildings.size - 1;
    }

    public void tickBuildings() {

        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        for (Building building : buildings) {
            buildingUseCounts.put(building.getBuildingUse(), buildingUseCounts.get(building.getBuildingUse()) + 1);
            building.tick(Gdx.graphics.getDeltaTime());
        }
        ;
    }

    public void drawBuildings() {
        for (Building building : buildings) building.draw(batch);
    }

    public Building getBuilding(Integer id) {
        return buildings.get(id);
    }

    public boolean doesBuildingOverlap(Integer id) {
        Building overlapBuilding = getBuilding(id);

        return doesBuildingOverlap(overlapBuilding);
    }

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

    public void dispose() {
        shapeRenderer.dispose();
        gridRenderer.dispose();
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
