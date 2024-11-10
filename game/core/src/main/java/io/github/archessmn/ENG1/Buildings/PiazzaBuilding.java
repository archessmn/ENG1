package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

/**
 * Wrapper of {@link Building} that creates a building with the PIAZZA type.
 */
public class PiazzaBuilding extends Building {
    public PiazzaBuilding(World world, float x, float y, boolean built) {
        super(world, Type.PIAZZA, x, y, 60, 60, 10f, built);
    }
}
