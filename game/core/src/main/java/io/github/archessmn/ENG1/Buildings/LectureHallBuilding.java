package io.github.archessmn.ENG1.Buildings;

import io.github.archessmn.ENG1.World;

/**
 * Wrapper of {@link Building} that creates a building with the LECTURE_HALL type.
 */
public class LectureHallBuilding extends Building {
   public LectureHallBuilding(World world, float x, float y, boolean built) {
        super(world, Type.LECTURE_HALL, x, y, 60, 60, 10f, built);
    }
}
