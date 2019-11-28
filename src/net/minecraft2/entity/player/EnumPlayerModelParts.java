package net.minecraft2.entity.player;

import net.minecraft2.util.text.ITextComponent;
import net.minecraft2.util.text.TextComponentTranslation;

public enum EnumPlayerModelParts {
	CAPE(0,"cape"),
	JACKET(1,"jacket"),
	LEFT_SLEEVE(2,"left_sleeve"),
	RIGHT_SLEEVE(3,"right_sleeve"),
	LEFT_PANTS_LEG(4,"left_pants_leg"),
	RIGHT_PANTS_LEG(5,"right_pants_leg"),
	HAT(6,"hat");
	
	private final int partId;
	private final int partMask;
	private final String partName;
	private final ITextComponent name;
	
	private EnumPlayerModelParts(int partId, String partName) {
		this.partId = partId;
		this.partMask = 1 << partId;
		this.partName = partName;
		this.name = new TextComponentTranslation("options.modelPart."+partName, new Object[0]);
	}
}
