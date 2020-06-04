package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.BaseGame;
import com.mygdx.game.HeroData;
import com.mygdx.game.ItemLoader;
import com.mygdx.game.actors.Arrow;
import com.mygdx.game.actors.BaseActor;
import com.mygdx.game.actors.Bush;
import com.mygdx.game.actors.Coin;
import com.mygdx.game.actors.DialogBox;
import com.mygdx.game.actors.Door;
import com.mygdx.game.actors.Flyer;
import com.mygdx.game.actors.Hero;
import com.mygdx.game.actors.NPC;
import com.mygdx.game.actors.Passage;
import com.mygdx.game.actors.ShopArrow;
import com.mygdx.game.actors.ShopHeart;
import com.mygdx.game.actors.Smoke;
import com.mygdx.game.actors.Sword;
import com.mygdx.game.actors.TilemapActor;
import com.mygdx.game.actors.Treasure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

public class ThirdScreen extends BaseScreen {
    Hero hero;
    Sword sword;
    Treasure treasure;
    ShopHeart shopHeart;
    ShopArrow shopArrow;
    Bush specialBush;
    NPC brother;
    boolean keyFound;
    boolean talked;
    long startTime;
    long totalTime;
    boolean challengeOn;


    ItemLoader itemLoader;

    Door door;
    boolean stopWatchOn;
    boolean gameOver;
    Label healthLabel;
    Label coinLabel;
    Label arrowLabel;
    Label messageLabel;
    DialogBox dialogBox;
    Label stopWatchLabel;


    public void initialize() {
        // com.mygdx.game.actors.TilemapActor tma = new TilemapActor("map/map2.tmx", mainStage);
        TilemapActor tma = new TilemapActor("b/SampleMap/thirdmap.tmx", mainStage);
        itemLoader = new ItemLoader(tma, mainStage);
        //  com.mygdx.game.actors.TilemapActor tma = new TilemapActor("map/maptest.tmx", mainStage);
        //  com.mygdx.game.actors.TilemapActor tma = new TilemapActor("b/SampleMap/samplemap.tmx", mainStage);






        talked = false;
        keyFound = false;
        gameOver = false;
        stopWatch(false);



        stopWatchLabel = new Label("",BaseGame.labelStyle);
        stopWatchLabel.setColor(Color.BLACK);
        stopWatchLabel.setWrap(true);



        healthLabel = new Label(" x ", BaseGame.labelStyle);
        healthLabel.setColor(Color.PINK);
        coinLabel = new Label(" x " , BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        arrowLabel = new Label(" x " , BaseGame.labelStyle);
        arrowLabel.setColor(Color.TAN);
        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setVisible(false);
        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);


        BaseActor healthIcon = new BaseActor(0, 0, uiStage);
        healthIcon.loadTexture("heart-icon.png");
        BaseActor coinIcon = new BaseActor(0, 0, uiStage);
        coinIcon.loadTexture("coin-icon.png");
        BaseActor arrowIcon = new BaseActor(0, 0, uiStage);
        arrowIcon.loadTexture("arrow-icon.png");


        uiTable.pad(20);
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(arrowIcon);
        uiTable.add(arrowLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(8).expandX().expandY();
        uiTable.row();
        uiTable.add(stopWatchLabel);
        uiTable.row();
        uiTable.add(dialogBox).colspan(8);




        sword = new Sword(0, 0, mainStage);
        sword.setVisible(false);

        itemLoader.loadItems();

        for (BaseActor npcActor : BaseActor.getList(mainStage, "NPC")) {
            NPC npc = (NPC) npcActor;
            if (npc.getID().equals("Brother")) {
                brother = npc;
            }
        }



        try {
            MapObject shopHeartTile = tma.getTileList("ShopHeart").get(0);
            MapProperties shopHeartProps = shopHeartTile.getProperties();
            shopHeart = new ShopHeart((float) shopHeartProps.get("x"), (float) shopHeartProps.get("y"),
                    mainStage);
            MapObject shopArrowTile = tma.getTileList("ShopArrow").get(0);
            MapProperties shopArrowProps = shopArrowTile.getProperties();
            shopArrow = new ShopArrow((float) shopArrowProps.get("x"), (float) shopArrowProps.get("y"),
                    mainStage);

            MapObject treasureTile = tma.getTileList("Treasure").get(0);
            MapProperties treasureProps = treasureTile.getProperties();
            treasure = new Treasure((float) treasureProps.get("x"), (float) treasureProps.get("y"),
                    mainStage);

            MapObject bushTile = tma.getTileList("SpecialBush").get(0);
            MapProperties bushProps = bushTile.getProperties();
            specialBush = new Bush((float) bushProps.get("x"), (float) bushProps.get("y"),
                    mainStage);

            System.out.println(specialBush);


        } catch (Exception e) {
            System.out.println(specialBush);
        }


        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);
        hero.setLastScreen("ThirdScreen");






    }


