package org.pepstock.charba.showcase.client;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.enums.DefaultDateAdapter;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.items.DatasetViewItem;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.AbstractDeferredResources;
import org.pepstock.charba.client.resources.AbstractEmbeddedResources;
import org.pepstock.charba.client.resources.DatefnsDeferredResources;
import org.pepstock.charba.client.resources.DatefnsEmbeddedResources;
import org.pepstock.charba.client.resources.DeferredResources;
import org.pepstock.charba.client.resources.EmbeddedResources;
import org.pepstock.charba.client.resources.EntryPointStarter;
import org.pepstock.charba.client.resources.InjectableTextResource;
import org.pepstock.charba.client.resources.LuxonDeferredResources;
import org.pepstock.charba.client.resources.LuxonEmbeddedResources;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.client.utils.JsWindowHelper;
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

	public static final String DATE_ADAPTER_PARAM = "dateAdapter";

	public static final String LOADING_PARAM = "loading";

	public static final String LOADING_EMBEDDED = "embedded";

	public static final String LOADING_DEFERRED = "deferred";

	public static final Logger LOG = Logger.getLogger("charba-showcase");

	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/tree/2.8/src/";

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

		DefaultDateAdapter adapter = Key.getKeyByValue(DefaultDateAdapter.class, Window.Location.getParameter(DATE_ADAPTER_PARAM), DefaultDateAdapter.MOMENT);
		String loadingParam = Window.Location.getParameter(LOADING_PARAM) != null ? Window.Location.getParameter(LOADING_PARAM) : LOADING_EMBEDDED;
		String loading = !loadingParam.equalsIgnoreCase(LOADING_DEFERRED) && !loadingParam.equalsIgnoreCase(LOADING_EMBEDDED) ? LOADING_EMBEDDED : loadingParam;

		if (loading.equals(LOADING_DEFERRED)) {
			isDeferred = true;
			deferredLoading(adapter);
		} else {
			isDeferred = false;
			embeddedLoading(adapter);
		}
	}

	private void embeddedLoading(DefaultDateAdapter adapter) {
		AbstractEmbeddedResources resources = null;
		if (DefaultDateAdapter.MOMENT.equals(adapter)) {
			resources = EmbeddedResources.INSTANCE;
		} else if (DefaultDateAdapter.LUXON.equals(adapter)) {
			resources = LuxonEmbeddedResources.INSTANCE;
		} else if (DefaultDateAdapter.DATE_FNS.equals(adapter)) {
			resources = DatefnsEmbeddedResources.INSTANCE;
		}
		ResourcesType.setClientBundle(resources);
		start();

	}

	private void deferredLoading(DefaultDateAdapter adapter) {
		final AbstractDeferredResources resources;
		if (DefaultDateAdapter.LUXON.equals(adapter)) {
			resources = LuxonDeferredResources.INSTANCE;
		} else if (DefaultDateAdapter.DATE_FNS.equals(adapter)) {
			resources = DatefnsDeferredResources.INSTANCE;
		} else {
			resources = DeferredResources.INSTANCE;
		}
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
							EntryPointStarter.run(resources, new Runnable() {

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

		JsWindowHelper.get().enableResizeOnBeforePrint();

		Defaults.get().getGlobal().setDefaultFontFamily("'Lato', sans-serif");

		Defaults.get().getGlobal().getTitle().setFontSize(16);

		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getControllers().extend(new AbstractController() {

			@Override
			public ControllerType getType() {
				return MyLineChart.TYPE;
			}

			@Override
			public void draw(ControllerContext jsThis, IsChart chart, double ease) {
				super.draw(jsThis, chart, ease);

				DatasetMetaItem metaItem = chart.getDatasetMeta(jsThis.getIndex());
				List<DatasetItem> items = metaItem.getDatasets();
				for (DatasetItem item : items) {
					DatasetViewItem view = item.getView();
					Context2dItem ctx = chart.getCanvas().getContext2d();
					ctx.save();
					ctx.setStrokeColor(view.getBorderColorAsString());
					ctx.setLineWidth(1D);
					ctx.strokeRect(view.getX() - 10, view.getY() - 10, 20, 20);
					ctx.restore();
				}
			}
		});

		Defaults.get().getControllers().extend(new MyHorizontalBarController());

		LabelsPlugin.enable();

		DataLabelsPlugin.enable();

		ZoomPlugin.enable();

		AnnotationPlugin.enable();

		Injector.ensureCssInjected(new InjectableTextResource(MyResources.INSTANCE.legend()));

		RootPanel.get().add(new MainView());
	}
}