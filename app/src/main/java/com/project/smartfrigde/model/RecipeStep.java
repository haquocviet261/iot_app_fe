package com.project.smartfrigde.model;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    private int step;
    private String instruction;

    public RecipeStep() {
    }

    public RecipeStep(int step, String instruction) {
        this.step = step;
        this.instruction = instruction;
    }

    public int getStep() {
        return step;
    }

    public String getInstruction() {
        return instruction;
    }
}
