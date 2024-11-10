package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

/**
 * Wrapper of {@link Building} that creates a building with the OFFICES type.
 */
public class OfficeBuilding extends Building {
    public OfficeBuilding(World world, float x, float y, boolean built) {
        super(world, Type.OFFICES, x, y, 60, 60, 10f, built);
    }
}
