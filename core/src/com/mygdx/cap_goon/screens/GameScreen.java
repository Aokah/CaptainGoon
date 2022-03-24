package com.mygdx.cap_goon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.cap_goon.CaptainGoon;
import com.mygdx.cap_goon.ammunition.AlienDroppin;
import com.mygdx.cap_goon.ammunition.Bullets;
import com.mygdx.cap_goon.ammunition.WShipAmmo;
import com.mygdx.cap_goon.ships.*;


public class GameScreen implements Screen{
    final CaptainGoon game;
    OrthographicCamera camera;
    Texture background;
    Stage stage;
    TextButton mute;

    HeroShip heroShip;
    Rectangle heroRectangle;

    BigEnemyShip bigEnemyShip;
    Rectangle bigEnemyRectangle;

    SmallEnemyShip small1;
    Rectangle small1Rectangle;

    SmallEnemyShip small2;
    Rectangle small2Rectangle;

    Array<EnemyShip> enemyShips;

    long lastShootTime;
    Array<Bullets> bullets;
    Array<AlienDroppin> alienDroppins;
    Array<WShipAmmo> wShipAmmos1;
    Array<WShipAmmo> wShipAmmos2;

    Music music;
    Sound winSound;
    Sound gameOverSound;

    boolean isMusicMuted = false;
    boolean isWinSoundPlayed = false;
    boolean isGameOverSoundPlayed = false;

    public GameScreen(final CaptainGoon game){
        this.game = game;
        instanciation();
        music.play();
    }

    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        batchDraw();
        play();
    }

    private void instanciation(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        background = new Texture(Gdx.files.internal("backgrounds/Planets.jpg"));

        heroShip = new HeroShip();
        heroRectangle = heroShip.getPosition();

        bigEnemyShip = new BigEnemyShip();
        bigEnemyRectangle = bigEnemyShip.getPosition();

        small1 = new SmallEnemyShip(1);
        small1Rectangle = small1.getPosition();

        small2 = new SmallEnemyShip(2);
        small2Rectangle = small2.getPosition();

        enemyShips = new Array<>();
        enemyShips.add(small1, small2, bigEnemyShip);

        bullets = heroShip.getBullets();

        alienDroppins = bigEnemyShip.getAmmos();
        wShipAmmos1 = small1.getAmmos();
        wShipAmmos2 = small2.getAmmos();

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/supercopter-jdg-pendant-plus-de-10-minutes.mp3"));
        music.setVolume(0.1f);
        mute = new TextButton("Mute",game.buttonSkin);

        winSound = Gdx.audio.newSound(Gdx.files.internal("audio/mixkit-winning-chimes-2015.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("audio/mixkit-game-over-dark-orchestra-633.wav"));
        stage = new Stage();
    }

    private void batchDraw(){
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        shipsDraw();
        ammoDraw();
        hudDraw();
        game.batch.end();
    }

    private void play(){
        if(heroShip.isAlive() || (small1.isAlive() && small2.isAlive() && bigEnemyShip.isAlive())) {
            if((small1.isAlive() || small2.isAlive() || bigEnemyShip.isAlive())) {
                heroShip.mouseDeplacement(camera);

                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.S)) heroShip.spawnHeroAmmo();

                if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && heroShip.isAlive()){
                    music.pause();
                    game.setScreen(new LeaveScreen(this, game, background, music));
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.M)) mute();


                if (TimeUtils.nanoTime() - lastShootTime > 1000000000) {
                    lastShootTime = bigEnemyShip.spawnEnemyAmmos();
                    small1.spawnEnemyAmmos();
                    small2.spawnEnemyAmmos();
                }

                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) System.out.println(Gdx.input.getX());

                for(EnemyShip ship : enemyShips){
                    ship.travelling();
                    ship.enemyShoot(ship.getAmmos(), heroShip, game.batch);
                }

                heroShip.heroShoot(bullets, enemyShips, game.batch);
            } else {
                gameWin();
            }
        }
    }

    private void mute(){
        if(isMusicMuted){
            music.play();
            isMusicMuted = !isMusicMuted;
        } else if(!isMusicMuted) {
            music.pause();
            isMusicMuted = !isMusicMuted;
        }
    }

    private void gameWin(){
        music.stop();
        if(!isWinSoundPlayed){
            winSound.setVolume(winSound.play(), 0.15f);
            isWinSoundPlayed = !isWinSoundPlayed;
        }
        game.batch.begin();
        game.font.draw(game.batch, "YOU WON THE GAME !",  (Gdx.graphics.getWidth()/2)-100, Gdx.graphics.getHeight()/2);
        game.batch.end();

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    private void gameOver(){
        music.stop();
        if(!isGameOverSoundPlayed){
            gameOverSound.setVolume(gameOverSound.play(), 0.15f);
            isGameOverSoundPlayed = !isGameOverSoundPlayed;
        }

        game.font.draw(game.batch, "GAME OVER", (Gdx.graphics.getWidth()/2)-50, Gdx.graphics.getHeight()/2);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
            music.stop();
            dispose();
        }
    }

    private void hudDraw(){
        game.batch.draw(heroShip.getImage(), 0, 0, 100 * 7/10, 136 * 7/10);
        game.font.getData().setScale(2f,2f);
        game.font.draw(game.batch, heroShip.getLife() + " %", 90, 75);
        mute.draw(game.batch, 1);
        game.batch.draw(bigEnemyShip.getImage(), (float)(Gdx.graphics.getWidth()-100), 0, 150 * 7/10, 150 * 7/10);
        game.font.draw(game.batch, bigEnemyShip.getLife() + " %", Gdx.graphics.getWidth()-200, 75);
        game.font.getData().setScale(1,1);
    }

    private void ammoDraw(){
        for(Bullets bullet: bullets){
            if(bullet.getTexture() != null) game.batch.draw(bullet.getTexture(), bullet.getPosition().x, bullet.getPosition().y);
        }
        for(AlienDroppin alienDrop : alienDroppins){
            if(alienDrop.getTexture() != null) game.batch.draw(alienDrop.getTexture(), alienDrop.getPosition().x, alienDrop.getPosition().y);
        }
        for(WShipAmmo wShipAmmo : wShipAmmos1){
            if(wShipAmmo.getTexture() != null) game.batch.draw(wShipAmmo.getTexture(), wShipAmmo.getPosition().x, wShipAmmo.getPosition().y);
        }
        for(WShipAmmo wShipAmmo: wShipAmmos2){
            if(wShipAmmo.getTexture() != null) game.batch.draw(wShipAmmo.getTexture(), wShipAmmo.getPosition().x, wShipAmmo.getPosition().y);
        }
    }

    private void shipsDraw(){
        if(heroShip.isAlive()){
            game.batch.setColor(heroShip.getColor());
            heroShip.spriteDraw(game.batch);
            game.batch.setColor(Color.WHITE);
        }
        else {
            heroShip.dieHandle(game.batch);
            gameOver();
        }

        for(EnemyShip ship: enemyShips){
            game.batch.setColor(ship.getColor());
            ship.shipHandle(game.batch);
            game.batch.setColor(Color.WHITE);
        }
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }

    public void show(){
        stage.draw();

        stage.addActor(mute);
        mute.setBounds(Gdx.graphics.getWidth()/2-150/2, 0, 150, 20);
        mute.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mute();
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        music.dispose();
        winSound.dispose();
        gameOverSound.dispose();
        stage.dispose();
    }
}
