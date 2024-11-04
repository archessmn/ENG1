package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class HallsBuilding extends Building {
    public HallsBuilding(World world, float x, float y) {
        super(world, Type.HALLS, x, y, 60, 60);

        this.sprite = new Sprite(world.assetManager.get("halls.png", Texture.class));

        this.sprite.setSize(60, 60);
    }
}
