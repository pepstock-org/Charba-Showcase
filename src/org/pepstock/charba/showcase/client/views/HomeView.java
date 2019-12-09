package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class HomeView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

//	V1.2 746 KB
//	V1.3 760 KB
//	V1.4 763 KB
//	V1.5 832 KB
//	V1.6 861 KB
//	V1.7 863 KB
//	V2.0 1200 MB
//	V2.1 1550 MB
//	V2.2 1710 MB
//	V2.3 1720 MB
//	V2.4 1910 MB
//	V2.5 1950 MB
//	V2.6 2040 MB
}
