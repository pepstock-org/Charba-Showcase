package org.pepstock.charba.showcase.client;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.controllers.StyleElement;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.defaults.IsDefaultNumberFormatOptions;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.enums.DefaultDateAdapter;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.intl.CLocale;
import org.pepstock.charba.client.intl.CLocaleBuilder;
import org.pepstock.charba.client.intl.FormatPart;
import org.pepstock.charba.client.intl.NumberFormat;
import org.pepstock.charba.client.intl.NumberFormatOptions;
import org.pepstock.charba.client.intl.Script;
import org.pepstock.charba.client.intl.enums.Currency;
import org.pepstock.charba.client.intl.enums.CurrencyDisplay;
import org.pepstock.charba.client.intl.enums.Style;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
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

	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/blob/3.2/src/";

	public static boolean isDeferred = false;

	public static DefaultDateAdapter dateAdapterTyoe = DefaultDateAdapter.UNKNOWN;

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

		DefaultDateAdapter adapter = Key.getKeyByValue(DefaultDateAdapter.values(), Window.Location.getParameter(DATE_ADAPTER_PARAM), DefaultDateAdapter.MOMENT);
		Charba_Showcase.dateAdapterTyoe = adapter;
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

		org.pepstock.charba.client.utils.Window.enableResizeOnBeforePrint();

		Defaults.get().getGlobal().getFont().setFamily("'Lato', sans-serif");

		Defaults.get().getGlobal().getTitle().getFont().setSize(16);
		Defaults.get().getGlobal().getElements().getLine().setTension(0.4D);

		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getOptions(ChartType.PIE).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.POLAR_AREA).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.RADAR).setAspectRatio(2D);
		Defaults.get().getOptions(ChartType.DOUGHNUT).setAspectRatio(2D);

		Defaults.get().getControllers().register(new AbstractController() {

			@Override
			public ControllerType getType() {
				return MyLineChart.TYPE;
			}

			@Override
			public void setHoverStyle(ControllerContext context, IsChart chart, StyleElement element, int datasetIndex, int index) {
				super.setHoverStyle(context, chart, element, datasetIndex, index);
			}

			@Override
			public void draw(ControllerContext jsThis, IsChart chart) {
				super.draw(jsThis, chart);

				DatasetMetaItem metaItem = chart.getDatasetMeta(jsThis.getIndex());
				List<DatasetItem> items = metaItem.getDatasets();
				for (DatasetItem item : items) {
					Context2dItem ctx = chart.getCanvas().getContext2d();
					ctx.save();
					ctx.setStrokeColor(item.getOptions().getBorderColorAsString());
					ctx.setLineWidth(1D);
					ctx.strokeRect(item.getX() - 10, item.getY() - 10, 20, 20);
					ctx.restore();
				}
			}
		});

		Defaults.get().getControllers().register(new MyHorizontalBarController());

		LabelsPlugin.enable();
		//
		DataLabelsPlugin.enable();
		//
		// ZoomPlugin.enable();

		// AnnotationOptions options = new AnnotationOptions();
		//
		// BoxAnnotation box = new BoxAnnotation("stock");
		// box.setEnabled(false);
		// // box.setName("BoxAnnotation");
		// box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		//// box.setXScaleID(DefaultScaleId.X.value());
		//// box.setYScaleID(DefaultScaleId.Y.value());
		////
		//// DateAdapter adapter = new DateAdapter();
		//// box.setXMin(adapter.add(new Date(), 5, TimeUnit.DAY));
		//// box.setXMax(adapter.add(new Date(), 15, TimeUnit.DAY));
		//// box.setYMax(100);
		//// box.setYMin(60);
		//// box.setBackgroundColor("rgba(101, 33, 171, 0.5)");
		//// box.setBorderColor("rgb(101, 33, 171)");
		//// box.setBorderWidth(1);
		//
		// options.setAnnotations(box);
		//
		// Defaults.get().getGlobal().getPlugins().setOptions(Annotation.ID, options);

		Injector.ensureCssInjected(new InjectableTextResource(MyResources.INSTANCE.legend()));

		RootPanel.get().add(new MainView());

		LOG.info(CLocaleBuilder.build("en").toString());

		LOG.info(CLocaleBuilder.build("en-US-variant").toString());

		LOG.info(CLocaleBuilder.build("en-"+Script.AHOM+"-US-variant").toString());

		doIntlNumberFormatTest(CLocale.getDefault());

	}

	private void doIntlNumberFormatTest(CLocale locale) {
		final double value = 1234567890D;

		NumberFormatOptions formatOptions = new NumberFormatOptions();
		// formatOptions.setStyle(Style.UNIT);
		// formatOptions.setUnitsOfMeasure(MeasureUnit.KILOMETER, MeasureUnit.HOUR);
		// formatOptions.setUnitOfMeasureDisplay(MeasureUnitDisplay.LONG);

		formatOptions.setStyle(Style.CURRENCY);
		formatOptions.setCurrency(Currency.AZERBAIJAN_MANAT);
		formatOptions.setCurrencyDisplay(CurrencyDisplay.SYMBOL);

		NumberFormat format = new NumberFormat(locale, formatOptions);

		LOG.info("-------- " + locale.getIdentifier() + "--------");
		LOG.info("Formatted value: " + format.format(value));

		LOG.info("Format parts:");
		List<FormatPart> parts = format.formatToParts(value);
		for (FormatPart part : parts) {
			LOG.info(" - Type: " + part.getType() + ", value: " + part.getValue());
		}
		LOG.info("Resolved options:");

		IsDefaultNumberFormatOptions options = format.resolvedOptions();

		LOG.info(" - Class: " + options.getClass());
		LOG.info(" - CompactDisplay: " + options.getCompactDisplay());
		LOG.info(" - Currency: " + options.getCurrency());
		LOG.info(" - CurrencyDisplay: " + options.getCurrencyDisplay());
		LOG.info(" - CurrencySign: " + options.getCurrencySign());
		LOG.info(" - LocaleMatcher: " + options.getLocaleMatcher());
		LOG.info(" - MaximumFractionDigits: " + options.getMaximumFractionDigits());
		LOG.info(" - MaximumSignificantDigits: " + options.getMaximumSignificantDigits());
		LOG.info(" - MinimumFractionDigits: " + options.getMinimumFractionDigits());
		LOG.info(" - MinimumIntegerDigits: " + options.getMinimumIntegerDigits());
		LOG.info(" - MinimumSignificantDigits: " + options.getMinimumSignificantDigits());
		LOG.info(" - Notation: " + options.getNotation());
		LOG.info(" - NumberingSystem: " + options.getNumberingSystem());
		LOG.info(" - SignDisplay: " + options.getSignDisplay());
		LOG.info(" - Style: " + options.getStyle());
		LOG.info(" - UnitOfMeasureDisplay: " + options.getUnitOfMeasureDisplay());
		LOG.info(" - UnitsOfMeasure: " + options.getUnitsOfMeasure());

		LOG.info("-------------------------------------------");

	}

}