package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.client.Charba;
import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.DeferredCharba;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.impl.charts.GaugeChart;
import org.pepstock.charba.client.impl.charts.MeterChart;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.InjectableTextResource;
import org.pepstock.charba.client.utils.Console;
import org.pepstock.charba.client.zoom.ZoomPlugin;
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
		Image.prefetch(Images.INSTANCE.cache().getSafeUri());
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

		Console.log(Defaults.get().getGlobal());	
		
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
		dataLabelsOption.store();

		RootPanel.get().add(new MainView());
		
	}

}