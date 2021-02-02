package org.pepstock.charba.showcase.client;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.dom.DOMBuilder;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.impl.callbacks.DataLabelsPointerHandler;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.items.DatasetElement;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetReference;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.DeferredResources;
import org.pepstock.charba.client.resources.EmbeddedResources;
import org.pepstock.charba.client.resources.EntryPointStarter;
import org.pepstock.charba.client.resources.InjectableTextResource;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.showcase.client.cases.commons.Toast;
import org.pepstock.charba.showcase.client.cases.miscellaneous.MyHorizontalBarController;
import org.pepstock.charba.showcase.client.cases.miscellaneous.MyLineChart;
import org.pepstock.charba.showcase.client.resources.Images;
import org.pepstock.charba.showcase.client.resources.MyResources;
import org.pepstock.charba.showcase.client.views.MainView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Charba_Showcase implements EntryPoint {

	public static final String LOADING_PARAM = "loading";

	public static final String LOADING_EMBEDDED = "embedded";

	public static final String LOADING_DEFERRED = "deferred";

	public static final Logger LOG = Logger.getLogger("charba-showcase");

	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/blob/3.2/src/";

	public static boolean isDeferred = false;

	public void onModuleLoad() {

		Image.prefetch(Images.INSTANCE.background().getSafeUri());
		Image.prefetch(Images.INSTANCE.pattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.patternHover().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagIT().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagFR().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagDE().getSafeUri());

		Image.prefetch(Images.INSTANCE.githubWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.extensionWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.fingerprintWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.headlineWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.visibilityWhite().getSafeUri());

		String loadingParam = Window.Location.getParameter(LOADING_PARAM) != null ? Window.Location.getParameter(LOADING_PARAM) : LOADING_EMBEDDED;
		String loading = !loadingParam.equalsIgnoreCase(LOADING_DEFERRED) && !loadingParam.equalsIgnoreCase(LOADING_EMBEDDED) ? LOADING_EMBEDDED : loadingParam;

		if (loading.equals(LOADING_DEFERRED)) {
			isDeferred = true;
			deferredLoading();
		} else {
			isDeferred = false;
			embeddedLoading();
		}
	}

	private void embeddedLoading() {
		ResourcesType.setClientBundle(EmbeddedResources.INSTANCE);
		start();
	}

	private void deferredLoading() {
		final SimplePanel trigger = new SimplePanel();
		trigger.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		trigger.addAttachHandler(new AttachEvent.Handler() {

			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if (event.isAttached()) {
					GWT.runAsync(new RunAsyncCallback() {

						@Override
						public void onFailure(Throwable throwable) {
							RootPanel.get().remove(trigger);
							Window.alert("Code download failed");
						}

						@Override
						public void onSuccess() {
							EntryPointStarter.run(DeferredResources.INSTANCE, new Runnable() {

								@Override
								public void run() {
									RootPanel.get().remove(trigger);
									start();
								}
							});
						}
					});
				}
			}
		});
		RootPanel.get().add(trigger);
	}

	private void start() {

		org.pepstock.charba.client.utils.Window.enableResizeOnBeforePrint();

		Defaults.get().getGlobal().getFont().setFamily("'Lato', sans-serif");

		Defaults.get().getGlobal().getTitle().getFont().setSize(16);
		Defaults.get().getGlobal().getElements().getLine().setTension(0.4D);

		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getOptions(ChartType.PIE).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.POLAR_AREA).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.RADAR).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.DOUGHNUT).setAspectRatio(2D);

		MyHorizontalBarController.TYPE.register();

		MyLineChart.TYPE.register();

		LabelsPlugin.enable();

		DataLabelsPlugin.enable();

		ZoomPlugin.enable();
		
		AnnotationPlugin.enable();

