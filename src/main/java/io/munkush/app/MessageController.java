package io.munkush.app;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;



    List<Room> roomList = new ArrayList<>();
    List<Player> players = Collections.synchronizedList(new ArrayList<>());
    @MessageMapping("/registration")
    public Player registration(@Payload Player player){
        if(players.size() == 0){
            players.add(player);
        } else {
            var versusPlayer = players.get(0);
            players.remove(0);

            initRoom(player, versusPlayer);
        }
        return player;
    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@Payload Step step) {

        var room = roomList.stream()
                .filter(r -> r.getRoomId().equals(step.getPlayerData().getRoomId()))
                .findFirst().orElseThrow();

        room.changeStep();

        sendStepToPlayers(room);




        room.addStep(step.getStepIndex() + 1, step.getPlayerData().getPlayer().getNickname());


        StepDraw stepDraw = new StepDraw();
        stepDraw.setX(step.getPlayerData().getPlayer().isX());
        stepDraw.setIndex(step.getStepIndex());
        stepDraw.setWinner(room.getWinner());

        messagingTemplate.convertAndSend("/broker/room/" + step.getPlayerData().getRoomId(), stepDraw);
    }

    @Scheduled(fixedDelay = 1000)
    public void schedule(){
        System.out.println(roomList);
    }
    private void sendStepToPlayers(Room room) {
        PlayerData playerData1 = new PlayerData();
        playerData1.setRoomId(room.getRoomId());
        playerData1.setPlayer(room.getFirst());
        playerData1.setVersusPlayerName(room.getSecond().getNickname());

        PlayerData playerData2 = new PlayerData();
        playerData2.setRoomId(room.getRoomId());
        playerData2.setPlayer(room.getSecond());
        playerData2.setVersusPlayerName(room.getFirst().getNickname());

        sendEndpoint(room.getFirst());
        sendEndpoint(room.getSecond());
    }


    private void initRoom(Player player, Player versusPlayer) {
        Random random = new Random();

        int xRandom = random.nextInt(2);
        int queue = random.nextInt(2);
        if(xRandom == 0){
            player.setX(true);
            versusPlayer.setX(false);
        } else {
            player.setX(false);
            versusPlayer.setX(true);
        }

        if(queue == 0){
            player.setFirst(true);
            versusPlayer.setFirst(false);
        } else {
            player.setFirst(false);
            versusPlayer.setFirst(true);
        }

        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setFirst(player);
        room.setSecond(versusPlayer);


        roomList.add(room);

        sendEndpoint(room.getFirst());
        sendEndpoint(room.getSecond());
    }

    public void sendEndpoint(Player player){

        var nickname = player.getNickname();

        var room = roomList.stream().filter(r -> r.isPlayerContains(nickname))
                .findFirst().orElseThrow();

        System.out.println(room.getRoomId());

        String destination = "/broker/" + nickname + "/queue/private";

        messagingTemplate.convertAndSend(destination, getPlayerData(room, nickname));
    }

    private PlayerData getPlayerData(Room room, String playerNickname) {
        Player player;
        Player versusPlayer;
        if(room.getFirst().getNickname().equals(playerNickname)) {
            player = room.getFirst();
            versusPlayer = room.getSecond();
        } else {
            player = room.getSecond();
            versusPlayer = room.getFirst();
        }

        PlayerData playerData = new PlayerData();

        playerData.setPlayer(player);
        playerData.setVersusPlayerName(versusPlayer.getNickname());
        playerData.setRoomId(room.getRoomId());

        return playerData;

    }


}
