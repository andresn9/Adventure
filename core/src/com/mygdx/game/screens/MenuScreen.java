package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.BaseGame;
import com.mygdx.game.actors.BaseActor;

public class MenuScreen extends BaseScreen {


    Button playButton;
    Image playButtonImg;
    Texture background;

    @Override
    public void initialize() {


        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.add("background", new Texture("menuBackground.gif"));
        TextButton start = new TextButton("Start", skin);
        TextButton exit = new TextButton("Exit", skin);


        uiTable.setBackground(skin.getDrawable("background"));
        uiTable.add(start);
        uiTable.row().pad(20,0,20,0);
        uiTable.add(exit);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BaseGame.setActiveScreen(new FirstScreen());
            }
        });






    }

    @Override
    public void update(float dt) {

    }
}
