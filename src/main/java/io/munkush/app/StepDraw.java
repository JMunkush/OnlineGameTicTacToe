package io.munkush.app;

import lombok.Data;

@Data
public class StepDraw {
    private int index;
    private boolean isX;
    private String winner = null;
}
