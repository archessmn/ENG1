package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class PiazzaBuilding extends Building {
    public PiazzaBuilding(World world, float x, float y) {
        super(world, Type.PIAZZA, x, y, 60, 60);

        this.sprite = new Sprite(world.assetManager.get("piazza.png", Texture.class));

        this.sprite.setSize(60, 60);
    }
}