    public void update(float dt) {
        healthLabel.setText(" x " + hero.getHealth());
        coinLabel.setText(" x " + hero.getCoins());
        arrowLabel.setText(" x " + hero.getArrows());



        stopWatch(stopWatchOn);


        try {
            checkChallenge();
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*
        checkChallenge();

        minuteLabel.setText(""+minutes);
        secondLabel.setText("" + seconds);
        milisecondsLabel.setText("" + miliseconds);
*/



        if (gameOver)
            return;




        if (!sword.isVisible() && !hero.isFrozen()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                hero.accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                hero.accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                hero.accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                hero.accelerateAtAngle(270);
            for (BaseActor solid : BaseActor.getList(mainStage, "Solid")) {
                hero.preventOverlap(solid);

            }
        }

        for (BaseActor solid : BaseActor.getList(mainStage, "Solid")) {
            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer")) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid);
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                }
            }
        }


        for(BaseActor a : BaseActor.getList(mainStage, "Passage")){
            if(hero.overlaps(a)){
                hero.save();
                Passage passage = (Passage) a;
                passage.travel();
            }
        }

        if (sword.isVisible()) {
            for (BaseActor bush : BaseActor.getList(mainStage, "Bush")) {
                if (sword.overlaps(bush)) {
                    if (bush == specialBush) {
                        messageLabel.setText("You found the key in this bush!");
                        messageLabel.setColor(Color.YELLOW);
                        messageLabel.setFontScale(1);
                        messageLabel.setVisible(true);
                        messageLabel.addAction(fadeOut(2.0f));
                        specialBush.remove();
                        keyFound = true;

                    } else {


                        bush.remove();

                    }
                }


            }


            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer")) {
                if (sword.overlaps(flyer)) {
                    flyer.remove();
                    Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }


        }

        for (BaseActor coin : BaseActor.getList(mainStage, "Coin")) {
            if (hero.overlaps(coin)) {
                coin.remove();
                hero.addCoin();
            }
        }
        /*if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            treasure.remove();
            gameOver = true;
        }*/

