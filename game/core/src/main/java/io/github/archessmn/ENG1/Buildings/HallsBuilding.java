package io.github.archessmn.ENG1.Buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.archessmn.ENG1.World;

public class HallsBuilding extends Building {
    public HallsBuilding(World world, float x, float y) {
        super(world, Type.HALLS, x, y, 60, 60, 10f, false);
    }

    public HallsBuilding(World world, float x, float y, boolean built) {
        super(world, Type.HALLS, x, y, 60, 60, 10f, built);
    }
}
