
package com.esotericsoftware.spine.workshop;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class J_TexturePacker {
	public static void main (String[] args) throws Exception {
		TexturePacker2.process("assets-raw/spineboy/images", "assets/spineboy", "spineboy");
		TexturePacker2.process("assets-raw/goblins/images", "assets/goblins", "goblins");
	}
}
