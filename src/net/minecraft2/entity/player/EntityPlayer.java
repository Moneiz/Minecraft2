package net.minecraft2.entity.player;

import net.minecraft2.entity.EntityLivingBase;

public abstract class EntityPlayer extends EntityLivingBase {

	public static enum EnumChatVisibility {
		FULL(0, "options.chat.visibility.full"),
		SYSTEM(1, "options.chat.visibility.system"),
		HIDDEN(2, "options.chat.visibility.hidden");
		
		private static final EntityPlayer.EnumChatVisibility[] ID_LOOKUP = new EntityPlayer.EnumChatVisibility[values().length];
		private final int chatVisibility;
		private final String resourceKey;
		
		private EnumChatVisibility(int id, String resourceKey) {
			this.chatVisibility = id;
			this.resourceKey = resourceKey;
		}
	}

	
	
}
