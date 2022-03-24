package com.mygdx.cap_goon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

public class CreditScreen implements Screen {
    final CaptainGoon game;
    OrthographicCamera camera;
    Texture background;
    TextButton returnButton;
    Skin buttonSkin;
    Stage stage;

    public CreditScreen(final CaptainGoon game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture(Gdx.files.internal("backgrounds/293495.jpg"));
        buttonSkin = new Skin(Gdx.files.internal("skin/commodore64/skin/uiskin.json"));
        returnButton = new TextButton("Go back", buttonSkin);
        stage = new Stage();
    }

    @Override
    public void show() {
        stage.draw();

        stage.addActor(returnButton);
        returnButton.setPosition(50,50);
        returnButton.addListener(new ChangeListener() {
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
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        returnButton.draw(game.batch, 1);

        game.font.getData().setScale(2f);
        game.font.draw(game.batch, "Music Credit", Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/10)+20);
        game.font.draw(game.batch, "Main menu - Composed by me", (Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4)-130, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/7));
        game.font.draw(game.batch, "Supercopter - JDG Youtube", (Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4)-130, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/5));
        game.font.draw(game.batch, "Win sound   - found on MixKit", (Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4)-130, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4)-10);
        game.font.draw(game.batch, "Lose sound   - found on MixKit", (Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/4)-130, (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3)+15);

        game.font.draw(game.batch, "Images Credits", Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()*(0.84f)), (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/10)+20);
        System.out.println();
        game.batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) goBack();
    }

    private void goBack(){
        game.setScreen(new MainMenuScreen(game));
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

    }
}
