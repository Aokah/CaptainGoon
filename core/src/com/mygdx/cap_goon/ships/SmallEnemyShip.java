package com.mygdx.cap_goon.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cap_goon.ammunition.WShipAmmo;

public class SmallEnemyShip extends EnemyShip{
    private static final int MAX_LIFE = 25;
    private Array<WShipAmmo> wShipAmmos;
    float incr;
    private float rayon;
    private float firstX;

    private void setRayon(float rayon){
        this.rayon = rayon;
    }

    public Array<WShipAmmo> getAmmos() {
        return wShipAmmos;
    }

    public SmallEnemyShip(int pos){
        super(MAX_LIFE);

        setImage(new Texture(Gdx.files.internal("sprites/ships/roundysh_small.png")));
        if (pos == 1){
            getPosition().x = Gdx.graphics.getWidth()/4 + 35 - 100/2;
            setRayon(Gdx.graphics.getWidth()/4);
            incr = 0;
        }
        else if (pos == 2){
            getPosition().x = (float) (Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/3.5) - 100/2;
            setRayon(Gdx.graphics.getWidth()/4);
            incr = 90;
        }
        firstX = getPosition().x;
        getPosition().y = Gdx.graphics.getHeight() - 150 - 100;
        getPosition().width = 100 * 9/10;
        getPosition().height = 100 * 9/10;
        wShipAmmos = new Array<WShipAmmo>();
    }

    public void spawnEnemyAmmos(){
        if(isAlive()) {
            WShipAmmo wShipAmmo = new WShipAmmo();
            wShipAmmo.getPosition().x = getPosition().x + 150 / 2;
            wShipAmmo.getPosition().y = getPosition().y + 150 / 2;
            wShipAmmo.getPosition().width = 40;
            wShipAmmo.getPosition().height = 69;
            wShipAmmos.add(wShipAmmo);
        }
    }

    public void travelling(){
        incr += 0.02 %(2*Math.PI);
        if(isAlive()){
            getPosition().x = (float) (Math.sin(incr) * rayon) + firstX;
            getPosition().y = (float) (Math.cos(incr) * Gdx.graphics.getHeight()/10) + Gdx.graphics.getHeight() -150 -100;
        }
    }
}
