package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.actors.BaseActor;

public class Hero extends BaseActor {
    Animation north;
    Animation south;
    Animation east;
    Animation west;
    private float facingAngle;
    private boolean frozen;
    private boolean vulnerable;
    private int health;
    private int coins;
    private int arrows;



    public Hero(float x, float y, Stage s) {
        super(x, y, s);
        String fileName = "hero.png";
        frozen = false;
        health = 30;
        coins = 300;
        arrows = 30;
        vulnerable = true;
        int rows = 4;
        int cols = 4;

        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
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

        setBoundaryPolygon(8);
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

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public int getHealth() {
        return health;
    }

    public boolean isFrozen(){
        return frozen;
    }

    public void setHealth(int health) {
        if(isVulnerable()){
        this.health = health;}

    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getArrows() {
        return arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public float getFacingAngle()
    {
        return facingAngle;
    }

    public void setFrozen(boolean x){
        frozen = x;
    }
    public void addCoin(){
        coins++;
    }


}