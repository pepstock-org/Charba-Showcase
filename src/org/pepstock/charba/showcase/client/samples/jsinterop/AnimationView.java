package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.jsinterop.data.Dataset;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
import org.pepstock.charba.client.jsinterop.enums.Fill;
import org.pepstock.charba.client.jsinterop.enums.InteractionMode;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.client.jsinterop.events.AnimationCompleteEvent;
import org.pepstock.charba.client.jsinterop.events.AnimationCompleteEventHandler;
import org.pepstock.charba.client.jsinterop.events.AnimationProgressEvent;
import org.pepstock.charba.client.jsinterop.events.AnimationProgressEventHandler;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.ProgressBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Andrea "Stock" Stocchero
 */
public class AnimationView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnimationView> {
	}

	@UiField
	LineChart chart;
	
	@UiField 
	ProgressBar progress;
	
	public AnimationView() {
		initWidget(uiBinder.createAndBindUi(this));
	
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.nearest);
		chart.getOptions().getHover().setIntersect(true);
		chart.getOptions().getAnimation().setDuration(2000);
		chart.addHandler(new AnimationProgressEventHandler() {

			@Override
			public void onProgress(AnimationProgressEvent event) {
				double value = event.getItem().getCurrentStep() / event.getItem().getNumSteps() * 100;
				progress.setProgress(value);
			}
			
		}, AnimationProgressEvent.TYPE);

		chart.addHandler(new AnimationCompleteEventHandler() {

			@Override
			public void onComplete(AnimationCompleteEvent event) {
				progress.setProgress(100);
			}
			
		}, AnimationCompleteEvent.TYPE);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.nofill);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		

	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();

		LineDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		IsColor color = Colors.ALL[datasets.size()]; 
		dataset.setBackgroundColor(color.toHex());
		dataset.setBorderColor(color.toHex());
		dataset.setFill(Fill.nofill);
		dataset.setData(getRandomDigits(months));
		
		datasets.add(dataset);
		
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		datasets.remove(datasets.size()-1);
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12){
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset dataset : datasets){
				dataset.getData().add(getRandomDigit());
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleremoveData(ClickEvent event) {
		if (months > 1){
			months--;
			chart.getData().setLabels(getLabels());
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset dataset : datasets){
				dataset.getData().remove(dataset.getData().size()-1);
			}
			chart.update();
		}
	}
}
