package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.PieChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.ColorCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.callbacks.OpacityCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.datalabels.enums.Weight;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsMultiLabelsCase extends BaseComposite{
	
	private static final int DATASET_NUMBER = 4;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsMultiLabelsCase> {
	}

	@UiField
	PieChart chart;

	@UiField
	VerticalPanel container;
	
	final NumberFormat percentageFormatter = NumberFormat.getPercentFormat();

	public DataLabelsMultiLabelsCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);

		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		PieDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(getSequenceColors(DATASET_NUMBER, 1));
		dataset1.setData(getRandomDigits(DATASET_NUMBER, false));
		
		DataLabelsOptions option1 = new DataLabelsOptions();

		DataLabelsOptions index = new DataLabelsOptions();
		index.setAlign(Align.END);
		index.setAnchor(Anchor.END);
		index.setDisplay(true);
		index.setColor(new ColorCallback() {
			
			@Override
			public Object invoke(IsChart chart, ScriptableContext context) {
				PieDataset ds = (PieDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(context.getIndex());
			}
		});
		index.setOffset(8);
		index.setFormatter(new FormatterCallback() {
			
			@Override
			public String invoke(IsChart chart, double value, ScriptableContext context) {
				return context.isActive() ? "index" : "#"+(context.getIndex()+1);
			}
		});
		
		index.setOpacity(new OpacityCallback() {
			
			@Override
			public Double invoke(IsChart chart, ScriptableContext context) {
				return context.isActive() ? 1D : 0.5D;
			}
		});
		option1.getLabels().setLabel("index", index);

		DataLabelsOptions name = new DataLabelsOptions();
		name.setAlign(Align.TOP);
		name.getFont().setSize(18);
		name.setDisplay(true);
		name.setFormatter(new FormatterCallback() {
			
			@Override
			public String invoke(IsChart chart, double value, ScriptableContext context) {
				return context.isActive() ? "name" : chart.getData().getLabels().getString(context.getIndex());
			}
		});
		option1.getLabels().setLabel("name", name);

		DataLabelsOptions value = new DataLabelsOptions();
		value.setAlign(Align.BOTTOM);
		value.setBackgroundColor(new BackgroundColorCallback() {
			
			@Override
			public Object invoke(IsChart chart, ScriptableContext context) {
				PieDataset ds = (PieDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getIndex());
				IsColor color = ds.getBackgroundColor().get(context.getIndex());
				return value > 50 ? HtmlColor.WHITE : color;
			}
		});
		value.setBorderColor(HtmlColor.WHITE);
		value.setBorderWidth(2);
		value.setBorderRadius(4);
		value.setColor(new ColorCallback() {
			
			@Override
			public Object invoke(IsChart chart, ScriptableContext context) {
				PieDataset ds = (PieDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getIndex());
				return value > 50 ? HtmlColor.BLACK : HtmlColor.WHITE;
			}
		});
		value.setFormatter(new FormatterCallback() {
			
			@Override
			public String invoke(IsChart chart, double value, ScriptableContext context) {
				return context.isActive() ? "value" : String.valueOf(value);
			}
		});
		value.getPadding().set(4);
		value.setDisplay(true);
		option1.getLabels().setLabel("value", value);

		
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);
		option.setDisplay(false);
		option.setOffset(0);
		option.getPadding().set(0);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
		
		DataLabelsOptions myOptions = dataset1.getOptions(DataLabelsPlugin.ID, DataLabelsPlugin.FACTORY);
		DataLabelsOptions index1 = myOptions.getLabels().getLabel("index");
		index1.getFont().setSize(18);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(DATASET_NUMBER, false));
		}
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
