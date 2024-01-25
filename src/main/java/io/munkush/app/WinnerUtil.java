package io.munkush.app;

import java.util.List;
import java.util.Map;

public class WinnerUtil {


    static Map<String,List<Integer>> patterns = Map.of(
            "1", List.of(1,2,3),
            "2", List.of(4,5,6),
            "3", List.of(7,8,9),
            "4", List.of(1,4,7),
            "5", List.of(2,5,8),
            "6", List.of(3,6,9),
            "7", List.of(1,5,9),
            "8", List.of(3,5,7)
    );



    public static boolean isWinner(List<Integer> steps) {
        if (steps.size() < 3) {
            return false;
        } else {
            for (int i = 1; i <= 8; i++) {
                List<Integer> pattern = patterns.get(String.valueOf(i));

                int count = 0;
                for (Integer step : steps) {
                    if (pattern.contains(step)) {
                        count++;
                    }
                }

                if (count == 3) {
                    return true;
                }
            }
        }
        return false;
    }

}
