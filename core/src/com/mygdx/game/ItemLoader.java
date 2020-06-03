package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Bush;
import com.mygdx.game.actors.Coin;
import com.mygdx.game.actors.Door;
import com.mygdx.game.actors.Flyer;
import com.mygdx.game.actors.NPC;
import com.mygdx.game.actors.Rock;
import com.mygdx.game.actors.Sign;
import com.mygdx.game.actors.Solid;
import com.mygdx.game.actors.TilemapActor;
import com.mygdx.game.actors.Treasure;

public class ItemLoader {

    public TilemapActor tma;
    public Stage mainStage;

    public ItemLoader(TilemapActor tma, Stage mainStage) {
        this.tma = tma;
        this.mainStage = mainStage;

    }


    public void loadSolid() {
        for (
                MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"),
                    mainStage);
        }
    }

    public void loadBush() {
        for (MapObject obj : tma.getTileList("Bush")) {
            MapProperties props = obj.getProperties();
            new Bush((float) props.get("x"), (float) props.get("y"), mainStage);
        }
    }


    public void loadRock() {
        for (MapObject obj : tma.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }
    }

    public void loadCoin() {
        for (MapObject obj : tma.getTileList("Coin")) {
            MapProperties props = obj.getProperties();
            new Coin((float) props.get("x"), (float) props.get("y"), mainStage);
        }
    }


    public void loadNpc() {
        for (MapObject obj : tma.getTileList("NPC")) {
            MapProperties props = obj.getProperties();
            NPC s = new NPC((float) props.get("x"), (float) props.get("y"), mainStage);
            s.setID((String) props.get("id"));
            s.setText((String) props.get("text"));


        }
    }

    public void loadFlyer() {
        for (MapObject obj : tma.getTileList("Flyer")) {
            MapProperties props = obj.getProperties();
            new Flyer((float) props.get("x"), (float) props.get("y"), mainStage);
        }
    }

    public void loadSign() {


        for (MapObject obj : tma.getTileList("Sign")) {
            MapProperties props = obj.getProperties();
            Sign s = new Sign((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"), mainStage);
            s.setText((String) props.get("text"));
            s.setSolid((boolean) props.get("solid"));
            if (!(boolean) props.get("visible")) {
                s.setVisible(false);
                System.out.println((float) props.get("width") + (float) props.get("height"));

            }

        }

    }
    /*
    public void loadTreasure(){
        MapObject treasureTile = tma.getTileList("Treasure").get(0);
        MapProperties treasureProps = treasureTile.getProperties();
        treasure = new Treasure((float) treasureProps.get("x"), (float) treasureProps.get("y"),
                mainStage);
    }
    */
    /*

    public void loadHeartShop(){
        MapObject doorTile = tma.getTileList("Door").get(0);
        MapProperties doorProps = doorTile.getProperties();
        door = new Door((float) doorProps.get("x"), (float) doorProps.get("y"), mainStage);
        door.setOpen(false);
    }*/

    public void loadDoor(){

    }

    public void loadArrowShop(){

    }


    public void loadItems(){
        loadBush();
        loadSolid();
        loadCoin();
        loadFlyer();
        loadNpc();
        loadRock();
        loadSign();
    }

}
