package com.mygdx.cap_goon.ammunition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class WShipAmmo extends Ammunition{
    public WShipAmmo(){
        super(10);
        setPosition(new Rectangle());
        setTexture(new Texture(Gdx.files.internal("sprites/ammo/wship-4.png")));
    }
}
