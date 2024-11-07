package io.github.archessmn.ENG1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Buildings.*;
import io.github.archessmn.ENG1.Buildings.Building;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
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

    Boolean paused = false;

    private Stage stage;
    private Table rightTable;

    private Label timerLabel;
    private Label sleepBuildingsCountLabel;
    private Label learnBuildingsCountLabel;
    private Label eatBuildingsCountLabel;
    private Label recreationBuildingsCountLabel;

    @Override
    public void create() {
        // 300 here represents the pixel width of the UI on the right hand side
        world = new World(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, VIEWPORT_WIDTH - 300, VIEWPORT_HEIGHT);

        atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.addRegions(atlas);

        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);
        TextButtonStyle textButtonStyle = skin.get(TextButtonStyle.class);

        Label label = new Label("Title", labelStyle);
        timerLabel = new Label("Timer", labelStyle);
        Label sleepBuildingsLabel = new Label("Sleep buildings:", labelStyle);
        Label learnBuildingsLabel = new Label("Education buildings:", labelStyle);
        Label eatBuildingsLabel = new Label("Food buildings:", labelStyle);
        Label recreationBuildingsLabel = new Label("Recreation buildings:", labelStyle);
        sleepBuildingsCountLabel = new Label("", labelStyle);
        learnBuildingsCountLabel = new Label("", labelStyle);
        eatBuildingsCountLabel = new Label("", labelStyle);
        recreationBuildingsCountLabel = new Label("", labelStyle);
        TextButton button = new TextButton("Clear Buildings", textButtonStyle);


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rightTable = new Table();

        rightTable.pad(10);

        rootTable.right().add(rightTable).expandY().fillY().width(300);

//        rootTable.setDebug(true);
//        rightTable.setDebug(true);

        rightTable.add(label).row();
        rightTable.add(timerLabel).row();
        rightTable.add(sleepBuildingsLabel).left();
        rightTable.add(sleepBuildingsCountLabel).right().row();
        rightTable.add(learnBuildingsLabel).left();
        rightTable.add(learnBuildingsCountLabel).right().row();
        rightTable.add(eatBuildingsLabel).left();
        rightTable.add(eatBuildingsCountLabel).right().row();
        rightTable.add(recreationBuildingsLabel).left();
        rightTable.add(recreationBuildingsCountLabel).right().row();
        rightTable.add(button).expandX().expandY().left().top().row();

        assetManager = world.assetManager;

        touchPos = new Vector2();
        unprojectedTouchPos = new Vector2();

//        buildings = new Array<>();
        draggableBuildings = new Array<>();

        draggableBuildings.add(new GymBuilding(world, 660, 0, true));
        draggableBuildings.add(new HallsBuilding(world, 720, 0, true));
        draggableBuildings.add(new LectureHallBuilding(world, 780, 0, true));
        draggableBuildings.add(new OfficeBuilding(world, 840, 0, true));
        draggableBuildings.add(new PiazzaBuilding(world, 900, 0, true));

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

            building.place();

            buildingClicked = -1;
        }

        if (buildingClicked != -1) {
            world.getBuilding(buildingClicked).setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic() {
        if (paused) return;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (buildingClicked != -1) {
            Building building = world.getBuilding(buildingClicked);
            Vector2 buldingCoords = building.getGridCoords();
            shapeRenderer.rect(buldingCoords.x - (building.width / 2), buldingCoords.y - (building.height / 2), building.width, building.height);
        }

        shapeRenderer.end();



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

        if (paused) font.draw(world.batch, "Paused", 480, 400);

        world.batch.end();

        sleepBuildingsCountLabel.setText(world.sleepBuildings);
        learnBuildingsCountLabel.setText(world.learnBuildings);
        eatBuildingsCountLabel.setText(world.eatBuildings);
        recreationBuildingsCountLabel.setText(world.recreationBuildings);

        stage.draw();
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
