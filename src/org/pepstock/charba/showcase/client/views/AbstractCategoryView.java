package org.pepstock.charba.showcase.client.views;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * MAIN VIEW
 */
abstract class AbstractCategoryView extends Composite {
	
	protected VerticalPanel content; 

	public AbstractCategoryView(VerticalPanel content) {
		this.content = content;
	}

	protected void clearPreviousChart() {
		int count = content.getWidgetCount();
		for (int i = 0; i < count; i++) {
			content.remove(i);
		}
	}
	
}
