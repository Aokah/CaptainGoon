package com.mygdx.cap_goon.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.cap_goon.ammunition.Ammunition;

public abstract class Ship {
    private int life;
    private Rectangle position;
    private Texture image;
    private boolean isHittable;
    private TextureAtlas explosion;
    private Animation<TextureRegion> animation;
    private float animDuration;
    private float saveX;
    private float saveY;
    private boolean saved = false;
    private Color color;
    private boolean isColored = false;

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public float getSaveY() {
        return saveY;
    }

    public float getSaveX() {
        return saveX;
    }

    public void setSaveY(float saveY) {
        this.saveY = saveY;
    }

    public void setSaveX(float saveX) {
        this.saveX = saveX;
    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return this.life;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public Rectangle getPosition(){
        return position;
    }

    public void setPosition(Rectangle position){
        this.position = position;
    }

    public void setHittable(boolean bool){
        this.isHittable = bool;
    }

    public boolean isHittable() {
        return isHittable;
    }

    public float getAnimDuration(){
        return animDuration;
    }

    public void setAnimDuration(float duration){
        this.animDuration = duration;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Color getColor() {
        return color;
    }

    public Ship(int life){
        setLife(life);
        setHittable(true);
        setPosition(new Rectangle());
        explosion = new TextureAtlas(Gdx.files.internal("sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas"));
        animation = new Animation<TextureRegion>(1f/60f, explosion.getRegions());
        setAnimDuration(0);
        color = new Color(255,255,255,1);
    }

    public void getHitWith(Ammunition ammo) {
        int value = (getLife() - ammo.getDamage() <= 0) ? 0 : (getLife() - ammo.getDamage());
        setLife(value);
        if(!isColored){
            color.sub(0, 1,1, 0);
        }
    }

    public boolean isAlive(){
        return (getLife() == 0) ? false : true;
    }

    public void explode(SpriteBatch batch){
        batch.draw(animation.getKeyFrame(animDuration), getSaveX(), getSaveY());
        setAnimDuration(getAnimDuration()+0.01f);
    }

    public void die() {
        if (!isSaved()) {
            setSaveX(getPosition().x - getPosition().width / 2);
            setSaveY(getPosition().y - getPosition().height / 2);
            setSaved(true);
        }
        getPosition().x = 0;
        getPosition().y = 0;
    }

    public void spriteDraw(SpriteBatch batch){
        batch.draw(getImage(), getPosition().x, getPosition().y, getPosition().width, getPosition().height);
    }

    public void dieHandle(SpriteBatch batch){
        die();
        explode(batch);
    }

    public abstract void travelling();
}
