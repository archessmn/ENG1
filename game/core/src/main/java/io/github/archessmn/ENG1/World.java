package io.github.archessmn.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.archessmn.ENG1.Util.GridUtils;

/** {@link com.badlogic.gdx.ApplicationListener} */
public class World {
    public FitViewport viewport;

    public AssetManager assetManager;

    public ShapeRenderer shapeRenderer;
    public ShapeRenderer gridRenderer;

    public BitmapFont font;

    public World(Integer VIEWPORT_WIDTH, Integer VIEWPORT_HEIGHT) {
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        assetManager = new AssetManager();

        assetManager.load("gym.png", Texture.class);
        assetManager.load("halls.png", Texture.class);
        assetManager.load("lecturehall.png", Texture.class);
        assetManager.load("offices.png", Texture.class);
        assetManager.load("piazza.png", Texture.class);
        assetManager.load("missing_texture.png", Texture.class);

        shapeRenderer = new ShapeRenderer();
        gridRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(0.05f * Gdx.graphics.getHeight());
        font = generator.generateFont(parameter);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        generator.dispose();

        assetManager.finishLoading();
    }

    public void drawGrid() {
        GridUtils.drawGrid(gridRenderer);
    }
}
