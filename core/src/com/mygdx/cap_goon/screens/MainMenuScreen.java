package com.mygdx.cap_goon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.cap_goon.CaptainGoon;

public class MainMenuScreen implements Screen {
    final CaptainGoon game;
    OrthographicCamera camera;
    Music waitingMusic;
    Texture background;
    Stage stage;
    TextButton level1Button;
    TextButton creditsScreen;

    public MainMenuScreen(final CaptainGoon game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        waitingMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/compo-main-menu.mp3"));
        background = new Texture(Gdx.files.internal("backgrounds/menu-background-3.jpg"));
        stage = new Stage();
        level1Button = new TextButton("1 - Level 1", game.buttonSkin);
        creditsScreen = new TextButton("C - Credits", game.buttonSkin);
    }

    @Override
    public void show() {
        stage.draw();

        stage.addActor(level1Button);
        level1Button.setBounds(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/5, 200, 175, 35);
        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goLevel1Screen();
            }
        });

        stage.addActor(creditsScreen);
        creditsScreen.setBounds(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/5, 150, 175, 35);
        creditsScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goCreditsScreen();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta){
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0,0,0.2f,1);
        waitingMusic.play();
        waitingMusic.setVolume(2f);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(background, 0, 0);

        game.font.getData().setScale(2,2);
        game.font.draw(game.batch, "Welcome to Captain Goon !", 100, 200);
        game.font.draw(game.batch, "Click on level 1 or press space to begin !", 100, 150);

        game.font.setColor(Color.WHITE);
        level1Button.draw(game.batch, 1);
        creditsScreen.draw(game.batch, 1);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) goLevel1Screen();
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) goCreditsScreen();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.setScreen(new QuitGameScreen(this, game));
    }

    private boolean goLevel1Screen(){
        game.setScreen(new GameScreen(game));
        waitingMusic.stop();
        dispose();
        return true;
    }

    private void goCreditsScreen(){
        game.setScreen(new CreditScreen(game));
        waitingMusic.stop();
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        waitingMusic.dispose();
    }
}
