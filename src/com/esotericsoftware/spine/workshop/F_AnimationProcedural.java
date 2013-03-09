
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
import com.badlogic.gdx.math.MathUtils;

public class F_AnimationProcedural extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	Animation walkAnimation;
	Animation bowAnimation;
	float time;
	Bone root;
	Bone leftShoulder;
	Bone rightShoulder;
	Bone torso;
	Bone head;
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

		leftShoulder = skeleton.findBone("left shoulder");
		rightShoulder = skeleton.findBone("right shoulder");
		torso = skeleton.findBone("torso");
		head = skeleton.findBone("head");
	}

	public void render () {
		time += Gdx.graphics.getDeltaTime() / 4;

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		walkAnimation.apply(skeleton, time, true);
		if (time > 1) {
			float jumpTime = time - 1;
			float mixTime = 0.4f;
			float mix = MathUtils.clamp(0.85f * jumpTime / mixTime, 0, 1);
			bowAnimation.mix(skeleton, jumpTime, false, mix);

			float x = leftShoulder.getWorldX() - Gdx.input.getX();
			float y = leftShoulder.getWorldY() - Gdx.input.getY();
			float r = ((float)Math.atan2(x, y) * MathUtils.radDeg + 90) * mix;
			leftShoulder.setRotation(leftShoulder.getRotation() + r);

			rightShoulder.setRotation(rightShoulder.getRotation() + r);

			torso.setRotation(torso.getRotation() + r * 0.3f);

			head.setRotation(head.getRotation() + r * 0.3f);
		}

		skeleton.updateWorldTransform();

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
		config.title = "AnimationProcedural - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new F_AnimationProcedural(), config);
	}
}
