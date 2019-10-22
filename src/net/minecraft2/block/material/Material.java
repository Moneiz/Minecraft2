package net.minecraft2.block.material;

public class Material {

	public static final Material AIR = new MaterialTransparent(MapColor.AIR);
	
	private final MapColor materialMapColor;
	private boolean replaceable;
	
	public Material(MapColor color) {
		this.materialMapColor = color;
	}
	
	public Material setReplaceable() {
		this.replaceable = true;
		return this;
	}

	public MapColor getMaterialMapColor() {
		return this.materialMapColor;
	}

	public boolean blocksLight() {
		return true;
	}
}
