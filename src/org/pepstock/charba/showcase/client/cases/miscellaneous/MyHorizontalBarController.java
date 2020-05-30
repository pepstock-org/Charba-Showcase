package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.elements.Img;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.options.Scale;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.resources.client.ImageResource;

public class MyHorizontalBarController extends AbstractController {

	public static final ControllerType TYPE = new ControllerType("myHorizontalBar", ChartType.HORIZONTAL_BAR);

	private static final int MIN = 50;

	private static final int MAX = 100;

	private static final int PERCENT = 10;

	@Override
	public ControllerType getType() {
		return TYPE;
	}

	@Override
	public void initialize(ControllerContext context, IsChart chart, int datasetIndex) {
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.X);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParentHtmlElement().getOffsetWidth());
		super.initialize(context, chart, datasetIndex);
	}

	@Override
	public void draw(ControllerContext context, IsChart chart, double ease) {
		super.draw(context, chart, ease);
		final int padding = 4;
		Context2dItem ctx = chart.getCanvas().getContext2d();
		ScaleItem scale = chart.getNode().getScales().getItems().get(DefaultScaleId.Y.value());
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.X);
		List<String> ticks = scale.getTicks();
		int heightAmongLabels = (scale.getBottom() - scale.getTop()) / ticks.size();
		final int height = Math.min(heightAmongLabels - (padding * 2), MIN);
		final int width = Math.min(60 * height / 40, axis.getScaleLabel().getPadding().getTop() - padding);
		int x = scale.getLeft() + axis.getScaleLabel().getPadding().getTop() - width + axis.getScaleLabel().getFontSize();
		int y = scale.getTop();
		for (String tick : ticks) {
			Img image = null;
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

	@Override
	public void update(ControllerContext context, IsChart chart, boolean reset) {
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.X);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParentHtmlElement().getOffsetWidth());
		super.update(context, chart, reset);
	}

	private void calculateAndSetScaleLabelPadding(Scale axis, int width) {
		int percent = width * PERCENT / 100;
		int padding = Math.min(Math.max(MIN, percent), MAX);
		axis.getScaleLabel().getPadding().setTop(padding);
	}

	private Img getImageElement(ImageResource resource) {
		return ImagesHelper.toImg(resource);
	}
}
