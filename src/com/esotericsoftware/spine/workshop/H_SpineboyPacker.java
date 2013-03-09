
package com.esotericsoftware.spine.workshop;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class H_SpineboyPacker {
	public static void main (String[] args) throws Exception {
		TexturePacker2.process("assets-raw/spineboy", "assets/spineboy", "spineboy");
	}
}
