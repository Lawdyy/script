package whiteberries;


import org.powerbot.core.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.client.Client;
import whiteberries.gui.OnStop;
import whiteberries.gui.StartGUI;
import whiteberries.nodes.Banking;
import whiteberries.nodes.Looting;
import whiteberries.walking.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Manifest(authors = {"Lawdyy"}, name = "LWhiteBerries", description = "Loots white berries in the wildy for money", version = 1.2, vip = true, topic = 1000680)

public class WhiteBerries extends ActiveScript implements MessageListener, PaintListener, MouseListener {

    public static int counting = 0;
    private int taken = 0, price;
    public static boolean dead = false;
    public long runTime, startTime;
    public static boolean guiWait = true;
    StartGUI g = new StartGUI();
    OnStop s = new OnStop();
    private Client client = Bot.client();
    private int mouseY = 0;
    private int mouseX = 0;
    public static String status = "";
    private int deaths = 0;

    public static int getPrice(final int id) {
        final String add = "http://scriptwith.us/api/?return=text&item=" + id;
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(
                new URL(add).openConnection().getInputStream()))) {
            final String line = in.readLine();
            return Integer.parseInt(line.split("[:]")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;

    public synchronized final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if(!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public void onStart(){
        provide(new Looting(), new Banking(), new Walk());
        startTime = System.currentTimeMillis();
        price = getPrice(239);
        g.setVisible(true);
        Mouse.setSpeed(Mouse.Speed.NORMAL);

    }

    public void onStop(){
        s.setVisible(true);
    }

    public static boolean rest = false;

    @Override
    public int loop() {
        while(guiWait){
            sleep(50);
        }

        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }
        if (client != Bot.client()) {
            WidgetCache.purge();
            Bot.context().getEventManager().addListener(this);
            client = Bot.client();
        }
        if(!rest){
        if(!dead){
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        }else{
            OnDeath.deathWalk();
        }
        }

        RunEnergy.Rest();

        if(!status.equals("Taking berries")){
            cameraMove();
        }

        if(Inventory.getCount(239) > counting){
            counting ++;
        }

        taken = counting + Banking.banked;

        return 150;
    }
    public static boolean pulled = true;


    @Override
    public void messageReceived(MessageEvent messageEvent) {
        String s = messageEvent.getMessage();
        if(s.contains("Oh dear,")){
            System.out.println("You have died");
            dead = true;
            deaths++;
        }

        if(s.contains("You pull the lever")){
            pulled = true;
        }
    }



    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }

    private final Image bgshow = getImage("http://i41.tinypic.com/nq778h.png");


    boolean hide = false;
    Point p;
    Rectangle close = new Rectangle(481, 394, 15, 114);

    @Override
    public void mouseClicked(MouseEvent e) {
        p = e.getPoint();
        if (close.contains(p) && !hide) {
            hide = true;
        } else if (close.contains(p) && hide) {
            hide = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private final Color color1 = new Color(255, 255, 255);

    private final Font font1 = new Font("Arial", 0, 16);

    private final Image img1 = getImage("http://i40.tinypic.com/54g3yv.png");

    DecimalFormat df = new DecimalFormat("#,###");

    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;

        if(!guiWait){
            if(!hide){
        g.drawImage(img1, 2, 392, null);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("" + time(runTime), 94, 445);
        g.drawString("" + taken, 114, 469);
        g.drawString("" + pHr(taken), 175, 498);
        g.drawString("" + df.format((taken * price)), 321, 444);
        g.drawString("" + df.format(pHr(taken * price)), 380, 472);
        g.drawString("" + deaths, 328, 421);
        g.drawString(status, 339, 495);
            }else{
                g.drawImage(bgshow, 483, 391, null);
            }

        mouseX = Mouse.getX();
        mouseY = Mouse.getY();
        g.setColor(Color.RED);
        g.drawLine(mouseX, mouseY - 9, mouseX, mouseY + 9);
        g.drawLine(mouseX - 9, mouseY, mouseX + 9, mouseY);
        g.setColor(Color.GREEN);
        Walking.getDestination().getLocation().draw(g);
        }
        runTime = System.currentTimeMillis() - startTime;
    }


    private int pHr(int number) {
        int ret = (int) ((3600000D / runTime) * number);
        return ret;
    }

    private String time(long amount) {
        String formated = "";
        long days = TimeUnit.MILLISECONDS.toDays(amount);
        long hours = TimeUnit.MILLISECONDS.toHours(amount)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(amount));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(amount)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(amount));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(amount)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(amount));
        if (days == 0) {
            formated = (hours + ":" + minutes + ":" + seconds);
        } else {
            formated = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return formated;
    }

    private void cameraMove(){
        if(Random.nextInt(1,25) == 2){
            Camera.setAngle(Random.nextInt(1, 360));
            Camera.setPitch(Random.nextInt(1, 90));
        }

        if(Calculations.distanceTo(Paths.berrieTile) < 7 && Inventory.getItem(239) == null){
            Camera.setAngle(Random.nextInt(229,235));
            Camera.setPitch(87);
        }

    }


}