        if (hero.getHealth() <= 0) {
            if (hero.getHealth() <= 0) {
                BaseGame.setActiveScreen(new GameOverScreen());
            }
        }
        for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer")) {
            if (hero.overlaps(flyer)) {
                hero.preventOverlap(flyer);
                flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                Vector2 heroPosition = new Vector2(hero.getX(), hero.getY());
                Vector2 flyerPosition = new Vector2(flyer.getX(), flyer.getY());
                Vector2 hitVector = heroPosition.sub(flyerPosition);
                hero.accelerateAtAngle(hitVector.angle());
                hero.setAnimationPaused(true);
                hero.setFrozen(true);
                hero.setMaxSpeed(400);
                hero.setDeceleration(0);
                hero.setHealth(hero.getHealth() - 1);
                hero.setVulnerable(false);
                float delay = 0.1f; // seconds
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

                        hero.setDeceleration(40000000);
                        hero.setFrozen(false);
                        hero.setVulnerable(true);
                        hero.setMaxSpeed(300);

                    }

                }, delay);


            }
        }

        //ARROWS
        for (BaseActor arrow : BaseActor.getList(mainStage, "Arrow")) {
            for (BaseActor flyer : BaseActor.getList(mainStage, "Flyer")) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove();
                    arrow.remove();
                    Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }
            for (BaseActor solid : BaseActor.getList(mainStage, "Solid")) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid);
                    arrow.setSpeed(0);
                    arrow.addAction(fadeOut(0.5f));
                    arrow.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }

        for (BaseActor npcActor : BaseActor.getList(mainStage, "NPC")) {
            NPC npc = (NPC) npcActor;

            hero.preventOverlap(npc);
            boolean nearby = hero.isWithinDistance(4, npc);
            if (nearby && !npc.isViewing()) {
// check NPC ID for dynamic text
                if (npc.getID().equals("Gatekeeper")) {
                    int flyerCount = BaseActor.count(mainStage, "Flyer");
                    String message = "Destroy the Flyers and you can have the treasure. ";
                    if (flyerCount > 1)
                        message += "There are " + flyerCount + " left.";
                    else if (flyerCount == 1)
                        message += "There is " + flyerCount + " left.";
                    else // flyerCount == 0
                    {
                        message += "It is yours!";
                        npc.addAction(fadeOut(5.0f));
                        npc.addAction(Actions.after(Actions.moveBy(-10000, -10000)));
                    }
                    dialogBox.setText(message);
                } else if (npc.getID().equals("Shopkeeper") && keyFound) {
                    if (!talked) {
                        String message = "Thank you so much! I don't have any money though... (You receive 30 arrows!)";
                        hero.setArrows(hero.getArrows() + 30);
                        dialogBox.setText(message);
                        talked = true;

                        hero.setFrozen(true);
                        float delay = 2f; // seconds
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                dialogBox.setText("If you use that ladder over there, you'll find my brother, he might have a job for you");
                                hero.setFrozen(false);
                            }
                        }, delay);
                    } else {
                        dialogBox.setText("If you use that ladder over there, you'll find my brother, he might have a job for you");
                    }
                } else {
                    dialogBox.setText(npc.getText());
                }
                dialogBox.setVisible(true);
                npc.setViewing(true);
            }
            if (npc.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                npc.setViewing(false);
            }
        }




    }

    public void swingSword() {
// visibility determines if sword is currently swinging
        if (sword.isVisible())
            return;
        hero.setSpeed(0);
        float facingAngle = hero.getFacingAngle();
        Vector2 offset = new Vector2();
        if (facingAngle == 0)
            offset.set(0.50f, 0.20f);
        else if (facingAngle == 90)
            offset.set(0.65f, 0.50f);
        else if (facingAngle == 180)
            offset.set(0.40f, 0.20f);
        else // facingAngle == 270
            offset.set(0.25f, 0.20f);
        sword.setPosition(hero.getX(), hero.getY());
        sword.moveBy(offset.x * hero.getWidth(), offset.y * hero.getHeight());
        float swordArc = 100;
        sword.setRotation(facingAngle - swordArc / 2);
        sword.setOriginX(0);
        sword.setVisible(true);
        sword.addAction(Actions.rotateBy(swordArc, 0.25f));
        sword.addAction(Actions.after(Actions.visible(false)));
// hero should appear in front of sword when facing north or west
        if (facingAngle == 90 || facingAngle == 180)
            hero.toFront();
        else
            sword.toFront();
    }


    public void stopWatch(boolean bool){
        if(bool){

            totalTime = (startTime - System.currentTimeMillis());

            long miliseconds = - (totalTime/10%100);
            int minutes = -(int) (totalTime /1000) / 60;
            int seconds = -(int) (totalTime /1000) % 60;

            if(seconds<=9){
                stopWatchLabel.setText(minutes+ " :0" + seconds + " : " + miliseconds);
            } else {

                stopWatchLabel.setText(minutes+ " : " + seconds + " : " + miliseconds);
            }
            }


        }


    public void shootArrow() {
        if (hero.getArrows() <= 0)
            return;
        hero.setArrows(hero.getArrows() - 1);
        Arrow arrow = new Arrow(0, 0, mainStage);
        arrow.centerAtActor(hero);
        arrow.setRotation(hero.getFacingAngle());
        arrow.setMotionAngle(hero.getFacingAngle());
    }

    public void challenge() {
        for (BaseActor rock : BaseActor.getList(mainStage, "Rock")) {
            Flyer flyer = new Flyer(0, 0, mainStage);
            flyer.centerAtActor(rock);
            startTime = System.currentTimeMillis();
            stopWatchOn = true;
            challengeOn = true;
        }
        System.out.println("Challenge");

    }

    public void checkChallenge() throws SQLException {
        if(BaseActor.count(mainStage, "Flyer") ==0 && challengeOn){
            stopWatchOn = false;
            this.challengeOn = false;

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/adventure?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
            Statement smt = con.createStatement();
            long now = System.currentTimeMillis();
            int rs = smt.executeUpdate("INSERT into score (id, time) values("+ now + "," + -totalTime +")");
            con.close();

            win();




        }
    }


    public void win(){
        messageLabel.setText("CONGRATULATIONS! YOU WON!");
        messageLabel.setColor(Color.YELLOW);
        messageLabel.setFontScale(1.5f);
        messageLabel.setVisible(true);
        messageLabel.addAction(Actions.fadeOut(2f));

        float delay = 3f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                BaseGame.setActiveScreen(new LeaderBoard());

            }

        }, delay);

        /*
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(4f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                BaseGame.setActiveScreen(new LeaderBoard());
            }
        }));
        mainStage.getRoot().addAction(sequenceAction);*/



    }


    public boolean keyDown(int keycode) {
        if (gameOver) {
            return false;
        }

        if (keycode == Input.Keys.Y) {
            if (hero.isWithinDistance(5, brother)) {
                challenge();
            }
        }

        if (keycode == Input.Keys.A) {
            shootArrow();

        }
        if (keycode == Input.Keys.S) {
            swingSword();


        }

        if (keycode == Input.Keys.B) {
            if (hero.overlaps(shopHeart) && hero.getCoins() >= 3) {
                hero.setCoins(hero.getCoins() - 3);
                hero.setHealth(hero.getHealth() + 1);
            }
            if (hero.overlaps(shopArrow) && hero.getCoins() >= 4) {

                hero.setCoins(hero.getCoins() - 4);
                hero.setArrows(hero.getArrows() + 3);
            }


        }
/*
        for (BaseActor npcActor : BaseActor.getList(mainStage, "NPC")) {
            NPC npc = (NPC) npcActor;
            if (hero.overlaps(npc) && npc.getID().equals("Brother")) {
                if (keycode == Input.Keys.Y) {
                    if ()
                        challenge();
                }
            }
        }*/



        return false;


    }





}