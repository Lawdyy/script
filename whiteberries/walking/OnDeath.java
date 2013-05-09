package whiteberries.walking;


import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import whiteberries.WhiteBerries;

import static org.powerbot.core.script.job.Task.sleep;

public class OnDeath {

    public static void deathWalk(){

        WhiteBerries.status = "Deathwalking" ;

        if(Calculations.distanceTo(new Tile(3091, 3474, 0)) < 30){
            if(Calculations.distanceTo(new Tile(3091, 3474, 0)) < 3){
                if(SceneEntities.getNearest(1814) != null){

                    if(Widgets.get(1189, 12).validate() && Widgets.get(1189, 12).isOnScreen()){
                        Widgets.get(1189, 12).interact("Continue");
                        sleep(900,1200);
                    }else if (Widgets.get(1188,3).validate() && Widgets.get(1188,3).isOnScreen()){
                        Widgets.get(1188,3).interact("Continue");
                        sleep(900,1200);
                    }else{
                        SceneEntities.getNearest(1814).interact("Pull");
                        sleep(500,800);
                        if(WhiteBerries.pulled){
                            sleep(3000, 4000);
                            WhiteBerries.pulled = false;
                        }
                    }


                }
            }else{
                Walking.findPath(new Tile(3091, 3474, 0)).traverse();
                sleep(1000,2000);
            }
        }else{
            if(Calculations.distanceTo(new Tile(3166, 3957, 0)) < 4){
                WhiteBerries.dead= false;
            }else{

                SceneObject web = SceneEntities.getNearest(65346);

                if(web != null && Calculations.distanceTo(web) < 8){

                    if(web.isOnScreen()){
                        web.interact("Slash");
                        Timer opening = new Timer(2000);
                        while(opening.isRunning() && web.validate()){
                            sleep(50, 70);
                        }
                    }else{
                        Camera.turnTo(web);
                    }

                }else{
                    Paths.deathPath.randomize(-1,1).traverse();
                }
            }
        }

    }

}
