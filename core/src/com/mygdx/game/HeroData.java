package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class HeroData {

    public int health;
    public int arrows;
    public int coins;
    public String lastScreen;


    public HeroData(int health, int arrows, int coins, String lastScreen){

        JsonReader reader = new JsonReader();
        JsonValue file = reader.parse(Gdx.files.internal("Hero.json"));

        this.health = file.getInt("health");
        this.coins = file.getInt("coins");
        this.arrows = file.getInt("arrows");
        this.lastScreen = file.getString("lastScreen");

        System.out.println(health);
    }

    public void save(){
        Json json = new Json();
        FileHandle file1 = Gdx.files.local("Hero.json");
        file1.writeString(json.prettyPrint(this),false);

    }

    public void read(){
        JsonReader reader = new JsonReader();
        JsonValue file = reader.parse(Gdx.files.internal("Hero.json"));

        this.health = file.getInt("health");
        this.coins = file.getInt("coins");
        this.arrows = file.getInt("arrows");
        this.lastScreen = file.getString("lastScreen");

        System.out.println(health);
    }

    public static void reset(){
        Json json = new Json();
        FileHandle file1 = Gdx.files.local("Hero.json");
        FileHandle file2 = Gdx.files.local("Hero2.json");
        file1.writeString(json.prettyPrint(file2.readString()),false);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArrows() {
        return this.arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getLastScreen() {
        return this.lastScreen;
    }

    public void setLastScreen(String lastScreen) {
        this.lastScreen = lastScreen;
    }
}
