package net.staticstudios.mines;

import java.util.ArrayList;
import java.util.List;

public class StaticMineUtils {
    public static List<String> filterStringList(List<String> list, String currentArg) {
        List<String> sortedList = new ArrayList<>();
        for (String str : list) {
            if (str.toLowerCase().startsWith(currentArg.toLowerCase())) sortedList.add(str);
        }
        return sortedList;
    }
}
