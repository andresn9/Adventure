package com.mygdx.game.actors;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.HeroData;
import com.mygdx.game.actors.BaseActor;

import java.io.StringWriter;

public class Hero extends BaseActor {
    Animation north;
    Animation south;
    Animation east;
    Animation west;
    private float facingAngle;
    private boolean frozen;
    private boolean vulnerable;
    public HeroData data;




    public Hero(float x, float y, Stage s) {
        super(x, y, s);
        String fileName = "hero.png";
        frozen = false;
        data = new HeroData(1,1,1,"a");


        vulnerable = true;
        int rows = 4;
        int cols = 4;





        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = (texture.getHeight() / rows);
        float frameDuration = 0.2f;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int c = 0; c < cols; c++)
            textureArray.add(temp[0][c]);
        south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add(temp[1][c]);
        west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add(temp[2][c]);
        east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add(temp[3][c]);
        north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(south);
        facingAngle = 270;

        setBoundaryPolygonHero(8);
        setAcceleration(40000000);
        setMaxSpeed(300);
        setDeceleration(40000000);
    }






    public void act(float dt)
    {
        super.act(dt);
// pause animation when character not moving
        if ( getSpeed() == 0 )
            setAnimationPaused(true);
        else
        {
            setAnimationPaused(false);
// set direction animation
            float angle = getMotionAngle();
            if (angle >= 45 && angle <= 135)
            {
                facingAngle = 90;
                setAnimation(north);
            }
            else if (angle > 135 && angle < 225)
            {
                facingAngle = 180;
                setAnimation(west);
            }
            else if (angle >= 225 && angle <= 315)
            {
                facingAngle = 270;
                setAnimation(south);
            }
            else
            {
                facingAngle = 0;
                setAnimation(east);
            }
        }
        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }

    public void setData(HeroData data){
        this.data = data;
    }

    public void save(){
        this.data.save();
    }

    public void setLastScreen(String lastScreen){
        data.lastScreen = lastScreen;
    }

    public String getLastScreen(){
        return data.getLastScreen();
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public int getHealth() {
        return data.getHealth();
    }

    public boolean isFrozen(){
        return frozen;
    }

    public void setHealth(int health) {
        if(isVulnerable()){
        data.setHealth(health);
        }

    }

    public int getCoins() {
        return data.getCoins();
    }

    public void setCoins(int coins) {
        data.setCoins(coins);

    }

    public int getArrows() {
        return data.getArrows();
    }

    public void setArrows(int arrows) {
        data.setArrows(arrows);
    }

    public float getFacingAngle()
    {
        return facingAngle;
    }

    public void setFrozen(boolean x){
        frozen = x;
    }
    public void addCoin(){
        data.setCoins(data.getCoins()+1);
    }



}