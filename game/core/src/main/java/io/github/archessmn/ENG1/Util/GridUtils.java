package io.github.archessmn.ENG1.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static io.github.archessmn.ENG1.Main.VIEWPORT_HEIGHT;
import static io.github.archessmn.ENG1.Main.VIEWPORT_WIDTH;

public class GridUtils {
    public static void drawGrid(ShapeRenderer gridRenderer) {
        gridRenderer.begin(ShapeRenderer.ShapeType.Line);

        gridRenderer.setColor(new Color(0x5b7e13ff));

        float gridWidth = (VIEWPORT_WIDTH / 16f);
        float gridHeight = (VIEWPORT_HEIGHT / 9f);

        for (int v = 1; v < 9; v++) {
            gridRenderer.line(0, gridHeight * v, VIEWPORT_WIDTH - 300, gridHeight * v);
        }

        for (int h = 1; h < 11; h++) {
            gridRenderer.line(gridWidth * h, 0, gridWidth * h, VIEWPORT_HEIGHT);
        }

        gridRenderer.end();

    }


    public static Vector2 getRawGridCoords(Vector2 pos) {
        float rawGridX = calcRawGridXCoord(pos.x);
        float rawGridY = calcRawGridYCoord(pos.y);
        return new Vector2(rawGridX, rawGridY);
    }

    public static Vector2 getRawGridCoords(float x, float y) {
        float rawGridX = calcRawGridXCoord(x);
        float rawGridY = calcRawGridYCoord(y);
        return new Vector2(rawGridX, rawGridY);
    }

    public static GridCoordTuple getGridCoords(float x, float y) {
        float gridWidth = (VIEWPORT_WIDTH / 16f);
        float gridHeight = (VIEWPORT_HEIGHT / 9f);

        int gridX = Math.round((x / gridWidth) + 0.5f);
        int gridY = Math.round((y / gridHeight) + 0.5f);

        return new GridCoordTuple(gridX, gridY);
    }

    public static float calcRawGridXCoord(float coord) {
        float gridWidth = (VIEWPORT_WIDTH / 16f);
        return (MathUtils.round(coord / gridWidth + 0.5f) * gridWidth) - (gridWidth / 2f);
    }

    public static float calcRawGridYCoord(float coord) {
        float gridHeight = (VIEWPORT_HEIGHT / 9f);
        return (MathUtils.round(coord / (gridHeight) + 0.5f) * gridHeight) - (gridHeight / 2f);
    }
}
