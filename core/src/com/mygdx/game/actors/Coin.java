package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.BaseActor;

public class Coin extends BaseActor {
    public Coin(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("coin.png");
    }
}