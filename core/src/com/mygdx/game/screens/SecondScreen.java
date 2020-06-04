package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.BaseGame;
import com.mygdx.game.HeroData;
import com.mygdx.game.ItemLoader;
import com.mygdx.game.actors.Arrow;
import com.mygdx.game.actors.BaseActor;
import com.mygdx.game.actors.Bush;
import com.mygdx.game.actors.Coin;
import com.mygdx.game.actors.DialogBox;
import com.mygdx.game.actors.Hero;
import com.mygdx.game.actors.NPC;
import com.mygdx.game.actors.Passage;
import com.mygdx.game.actors.ShopArrow;
import com.mygdx.game.actors.ShopHeart;
import com.mygdx.game.actors.Smoke;
import com.mygdx.game.actors.Sword;
import com.mygdx.game.actors.TilemapActor;
import com.mygdx.game.actors.Treasure;

public class SecondScreen extends BaseScreen {
    Hero hero;
    Sword sword;
    Treasure treasure;
    ShopHeart shopHeart;
    ShopArrow shopArrow;
    Bush specialBush;
    boolean keyFound;
    boolean talked;

    ItemLoader itemLoader;

    Label healthLabel;
    Label coinLabel;
    Label arrowLabel;
    Label messageLabel;
    com.mygdx.game.actors.DialogBox dialogBox;


    public void initialize() {
        com.mygdx.game.actors.TilemapActor tma = new TilemapActor("SampleMap/secondScreen.tmx", mainStage);
        itemLoader = new ItemLoader(tma, mainStage);
        HeroData heroData = new HeroData(1, 1, 1, "s");

        talked = false;
        keyFound = false;
        healthLabel = new Label(" x ", BaseGame.labelStyle);
        healthLabel.setColor(Color.PINK);
        coinLabel = new Label(" x ", BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        arrowLabel = new Label(" x ", BaseGame.labelStyle);
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


        BaseActor healthIcon = new com.mygdx.game.actors.BaseActor(0, 0, uiStage);
        healthIcon.loadTexture("heart-icon.png");
        BaseActor coinIcon = new com.mygdx.game.actors.BaseActor(0, 0, uiStage);
        coinIcon.loadTexture("coin-icon.png");
        BaseActor arrowIcon = new com.mygdx.game.actors.BaseActor(0, 0, uiStage);
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
        uiTable.add(dialogBox).colspan(8);


        sword = new Sword(0, 0, mainStage);
        sword.setVisible(false);

        itemLoader.loadItems();


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


        } catch (Exception e) {

        }


        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();

        MapObject thirdPoint = tma.getRectangleList("fromTS").get(0);
        MapProperties thirdProps = thirdPoint.getProperties();


        if (heroData.getLastScreen().equals("ThirdScreen")) {
            hero = new Hero((float) thirdProps.get("x"), (float) thirdProps.get("y"), mainStage);

        } else {
            hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);
        }


    }


    public void update(float dt) {

        //update GUI labels
        healthLabel.setText(" x " + hero.getHealth());
        coinLabel.setText(" x " + hero.getCoins());
        arrowLabel.setText(" x " + hero.getArrows());


        //hero movement

        if (!sword.isVisible() && !hero.isFrozen()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                hero.accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                hero.accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                hero.accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                hero.accelerateAtAngle(270);
            for (com.mygdx.game.actors.BaseActor solid : com.mygdx.game.actors.BaseActor.getList(mainStage, "Solid")) {
                hero.preventOverlap(solid);
                for (com.mygdx.game.actors.BaseActor flyer : com.mygdx.game.actors.BaseActor.getList(mainStage, "Flyer")) {
                    if (flyer.overlaps(solid)) {
                        flyer.preventOverlap(solid);
                        flyer.setMotionAngle(flyer.getMotionAngle() + 180);
                    }
                }
            }
        }


        //sends you to thirdScreen

        for (BaseActor a : BaseActor.getList(mainStage, "Passage")) {
            if (hero.overlaps(a)) {
                hero.setLastScreen("SecondScreen");
                hero.save();
                Passage passage = (Passage) a;
                passage.travel();

            }
        }


        //checks if you found the key
        if (sword.isVisible()) {
            for (com.mygdx.game.actors.BaseActor bush : com.mygdx.game.actors.BaseActor.getList(mainStage, "Bush")) {
                if (sword.overlaps(bush)) {
                    if (bush == specialBush) {
                        messageLabel.setText("You found the key in this bush!");
                        messageLabel.setColor(Color.YELLOW);
                        messageLabel.setFontScale(1);
                        messageLabel.setVisible(true);
                        messageLabel.addAction(Actions.fadeOut(2.0f));
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


        //removes coins when overlapped

        for (com.mygdx.game.actors.BaseActor coin : com.mygdx.game.actors.BaseActor.getList(mainStage, "Coin")) {
            if (hero.overlaps(coin)) {
                coin.remove();
                hero.addCoin();
            }
        }

        //gameover

        if (hero.getHealth() <= 0) {
            messageLabel.setText("Game over...");
            messageLabel.setColor(Color.RED);
            messageLabel.setFontScale(2);
            messageLabel.setVisible(true);
            hero.remove();

        }
        //enemy behaviour
        for (com.mygdx.game.actors.BaseActor flyer : com.mygdx.game.actors.BaseActor.getList(mainStage, "Flyer")) {
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
        for (com.mygdx.game.actors.BaseActor arrow : com.mygdx.game.actors.BaseActor.getList(mainStage, "Arrow")) {
            for (com.mygdx.game.actors.BaseActor flyer : com.mygdx.game.actors.BaseActor.getList(mainStage, "Flyer")) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove();
                    arrow.remove();
                    com.mygdx.game.actors.Coin coin = new Coin(0, 0, mainStage);
                    coin.centerAtActor(flyer);
                    com.mygdx.game.actors.Smoke smoke = new Smoke(0, 0, mainStage);
                    smoke.centerAtActor(flyer);
                }
            }
            for (com.mygdx.game.actors.BaseActor solid : com.mygdx.game.actors.BaseActor.getList(mainStage, "Solid")) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid);
                    arrow.setSpeed(0);
                    arrow.addAction(Actions.fadeOut(0.5f));
                    arrow.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }


        //NPC behaviour
        for (com.mygdx.game.actors.BaseActor npcActor : com.mygdx.game.actors.BaseActor.getList(mainStage, "NPC")) {
            com.mygdx.game.actors.NPC npc = (NPC) npcActor;

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
                        npc.addAction(Actions.fadeOut(5.0f));
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


    public void shootArrow() {
        if (hero.getArrows() <= 0)
            return;
        hero.setArrows(hero.getArrows() - 1);
        com.mygdx.game.actors.Arrow arrow = new Arrow(0, 0, mainStage);
        arrow.centerAtActor(hero);
        arrow.setRotation(hero.getFacingAngle());
        arrow.setMotionAngle(hero.getFacingAngle());
    }


    public boolean keyDown(int keycode) {


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


        return false;


    }


}