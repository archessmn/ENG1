package io.github.archessmn.ENG1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Util.GridUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final Integer VIEWPORT_WIDTH = 960;
    public static final Integer VIEWPORT_HEIGHT = 540;

    private SpriteBatch batch;
    private Texture image;
    private Texture pipe;

    ShapeRenderer gridRenderer;
    ShapeRenderer shapeRenderer;

    Sound pipeDrop;
    Sprite logo;
    Sprite pipeSprite;
    FitViewport viewport;

    Vector2 touchPos;
    Vector2 unprojectedTouchPos;

    Array<Sprite> dropSprites;

    float dropTimer;

    Rectangle logoRectangle;
    Rectangle dropRectangle;
    BitmapFont font;
    Boolean isClicked = false;
    Boolean isLogoCLicked = false;

    Boolean paused = false;

    Integer score = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        pipe = new Texture("pipe.png");
        pipeDrop = Gdx.audio.newSound(Gdx.files.internal("pipe-drop.mp3"));

        logo = new Sprite(image);
        pipeSprite = new Sprite(pipe);
        logo.setSize(60,60);

        touchPos = new Vector2();
        unprojectedTouchPos = new Vector2();

        dropSprites = new Array<>();

        gridRenderer = new ShapeRenderer();
        shapeRenderer = new ShapeRenderer();

        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        logoRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(0.05f * Gdx.graphics.getHeight());
        font = generator.generateFont(parameter);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        generator.dispose();

        createDroplet();
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

        Sprite dropSprite = new Sprite(pipeSprite);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) paused = !paused;

        if (paused) {
            isLogoCLicked = false;
            return;
        };

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        unprojectedTouchPos.set(viewport.unproject(touchPos));

        isClicked = Gdx.input.isTouched();

        if (Gdx.input.justTouched()) {
            isLogoCLicked = logoRectangle.contains(unprojectedTouchPos);
        } else if (!isClicked) {
            isLogoCLicked = false;

            Vector2 gridCoords = GridUtils.getGridCoords(logo.getX() + logo.getWidth() / 2, logo.getY() + logo.getHeight() / 2);

            logo.setCenter(gridCoords.x, gridCoords.y);
        }


        if (isLogoCLicked) {
            logo.setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic() {
        if (paused) return;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();

        logo.setX(MathUtils.clamp(logo.getX(), 0, worldWidth - logoWidth));
        logo.setY(MathUtils.clamp(logo.getY(), 0, worldHeight - logoHeight));

        float delta = Gdx.graphics.getDeltaTime();

        logoRectangle.set(logo.getX(), logo.getY(), logoWidth, logoHeight);

        // Loop through the sprites backwards to prevent out of bounds errors
        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i); // Get the sprite from the list
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-120f * delta);

            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            // if the top of the drop goes below the bottom of the view, remove it
            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (logoRectangle.overlaps(dropRectangle)) { // Check if the bucket overlaps the drop
                dropSprites.removeIndex(i); // Remove the drop
                score += 1;
                pipeDrop.play();
            }
        }

        dropTimer += delta; // Adds the current delta to the timer
        if (dropTimer > 1f) { // Check if it has been more than a second
            dropTimer = 0; // Reset the timer
            createDroplet(); // Create the droplet
        }
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        //Draw grid using util function
        GridUtils.drawGrid(gridRenderer);

        // Draw red outline for where the logo will snap to
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f, 0, 0, 1f);
        Vector2 logoCoords = GridUtils.getGridCoords(logo.getX() + logo.getWidth() / 2, logo.getY() + logo.getHeight() / 2);
        shapeRenderer.rect(logoCoords.x - (logo.getWidth() / 2), logoCoords.y - (logo.getHeight() / 2), logoRectangle.width, logoRectangle.height);
        shapeRenderer.end();

        batch.begin();

        logo.draw(batch);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(batch);
        }

        font.draw(batch, String.format("Score: %d", score), 0, 520);
        font.draw(batch, String.format("touch x: %f touch y: %f", touchPos.x, touchPos.y), 0, 440);
        font.draw(batch, String.format("touch x: %f touch y: %f", logoRectangle.x, logoRectangle.y), 0, 400);

        if (paused) font.draw(batch, "Paused", 480, 400);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
