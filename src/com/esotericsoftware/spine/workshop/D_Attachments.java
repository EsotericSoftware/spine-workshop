
package com.esotericsoftware.spine.workshop;

import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class D_Attachments extends ApplicationAdapter {
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

		atlas = new TextureAtlas(Gdx.files.internal("goblins/goblins.atlas"));
		SkeletonJson json = new SkeletonJson(atlas);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("goblins/goblins-skeleton.json"));
		animation = json.readAnimation(Gdx.files.internal("goblins/goblins-walk.json"), skeletonData);

		skeleton = new Skeleton(skeletonData);

		root = skeleton.getRootBone();
		root.setX(220);
		root.setY(20);

		skeleton.setSkin("goblin");
		skeleton.setSlotsToBindPose();
		skeleton.updateWorldTransform();

		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean keyDown (int keycode) {
				switch (keycode) {
				case Keys.NUM_1:
					skeleton.setAttachment("left hand item", "spear");
					break;
				case Keys.NUM_2:
					skeleton.setAttachment("left hand item", "dagger");
					break;
				case Keys.NUM_3:
					skeleton.setAttachment("right hand item", "dagger");
					break;
				case Keys.NUM_4:
					skeleton.findSlot("left hand item").setAttachment(null);
					skeleton.findSlot("right hand item").setAttachment(null);
					break;
				case Keys.NUM_5:
					skeleton.setSkin("goblingirl");
					skeleton.setSlotsToBindPose();
					break;
				case Keys.NUM_6:
					skeleton.setSkin("goblin");
					skeleton.setSlotsToBindPose();
					break;
				}
				return true;
			}
		});
	}

	public void render () {
		time += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		animation.apply(skeleton, time, true);
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
		config.title = "Attachments - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new D_Attachments(), config);
	}
}
