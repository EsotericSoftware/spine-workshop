
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
import com.badlogic.gdx.utils.ObjectMap;

public class E_AnimationTransition2 extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer renderer;

	TextureAtlas atlas;
	Skeleton skeleton;
	Animation walkAnimation;
	Animation jumpAnimation;
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
		jumpAnimation = json.readAnimation(Gdx.files.internal("spineboy/spineboy-jump.json"), skeletonData);

		skeleton = new Skeleton(skeletonData);

		root = skeleton.getRootBone();
		root.setX(250);
		root.setY(20);

		skeleton.updateWorldTransform();
	}

	static class AnimationState {
		Animation current;
		float currentTime;
		Animation previous;
		float previousTime;
		float mixTime;
		ObjectMap<Key, Float> animationToMixTime = new ObjectMap();

		public void setAnimation (Animation animation) {
			setAnimation(animation, 0);
		}

		public void setAnimation (Animation animation, float time) {
			if (current != null) {

			}
			current = animation;
			currentTime = time;
		}

		static class Key {
			Animation a1, a2;

			public int hashCode () {
				return 31 * (31 + a1.hashCode()) + a2.hashCode();
			}

			public boolean equals (Object obj) {
				if (this == obj) return true;
				if (obj == null) return false;
				if (getClass() != obj.getClass()) return false;
				Key other = (Key)obj;
				if (a1 == null) {
					if (other.a1 != null) return false;
				} else if (!a1.equals(other.a1)) return false;
				if (a2 == null) {
					if (other.a2 != null) return false;
				} else if (!a2.equals(other.a2)) return false;
				return true;
			}
		}
	}

	public void render () {
		time += Gdx.graphics.getDeltaTime() / 6;

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();

		walkAnimation.apply(skeleton, time, true);
		if (time > 1) {
			float jumpTime = time - 1;
			float mixTime = 0.2f;
			if (jumpTime > mixTime)
				jumpAnimation.apply(skeleton, jumpTime, false);
			else
				jumpAnimation.mix(skeleton, jumpTime, false, jumpTime / mixTime);
			if (time > 4) time = 0;
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
		config.title = "AnimationTransition - Spine";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new E_AnimationTransition2(), config);
	}
}
