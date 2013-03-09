
package com.esotericsoftware.spine.workshop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class A_EmptyGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;

	public void create () {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();

		// Add loading here.
	}

	public void render () {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Add SpriteBatch drawing here.

		batch.end();
	}

	public void resize (int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
	}

	public static void main (String[] args) throws Exception {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "EmptyGame - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new A_EmptyGame(), config);
	}
}
