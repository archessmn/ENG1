package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

/**
 * Wrapper of {@link Building} that creates a building with the GYM type.
 */
public class GymBuilding extends Building {
    public GymBuilding(World world, float x, float y, boolean built) {
        super(world, Type.GYM, x, y, 60, 60, 10f, built);
    }
}
