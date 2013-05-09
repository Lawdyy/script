package whiteberries.walking;


import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;
import whiteberries.WhiteBerries;

import static org.powerbot.core.script.job.Task.sleep;

public class RunEnergy {

    public static void Rest(){
    if(Walking.getEnergy() < 2){
        if(!Players.getLocal().isInCombat()){
            sleep(900,1200);
            if(Walking.getEnergy() < 10){
                WhiteBerries.rest = true;
            }
        }else{
            WhiteBerries.rest = false;
        }
    }

    if(WhiteBerries.rest){
        if(Widgets.get(548, 166).validate() && Widgets.get(548, 166).isOnScreen()){
            Widgets.get(548, 166).interact("Rest");
            Timer resting = new Timer(20000);
            while(resting.isRunning() && Walking.getEnergy() < 70){
                sleep(50);
            }
            WhiteBerries.rest = false;
        }
    }
    }
}
