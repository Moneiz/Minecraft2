package net.minecraft2.util;

import net.minecraft2.util.text.ITextComponent;
import net.minecraft2.util.text.TextComponentTranslation;;

public enum EnumHandSide {
	LEFT(new TextComponentTranslation("options.mainHand.left", new Object[0])),
	RIGHT(new TextComponentTranslation("options.mainHand.right", new Object[0]));
	
	private final ITextComponent handName;
	
	private EnumHandSide(ITextComponent name) {
		this.handName = name;
	}
}
