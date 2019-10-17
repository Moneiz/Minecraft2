package net.minecraft2.init;

import net.minecraft2.util.ResourceLocation;
import net.minecraft2.util.SoundEvent;

public class SoundEvents {
	public static final SoundEvent BLOCK_STONE_BREAK;
	public static final SoundEvent BLOCK_STONE_STEP;
	public static final SoundEvent BLOCK_STONE_HIT;
	public static final SoundEvent BLOCK_STONE_PLACE;
	public static final SoundEvent BLOCK_STONE_FALL;
	
	private static SoundEvent getRegisteredSoundEvent(String id) {
		SoundEvent soundevent = SoundEvent.REGISTRY.getObject(new ResourceLocation(id));
		if(soundevent == null) {
			throw new IllegalStateException("Son requis invalide: "+ id);
		}else {
			return soundevent;
		}
	}
	
	static {
		if(!Bootstrap.isRegistered()) {
			throw new RuntimeException("Accès aux sons avant leur mise en registre Bootstrap!");
		}else {
			BLOCK_STONE_STEP = getRegisteredSoundEvent("block.stone.step");
			BLOCK_STONE_BREAK = getRegisteredSoundEvent("block.stone.break");
			BLOCK_STONE_HIT = getRegisteredSoundEvent("block.stone.hit");
			BLOCK_STONE_PLACE = getRegisteredSoundEvent("block.stone.place");
			BLOCK_STONE_FALL = getRegisteredSoundEvent("block.stone.fall");
		}
	}
}
