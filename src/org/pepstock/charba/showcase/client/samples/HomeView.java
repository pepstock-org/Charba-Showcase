package org.pepstock.charba.showcase.client.samples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class HomeView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
