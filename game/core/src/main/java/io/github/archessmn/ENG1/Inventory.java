package io.github.archessmn.ENG1;

import com.badlogic.gdx.graphics.Texture;

public class Inventory {
    private Texture[] buildings;
    private int selectedIndex;

    public Inventory() {
        buildings = new Texture[] {
            new Texture("offices.png"),
            new Texture("piazza.png"),
            new Texture("halls.png"),
            new Texture("gym.png"),
            new Texture("lecturehall.png")
        };
        selectedIndex = 0;
    }

    public Building getSelectedBuilding() {
        if (selectedIndex < buildings.length) {
            return new Building(buildings[selectedIndex]);
        }
        return null;
    }

    public void cycleInventory() {
        selectedIndex = (selectedIndex + 1) % buildings.length;
    }

    public void dispose() {
        for (Texture texture : buildings) {
            texture.dispose();
        }
    }
}
