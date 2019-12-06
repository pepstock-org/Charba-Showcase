package org.pepstock.charba.showcase.client.cases.jsinterop;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.ChartResizeEvent;
import org.pepstock.charba.client.events.ChartResizeEventHandler;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.options.Scales;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class HorizontalFlagsBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	private static final String[] COUNTRIES = {"br","de","fr","gb","it","us"};
	
	interface ViewUiBinder extends UiBinder<Widget, HorizontalFlagsBarView> {
	}
	
	@UiField
	BarChart chart;
	
	CartesianCategoryAxis axis;
	
	private static final int MIN = 50;
	
	private static final int MAX = 100;
	
	private static final int PERCENT = 10;
	
	public HorizontalFlagsBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Horizontal Bar Chart with Flags");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Countries");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(COUNTRIES.length, false));

		axis = new CartesianCategoryAxis(chart);
		axis.setDisplay(true);
		axis.getScaleLabel().setDisplay(true);

		chart.getData().setLabels(COUNTRIES);
		chart.getData().setDatasets(dataset1);

		chart.getOptions().getScales().setYAxes(axis);
		
		chart.addHandler(new ChartResizeEventHandler() {

			private final Timer t = new Timer() {
				@Override
				public void run() {
					chart.reset();
					chart.draw();
				}
			};
			
			@Override
			public void onResize(final ChartResizeEvent event) {
				int width = event.getSize().getWidth();
				calculateAndSetScaleLabelPadding(width);
				t.schedule(500);
			}
		}, ChartResizeEvent.TYPE);
		
		AbstractPlugin p = new AbstractPlugin() {
			
			@Override
			public String getId() {
				return "test";
			}

			@Override
			public void onAfterDatasetsDraw(IsChart chart, double easing) {
				final int padding = 4;
				Context2d ctx = chart.getCanvas().getContext2d();
				ScaleItem scale = chart.getNode().getScales().getItems().get(Scales.DEFAULT_Y_AXIS_ID);
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
		};
		chart.getPlugins().add(p);
	
	}
	
	@Override
	protected void onAttach() {
		calculateAndSetScaleLabelPadding(chart.getCanvas().getParent().getOffsetWidth());
		super.onAttach();
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
	
	private void calculateAndSetScaleLabelPadding(int width) {
		int percent = width * PERCENT / 100;
		int padding = Math.min(Math.max(MIN, percent), MAX);
		axis.getScaleLabel().getPadding().setTop(padding);
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
