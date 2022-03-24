package com.mygdx.cap_goon.ammunition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class AlienDroppin extends Ammunition{
    public AlienDroppin(){
        super(25);
        setPosition(new Rectangle());
        setTexture(new Texture(Gdx.files.internal("sprites/ammo/aliendropping_small.png")));
    }
}
