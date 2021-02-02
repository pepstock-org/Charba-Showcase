package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.callbacks.ScaleBorderDashOffsetCallback;
import org.pepstock.charba.client.callbacks.ScaleColorCallback;
import org.pepstock.charba.client.callbacks.ScaleLineWidthCallback;
import org.pepstock.charba.client.callbacks.ScaleScriptableContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GridLinesStyleCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GridLinesStyleCase> {
	}

	@UiField
	LineChartWidget chart;

	private static final String[] COLORS = new String[] { "pink", "red", "orange", "yellow", "green", "blue", "indigo", "purple" };

	public GridLinesStyleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Gridline styles");

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months, false));
		dataset1.setFill(Fill.FALSE);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.FALSE);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		Labels lbl = getMultiLabels();
		axis1.setLabels(lbl);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.getGrideLines().setDrawBorder(false);

		axis2.getGrideLines().setColor(new ScaleColorCallback() {

			@Override
			public Object invoke(Axis axis, ScaleScriptableContext context) {
				int mod = context.getIndex() % COLORS.length;
				return COLORS[mod];
			}
		});

		axis2.getGrideLines().setLineWidth(new ScaleLineWidthCallback() {

			@Override
			public Integer invoke(Axis axis, ScaleScriptableContext context) {
				return context.getIndex() % 5;
			}
		});

		axis2.getGrideLines().setBorderDashOffset(new ScaleBorderDashOffsetCallback() {

			@Override
			public Double invoke(Axis axis, ScaleScriptableContext context) {
				return (double)(context.getIndex() % 10);
			}
		});
		
		axis2.getGrideLines().setTickColor(new ScaleColorCallback() {
			
			@Override
			public Object invoke(Axis axis, ScaleScriptableContext context) {
				return HtmlColor.BLACK;
			}
		});

		axis2.setMin(0D);
		axis2.setMax(100D);
		axis2.getTicks().setStepSize(10D);
		chart.getOptions().getScales().setAxes(axis1, axis2);
		chart.getData().setDatasets(dataset1, dataset2);
		
		Charba_Showcase.LOG.info(axis2.toJSON());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private Labels getMultiLabels() {
		String[] labels = getLabels();
		Labels l = Labels.build();
		for (int i = 0; i < labels.length; i++) {
			if (i % 2 == 0) {
				l.add(new String[] { labels[i], "2020" });
			} else {
				l.add(labels[i]);
			}
		}
		return l;
	}
}
