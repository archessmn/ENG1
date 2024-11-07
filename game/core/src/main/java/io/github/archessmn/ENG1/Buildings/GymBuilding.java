package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

public class GymBuilding extends Building {
    public GymBuilding(World world, float x, float y) {
        super(world, Type.GYM, x, y, 60, 60, 10f, false);
    }

    public GymBuilding(World world, float x, float y, boolean built) {
        super(world, Type.GYM, x, y, 60, 60, 10f, built);
    }
}
