package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class BarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, BarCase> {
	}

	@UiField
	BarChartWidget chart;

	public BarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setHidden(true);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
//		//FIXME
//		
//		chart.getPlugins().add(new AbstractPlugin() {
//			
//			private static final int INCREMENT = 2;
//			private int level = -2;
//			
//			private String getIndentation() {
//				if (level > 0) {
//					StringBuilder sb = new StringBuilder();
//					for (int i=0; i<level; i++) {
//						sb.append("-");
//					}
//					sb.append(" ");
//					return sb.toString();
//				}
//				return "";
//			}
//			
//			@Override
//			public String getId() {
//				return "callschain";
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeInit(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public void onBeforeInit(IsChart chart) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeInit");
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterInit(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.Chart)
//			 */
//			@Override
//			public void onAfterInit(IsChart chart, Chart nativeChart) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterInit");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeUpdate(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public boolean onBeforeUpdate(IsChart chart) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeUpdate");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterUpdate(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public void onAfterUpdate(IsChart chart) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterUpdate");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeLayout(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public boolean onBeforeLayout(IsChart chart) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeLayout");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterLayout(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public void onAfterLayout(IsChart chart) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterLayout");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDatasetsUpdate(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public boolean onBeforeDatasetsUpdate(IsChart chart) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeDatasetsUpdate");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetsUpdate(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public void onAfterDatasetsUpdate(IsChart chart) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterDatasetsUpdate");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDatasetUpdate(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.DatasetPluginItem)
//			 */
//			@Override
//			public boolean onBeforeDatasetUpdate(IsChart chart, DatasetPluginItem item) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeDatasetUpdate ds:"+item.getIndex());
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetUpdate(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.DatasetPluginItem)
//			 */
//			@Override
//			public void onAfterDatasetUpdate(IsChart chart, DatasetPluginItem item) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterDatasetUpdate ds:"+item.getIndex());
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeRender(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public boolean onBeforeRender(IsChart chart) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeRender");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterRender(org.pepstock.charba.client.IsChart)
//			 */
//			@Override
//			public void onAfterRender(IsChart chart) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterRender");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDraw(org.pepstock.charba.client.IsChart, double)
//			 */
//			@Override
//			public boolean onBeforeDraw(IsChart chart, double easing) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeDraw easing");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDraw(org.pepstock.charba.client.IsChart, double)
//			 */
//			@Override
//			public void onAfterDraw(IsChart chart, double easing) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterDraw easing");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDatasetsDraw(org.pepstock.charba.client.IsChart, double)
//			 */
//			@Override
//			public boolean onBeforeDatasetsDraw(IsChart chart, double easing) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeDatasetsDraw easing");
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetsDraw(org.pepstock.charba.client.IsChart, double)
//			 */
//			@Override
//			public void onAfterDatasetsDraw(IsChart chart, double easing) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterDatasetsDraw easing");
//				level = level - INCREMENT;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDatasetDraw(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.DatasetPluginItem)
//			 */
//			@Override
//			public boolean onBeforeDatasetDraw(IsChart chart, DatasetPluginItem item) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeDatasetDraw ds:"+item.getIndex());
//				return  true;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetDraw(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.DatasetPluginItem)
//			 */
//			@Override
//			public void onAfterDatasetDraw(IsChart chart, DatasetPluginItem item) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterDatasetDraw ds:"+item.getIndex());
//				level = level - INCREMENT;
//			}
//
////			/* (non-Javadoc)
////			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeTooltipDraw(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.TooltipPluginItem)
////			 */
////			@Override
////			public boolean onBeforeTooltipDraw(IsChart chart, TooltipPluginItem item) {
////				Charba_Showcase.LOG.info(getIndentation()+"onBeforeTooltipDraw(chart, item);
////			}
////
////			/* (non-Javadoc)
////			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterTooltipDraw(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.TooltipPluginItem)
////			 */
////			@Override
////			public void onAfterTooltipDraw(IsChart chart, TooltipPluginItem item) {
////				Charba_Showcase.LOG.info(getIndentation()+"onAfterTooltipDraw(chart, item);
////			}
////
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeEvent(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.dom.BaseNativeEvent)
//			 */
//			@Override
//			public boolean onBeforeEvent(IsChart chart, BaseNativeEvent event) {
//				level = level + INCREMENT;
//				Charba_Showcase.LOG.info(getIndentation()+"onBeforeEvent");
//				org.pepstock.charba.client.utils.Window.getConsole().log(event);
//				return false;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterEvent(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.dom.BaseNativeEvent)
//			 */
//			@Override
//			public void onAfterEvent(IsChart chart, BaseNativeEvent event) {
//				Charba_Showcase.LOG.info(getIndentation()+"onAfterEvent");
//				level = level - INCREMENT;
//			}
////
////			/* (non-Javadoc)
////			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onResize(org.pepstock.charba.client.IsChart, org.pepstock.charba.client.items.SizeItem)
////			 */
////			@Override
////			public void onResize(IsChart chart, SizeItem size) {
////				Charba_Showcase.LOG.info(getIndentation()+"onResize(chart, size);
////			}
////
////			/* (non-Javadoc)
////			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onDestroy(org.pepstock.charba.client.IsChart)
////			 */
////			@Override
////			public void onDestroy(IsChart chart) {
////				Charba_Showcase.LOG.info(getIndentation()+"onDestroy(chart);
////			}
//			
//			
//			
//		});
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
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
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(1);
		dataset.setData(getRandomDigits(months));

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
}
