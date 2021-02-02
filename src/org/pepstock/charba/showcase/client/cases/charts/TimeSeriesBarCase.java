package org.pepstock.charba.showcase.client.cases.charts;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.TimeSeriesBarDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesBarChartWidget;
import org.pepstock.charba.client.intl.CLocale;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesBarCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 15;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesBarCase> {
	}

	@UiField
	TimeSeriesBarChartWidget chart;

	CartesianTimeSeriesAxis axis;
	
	private final long startingPoint = System.currentTimeMillis();

	public TimeSeriesBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Timeseries bar chart");

		TimeSeriesBarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		TimeSeriesBarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);

		List<TimeSeriesItem> data1 = new LinkedList<>();
		List<TimeSeriesItem> data2 = new LinkedList<>();

		DateAdapter adapter = new DateAdapter();

		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			data1.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), 100 * Math.random()));
			data2.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), 100 * Math.random()));
		}
		dataset1.setTimeSeriesData(data1);
		dataset2.setTimeSeriesData(data2);

		axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.DAY, "d MMMM");
		axis.setOffset(true);
		
		axis.getAdapters().getDate().setLocale(CLocale.GERMANY);
// FIXME		
//		axis.getAdapters().getDate().setLocale(CLocale.ITALY);
//		axis.getAdapters().getDate().setNumberingSystem(NumberingSystem.ARAB);
//		axis.getAdapters().getDate().setOutputCalendar(Calendar.CHINESE);
//		axis.getAdapters().getDate().setZone(TimeZone.AMERICA_NEW_YORK);		

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			TimeSeriesBarDataset scDataset = (TimeSeriesBarDataset) dataset;
			for (TimeSeriesItem dp : scDataset.getTimeSeriesData()) {
				dp.setValue(getRandomDigit(false));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
