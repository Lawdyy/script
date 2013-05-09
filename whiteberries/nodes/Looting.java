package whiteberries.nodes;


import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import whiteberries.WhiteBerries;
import whiteberries.walking.Paths;

public class Looting extends Node {


    @Override
    public boolean activate() {
        return !Inventory.isFull() && Calculations.distanceTo(Paths.berrieTile) < 7;
    }

    @Override
    public void execute() {

        WhiteBerries.status = "Taking berries";
        GroundItem berrie = GroundItems.getNearest(239);

        if(berrie != null && berrie.validate()){
            if(berrie.isOnScreen()){

                berrie.interact("Take", "White berries");
                sleep(500,600);
                    Timer taking = new Timer(2000);
                    while(taking.isRunning() && berrie.validate()){
                        Task.sleep(50, 70);
                    }
                }else{
                Camera.turnTo(berrie);
            }
        }
    }
}

