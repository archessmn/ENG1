package io.github.archessmn.ENG1.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static io.github.archessmn.ENG1.Main.VIEWPORT_HEIGHT;
import static io.github.archessmn.ENG1.Main.VIEWPORT_WIDTH;

/**
 * Utilities to assist with usage of the world grid.
 */
public class GridUtils {
    /**
     * Draws a grid into the viewport using the {@link ShapeRenderer} passed to it.
     * @param gridRenderer The {@link ShapeRenderer} used to draw the grid.
     */
    public static void drawGrid(ShapeRenderer gridRenderer) {
        gridRenderer.begin(ShapeRenderer.ShapeType.Line);

        gridRenderer.setColor(new Color(0x5b7e13ff));

        float gridWidth = (VIEWPORT_WIDTH / 16f);
        float gridHeight = (VIEWPORT_HEIGHT / 9f);

        for (int v = 1; v < 9; v++) {
            gridRenderer.line(0, gridHeight * v, VIEWPORT_WIDTH - 3, gridHeight * v);
        }

        for (int h = 1; h < 11; h++) {
            gridRenderer.line(gridWidth * h, 0, gridWidth * h, VIEWPORT_HEIGHT);
        }

        gridRenderer.end();

    }

    /**
     * Get the raw coordinates of the grid square the given coordinates would snap to.
     * @param x The center X coordinate of the object
     * @param y The center Y coordinate of the object
     * @return A {@link Vector2} with coordinates of a grid square.
     */
    public static Vector2 getRawGridCoords(float x, float y) {
        float rawGridX = calcRawGridXCoord(x);
        float rawGridY = calcRawGridYCoord(y);
        return new Vector2(rawGridX, rawGridY);
    }

    /**
     * Get the normalised coordinates of the grid square the given coordinates
     * would snap to, relative to the grid.
     * For example, the top left grid position would be (0, 8).
     * @param x The center X coordinate of the object
     * @param y The center Y coordinate of the object
     * @return A {@link GridCoordTuple} with coordinates in the grid.
     */
    public static GridCoordTuple getGridCoords(float x, float y) {
        float gridWidth = (VIEWPORT_WIDTH / 16f);
        float gridHeight = (VIEWPORT_HEIGHT / 9f);

        int gridX = Math.round((x / gridWidth) + 0.5f);
        int gridY = Math.round((y / gridHeight) + 0.5f);

        return new GridCoordTuple(gridX, gridY);
    }

    /**
     * Used to calculate the closes grid square in the X direction
     * @param coord The X coordinate of the object
     * @return X coordinate of the closest grid square
     */
    public static float calcRawGridXCoord(float coord) {
        float gridWidth = (VIEWPORT_WIDTH / 16f);
        return (MathUtils.round(coord / gridWidth + 0.5f) * gridWidth) - (gridWidth / 2f);
    }

    /**
     * Used to calculate the closes grid square in the Y direction
     * @param coord The Y coordinate of the object
     * @return Y coordinate of the closest grid square
     */
    public static float calcRawGridYCoord(float coord) {
        float gridHeight = (VIEWPORT_HEIGHT / 9f);
        return (MathUtils.round(coord / (gridHeight) + 0.5f) * gridHeight) - (gridHeight / 2f);
    }
}
