package org.pepstock.charba.showcase.client;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.Charba;
import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.DeferredCharba;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.events.ClickEventHandler;
import org.pepstock.charba.client.events.ChartEventContext;
import org.pepstock.charba.client.geo.Feature;
import org.pepstock.charba.client.geo.GeoUtils;
import org.pepstock.charba.client.geo.TopoJson;
import org.pepstock.charba.client.impl.charts.GaugeChart;
import org.pepstock.charba.client.impl.charts.MeterChart;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.InjectableTextResource;
import org.pepstock.charba.client.utils.CScheduler;
import org.pepstock.charba.client.utils.toast.Toaster;
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

	public static final String GALLERY_PARAM = "gallery";
	
	public static final String LOADING_PARAM = "loading";

	public static final String LOADING_EMBEDDED = "embedded";

	public static final String LOADING_DEFERRED = "deferred";

	public static final Logger LOG = Logger.getLogger("charba-showcase");

	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/blob/4.2/src/";

	public static boolean isDeferred = false;
	
	public static List<Feature> EARTH_FEATURES;

	public static TopoJson US;

	public static TopoJson EUROPE;

	public static TopoJson ITALY;

	public static TopoJson GERMANY;
	
	private String gallery = null;

	public void onModuleLoad() {

		Image.prefetch(Images.INSTANCE.background().getSafeUri());
		Image.prefetch(Images.INSTANCE.pattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.patternHover().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagIT().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagFR().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagDE().getSafeUri());
		Image.prefetch(Images.INSTANCE.cache().getSafeUri());
		Image.prefetch(Images.INSTANCE.extensionWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.fingerprintWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.headlineWhite().getSafeUri());
		Image.prefetch(Images.INSTANCE.visibilityWhite().getSafeUri());

		String loadingParam = Window.Location.getParameter(LOADING_PARAM) != null ? Window.Location.getParameter(LOADING_PARAM) : LOADING_EMBEDDED;
		String loading = !loadingParam.equalsIgnoreCase(LOADING_DEFERRED) && !loadingParam.equalsIgnoreCase(LOADING_EMBEDDED) ? LOADING_EMBEDDED : loadingParam;

		gallery = Window.Location.getParameter(GALLERY_PARAM) != null ? Window.Location.getParameter(GALLERY_PARAM) : null;
		
		if (loading.equals(LOADING_DEFERRED)) {
			isDeferred = true;
			deferredLoading();
		} else {
			isDeferred = false;
			embeddedLoading();
		}
	}

	private void embeddedLoading() {
		Charba.enable();
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
							DeferredCharba.enable(() -> {
								RootPanel.get().remove(trigger);
								start();
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

		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getOptions(ChartType.LINE).getElements().getLine().setTension(0.4D);

		Defaults.get().getOptions(ChartType.PIE).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.POLAR_AREA).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.RADAR).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.DOUGHNUT).setAspectRatio(2D);
		Defaults.get().getOptions(MeterChart.CONTROLLER_TYPE).setAspectRatio(2D);
		Defaults.get().getOptions(GaugeChart.CONTROLLER_TYPE).setAspectRatio(2D);
		
		MyHorizontalBarController.TYPE.register();

		MyLineChart.TYPE.register();

		LabelsPlugin.enable();

		DataLabelsPlugin.enable();

		ZoomPlugin.enable();
		
		AnnotationPlugin.enable();

		Injector.ensureCssInjected(new InjectableTextResource(MyResources.INSTANCE.legend()));
		
		DataLabelsOptions dataLabelsOption = new DataLabelsOptions();
		dataLabelsOption.getPadding().set(4);
		dataLabelsOption.getListeners().setClickEventHandler(new ClickEventHandler() {
			
			@Override
			public boolean onClick(DataLabelsContext context, ChartEventContext event) {
				new Toast("Click!", "Test on DATALABELS click").show();
				return true;
			}
		});
		dataLabelsOption.store();
		
		Toaster.get().getDefaults().setTimeout(3000);
		Toaster.get().setMaxHistoryItems(10);
		
		MainView view = new MainView();

		RootPanel.get().add(view);

		CScheduler.get().submit(new Runnable() {
			
			@Override
			public void run() {
				Charba_Showcase.EARTH_FEATURES = GeoUtils.features(MyResources.INSTANCE.topojsonEarth().getText(), "countries");
				Charba_Showcase.US = GeoUtils.createTopoJson(MyResources.INSTANCE.topojsonUS().getText());
				Charba_Showcase.EUROPE = GeoUtils.createTopoJson(MyResources.INSTANCE.topojsonEurope().getText());
				Charba_Showcase.ITALY = GeoUtils.createTopoJson(MyResources.INSTANCE.topojsonItaly().getText());
				Charba_Showcase.GERMANY = GeoUtils.createTopoJson(MyResources.INSTANCE.topojsonGermany().getText());

				view.setGallery(gallery);
			}
		});
		
	}

}