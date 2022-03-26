package com.mygdx.cap_goon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.cap_goon.CaptainGoon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		config.forceExit = false;
		config.title = "Captain Goon";
		new LwjglApplication(new CaptainGoon(), config);
	}
}
