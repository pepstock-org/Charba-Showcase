package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.Plugin;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.items.ChartAreaNode;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.zoom.ZoomContext;
import org.pepstock.charba.client.zoom.ZoomOptions;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.client.zoom.callbacks.StartCallback;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ZoomPanRegionCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 120;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomPanRegionCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	public ZoomPanRegionCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Pan region on scatter chart");

		ScatterDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys1 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(xs1[i]);
			dp1[i].setY(ys1[i]);
		}
		dataset1.setDataPoints(dp1);

		ScatterDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys2 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp2 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp2[i] = new DataPoint();
			dp2[i].setX(xs2[i]);
			dp2[i].setY(ys2[i]);
		}
		dataset2.setDataPoints(dp2);

		chart.getData().setDatasets(dataset1, dataset2);

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(true);
		options.getPan().setStartCallback(new StartCallback() {
			
			@Override
			public boolean onStart(ZoomContext context) {
				ChartAreaNode area = context.getChart().getNode().getChartArea();
				double w25 = area.getWidth() * 0.25;
				double h25 = area.getHeight() * 0.25;
				if (context.getPoint().getX() < area.getLeft() + w25 || context.getPoint().getX() > area.getRight() - w25
						|| context.getPoint().getY() < area.getTop() + h25 || context.getPoint().getY() > area.getBottom() - h25) {
					return false;
				}
				return true;
			}
		});
		options.getPan().setMode(InteractionAxis.XY);
		options.getLimits().getX().setMin(-200);
		options.getLimits().getX().setMax(200);
		options.getLimits().getX().setMinRange(50);
		options.getLimits().getY().setMin(-200);
		options.getLimits().getY().setMax(200);
		options.getLimits().getY().setMinRange(50);
		
		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);
		
		Plugin borderPlugin = new AbstractPlugin("panAreaBorder") {

			@Override
			public boolean onBeforeDraw(IsChart chart) {
				ChartAreaNode area = chart.getNode().getChartArea();
			    Context2dItem ctx = chart.getCanvas().getContext2d();
			    ctx.save();
			    ctx.setStrokeColor("rgba(255, 0, 0, 0.3)");
			    ctx.setLineWidth(1);
			    ctx.strokeRect(area.getLeft() + area.getWidth() * 0.25, area.getTop() + area.getHeight() * 0.25, area.getWidth() / 2, area.getHeight() / 2);
			    ctx.restore();
			    return true;
			}
			
		};
		
		chart.getPlugins().add(borderPlugin);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			ScatterDataset scDataset = (ScatterDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setX(getRandomDigit());
				dp.setY(getRandomDigit());
			}
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.reset(chart);
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
