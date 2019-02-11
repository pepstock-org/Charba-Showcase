package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TooltipCustomCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.TooltipPosition;
import org.pepstock.charba.client.items.TooltipBodyItem;
import org.pepstock.charba.client.items.TooltipLabelColor;
import org.pepstock.charba.client.items.TooltipModel;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class TooltipHTMLlineView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipHTMLlineView> {
	}

	@UiField
	LineChart chart;
	
	public TooltipHTMLlineView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Custom info in tooltip");
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getTooltips().setPosition(TooltipPosition.nearest);
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setCustomCallback(new TooltipCustomCallback() {
			
			private DivElement element = null;
			
			@Override
			public void onCustom(AbstractChart<?, ?> chart, TooltipModel model) {
				// Hide if no tooltip
				if (model.getOpacity() == 0){
					element.getStyle().setOpacity(0);
					return;
				}
				if (element == null){
					element = Document.get().createDivElement();
					chart.getElement().appendChild(element);
				}
				// Set caret Position
				element.removeClassName("above");
				element.removeClassName("below");
				element.removeClassName("no-transform");
				if (model.getYAlign() != null){
					element.addClassName(model.getYAlign());
				} else {
					element.addClassName("no-transform");
				}
				StringBuilder innerHTML = new StringBuilder("<table cellpadding=2>");
				// Set Text
				if (model.getBody() != null && !model.getBody().isEmpty()){
					innerHTML.append("<thead>");
					if (model.getTitle() != null && !model.getTitle().isEmpty()){
						for (String title : model.getTitle()){
							innerHTML.append("<tr><th style='font-size: 18px;'>").append(title).append("</th></tr>");
						}
					}
					innerHTML.append("</thead><tbody>");
					List<TooltipLabelColor> colors = model.getLabelColors();
					int index = 0;
					for (TooltipBodyItem item : model.getBody()){
						List<String> lines = item.getLines();
						for (int i=0; i<lines.size(); i++){
							TooltipLabelColor color = colors.get(index);
							DivElement wrapper = Document.get().createDivElement();
							SpanElement span = Document.get().createSpanElement();
							span.getStyle().setDisplay(Display.INLINE_BLOCK);
							span.getStyle().setWidth(10, Unit.PX);
							span.getStyle().setHeight(10, Unit.PX);
							span.getStyle().setMarginRight(10, Unit.PX);
							span.getStyle().setBackgroundColor(color.getBackgroundColor().toRGBA());
							span.getStyle().setBorderColor(color.getBorderColor().toRGBA());
							span.getStyle().setBorderStyle(BorderStyle.SOLID);
							span.getStyle().setBorderWidth(2, Unit.PX);
							wrapper.appendChild(span);
							innerHTML.append("<tr><td style='white-space: nowrap;'>").append(wrapper.getInnerHTML()).append(lines.get(i)).append("</td></tr>");
						}
						index++;
					}
					innerHTML.append("</tbody>");
				}
				innerHTML.append("</table>");
				element.setInnerHTML(innerHTML.toString());
				element.getStyle().setLeft(model.getCaretX(), Unit.PX);
				element.getStyle().setTop(model.getCaretY(), Unit.PX);
				element.getStyle().setFontSize(model.getBodyFontSize(), Unit.PX);
				element.getStyle().setPaddingLeft(model.getXPadding(), Unit.PX);
				element.getStyle().setPaddingTop(model.getYPadding(), Unit.PX);

				element.getStyle().setOpacity(1);
				element.getStyle().setBackgroundColor("rgba(0, 0, 0, .7)");
				element.getStyle().setPosition(com.google.gwt.dom.client.Style.Position.ABSOLUTE);
				element.getStyle().setColor("white");
				element.getStyle().setProperty("borderRadius", "3px");
				element.getStyle().setProperty("WebkitTransition", "all .1s ease");
				element.getStyle().setProperty("transition", "all .1s ease");
				element.getStyle().setProperty("pointerEvents", "none");
				element.getStyle().setProperty("WebkitTransform", "translate(-50%, 0)");
				element.getStyle().setProperty("transform", "translate(-50%, 0)");
			}
		});
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.nofill);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

}
