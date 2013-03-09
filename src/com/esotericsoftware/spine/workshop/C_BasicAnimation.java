
package com.esotericsoftware.spine.workshop;

import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class C_BasicAnimation extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	Animation animation;
	float time;
	Bone root;

	public void create () {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();

		atlas = new TextureAtlas(Gdx.files.internal("spineboy/spineboy.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spineboy/spineboy-skeleton.json"));
		animation = json.readAnimation(Gdx.files.internal("spineboy/spineboy-walk.json"), skeletonData);

		skeleton = new Skeleton(skeletonData);

		root = skeleton.getRootBone();
		root.setX(100);
		root.setY(20);

		skeleton.updateWorldTransform();
	}

	public void render () {
		time += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		animation.apply(skeleton, time, true);
		root.setX(root.getX() + 100 * Gdx.graphics.getDeltaTime());
		skeleton.updateWorldTransform();
		skeleton.draw(batch);

		batch.end();

		skeleton.drawDebug(renderer);
	}

	public void resize (int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
	}

	public void dispose () {
		atlas.dispose();
	}

	public static void main (String[] args) throws Exception {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "BasicAnimation - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new C_BasicAnimation(), config);
	}
}
