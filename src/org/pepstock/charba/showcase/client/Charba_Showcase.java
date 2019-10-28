package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.client.resources.EmbeddedResources;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.client.utils.JsWindowHelper;
import org.pepstock.charba.showcase.client.resources.Images;
import org.pepstock.charba.showcase.client.samples.jsinterop.DemoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Charba_Showcase implements EntryPoint {
	
	public static final Logger LOG = Logger.getLogger("charba-showcase");
	
	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/tree/2.6/src/";

	public void onModuleLoad() {
		Image.prefetch(Images.INSTANCE.pattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.patternHover().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagIT().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagFR().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagDE().getSafeUri());
		
		Image.prefetch(Images.INSTANCE.githubWhite().getSafeUri());

		ResourcesType.setClientBundle(EmbeddedResources.INSTANCE);

		JsWindowHelper.get().enableResizeOnBeforePrint();
		
		RootPanel.get().add(new DemoView());
		
	}

}