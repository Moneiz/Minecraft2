package net.minecraft2.util.text;

import java.util.List;

import com.google.common.collect.Lists;

public class TextComponentBase implements ITextComponent{

	private Style style;
	protected List<ITextComponent> siblings = Lists.<ITextComponent>newArrayList();
	
	public Style getStyle() {
		if(this.style == null) {
			this.style = new Style();
			
			for(ITextComponent iTextComponent : this.siblings) {
				iTextComponent.getStyle().setParentStyle(this.style);
			}
		}
		return style;
	}

}
