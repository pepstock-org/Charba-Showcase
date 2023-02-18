package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.plugins.AbstractPluginOptions;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ImportingCrosshairPluginCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ImportingCrosshairPluginCase> {
	}

	@UiField
	BarChartWidget chart;

	static final String PLUGIN_NAME = "crosshair";

	public ImportingCrosshairPluginCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Importing Crosshair plugin on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];
		dataset1.setBackgroundColor(color1.alpha(0.2D));
		dataset1.setData(getRandomDigits(months, false));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		IsColor color2 = GoogleChartColor.values()[1];
		dataset2.setBackgroundColor(color2.alpha(0.2D));
		dataset2.setData(getRandomDigits(months, false));

		BarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 3");
		IsColor color3 = GoogleChartColor.values()[2];
		dataset3.setBackgroundColor(color3.alpha(0.2D));
		dataset3.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
		
        CharbaCrosshairOptions crosshairOptions = new CharbaCrosshairOptions();
        crosshairOptions.setLine(LineOptions.getDefault());
        crosshairOptions.store(chart);
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();

		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));

		IsColor color = GoogleChartColor.values()[datasets.size()];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setData(getRandomDigits(months, false));
		datasets.add(dataset);
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	static class CharbaCrosshairOptions extends AbstractPluginOptions {
	    private enum Property implements Key {
	        line,
	        sync,
	        zoom;

	        @Override
	        public String value() {
	            return this.toString();
	        }
	    }

	    public CharbaCrosshairOptions() {
	        super(PLUGIN_NAME, null);
	    }

	    public void setLine(LineOptions lineOptions) {
	        setValue(Property.line, lineOptions);
	    }

	}

	static final class LineOptions extends NativeObjectContainer {
	    private enum Property implements Key {
	        color,
	        width;

	        @Override
	        public String value() {
	            return this.toString();
	        }
	    }

	    public static LineOptions getDefault() {
	        LineOptions lineOptions = new LineOptions();
	        lineOptions.setColor("#F66");
	        lineOptions.setWidth(3);
	        return lineOptions;
	    }

	    public LineOptions() {
	        this(null);
	    }

	    LineOptions(NativeObject nativeObject) {
	        super(nativeObject);
	    }

	    public void setColor(String color) {
	        setValue(Property.color, color);
	    }

	    public void setWidth(double width) {
	        setValue(Property.width, width);
	    }
	}

}
