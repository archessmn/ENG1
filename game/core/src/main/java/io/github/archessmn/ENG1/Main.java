package io.github.archessmn.ENG1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture pipe;

    Sound pipeDrop;
    Sprite logo;
    Sprite pipeSprite;
    FitViewport viewport;

    Vector2 touchPos;

    Array<Sprite> dropSprites;

    float dropTimer;

    Rectangle logoRectangle;
    Rectangle dropRectangle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        pipe = new Texture("pipe.png");
        pipeDrop = Gdx.audio.newSound(Gdx.files.internal("pipe-drop.mp3"));

        logo = new Sprite(image);
        pipeSprite = new Sprite(pipe);
        logo.setSize(1,1);

        touchPos = new Vector2();

        dropSprites = new Array<>();

        viewport = new FitViewport(16, 9);

        logoRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        createDroplet();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
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

        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            logo.translateX((speed * delta));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            logo.translateX(-(speed * delta));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            logo.translateY((speed * delta));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            logo.translateY(-(speed * delta));
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());

            viewport.unproject(touchPos);
            logo.setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic() {
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

            dropSprite.translateY(-2f * delta);

            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            // if the top of the drop goes below the bottom of the view, remove it
            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (logoRectangle.overlaps(dropRectangle)) { // Check if the bucket overlaps the drop
                dropSprites.removeIndex(i); // Remove the drop
                logo.setSize(logoWidth * 1.1f, logoHeight * 1.1f);
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

        batch.begin();

        logo.draw(batch);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
