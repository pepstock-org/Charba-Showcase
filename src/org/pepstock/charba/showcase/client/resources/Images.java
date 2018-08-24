package org.pepstock.charba.showcase.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Client Bundle with all images used in the WEB APP.<br>
 * Is improving the performance.
 * 
 * @author Andrea "Stock" Stocchero
 * 
 */
public interface Images extends ClientBundle {

	/**
	 * Static reference to be used everywhere.
	 */
	Images INSTANCE = GWT.create(Images.class);

	/**
	 * @return GWT logo image
	 */
	@Source("/images/gwt.png")
	ImageResource gwt();

	/**
	 * @return CHARTJS logo image
	 */
	@Source("/images/chartjs.png")
	ImageResource chartjs();

	/**
	 */
	@Source("/images/br.png")
	ImageResource flagBR();

	/**
	 */
	@Source("/images/de.png")
	ImageResource flagDE();

	/**
	 */
	@Source("/images/fr.png")
	ImageResource flagFR();

	/**
	 */
	@Source("/images/gb.png")
	ImageResource flagGB();

	/**
	 */
	@Source("/images/it.png")
	ImageResource flagIT();

	/**
	 */
	@Source("/images/us.png")
	ImageResource flagUS();

}
