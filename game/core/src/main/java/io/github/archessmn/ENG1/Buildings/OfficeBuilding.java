package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class OfficeBuilding extends Building {
    public OfficeBuilding(World world, float x, float y) {
        super(world, Type.OFFICES, x, y, 60, 60);

        this.sprite = new Sprite(world.assetManager.get("offices.png", Texture.class));

        this.sprite.setSize(60, 60);
    }
}