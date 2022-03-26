package com.mygdx.cap_goon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.cap_goon.screens.MainMenuScreen;

public class CaptainGoon extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Skin buttonSkin;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("skin/terra-mother/raw/199x-export.fnt"));
		buttonSkin = new Skin(Gdx.files.internal("skin/kenney-pixel/skin/skin.json"));
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		buttonSkin.dispose();
	}
}
