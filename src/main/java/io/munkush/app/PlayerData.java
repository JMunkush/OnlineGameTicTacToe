package io.munkush.app;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerData {
    private String roomId;

    private String versusPlayerName;

    private Player player;

    private List<Integer> steps = new ArrayList<>();



}
