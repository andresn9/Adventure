package com.mygdx.game;

import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SecondScreen;
import com.mygdx.game.screens.ThirdScreen;

public class CustomGame extends BaseGame
{
    public String lastScreen;

    public void create() 
    {        
        super.create();
        setActiveScreen( new SecondScreen());
    }






}