package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.callbacks.FontCallback;
import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
import org.pepstock.charba.client.items.FontItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class PolarAreaCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PolarAreaCase> {
	}

	@UiField
	PolarAreaChartWidget chart;
	
	public PolarAreaCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Polar area chart");

		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 0.5D));
		dataset.setData(getRandomDigits(months, false));

		RadialAxis axis = new RadialAxis(chart);
		axis.setBeginAtZero(true);
		axis.setReverse(false);
		axis.getGrid().setCircular(true);
		axis.getAngleLines().setDisplay(true);
		axis.getPointLabels().setDisplay(true);
		axis.getPointLabels().getFont().setSize(8);
		axis.getPointLabels().setFont(new FontCallback<ScaleContext>() {
			
			@Override
			public FontItem invoke(ScaleContext context) {
				FontItem fo = new FontItem();
				fo.setSize(16);
				return fo;
			}
		});
		chart.getOptions().getScales().setAxes(axis);
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				PolarAreaDataset pds = (PolarAreaDataset) ds;
				pds.setBackgroundColor(getSequenceColors(months, 0.2D));
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
