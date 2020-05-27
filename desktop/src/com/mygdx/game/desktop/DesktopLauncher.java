package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Adventure;
import com.mygdx.game.CustomGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new CustomGame();
		LwjglApplication launcher = new LwjglApplication( myGame, "Game Title", 800, 600 );
	}
}
