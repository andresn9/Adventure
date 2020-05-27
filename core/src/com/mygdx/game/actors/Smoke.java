package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.actors.BaseActor;

public class Smoke extends BaseActor
{
    public Smoke(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("smoke.png");
        addAction( Actions.fadeOut(0.5f) );
        addAction( Actions.after( Actions.removeActor() ) );

    }
}