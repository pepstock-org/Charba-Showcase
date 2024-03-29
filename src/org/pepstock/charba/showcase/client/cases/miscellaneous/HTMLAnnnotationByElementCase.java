package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.dom.DOMBuilder;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.dom.elements.Div;
import org.pepstock.charba.client.dom.elements.Img;
import org.pepstock.charba.client.dom.events.NativeAbstractMouseEvent;
import org.pepstock.charba.client.enums.AxisPosition;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.events.ChartClickEvent;
import org.pepstock.charba.client.events.ChartClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.BarElement;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.utils.AnnotationBuilder;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HTMLAnnnotationByElementCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HTMLAnnnotationByElementCase> {
	}

	@UiField
	BarChartWidget chart;

	BarDataset dataset;

	double imgX = 0;

	double imgY = 0;

	Img imgElement = null;

	public HTMLAnnnotationByElementCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTitle().setText("HTML annotation by HTML element on bar chart");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(true);
		chart.getOptions().getLayout().getPadding().setTop(100);

		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart);
		axis1.setPosition(AxisPosition.LEFT);
		axis1.setDisplay(true);
		axis1.setBeginAtZero(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Percentage");

		chart.getOptions().getScales().setAxes(axis1);

		dataset = chart.newDataset();
		dataset.setLabel("Humidity");

		dataset.setData(getRandomDigits(months, 0, 75));

		IsColor[] colors = new IsColor[months];
		for (int i = 0; i < months; i++) {
			if (i == 3) {
				dataset.getData().set(3, getRandomDigits(1, 85, 100)[0]);
				colors[i] = HtmlColor.RED;
			} else {
				colors[i] = HtmlColor.TURQUOISE;
			}
		}
		dataset.setBackgroundColor(colors);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		chart.addHandler(new ChartClickEventHandler() {

			@Override
			public void onClick(ChartClickEvent clickEvent) {
				NativeAbstractMouseEvent event = (NativeAbstractMouseEvent) clickEvent.getNativeEvent();

				boolean isX = event.getLayerX() >= imgX && event.getLayerX() <= (imgX + imgElement.getWidth());
				boolean isY = event.getLayerY() >= imgY && event.getLayerY() <= (imgY + imgElement.getHeight());
				if (isX && isY) {
					new Toast("Annotation Selected!", "Annotation Selected!").show();
				}

			}
		}, ChartClickEvent.TYPE);

		chart.getPlugins().add(new AbstractPlugin("raster") {

			private static final String ANNOTATION = "<div style=\"border: 1px solid; border-color: rgba(255, 29, 29); padding: 6px; -moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px; background: rgba(255, 137, 137); color: black; -webkit-box-shadow: 0 2px 4px rgba(0,0,0,0.2); box-shadow: 0 2px 4px rgba(0,0,0,0.2);\">"
					+ "			  <div class=\"popupContent\">" + "			    <table cellspacing=\"2\" cellpadding=\"0\">" + "				  <tbody>" + "				    <tr>"
					+ "					  <td style=\"vertical-align: top;\" align=\"left\">" + "					    <div style=\"font-size: 16px; font-weight: bold;\">Issue 26</div>" + "					  </td>" + "					</tr>"
					+ "					<tr>" + "					  <td style=\"vertical-align: top;\" align=\"left\">" + "					    <div>This is a description of issue 26</div>" + "				      </td>" + "					</tr>"
					+ "			      </tbody>" + "				</table>" + "			  </div>" + "			</div>";

			@Override
			public void onAfterDraw(IsChart chart) {
				final Context2dItem ctx = chart.getCanvas().getContext2d();

				DatasetItem item = chart.getDatasetItem(0);
				BarElement elem = (BarElement) item.getElements().get(3);
				Div tempDiv = DOMBuilder.get().createDivElement();
				tempDiv.setInnerHTML(ANNOTATION);
				Img img = AnnotationBuilder.build(tempDiv, 300, 64);

				double x = elem.getX() - (elem.getWidth() / 2);
				double y = elem.getY() - img.getHeight() - 10;

				ctx.drawImage(img, x, y);

				HTMLAnnnotationByElementCase.this.imgElement = img;
				HTMLAnnnotationByElementCase.this.imgX = x;
				HTMLAnnnotationByElementCase.this.imgY = y;
			}

		});

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {

		dataset.setData(getRandomDigits(months, 0, 75));

		IsColor[] colors = new IsColor[months];
		for (int i = 0; i < months; i++) {
			if (i == 3) {
				dataset.getData().set(3, getRandomDigits(1, 85, 100)[0]);
				colors[i] = HtmlColor.RED;
			} else {
				colors[i] = HtmlColor.TURQUOISE;
			}
		}
		dataset.setBackgroundColor(colors);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
