package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Solid;

public class Rock extends Solid
{
    public Rock(float x, float y, Stage s)
    {
        super(x,y,32,32,s);
        loadTexture("map/rock.png");
        setBoundaryPolygon(8);
    }
}