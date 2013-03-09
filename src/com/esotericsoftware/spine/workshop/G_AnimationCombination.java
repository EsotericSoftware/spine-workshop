
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

public class G_AnimationCombination extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	Animation walkAnimation;
	Animation bowAnimation;
	float time;
	Bone root;
	String state = "walk";

	public void create () {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();

		atlas = new TextureAtlas(Gdx.files.internal("spineboy/spineboy.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spineboy/spineboy-skeleton.json"));
		walkAnimation = json.readAnimation(Gdx.files.internal("spineboy/spineboy-walk.json"), skeletonData);
		bowAnimation = json.readAnimation(Gdx.files.internal("spineboy/spineboy-bow.json"), skeletonData);

		skeleton = new Skeleton(skeletonData);

		root = skeleton.getRootBone();
		root.setX(250);
		root.setY(20);

		skeleton.updateWorldTransform();
	}

	public void render () {
		time += Gdx.graphics.getDeltaTime() / 2;

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		walkAnimation.apply(skeleton, time, true);
		if (time > 1) {
			float jumpTime = time - 1;
			float mixTime = 0.4f;
			if (jumpTime > mixTime)
				bowAnimation.mix(skeleton, jumpTime, false, 0.95f);
			else
				bowAnimation.mix(skeleton, jumpTime, false, 0.95f * jumpTime / mixTime);
		}
		skeleton.updateWorldTransform();
		skeleton.draw(batch);

		batch.end();
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
		config.title = "AnimationCombination - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new G_AnimationCombination(), config);
	}
}
