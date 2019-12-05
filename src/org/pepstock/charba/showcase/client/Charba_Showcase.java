package org.pepstock.charba.showcase.client;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.items.DatasetViewItem;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.EmbeddedResources;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.client.utils.JsWindowHelper;
import org.pepstock.charba.showcase.client.cases.jsinterop.LineMyChart;
import org.pepstock.charba.showcase.client.cases.jsinterop.MyHorizontalBarController;
import org.pepstock.charba.showcase.client.resources.Images;
import org.pepstock.charba.showcase.client.resources.MyResources;
import org.pepstock.charba.showcase.client.views.MainView;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Charba_Showcase implements EntryPoint {
	
	public static final Logger LOG = Logger.getLogger("charba-showcase");
	
	public static final String BASE_URL = "https://github.com/pepstock-org/Charba-Showcase/tree/2.6/src/";

	public void onModuleLoad() {
		Image.prefetch(Images.INSTANCE.backgroundPattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.backgroundPattern1().getSafeUri());
		Image.prefetch(Images.INSTANCE.backgroundPattern2().getSafeUri());
		Image.prefetch(Images.INSTANCE.pattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.patternHover().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagIT().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagFR().getSafeUri());
		Image.prefetch(Images.INSTANCE.flagDE().getSafeUri());
		
		Image.prefetch(Images.INSTANCE.githubWhite().getSafeUri());

		ResourcesType.setClientBundle(EmbeddedResources.INSTANCE);

		JsWindowHelper.get().enableResizeOnBeforePrint();
		
		Defaults.get().getGlobal().getTitle().setFontSize(16);
		
		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getControllers().extend(new AbstractController() {

			@Override
			public ControllerType getType() {
				return LineMyChart.TYPE;
			}

			@Override
			public void draw(ControllerContext jsThis, IsChart chart, double ease) {
				super.draw(jsThis, chart, ease);

				DatasetMetaItem metaItem = chart.getDatasetMeta(jsThis.getIndex());
				List<DatasetItem> items = metaItem.getDatasets();
				for (DatasetItem item : items) {
					DatasetViewItem view = item.getView();
					Context2d ctx = chart.getCanvas().getContext2d();
					ctx.save();
					ctx.setStrokeStyle(view.getBorderColorAsString());
					ctx.setLineWidth(1D);
					ctx.strokeRect(view.getX() - 10, view.getY() - 10,  20, 20);
					ctx.restore();
				}
			}
		});

		Defaults.get().getControllers().extend(new MyHorizontalBarController());
		
		LabelsPlugin.enable();
		
		DataLabelsPlugin.enable();
		
		Injector.ensureInjected(ResourcesType.getClientBundle().chartJs());
		Injector.ensureInjected(ResourcesType.getClientBundle().charbaHelper());
		Injector.ensureInjected(MyResources.INSTANCE.chartJsAnnotationSource());
		
		Injector.ensureCssInjected(MyResources.INSTANCE.legend());


		//RootPanel.get().add(new DemoView());
		RootPanel.get().add(new MainView());
		
	}

}