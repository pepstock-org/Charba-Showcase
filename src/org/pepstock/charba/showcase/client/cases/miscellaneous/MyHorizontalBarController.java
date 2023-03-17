package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Helpers;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.elements.Img;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.ScaleTickItem;
import org.pepstock.charba.client.options.Scale;
import org.pepstock.charba.client.options.TransitionMode;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.resources.client.ImageResource;

public class MyHorizontalBarController extends AbstractController {

	public static final ControllerType TYPE = new ControllerType("myHorizontalBar", ChartType.BAR, (controllerType) -> new MyHorizontalBarController()); 

	private static final int MIN = 50;

	private static final int MAX = 100;

	private static final int PERCENT = 10;

	public MyHorizontalBarController() {
		super(TYPE);
	}
	
	@Override
	public void onBeforeInitialize(ControllerContext context, IsChart chart) {
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.Y);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParentHtmlElement().getOffsetWidth());
	}

	@Override
	public void onAfterDraw(ControllerContext context, IsChart chart) {
		final int padding = 4;
		Context2dItem ctx = chart.getCanvas().getContext2d();
		Helpers.get().unclipArea(ctx);
		ScaleItem scale = chart.getNode().getScales().getItems().get(DefaultScaleId.Y.value());
		
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.Y);
		List<ScaleTickItem> ticks = scale.getTicks();
		double heightAmongLabels = (scale.getBottom() - scale.getTop()) / ticks.size();
		final double height = Math.min(heightAmongLabels - (padding * 2), MIN);
		final double width = Math.min(60 * height / 40, axis.getTitle().getPadding().getTop() - padding);
		double x = scale.getLeft() + axis.getTitle().getPadding().getTop() - width + axis.getTitle().getFont().getSize();
		double y = scale.getTop();
		for (ScaleTickItem tick : ticks) {
			Img image = null;
			if (tick.getLabel().equalsIgnoreCase("br")) {
				image = getImageElement(Images.INSTANCE.flagBR());
			} else if (tick.getLabel().equalsIgnoreCase("de")) {
				image = getImageElement(Images.INSTANCE.flagDE());
			} else if (tick.getLabel().equalsIgnoreCase("fr")) {
				image = getImageElement(Images.INSTANCE.flagFR());
			} else if (tick.getLabel().equalsIgnoreCase("gb")) {
				image = getImageElement(Images.INSTANCE.flagGB());
			} else if (tick.getLabel().equalsIgnoreCase("it")) {
				image = getImageElement(Images.INSTANCE.flagIT());
			} else if (tick.getLabel().equalsIgnoreCase("us")) {
				image = getImageElement(Images.INSTANCE.flagUS());
			}
			if (image != null) {
				double yToDraw = y + (heightAmongLabels - height) / 2;
				ctx.drawImage(image, x, yToDraw, width, height);
			}
			y = y + heightAmongLabels;
		}
	}
	
	@Override
	public void onBeforeUpdate(ControllerContext context, IsChart chart, TransitionMode mode) {
		Scale axis = (Scale) context.getNode().getOptions().getScales().getAxis(DefaultScaleId.Y);
		calculateAndSetScaleLabelPadding(axis, chart.getCanvas().getParentHtmlElement().getOffsetWidth());
	}

	private void calculateAndSetScaleLabelPadding(Scale axis, int width) {
		int percent = width * PERCENT / 100;
		int padding = Math.min(Math.max(MIN, percent), MAX);
		axis.getTitle().getPadding().setTop(padding);
	}

	private Img getImageElement(ImageResource resource) {
		return ImagesHelper.toImg(resource);
	}
}
