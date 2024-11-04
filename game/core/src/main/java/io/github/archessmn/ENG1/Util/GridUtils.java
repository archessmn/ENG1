package io.github.archessmn.ENG1.Util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static io.github.archessmn.ENG1.Main.VIEWPORT_HEIGHT;
import static io.github.archessmn.ENG1.Main.VIEWPORT_WIDTH;

public class GridUtils {
    public static void drawGrid(ShapeRenderer gridRenderer) {
        gridRenderer.begin(ShapeRenderer.ShapeType.Line);

        float gridWidth = (VIEWPORT_WIDTH / 16f);
        float gridHeight = (VIEWPORT_HEIGHT / 9f);

        for (int v = 1; v < 9; v++) {
            gridRenderer.line(0, gridWidth * v, VIEWPORT_WIDTH, gridWidth * v);
        }

        for (int h = 1; h < 16; h++) {
            gridRenderer.line(gridHeight * h, 0,gridHeight * h, VIEWPORT_HEIGHT);
        }

        gridRenderer.end();

    }


    public static Vector2 getGridCoords(Vector2 pos) {
        float gridX = calcGridXCoord(pos.x);
        float gridY = calcGridYCoord(pos.y);
        return new Vector2(gridX, gridY);
    }

    public static Vector2 getGridCoords(float x, float y) {
        float gridX = calcGridXCoord(x);
        float gridY = calcGridYCoord(y);
        return new Vector2(gridX, gridY);
    }

    public static float calcGridXCoord(float coord) {
        float gridWidth = (VIEWPORT_WIDTH / 16f);
        return (MathUtils.round(coord / gridWidth + 0.5f) * gridWidth) - (gridWidth / 2f);
    }

    public static float calcGridYCoord(float coord) {
        float gridHeight = (VIEWPORT_HEIGHT / 9f);
        return (MathUtils.round(coord / (gridHeight) + 0.5f) * gridHeight) - (gridHeight / 2f);
    }
}
