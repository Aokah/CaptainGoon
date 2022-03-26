package com.mygdx.cap_goon.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.cap_goon.ammunition.AlienDroppin;

public class BigEnemyShip extends EnemyShip{
    private static final int MAX_LIFE = 100;
    private Array<AlienDroppin> alienDroppins;
    private int rayon;
    private float incr;
    private float firstX;

    public Array<AlienDroppin> getAmmos(){
        return alienDroppins;
    }

     public BigEnemyShip(){
         super(MAX_LIFE);
         setImage(new Texture(Gdx.files.internal("sprites/ships/spco_small.png")));
         rayon = Gdx.graphics.getWidth()/2 - getImage().getWidth()/2;
         getPosition().x = Gdx.graphics.getWidth()/2 - getImage().getWidth()/2;
         firstX = getPosition().x;
         getPosition().y = Gdx.graphics.getHeight() - 150;
         getPosition().width = getImage().getWidth() * 9/10;
         getPosition().height = getImage().getHeight() * 9/10;

         alienDroppins = new Array<>();
     }

     public long spawnEnemyAmmos(){
        if(isAlive()) {
            AlienDroppin alienDrop = new AlienDroppin();
            alienDrop.getPosition().x = getPosition().x + getImage().getWidth() /2;
            alienDrop.getPosition().y = getPosition().y - 30;
            alienDrop.getPosition().width = alienDrop.getTexture().getWidth() * 8/10;
            alienDrop.getPosition().height = alienDrop.getTexture().getHeight() * 8/10;
            alienDroppins.add(alienDrop);
        }
         return TimeUtils.nanoTime();
     }

     public void travelling(){
        incr = (float) ((incr + 0.015) %(2*Math.PI));
        if(isAlive()) {
            getPosition().x = (float) (Math.sin(incr) * rayon) + firstX;
        }
     }
}
