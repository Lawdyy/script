package whiteberries.walking;


import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import whiteberries.WhiteBerries;

public class Walk extends Node {

    private int gate[] = {1596, 1597, 1557, 1558};

    private boolean toBerries(){
        return Inventory.getItem(239) == null && Calculations.distanceTo(Paths.berrieTile) > 4;
    }

    private boolean toBank(){
        return Inventory.isFull() && Calculations.distanceTo(Paths.bankTile) > 7;
    }

    @Override
    public boolean activate() {

        return toBerries() || toBank();
    }

    @Override
    public void execute() {

        WhiteBerries.status = "Walking to berries";

        SceneObject gates = SceneEntities.getNearest(gate);
        SceneObject woodenGate = SceneEntities.getNearest(65386);
        SceneObject web = SceneEntities.getNearest(64729);

        if(!WhiteBerries.rest){
        if(Calculations.distanceTo(Paths.bankTile) > 10){
            if(gates != null){
                if(Calculations.distanceTo(gates) < 3){
                    if(gates.isOnScreen()){
                        gates.interact("Open");
                        Timer opening = new Timer(4000);
                        while(opening.isRunning() && gates.validate()){
                            Task.sleep(50, 70);
                        }
                    }else{
                        Camera.turnTo(gates);
                    }
                }else{
                    Walking.findPath(gates).traverse();
                }
            }else if(woodenGate != null && Calculations.distanceTo(woodenGate) < 5){

                    if(woodenGate.isOnScreen()){
                        woodenGate.interact("Open");
                        Timer opening = new Timer(4000);
                        while(opening.isRunning() && woodenGate.validate()){
                            Task.sleep(50, 70);
                        }
                    }else{
                        Camera.turnTo(woodenGate);
                    }
            }else if(web != null && Calculations.distanceTo(web) < 8){

                if(web.isOnScreen()){
                    web.interact("Slash");
                    Timer opening = new Timer(2000);
                    while(opening.isRunning() && web.validate()){
                        Task.sleep(50, 70);
                    }
                }else{
                    Camera.turnTo(web);
                }

            }else{
                if(toBerries()){
                    Paths.toBerries.randomize(-1, 1).traverse();
                }else if(toBank()){
                    if (Calculations.distanceTo(new Tile(3090, 3957, 0)) < 3){
                        if(SceneEntities.getNearest(5959) != null){
                            SceneEntities.getNearest(5959).interact("Pull");
                            sleep(2000,3000) ;
                        }
                    }else{
                    Paths.toBank.randomize(-1, 1).traverse();
                    }
                }
                Timer walking = new Timer(1000);
                while(walking.isRunning() && Calculations.distanceTo(Walking.getDestination()) > 6){
                    Task.sleep(50, 70);
                }
            }


        }else{
            if(!Bank.isOpen() && toBerries()){
            if(SceneEntities.getNearest(5960) != null){
                if(Calculations.distanceTo(SceneEntities.getNearest(5960)) < 3){
                    SceneEntities.getNearest(5960).interact("Pull");
                    sleep(3000,4000);
                }else{
                    Walking.findPath(SceneEntities.getNearest(5960)).traverse();

                }
            }
        }
            }


    }
    }
}
