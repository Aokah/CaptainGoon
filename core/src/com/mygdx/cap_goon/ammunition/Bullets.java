package com.mygdx.cap_goon.ammunition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullets extends Ammunition{

    public Bullets(){
        super(10);
        setPosition(new Rectangle());
        setTexture(new Texture(Gdx.files.internal("sprites/ammo/bullets/glowtube_small.png")));
    }
}
