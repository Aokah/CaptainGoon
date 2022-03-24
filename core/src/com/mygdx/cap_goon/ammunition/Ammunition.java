package com.mygdx.cap_goon.ammunition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Ammunition{
    private int damage;
    private Rectangle position;
    private Texture texture;
    private static TextureAtlas explosion = new TextureAtlas(Gdx.files.internal("sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas"));
    private Animation<TextureRegion> animation;
    private float animDuration;
    private float saveX;
    private float saveY;
    private boolean saved = false;
    private boolean animStart = false;

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

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getAnimDuration(){
        return animDuration;
    }

    public void setAnimDuration(float duration) {
        this.animDuration = duration;
    }

    public Animation<TextureRegion> getAnimation(){
        return animation;
    }

    public boolean isAnimStart() {
        return animStart;
    }

    public void setAnimStart(boolean animStart) {
        this.animStart = animStart;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Ammunition(int damage){
        setDamage(damage);
        animation = new Animation<TextureRegion>(1f/60f, explosion.getRegions());
        setAnimDuration(0);
    }

    public void die() {
        if (!isSaved()) {
            setSaveX(getPosition().x - getPosition().width / 2);
            setSaveY(getPosition().y - getPosition().height / 2);
            setSaved(true);
        }
        setAnimStart(true);
        setTexture(null);
    }
    public void explode(SpriteBatch batch){
        batch.begin();
        batch.draw(animation.getKeyFrame(animDuration), getSaveX(), getSaveY(), 100, 100);
        batch.end();
        setAnimDuration(getAnimDuration()+0.01f);
    }

}
