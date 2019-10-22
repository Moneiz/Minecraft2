package net.minecraft2.block.material;

public class MapColor {

	private static final MapColor[] COLORS = new MapColor[64];
	public static final MapColor AIR = new MapColor(0,0);

	
	public final int colorIndex;
	public int colorValue;
	
	public MapColor(int index, int color) {
		if(index >= 0 && index <= 63) {
			this.colorIndex = index;
			this.colorValue = color;
			COLORS[index] = this;
		}else {
			throw new IndexOutOfBoundsException("L'ID de la carte couleur doit être compris entre 0 et 63 inclus");
		}
	}
}
