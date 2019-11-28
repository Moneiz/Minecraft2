package net.minecraft2.util.text;

public class TextComponentTranslation extends TextComponentBase {

	private final String key;
	private final Object[] formatArgs;
	private final Object syncLock = new Object();
	
	public TextComponentTranslation(String translateKey, Object...args) {
		this.key = translateKey;
		this.formatArgs = args;
		for(Object obj : args) {
			if(obj instanceof ITextComponent) {
				((ITextComponent)obj).getStyle().setParentStyle(this.getStyle());
			}
		}
	}

}
