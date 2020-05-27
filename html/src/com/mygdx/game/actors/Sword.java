package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sword extends BaseActor
{
    public Sword(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("sword.png");
    }
}