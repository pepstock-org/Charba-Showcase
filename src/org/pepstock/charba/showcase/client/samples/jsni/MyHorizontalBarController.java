package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.Context;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.defaults.scale.Scale;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.ScalesNode;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class MyHorizontalBarController extends AbstractController {
	
	public static final ControllerType TYPE = new ControllerType("stock1");
	
	private static final int MIN = 50;
	
	private static final int MAX = 100;
	
	private static final int PERCENT = 10;


	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.Controller#getType()
	 */
	@Override
	public Type getType() {
		return TYPE;
	}

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.Controller#getChartType()
	 */
	@Override
	public ChartType getChartType() {
		return ChartType.horizontalBar;
	}

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.controllers.AbstractController#initialize(org.pepstock.charba.client.controllers.Context, org.pepstock.charba.client.AbstractChart, int)
	 */
	@Override
	public void initialize(Context jsThis, AbstractChart<?, ?> chart, int datasetIndex) {
		Scale axis = (Scale)jsThis.getChartNode().getOptions().getScales().getYAxes().get(0);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParent().getOffsetWidth());
		super.initialize(jsThis, chart, datasetIndex);
	}				
	
	private void calculateAndSetScaleLabelPadding(Scale axis, int width) {
		int percent = width * PERCENT / 100;
		int padding = Math.min(Math.max(MIN, percent), MAX);
		axis.getScaleLabel().getPadding().setTop(padding);
	}

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.controllers.AbstractController#update(org.pepstock.charba.client.controllers.Context, org.pepstock.charba.client.AbstractChart, boolean)
	 */
	@Override
	public void update(Context jsThis, AbstractChart<?, ?> chart, boolean reset) {
		Scale axis = (Scale)jsThis.getChartNode().getOptions().getScales().getYAxes().get(0);
		ScaleItem scale = chart.getChartNode().getScales().getItems().get(ScalesNode.DEFAULT_Y_AXIS_ID);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParent().getOffsetWidth());
		if (scale != null && scale.getRight() < axis.getScaleLabel().getPadding().getTop()) {
			chart.update();
		} else {
			super.update(jsThis, chart, reset);
		}
	}

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.controllers.AbstractController#draw(org.pepstock.charba.client.controllers.Context, org.pepstock.charba.client.AbstractChart, double)
	 */
	@Override
	public void draw(Context jsThis, AbstractChart<?, ?> chart, double ease) {
//		LOG.info("DRAW "+jsThis.getChartNode().getWidth());
		Scale axis = (Scale)chart.getChartNode().getOptions().getScales().getYAxes().get(0);
		super.draw(jsThis, chart, ease);
		final int padding = 4;
		Context2d ctx = chart.getCanvas().getContext2d();
		ScaleItem scale = chart.getChartNode().getScales().getItems().get(ScalesNode.DEFAULT_Y_AXIS_ID);
		axis = (Scale)chart.getChartNode().getOptions().getScales().getYAxes().get(0);
		List<String> ticks = scale.getTicks();
		int heightAmongLabels = (scale.getBottom() - scale.getTop()) / ticks.size();
		final int height = Math.min(heightAmongLabels - (padding * 2), MIN);
		final int width = Math.min(60 * height / 40, axis.getScaleLabel().getPadding().getTop() - padding);
		int x = scale.getLeft() + axis.getScaleLabel().getPadding().getTop() - width + axis.getScaleLabel().getFontSize();
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
	
	private ImageElement getImageElement(ImageResource resource) {
		Image img = new Image(resource.getSafeUri());
	    return ImageElement.as(img.getElement());
	}		

}
