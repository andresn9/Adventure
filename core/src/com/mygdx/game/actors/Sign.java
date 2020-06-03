package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends Solid
{

    private String text;
    private boolean viewing;
    private boolean solid;


    public Sign(float x, float y, float width, float height, Stage s)
    {
        super(x,y, width, height, s);
        loadTexture("map/sign.png");
        setBoundaryRectangle();
        solid = true;

    }





    public void setText(String t)
    { text = t; }
    public String getText()
    { return text; }
    public void setViewing(boolean v)
    { viewing = v; }
    public boolean isViewing()
    { return viewing; }
    public void setSolid(boolean a){
        solid = a;
    }

    public boolean isSolid(){
        return solid;
    }






}