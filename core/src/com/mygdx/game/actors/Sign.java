package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends Solid
{
    public Sign(float x, float y, Stage s)
    {
        super(x,y,32,32,s);
        loadTexture("map/bush.png");
        setBoundaryPolygon(8);
    }
}