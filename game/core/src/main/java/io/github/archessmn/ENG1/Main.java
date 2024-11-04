package io.github.archessmn.ENG1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Buildings.*;
import io.github.archessmn.ENG1.Buildings.Building;
import io.github.archessmn.ENG1.Util.GridUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final Integer VIEWPORT_WIDTH = 960;
    public static final Integer VIEWPORT_HEIGHT = 540;

    World world;

    private SpriteBatch batch;

    AssetManager assetManager;

    ShapeRenderer gridRenderer;
    ShapeRenderer shapeRenderer;

    FitViewport viewport;

    Vector2 touchPos;
    Vector2 unprojectedTouchPos;

//    Array<Sprite> logoSprites;

//    Array<Sprite> dropSprites;

    Array<Building> draggableBuildings;
    Array<Building> buildings;

    float dropTimer;

    float gameTimer;

    Rectangle buildingRectangle;
//    Rectangle dropRectangle;
    BitmapFont font;
    Boolean isClicked = false;
    Integer buildingClicked = -1;

    Boolean paused = false;

    Integer score = 0;

    @Override
    public void create() {
        world = new World(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        assetManager = world.assetManager;

        touchPos = new Vector2();
        unprojectedTouchPos = new Vector2();

        buildings = new Array<>();
        draggableBuildings = new Array<>();

        draggableBuildings.add(new GymBuilding(world, 0, 0));
        draggableBuildings.add(new HallsBuilding(world, 60, 0));
        draggableBuildings.add(new LectureHallBuilding(world, 120, 0));
        draggableBuildings.add(new OfficeBuilding(world, 180, 0));
        draggableBuildings.add(new PiazzaBuilding(world, 240, 0));

        gameTimer = 0f;

        shapeRenderer = world.shapeRenderer;
        gridRenderer = world.gridRenderer;

        viewport = world.viewport;

        buildingRectangle = new Rectangle();

        font = world.font;
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void createDroplet() {
        float dropWidth = 60;
        float dropHeight = 60;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

//        Sprite dropSprite = new Sprite(hallsSprite);
//        dropSprite.setSize(dropWidth, dropHeight);
//        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
//        dropSprite.setY(worldHeight);
//        dropSprites.add(dropSprite);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) paused = !paused;

        if (paused) {
            buildingClicked = -1;
            return;
        }

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        unprojectedTouchPos.set(viewport.unproject(touchPos));

        isClicked = Gdx.input.isTouched();

        if (Gdx.input.justTouched()) {
            for (int i = draggableBuildings.size - 1; i >= 0; i--) {
                Building building = draggableBuildings.get(i);
//                Sprite sprite = building.sprite;

                if (building.getBounds().contains(unprojectedTouchPos)) {
                    buildings.add(building.makeCopy());
                    buildingClicked = buildings.size - 1;
                    break;
                }
            }
        } else if (!isClicked && buildingClicked != -1) {
            Building building = buildings.get(buildingClicked);

            building.snapToGrid();

            buildingClicked = -1;
        }

        if (buildingClicked != -1) {
            buildings.get(buildingClicked).setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic() {
        if (paused) return;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        for (Building building : buildings) {
            building.sprite.setX(MathUtils.clamp(building.x, 0, worldWidth - building.width));
            building.sprite.setY(MathUtils.clamp(building.y, 0, worldHeight - building.height));
        }

        float delta = Gdx.graphics.getDeltaTime();

        gameTimer += delta;

        dropTimer += delta; // Adds the current delta to the timer
        if (dropTimer > 1f) { // Check if it has been more than a second
            dropTimer = 0; // Reset the timer
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.OLIVE);
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        world.drawGrid();

        // Draw red outline for where the logo will snap to
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (buildingClicked != -1) {
            Building building = buildings.get(buildingClicked);
            Vector2 buldingCoords = building.getGridCoords();
            shapeRenderer.rect(buldingCoords.x - (building.width / 2), buldingCoords.y - (building.height / 2), building.width, building.height);
        }

        shapeRenderer.end();

        batch.begin();

        for (Building building : draggableBuildings) {
            building.draw(batch);
        }

        for (Building building : buildings) {
            building.draw(batch);
        }

        font.draw(batch, String.format("Score: %d", score), 0, 520);
        font.draw(batch, String.format("Time: %f", gameTimer), 0, 480);
        font.draw(batch, String.format("touch x: %f touch y: %f", touchPos.x, touchPos.y), 0, 440);
        if (buildingClicked != -1) font.draw(batch, String.format("touch x: %f touch y: %f", buildings.get(buildingClicked).x, buildings.get(buildingClicked).y), 0, 400);
        font.draw(batch, String.format("buildingClicked: %d", buildingClicked), 0, 360);

        if (paused) font.draw(batch, "Paused", 480, 400);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }
}
