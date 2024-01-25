package io.munkush.app;

import lombok.Data;

@Data
public class Room {
    private String roomId;
    private Player first;
    private Player second;

    private String winner = null;
    public void addStep(int step, String nickname){
        if(first.getNickname().equals(nickname)) {
            first.addStep(step);
        } else {
            second.addStep(step);
        }

        if(WinnerUtil.isWinner(first.getSteps())){
            winner = first.getNickname();
        } else if(WinnerUtil.isWinner(second.getSteps())){
            winner = second.getNickname();
        }
    }
    public boolean isPlayerContains(String nickname){
        return first.getNickname().equals(nickname) || second.getNickname().equals(nickname);
    }


    public void changeStep(){
        if(first.isFirst()){
            first.setFirst(false);
            second.setFirst(true);
        } else {
            first.setFirst(true);
            second.setFirst(false);
        }
    }
}
