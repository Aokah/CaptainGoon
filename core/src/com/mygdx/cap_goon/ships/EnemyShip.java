package com.mygdx.cap_goon.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.cap_goon.ammunition.Ammunition;

import java.util.Iterator;

public abstract class EnemyShip extends Ship{
    public EnemyShip(int life){
        super(life);
    }


    public void enemyShoot(Array<? extends Ammunition> enemyAmmos, HeroShip heroShip, SpriteBatch batch){
        Iterator<? extends Ammunition> enemy_iter = enemyAmmos.iterator();
        while(enemy_iter.hasNext()){
            Ammunition enemyDrop = enemy_iter.next();
            enemyDrop.getPosition().y -= 200 * Gdx.graphics.getDeltaTime();
            if(enemyDrop.getPosition().y - 69 > Gdx.graphics.getHeight()) enemy_iter.remove();
            if(enemyDrop.getPosition().overlaps(heroShip.getPosition())){
                if(heroShip.isHittable()){
                    if(enemyDrop.getDamage()!=0) {
                        heroShip.getHitWith(enemyDrop);
                        enemyDrop.setDamage(0);
                    }
                    enemyDrop.die();
                }
                if(!heroShip.isAlive()){
                    heroShip.setHittable(false);
                }
            }
            if(enemyDrop.isAnimStart()){
                enemyDrop.explode(batch);
                if(enemyDrop.getAnimation().isAnimationFinished(enemyDrop.getAnimDuration())) enemy_iter.remove();
            }
        }
    }

    public void shipHandle(SpriteBatch batch){
        if(isAlive()) spriteDraw(batch);
        else{
            batch.setColor(Color.WHITE);
            dieHandle(batch);
        }
    }

    @Override
    public void travelling(){

    }

    public abstract Array<? extends Ammunition> getAmmos();
}
