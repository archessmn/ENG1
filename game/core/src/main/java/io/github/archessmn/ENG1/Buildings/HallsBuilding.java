package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

/**
 * Wrapper of {@link Building} that creates a building with the HALLS type.
 */
public class HallsBuilding extends Building {
    public HallsBuilding(World world, float x, float y, boolean built) {
        super(world, Type.HALLS, x, y, 60, 60, 10f, built);
    }
}
