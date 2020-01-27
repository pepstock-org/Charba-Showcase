package org.pepstock.charba.showcase.client.views;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

abstract class AbstractView extends Composite {

	protected VerticalPanel content;

	public AbstractView(VerticalPanel content) {
		this.content = content;
	}

	protected void clearPreviousChart() {
		int count = content.getWidgetCount();
		for (int i = 0; i < count; i++) {
			content.remove(i);
		}
	}

}
