package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.ChartClickEvent;
import org.pepstock.charba.client.events.ChartClickEventHandler;
import org.pepstock.charba.client.events.ChartNativeEvent;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.utils.AnnotationBuilder;
import org.pepstock.charba.showcase.client.samples.Toast;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class HTMLAnnnotationByElementView extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HTMLAnnnotationByElementView> {
	}

	@UiField
	BarChart chart;
	
	BarDataset dataset;
	
	boolean useElement = true;
	
	double imgX = 0;
	
	double imgY = 0;
	
	ImageElement imgElement = null;
	
	public HTMLAnnnotationByElementView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTitle().setText("Charba Raster Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(true);
		chart.getOptions().getLayout().getPadding().setTop(100);

		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart);
		axis1.setPosition(Position.left);
		axis1.setDisplay(true);
		axis1.getTicks().setBeginAtZero(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Percentage");

		chart.getOptions().getScales().setYAxes(axis1);

		dataset = chart.newDataset();
		dataset.setType(ChartType.bar);
		dataset.setLabel("Humidity");

		dataset.setData(getRandomDigits(months, 0, 75));
		
		IsColor[] colors = new IsColor[months];
		for (int i=0; i<months; i++) {
			if (i == 3) {
				dataset.getData().set(3, getRandomDigits(1, 85, 100)[0]);
				colors[i] = HtmlColor.Red;
			} else {
				colors[i] = HtmlColor.Turquoise;
			}
		}
		dataset.setBackgroundColor(colors);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		
		chart.addHandler(new ChartClickEventHandler() {
			
			@Override
			public void onClick(ChartClickEvent clickEvent) {
				ChartNativeEvent event = (ChartNativeEvent)clickEvent.getNativeEvent();

				boolean isX = event.getLayerX() >= imgX && event.getLayerX() <= (imgX+imgElement.getWidth());
				boolean isY = event.getLayerY() >= imgY  && event.getLayerY() <= (imgY+imgElement.getHeight());
				if (isX && isY){
					new Toast("Annotation Selected!", "Annotation Selected!").show();
				}
				
			}
		}, ChartClickEvent.TYPE);

		chart.getPlugins().add(new AbstractPlugin() {
			
			private static final String ANNOTATION = "<div style=\"border: 1px solid; border-color: rgba(255, 29, 29); padding: 6px; -moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px; background: rgba(255, 137, 137); color: black; -webkit-box-shadow: 0 2px 4px rgba(0,0,0,0.2); box-shadow: 0 2px 4px rgba(0,0,0,0.2);\">" + 
					"			  <div class=\"popupContent\">" + 
					"			    <table cellspacing=\"2\" cellpadding=\"0\">" + 
					"				  <tbody>" + 
					"				    <tr>" + 
					"					  <td style=\"vertical-align: top;\" align=\"left\">" + 
					"					    <div style=\"font-size: 16px; font-weight: bold;\">Issue 26</div>" + 
					"					  </td>" + 
					"					</tr>" + 
					"					<tr>" + 
					"					  <td style=\"vertical-align: top;\" align=\"left\">" + 
					"					    <div>This is a description of issue 26</div>" + 
					"				      </td>" + 
					"					</tr>" + 
					"			      </tbody>" + 
					"				</table>" + 
					"			  </div>" + 
					"			</div>";		
			
			final Toast toast = new Toast("Issue 26", "This is a description of issue 26");
			
			@Override
			public String getId() {
				return "raster";
			}

			@Override
			public void onAfterDraw(AbstractChart<?, ?> chart, double easing) {
				Element element = toast.getElement();
		
				final Context2d ctx = chart.getCanvas().getContext2d();

				DatasetMetaItem meta = chart.getDatasetMeta(0);
				DatasetItem item = meta.getDatasets().get(3);
				
				ImageElement img;
				if (useElement) {
					img = AnnotationBuilder.build(element, 300, 48);
				} else {
					img = AnnotationBuilder.build(ANNOTATION, 300, 64);
				}
		
				double x = item.getView().getX() - (item.getView().getWidth()/2);
				double y = item.getView().getY() - img.getHeight() - 10;
				
				ctx.drawImage(img, x, y);
				
				HTMLAnnnotationByElementView.this.imgElement = img;
				HTMLAnnnotationByElementView.this.imgX = x;
				HTMLAnnnotationByElementView.this.imgY = y;
			}

		});

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {

		dataset.setData(getRandomDigits(months, 0, 75));

		IsColor[] colors = new IsColor[months];
		for (int i=0; i<months; i++) {
			if (i == 3) {
				dataset.getData().set(3, getRandomDigits(1, 85, 100)[0]);
				colors[i] = HtmlColor.Red;
			} else {
				colors[i] = HtmlColor.Turquoise;
			}
		}
		dataset.setBackgroundColor(colors);
		chart.update();
	}
	
	@UiHandler("useElement")
	protected void handleUseElement(ClickEvent event) {
		useElement = ((CheckBox) event.getSource()).getValue();
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
