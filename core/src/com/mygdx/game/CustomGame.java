package com.mygdx.game;

import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.LeaderBoard;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SecondScreen;
import com.mygdx.game.screens.ThirdScreen;

import java.awt.Menu;

public class CustomGame extends BaseGame
{
    public String lastScreen;

    public void create() 
    {        
        super.create();
        setActiveScreen( new MenuScreen());
    }






}