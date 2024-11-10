package io.github.archessmn.ENG1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Buildings.*;
import io.github.archessmn.ENG1.Buildings.Building;

import java.util.HashMap;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    public static final Integer VIEWPORT_WIDTH = 960;
    public static final Integer VIEWPORT_HEIGHT = 540;

    World world;

    AssetManager assetManager;

    TextureAtlas atlas;
    Skin skin;

    ShapeRenderer gridRenderer;
    ShapeRenderer shapeRenderer;
    ShapeRenderer blockRenderer;

    FitViewport viewport;

    Vector2 touchPos;
    Vector2 unprojectedTouchPos;

    Array<Building> draggableBuildings;
//    Array<Building> buildings;

    float gameTimer;

    Rectangle buildingRectangle;
    BitmapFont font;
    Boolean isClicked = false;
    Integer buildingClicked = -1;

    Boolean paused = true;

    private Stage stage;
    private Table rightTable;

    private Label timerLabel;
    private final HashMap<Building.Use, Label> buildingUseCountLabels = new HashMap<>();
    private final HashMap<Building.Use, Label> buildingUseNameLabels = new HashMap<>();

    @Override
    public void create() {
        // 300 here represents the pixel width of the UI on the right hand side
        world = new World(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, VIEWPORT_WIDTH - 300, VIEWPORT_HEIGHT);

        atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.addRegions(atlas);

        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);
        TextButtonStyle textButtonStyle = skin.get(TextButtonStyle.class);

        Label label = new Label("ENG1 CH2 GRP3 UniSim", labelStyle);
        timerLabel = new Label("Timer", labelStyle);

        for (Building.Use buildingUse : Building.Use.values()) {
            String useName = buildingUse.toString().charAt(0) + buildingUse.toString().substring(1).toLowerCase();
            buildingUseNameLabels.put(buildingUse, new Label(useName + " buildings:", labelStyle));
        }
        for (Building.Use buildingUse : Building.Use.values()) {
            buildingUseCountLabels.put(buildingUse, new Label("0", labelStyle));
        }

        TextButton button = new TextButton("Clear Buildings", textButtonStyle);


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rightTable = new Table();

        rightTable.pad(10);

        rootTable.right().add(rightTable).expandY().fillY().width(300);

        rightTable.add(label).row();
        rightTable.add(timerLabel).row();
        for (Building.Use buildingUse : Building.Use.values()) {
            rightTable.add(buildingUseNameLabels.get(buildingUse)).left();
            rightTable.add(buildingUseCountLabels.get(buildingUse)).right().row();
        }
        rightTable.add(new Label("Gym  Halls  Lecture Hall  Office  Piazza", labelStyle)).expandX().expandY().bottom();

        assetManager = world.assetManager;

        touchPos = new Vector2();
        unprojectedTouchPos = new Vector2();

//        buildings = new Array<>();
        draggableBuildings = new Array<>();

        draggableBuildings.add(new GymBuilding(world, 660, 40, true));
        draggableBuildings.add(new HallsBuilding(world, 720, 40, true));
        draggableBuildings.add(new LectureHallBuilding(world, 780, 40, true));
        draggableBuildings.add(new OfficeBuilding(world, 840, 40, true));
        draggableBuildings.add(new PiazzaBuilding(world, 900, 40, true));

        gameTimer = 0f;

        shapeRenderer = world.shapeRenderer;
        gridRenderer = world.gridRenderer;
        blockRenderer = new ShapeRenderer();

        viewport = world.viewport;

        buildingRectangle = new Rectangle();

        font = world.font;

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.buildings.clear();
            }
        });
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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

                if (building.getBounds().contains(unprojectedTouchPos)) {
                    buildingClicked = world.addBuilding(building.makeCopy());
                    break;
                }
            }
        } else if (!isClicked && buildingClicked != -1) {
            Building building = world.getBuilding(buildingClicked);

            boolean placeSuccess = building.place();

            if (!placeSuccess) {
                world.buildings.removeIndex(buildingClicked);
            }

            buildingClicked = -1;
        }

        if (buildingClicked != -1) {
            world.getBuilding(buildingClicked).setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic() {
        if (paused) return;

        float delta = Gdx.graphics.getDeltaTime();

        world.tickBuildings();

        gameTimer += delta;

        stage.act(delta);
    }

    private void draw() {
        ScreenUtils.clear(Color.OLIVE);
        viewport.apply();

        world.batch.setProjectionMatrix(viewport.getCamera().combined);

        world.drawGrid();

        // Draw red outline for where the logo will snap to
        if (buildingClicked != -1) {
            Building building = world.getBuilding(buildingClicked);
            if (world.doesBuildingOverlap(building)) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            }
            shapeRenderer.setColor(Color.RED);
            Vector2 buldingCoords = building.getRawGridCoords();
            shapeRenderer.rect(buldingCoords.x - (building.width / 2), buldingCoords.y - (building.height / 2), building.width, building.height);
            shapeRenderer.end();
        }


        blockRenderer.begin(ShapeRenderer.ShapeType.Filled);
        blockRenderer.setColor(Color.DARK_GRAY);

        blockRenderer.rect(rightTable.getX(), rightTable.getY(), rightTable.getWidth(), rightTable.getHeight());

        blockRenderer.end();

        world.batch.begin();

        for (Building building : draggableBuildings) {
            building.draw(world.batch);
        }

        world.drawBuildings();

        timerLabel.setText(String.format("Year: %d, Day: %d", (int) (gameTimer / 60) + 1, (int) ((gameTimer % 60) / (60 / (double) 365)) + 1));
        if (buildingClicked != -1) {
            if (world.doesBuildingOverlap(buildingClicked)) {
                font.draw(world.batch, "Buildings overlap", 20, 520);
            }
        }

        if (paused) {
            font.draw(world.batch, "Paused, press [ESC] to resume", 20, 460);
            font.draw(world.batch, "Building icons from macrovector on Freepik", 0, 25);
        }

        world.batch.end();

        for (Building.Use use : Building.Use.values()) {
            buildingUseCountLabels.get(use).setText(world.buildingUseCounts.get(use));
        }

        stage.draw();
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
