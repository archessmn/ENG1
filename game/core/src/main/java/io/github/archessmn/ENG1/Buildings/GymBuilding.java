package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class GymBuilding extends Building {
    public GymBuilding(World world, float x, float y) {
        super(world, Type.GYM, x, y, 60, 60);

        this.sprite = new Sprite(world.assetManager.get("gym.png", Texture.class));

        this.sprite.setSize(60, 60);
    }
}
