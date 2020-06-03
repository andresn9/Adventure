package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseGame;
import com.mygdx.game.CustomGame;
import com.mygdx.game.screens.SecondScreen;
import com.mygdx.game.screens.ThirdScreen;



public class Passage extends BaseActor {

    private String place;

    public Passage(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
    }


    public void travel(){

        switch (place){
            case "ThirdScreen":
                (new CustomGame()).setActiveScreen(new ThirdScreen());
                break;

            case "SecondScreen":
                (new CustomGame()).setActiveScreen(new SecondScreen());
                break;


        }



    }


    public void setPlace(String place){
        this.place = place;
    }



}
