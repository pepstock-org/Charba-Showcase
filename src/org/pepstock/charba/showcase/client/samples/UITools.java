package org.pepstock.charba.showcase.client.samples;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;

public final class UITools {
	
	private UITools() {
	}

	public static boolean isEventInsideWidget(NativeEvent event, Widget widget){
		int x = event.getClientX();
		int y = event.getClientY();

		int top = widget.getAbsoluteTop();
		int bottom = top + widget.getOffsetHeight();
		int left = widget.getAbsoluteLeft();
		int right = left + widget.getOffsetWidth();

		if (x < left || x > right ){
			return false;
		} else if (y < top || y > bottom ){
			return false;
		}
		return true;
	}
}