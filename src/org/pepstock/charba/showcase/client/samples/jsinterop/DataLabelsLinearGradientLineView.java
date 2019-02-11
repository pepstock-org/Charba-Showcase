package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientOrientation;
import org.pepstock.charba.client.colors.GradientScope;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.BorderColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.Weight;
import org.pepstock.charba.client.utils.Window;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsLinearGradientLineView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsLinearGradientLineView> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsLinearGradientLineView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		final Pattern pattern = new Pattern(Images.INSTANCE.pattern());
		
		final Gradient gradient1  = new Gradient(GradientType.linear, GradientOrientation.leftRight, GradientScope.chart);

		gradient1.addColorStop(0, HtmlColor.Orange);
		gradient1.addColorStop(1, HtmlColor.Purple);
		
		dataset1.setBackgroundColor(gradient1);
		
		dataset1.setBorderColor(gradient1);
		dataset1.setPointBackgroundColor(gradient1);
		dataset1.setPointHoverBackgroundColor(gradient1);
		dataset1.setPointHoverBorderColor(gradient1);
		
		dataset1.setPointRadius(5);
		
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill(Fill.nofill);
		
		//dataset1.setPointStyle(Images.INSTANCE.customPoint());

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
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new BackgroundColorCallback() {


			@Override
			public Object backgroundColor(AbstractChart<?, ?> chart, Context context) {
				if (chart.isInitialized()) {
//					LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
					Window.getConsole().log("EEE "+gradient1);
					return pattern;
				} 
				return null;
			}
		});
		option.setBorderColor(new BorderColorCallback() {
			
			@Override
			public Object borderColor(AbstractChart<?, ?> chart, Context context) {
				if (chart.isInitialized()) {
//					LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
					return gradient1;
				} 
				return null;
			}
		});
		option.setBorderRadius(1);
		option.setColor(HtmlColor.Black);
		option.getFont().setWeight(Weight.bold);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}
}
