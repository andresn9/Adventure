package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.BaseGame;
import com.mygdx.game.HeroData;

public class GameOverScreen extends BaseScreen {


    Button playButton;
    Image playButtonImg;
    Texture background;

    @Override
    public void initialize() {


        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.add("background", new Texture("gameOver.jpg"));
        TextButton retry = new TextButton("Retry?", skin);
        TextButton menu = new TextButton("Menu", skin);
        TextButton exit = new TextButton("Exit", skin);


        int buttonSize = 300;

        uiTable.setBackground(skin.getDrawable("background"));
        uiTable.bottom();
        uiTable.padBottom(30);
        uiTable.add(menu).width(buttonSize);
        uiTable.add(retry).width(buttonSize);



        HeroData.reset();


        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                BaseGame.setActiveScreen(new FirstScreen());

        }});


        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BaseGame.setActiveScreen(new MenuScreen());
            }
        });









    }

    @Override
    public void update(float dt) {

    }
}
