package ph.edu.usc.skillboost.model;

import java.util.List;

public class Topic {
    private String name;
    private boolean selected;

    public Topic() {}
    public Topic(String name) {
        this.name = name;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelected() {
        this.selected = !this.selected;
    }
}
