package whiteberries.nodes;


import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import whiteberries.WhiteBerries;
import whiteberries.walking.Paths;

public class Banking extends Node {

    public static int banked = 0;

    @Override
    public boolean activate() {
        return Inventory.isFull() && Calculations.distanceTo(Paths.bankTile) < 7;
    }

    @Override
    public void execute() {
        WhiteBerries.status = "Banking";
        NPC banker = NPCs.getNearest("Gundai");



        if(banker != null){

            if(Bank.isOpen()){
                WhiteBerries.counting = 0;
                banked += Inventory.getCount(239);
                if(Inventory.getItem(239) != null){
                    Bank.depositInventory();
                    sleep(900,1200);
                    if(Inventory.getItem(239) == null){
                        Bank.close();
                    }
                }

            }else{

            if(Calculations.distanceTo(banker) < 3){
                if(banker.isOnScreen()){
                    banker.interact("Bank");
                    sleep(2900,3400);
                }else{
                    Camera.turnTo(banker.getLocation());
                }
            }else{
                Walking.findPath(banker).traverse();
                Timer walk = new Timer(2000);
                while(walk.isRunning() && Players.getLocal().isMoving()){
                    Task.sleep(50, 70);
                }
            }
        }

        }

    }
}

