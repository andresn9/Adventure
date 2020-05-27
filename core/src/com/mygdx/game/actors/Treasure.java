package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.BaseActor;

public class Treasure extends BaseActor
{
    public Treasure(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("treasure-chest.png");
    }
}
