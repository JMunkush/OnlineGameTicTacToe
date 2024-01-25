package io.munkush.app;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Player {
    private String nickname;
    private boolean isX;
    private boolean isFirst;

    private List<Integer> steps = new ArrayList<>();

    public void addStep(int step){
        steps.add(step);
    }
}
