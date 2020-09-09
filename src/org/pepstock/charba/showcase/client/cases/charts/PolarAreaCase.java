package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.callbacks.ScaleColorCallback;
import org.pepstock.charba.client.callbacks.ScaleFontCallback;
import org.pepstock.charba.client.callbacks.ScaleScriptableContext;
import org.pepstock.charba.client.callbacks.ScaleShowLabelBackdropCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.Axis;
import org.pepstock.charba.client.configuration.FontOptions;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
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

		RadialAxis axis = new RadialAxis(chart);
		axis.setBeginAtZero(true);
		axis.setReverse(false);
		axis.getGrideLines().setCircular(true);
		axis.getAngleLines().setDisplay(true);
		axis.getAngleLines().setColor(new ScaleColorCallback() {
			
			@Override
			public IsColor invoke(Axis axis, ScaleScriptableContext context) {
				return HtmlColor.RED;
			}
		});
		
		axis.getPointLabels().setDisplay(true);
//		axis.getPointLabels().setCallback(new RadialPointLabelCallback() {
//			
//			@Override
//			public String onCallback(Axis axis, String item) {
//				return item;
//			}
//		});
		
		axis.getPointLabels().setFont(new ScaleFontCallback() {
			
			@Override
			public FontOptions invoke(Axis axis, ScaleScriptableContext context) {
				FontOptions fo = new FontOptions();
				fo.setColor(context.getIndex() % 2  == 0 ? HtmlColor.RED : HtmlColor.BLACK);
				fo.setSize(16);
				return fo;
			}
		});
		
		//axis.getTicks().getFont().setColor(HtmlColor.GRAY);
		axis.getTicks().setFont(new ScaleFontCallback() {
			
			@Override
			public FontOptions invoke(Axis axis, ScaleScriptableContext context) {
				FontOptions fo = new FontOptions();
				fo.setColor(context.getIndex() % 2  == 0 ? HtmlColor.RED : HtmlColor.BLACK);
				fo.setSize(24);
				return fo;
			}
		});
		
		axis.getTicks().setBackdropColor(new ScaleColorCallback() {
			
			@Override
			public Object invoke(Axis axis, ScaleScriptableContext context) {
				return context.getIndex() % 2  == 0 ? HtmlColor.ORANGE : HtmlColor.GRAY;
			}
		});

		axis.getTicks().setShowLabelBackdrop(new ScaleShowLabelBackdropCallback() {
			
			@Override
			public Boolean invoke(Axis axis, ScaleScriptableContext context) {
				return context.getIndex() % 2  == 0 ? false : false;
			}
		});

		chart.getOptions().getScales().setAxes(axis);

		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 0.2D));
		dataset.setData(getRandomDigits(months, false));

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
