package net.minecraft2.block;

import net.minecraft2.init.SoundEvents;
import net.minecraft2.util.SoundEvent;

public class SoundType {

	
	public static final SoundType STONE = new SoundType(1.0f,1.0f, SoundEvents.BLOCK_STONE_BREAK, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT,  SoundEvents.BLOCK_STONE_FALL);
	private final float volume;
	private final float pitch;
	private final SoundEvent breakSound;
	private final SoundEvent stepSound;
	private final SoundEvent placeSound;
	private final SoundEvent hitSound;
	private final SoundEvent fallSound;
	
	public SoundType(float vol, float pitch, SoundEvent breakS, SoundEvent stepS,
			SoundEvent placeS, SoundEvent hitS, SoundEvent fallS) {

		this.volume = vol;
		this.pitch = pitch;
		this.breakSound = breakS;
		this.stepSound = stepS;
		this.placeSound = placeS;
		this.hitSound = hitS;
		this.fallSound = fallS;
		
		
		
	}

	
}
