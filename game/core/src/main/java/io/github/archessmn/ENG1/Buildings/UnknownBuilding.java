package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class UnknownBuilding extends Building {
    public UnknownBuilding(World world, Type buildingType, float x, float y, float width, float height) {
        super(world, buildingType, x, y, width, height);

        String spriteFileName;

        switch (buildingType) {
            case GYM -> spriteFileName = "gym.png";
            case HALLS -> spriteFileName = "halls.png";
            case LECTURE_HALL -> spriteFileName = "lecturehall.png";
            case OFFICES -> spriteFileName = "offices.png";
            case PIAZZA -> spriteFileName = "piazza.png";
            default -> spriteFileName = "missing_texture.png";
        }

        this.sprite = new Sprite(world.assetManager.get(spriteFileName, Texture.class));

        this.sprite.setSize(60, 60);
    }
}
