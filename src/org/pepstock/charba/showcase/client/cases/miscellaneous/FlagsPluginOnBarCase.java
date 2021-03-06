package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.HorizontalBarDataset;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.elements.Img;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.gwt.widgets.HorizontalBarChartWidget;
import org.pepstock.charba.client.items.PluginResizeArgument;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.ScaleTickItem;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class FlagsPluginOnBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FlagsPluginOnBarCase> {
	}

	@UiField
	HorizontalBarChartWidget chart;

	CartesianCategoryAxis axis;

	private static final String[] COUNTRIES = { "br", "de", "fr", "gb", "it", "us" };

	private static final double MIN = 50D;

	private static final double MAX = 100D;

	private static final double PERCENT = 10D;

	public FlagsPluginOnBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Flags plugin on bar chart");

		HorizontalBarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(COUNTRIES.length, false));

		axis = new CartesianCategoryAxis(chart, AxisKind.Y);
		axis.setDisplay(true);
		axis.getTitle().setDisplay(true);

		chart.getData().setLabels(COUNTRIES);
		chart.getData().setDatasets(dataset1);

		chart.getOptions().getScales().setAxes(axis);
		
		AbstractPlugin p = new AbstractPlugin("flagsplugin") {

			@Override
			public void onAfterDatasetsDraw(IsChart chart) {
				final int padding = 4;
				Context2dItem ctx = chart.getCanvas().getContext2d();
				ScaleItem scale = chart.getNode().getScales().getItems().get(DefaultScaleId.Y.value());
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
			public void onResize(IsChart chart, PluginResizeArgument argument) {
				double width = argument.getSizeItem().getWidth();
				calculateAndSetScaleLabelPadding(width);
			}
			
		};
		chart.getPlugins().add(p);

	}

	@Override
	protected void onAttach() {
		calculateAndSetScaleLabelPadding(chart.getCanvas().getParentHtmlElement().getOffsetWidth());
		super.onAttach();
	}

	private Img getImageElement(ImageResource resource) {
		return ImagesHelper.toImg(resource);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	void calculateAndSetScaleLabelPadding(double width) {
		double percent = width * PERCENT / 100D;
		int padding = (int)Math.min(Math.max(MIN, percent), MAX);
		axis.getTitle().getPadding().setTop(padding);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
