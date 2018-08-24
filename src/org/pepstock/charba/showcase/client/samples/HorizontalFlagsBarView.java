package org.pepstock.charba.showcase.client.samples;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.ScalesNode;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.showcase.client.resources.Images;
import org.pepstock.charba.showcase.client.samples.Toast.Level;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class HorizontalFlagsBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	private static final String[] COUNTRIES = {"br","de","fr","gb","it","us"};
	
	interface ViewUiBinder extends UiBinder<Widget, HorizontalFlagsBarView> {
	}
	
	@UiField
	BarChart chart;
	
	public HorizontalFlagsBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.right);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Horizontal Bar Chart with Flags");
		chart.getOptions().getLayout().getPadding().setLeft(100);
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		
		dataset1.setData(getRandomDigits(COUNTRIES.length, false));

		chart.getData().setLabels(COUNTRIES);
		chart.getData().setDatasets(dataset1);
		
		AbstractPlugin p = new AbstractPlugin() {

			@Override
			public String getId() {
				return "test";
			}

			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetsDraw(org.pepstock.charba.client.AbstractChart, double)
			 */
			@Override
			public void onAfterDatasetsDraw(AbstractChart<?, ?> chart, double easing, JavaScriptObject options) {
				final int padding = 20;
				Context2d ctx = chart.getCanvas().getContext2d();
				ScaleItem scale = chart.getChartNode().getScales().getItems().get(ScalesNode.DEFAULT_Y_AXIS_ID);
			
				List<String> ticks = scale.getTicks();
				
				int heightAmongLabels = (scale.getBottom() - scale.getTop()) / ticks.size();

				final int height = Math.min(heightAmongLabels - (padding * 2), 60);
				final int width = 60 * height / 40;
				
				int x = scale.getLeft() - width;
				int y = scale.getTop();
				for (String tick : ticks) {
					ImageElement image = null;
					if (tick.equalsIgnoreCase("br")) {
						image = getImageElement(Images.INSTANCE.flagBR());
					} else if (tick.equalsIgnoreCase("de")) {
						image = getImageElement(Images.INSTANCE.flagDE());
					} else if (tick.equalsIgnoreCase("fr")) {
						image = getImageElement(Images.INSTANCE.flagFR());
					} else if (tick.equalsIgnoreCase("gb")) {
						image = getImageElement(Images.INSTANCE.flagGB());
					} else if (tick.equalsIgnoreCase("it")) {
						image = getImageElement(Images.INSTANCE.flagIT());
					} else if (tick.equalsIgnoreCase("us")) {
						image = getImageElement(Images.INSTANCE.flagUS());
					}
					
					if (image != null) {
						int yToDraw = y + (heightAmongLabels - height) / 2;
						ctx.drawImage(image, x, yToDraw, width, height);
					}
					y = y + heightAmongLabels;
				}
			}
		};
		try {
			chart.getPlugins().add(p);
		} catch (InvalidPluginIdException e) {
			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
		}
	
	}
	
	private ImageElement getImageElement(ImageResource resource) {
		Image img = new Image(resource.getSafeUri());
	    return ImageElement.as(img.getElement());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
}
