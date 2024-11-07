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
import io.github.archessmn.ENG1.Util.GridUtils;

/** {@link com.badlogic.gdx.ApplicationListener} */
public class World {
    public FitViewport viewport;

    public AssetManager assetManager;

    public ShapeRenderer shapeRenderer;
    public ShapeRenderer gridRenderer;

    public SpriteBatch batch;

    public BitmapFont font;

    public Integer width, height;

    public Array<Building> buildings;

    public Integer sleepBuildings = 0;
    public Integer learnBuildings = 0;
    public Integer eatBuildings = 0;
    public Integer recreationBuildings = 0;

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
        assetManager.load("missing_texture.png", Texture.class);

        shapeRenderer = new ShapeRenderer();
        gridRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(0.05f * Gdx.graphics.getHeight());
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

        sleepBuildings = 0;
        learnBuildings = 0;
        eatBuildings = 0;
        recreationBuildings = 0;

        for (Building building : buildings) {
            switch (building.getBuildingUse()) {
                case SLEEP: sleepBuildings++; break;
                case LEARN: learnBuildings++; break;
                case EAT: eatBuildings++; break;
                case RECREATION: recreationBuildings++; break;
            }
            building.tick(Gdx.graphics.getDeltaTime());
        };
    }

    public void drawBuildings() {
        for (Building building : buildings) building.draw(batch);
    }

    public Building getBuilding(Integer id) {
        return buildings.get(id);
    }

    public void dispose() {
        shapeRenderer.dispose();
        gridRenderer.dispose();
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
