package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.BaseActor;

public class ShopHeart extends BaseActor
{
    public ShopHeart(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("map/apple-icon.png");
    }
}