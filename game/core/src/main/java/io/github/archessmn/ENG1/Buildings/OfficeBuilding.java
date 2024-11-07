package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class OfficeBuilding extends Building {
    public OfficeBuilding(World world, float x, float y) {
        super(world, Type.OFFICES, x, y, 60, 60, 10f, false);
    }

    public OfficeBuilding(World world, float x, float y, boolean built) {
        super(world, Type.OFFICES, x, y, 60, 60, 10f, built);
    }
}
