package com.mygdx.game;

import com.mygdx.game.screens.LevelScreen;
import com.mygdx.game.screens.MenuScreen;

public class CustomGame extends BaseGame
{
    public void create() 
    {        
        super.create();
        setActiveScreen( new MenuScreen());
    }
}