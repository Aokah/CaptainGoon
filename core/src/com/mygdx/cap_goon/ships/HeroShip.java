package com.mygdx.cap_goon.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cap_goon.ammunition.Bullets;
import java.util.Iterator;


public class HeroShip extends Ship{
    private static int MAX_LIFE = 100;
    private Array<Bullets> bullets;

    public Array<Bullets> getBullets(){
        return this.bullets;
    }

    public HeroShip(){
        super(MAX_LIFE);

        setImage(new Texture(Gdx.files.internal("sprites/ships/blueships1_small.png")));

        getPosition().x = Gdx.graphics.getWidth()/2 - 115/2;
        getPosition().y = 20;
        getPosition().width = 100 * 9/10;
        getPosition().height = 136 * 9/10;
        bullets = new Array<Bullets>();

    }

    public void spawnHeroAmmo(){
        if(isAlive()) {
            Bullets bullet = new Bullets();
            bullet.getPosition().x = getPosition().x + 100 / 2;
            bullet.getPosition().y = getPosition().y + 130;
            bullet.getPosition().width = 10;
            bullet.getPosition().height = 25;
            bullets.add(bullet);
        } else return ;
    }

    public void mouseDeplacement(OrthographicCamera camera){
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        getPosition().x = (int) (touchPos.x - 100/2);
        getPosition().y = (int) (touchPos.y - 100/2);
        positionTest();
    }

    public void positionTest(){
        if(getPosition().x < 0) getPosition().x = 0;
        if(getPosition().x > Gdx.graphics.getWidth() - 100) getPosition().x = Gdx.graphics.getWidth()-100;
        if(getPosition().y > 400 - 100) getPosition().y = 400 - 100;
        if(getPosition().y < 0) getPosition().y = 0;
    }

    public void explode(SpriteBatch batch){
        batch.draw(getAnimation().getKeyFrame(getAnimDuration()), getSaveX(), getSaveY());
        setAnimDuration(getAnimDuration()+0.01f);
    }

    public void heroShoot(Array<Bullets> bullets, Array<EnemyShip> enemyShips, SpriteBatch batch){
        Iterator<Bullets> bullets_iter = bullets.iterator();
        while(bullets_iter.hasNext()){
            Bullets bullet = bullets_iter.next();
            bullet.getPosition().y += 350 * Gdx.graphics.getDeltaTime();
            if(bullet.getPosition().y + 25 < 0) bullets_iter.remove();
            for(Ship enemyShip: enemyShips){
                if(bullet.getPosition().overlaps(enemyShip.getPosition())) {
                    if (enemyShip.isHittable()) {
                        if (bullet.getDamage() != 0) {
                            enemyShip.getHitWith(bullet);
                            bullet.setDamage(0);
                        }
                        bullet.die();
                    }
                    if (!enemyShip.isAlive()) {
                        enemyShip.setHittable(false);
                    }
                }
            }
            if(bullet.isAnimStart()){
                bullet.explode(batch);
                if(bullet.getAnimation().isAnimationFinished(bullet.getAnimDuration())) bullets_iter.remove();
            }
        }
    }

    @Override
    public void travelling(){}
}
