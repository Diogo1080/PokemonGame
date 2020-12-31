package Game.Ententies.Events;

import java.util.List;

public class onPlayerPosition extends Event{
    public onPlayerPosition(String name, List<String> talks, boolean repeat,boolean moveToPlayer, boolean done,List<Integer> x,List<Integer> y) {
        super(name, talks, repeat);
    }
}
