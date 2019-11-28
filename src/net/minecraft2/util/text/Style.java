package net.minecraft2.util.text;

public class Style {

	private Style parentStyle;
	
	public Style setParentStyle(Style parent) {
		this.parentStyle = parent;
		return this;
	}

}
