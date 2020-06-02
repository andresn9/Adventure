package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Door extends BaseActor  {

    private boolean open;

    public Door(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("map/door.png");
        setBoundaryRectangle();
    }



    public void setOpen (boolean a){
        open = a;
    }

    public boolean isOpen(){
        return open;
    }
}
