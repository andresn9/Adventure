package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.BaseGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeaderBoard extends BaseScreen {


    Button playButton;
    Image playButtonImg;
    Texture background;

    @Override
    public void initialize() {


        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        skin.add("background", new Texture("menuBackground.gif"));
        TextButton name = new TextButton("Date", skin);
        TextButton score = new TextButton("Score", skin);
        TextButton back = new TextButton("Back", skin);


        back.setTransform(true);
        back.setScale(0.5f);


        int buttonSize = 380;


        uiTable.setBackground(skin.getDrawable("background"));
        uiTable.top();
        uiTable.add(back).reset();
        uiTable.row();
        uiTable.pad(20);
        uiTable.add(name);
        uiTable.add(score);
        uiTable.row();

        try {
            getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BaseGame.setActiveScreen(new MenuScreen());
            }
        });


    }

    public void getData() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/adventure?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery("SELECT * from score");

        loadData(rs);

        con.close();


    }

    public void loadData(ResultSet rs) {
        DateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatterTime = new SimpleDateFormat("mm:ss");
        Calendar calendar = Calendar.getInstance();


        try {

            while (rs.next()) {
                calendar.setTimeInMillis(rs.getLong("id"));
                String date = formatterDate.format(calendar.getTime());

                Label dateLabel = new Label(date, BaseGame.labelStyle);
                dateLabel.setColor(Color.GOLD);
                uiTable.add(dateLabel);


                calendar.setTimeInMillis(rs.getLong("time"));
                String time = formatterTime.format(calendar.getTime());

                Label timeLabel = new Label(time, BaseGame.labelStyle);
                timeLabel.setColor(Color.GOLD);
                uiTable.add(timeLabel);

                uiTable.row();


            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public void update(float dt) {

    }
}
