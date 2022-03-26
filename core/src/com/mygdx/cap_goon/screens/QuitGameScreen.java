package com.mygdx.cap_goon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.cap_goon.CaptainGoon;

public class QuitGameScreen implements Screen {
    private final CaptainGoon game;
    private OrthographicCamera camera;
    private Screen previousScreen;
    Stage stage;
    TextButton yesButton;
    TextButton noButton;

    public QuitGameScreen(MainMenuScreen gameScreen, final CaptainGoon game){
        this.game = game;
        previousScreen = gameScreen;
        camera = new OrthographicCamera();
        stage = new Stage();
        yesButton = new TextButton("Yes", game.buttonSkin);
        noButton = new TextButton("No", game.buttonSkin);
    }

    @Override
    public void show() {
        stage.draw();

        stage.addActor(yesButton);
        yesButton.setBounds(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight()/2 -105, 65, 50);
        yesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exit();
            }
        });

        stage.addActor(noButton);
        noButton.setBounds(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/2 + 30, Gdx.graphics.getHeight()/2 - 105, 65, 50);
        noButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBack();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Are you sure to quit the game ?", Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/2 - 250, Gdx.graphics.getHeight()/2);
        yesButton.draw(game.batch, 1);
        noButton.draw(game.batch, 1);
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.N) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) goBack();

        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) exit();
    }

    public void exit(){
        dispose();
        Gdx.app.exit();
    }

    public void goBack(){
        game.setScreen(previousScreen);
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
        stage.dispose();
    }
}
