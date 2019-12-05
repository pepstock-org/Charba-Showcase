package org.pepstock.charba.showcase.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {

	Images INSTANCE = GWT.create(Images.class);

	@Source("/images/GitHub-Mark-32px.png")
	ImageResource github();

	@Source("/images/GitHub-Mark-Light-32px.png")
	ImageResource githubWhite();

	@Source("/images/gwt.png")
	ImageResource gwt();

	@Source("/images/weather-sun.png")
	ImageResource sun();

	@Source("/images/chartjs.png")
	ImageResource chartjs();
	
	@Source("/images/crossline-lines.png")
	ImageResource pattern();

	@Source("/images/positive.png")
	ImageResource patternHover();

	@Source("/images/custom_point.png")
	ImageResource customPoint();

	@Source("/images/br.png")
	ImageResource flagBR();

	@Source("/images/de.png")
	ImageResource flagDE();

	@Source("/images/fr.png")
	ImageResource flagFR();

	@Source("/images/gb.png")
	ImageResource flagGB();

	@Source("/images/it.png")
	ImageResource flagIT();

	@Source("/images/us.png")
	ImageResource flagUS();

	@Source("/images/backgroundPattern.jpg")
	ImageResource backgroundPattern();

	@Source("/images/backgroundPattern1.jpg")
	ImageResource backgroundPattern1();

	@Source("/images/backgroundPattern2.png")
	ImageResource backgroundPattern2();

}