//		 AnnotationOptions options = new AnnotationOptions();
//		
//		 BoxAnnotation box = new BoxAnnotation("stocktemplate");
//		 box.setDisplay(false);
//		 box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
//		 box.setXScaleID(DefaultScaleId.X);
//		 box.setYScaleID(DefaultScaleId.Y.value());
//		////
//		//// DateAdapter adapter = new DateAdapter();
//		//// box.setXMin(adapter.add(new Date(), 5, TimeUnit.DAY));
//		//// box.setXMax(adapter.add(new Date(), 15, TimeUnit.DAY));
//		//// box.setYMax(100);
//		//// box.setYMin(60);
//		 box.setBackgroundColor("rgba(101, 33, 171, 0.5)");
//		 box.setBorderColor("rgb(101, 33, 171)");
//		 box.setBorderWidth(3);
//		
//		 options.setAnnotations(box);
//		 
//		 Defaults.get().getGlobal().getPlugins().setOptions(AnnotationPlugin.ID, options);

		// --------
		// FIXME
		// --------

//		DataLabelsOptions option = Defaults.get().getGlobal().getPlugins().getOptions(DataLabelsPlugin.FACTORY);
//		option.setBackgroundColor(new BackgroundColorCallback() {
//
//			@Override
//			public String invoke(IsChart chart, ScriptableContext context) {
//				if (context.isActive()) {
//					return null;
//				}
//				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
//				return ds.getBackgroundColorAsString();
//			}
//		});
//		option.setBorderRadius(4);
//		option.setColor(HtmlColor.WHITE);
//		option.getFont().setWeight(Weight.BOLD);
//		MyListener listener = new MyListener();
//		option.setListenersHandler(listener);
		
//		DataLabelsOptions option = Defaults.get().getGlobal().getPlugins().getOptions(DataLabelsPlugin.FACTORY);
//		option.setFormatter(new FormatterCallback() {
//			
//			@Override
//			public String invoke(IsChart chart, DataItem dataItem, ScriptableContext context) {
//				return "Stock "+dataItem.getValueAsString();
//			}
//		});
//		Defaults.get().getGlobal().getPlugins().setOptions(DataLabelsPlugin.ID, option);
		

		Injector.ensureCssInjected(new InjectableTextResource(MyResources.INSTANCE.legend()));

		RootPanel.get().add(new MainView());

	}

	// --------
	// FIXME
	// --------

	class MyListener extends DataLabelsPointerHandler {

		MyListener() {
			super();
		}

		@Override
		public boolean onLeave(IsChart chart, ScriptableContext context) {
			super.onLeave(chart, context);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			LOG.info("> LEAVE: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			return true;
		}

		@Override
		public boolean onEnter(IsChart chart, ScriptableContext context) {
			super.onEnter(chart, context);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			LOG.info("> ENTER: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			return true;
		}

		@Override
		public boolean onClick(IsChart chart, ScriptableContext context) {
			super.onClick(chart, context);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			Labels labels = chart.getData().getLabels();
			List<Dataset> datasets = chart.getData().getDatasets();
			DatasetItem item = chart.getDatasetItem(context.getDatasetIndex());
			DatasetElement element = item.getElements().get(context.getDataIndex());
			if (datasets != null && !datasets.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset index: <b>").append(context.getDatasetIndex()).append("</b><br>");
				sb.append("Dataset label: <b>").append(datasets.get(context.getDatasetIndex()).getLabel()).append("</b><br>");
				sb.append("Dataset data: <b>").append(datasets.get(context.getDatasetIndex()).getData().get(context.getDataIndex())).append("</b><br>");
				sb.append("Index: <b>").append(context.getDataIndex()).append("</b><br>");
				sb.append("Value: <b>").append(labels.getStrings(context.getDataIndex()).get(0)).append("</b><br>");
				new Toast("Dataset Selected!", sb.toString()).show();
			}
			LOG.info("> CLICK: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			DatasetReference referenceItem = new DatasetReference(context, element);
			chart.fireEvent(new DatasetSelectionEvent(DOMBuilder.get().createChangeEvent(), chart, referenceItem));
			return true;
		}
	}

}