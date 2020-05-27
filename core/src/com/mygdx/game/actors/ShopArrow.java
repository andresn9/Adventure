package com.mygdx.game.actors;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.BaseActor;

public class ShopArrow extends BaseActor
{
    public ShopArrow(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("map/sword-icon.png");
    }
}