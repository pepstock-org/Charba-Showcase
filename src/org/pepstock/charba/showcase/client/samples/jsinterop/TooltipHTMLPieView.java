package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.PieChart;
import org.pepstock.charba.client.callbacks.TooltipCustomCallback;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.items.TooltipBodyItem;
import org.pepstock.charba.client.items.TooltipLabelColor;
import org.pepstock.charba.client.items.TooltipModel;

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
public class TooltipHTMLPieView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipHTMLPieView> {
	}

	@UiField
	PieChart chart;

	public TooltipHTMLPieView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getTooltips().setCustomCallback(new TooltipCustomCallback() {
			
			private DivElement element = null;
			
			@Override
			public void onCustom(AbstractChart<?, ?> chart, TooltipModel model) {
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
				StringBuilder innerHTML = new StringBuilder("<table cellpadding=5>");
				// Set Text
				if (model.getBody() != null && !model.getBody().isEmpty()){
					if (model.getTitle() != null && !model.getTitle().isEmpty()){
						innerHTML.append("<thead>");
						for (String title : model.getTitle()){
							innerHTML.append("<tr><th>").append(title).append("</th></tr>");
						}
						innerHTML.append("<tbody>");
					}
					innerHTML.append("<tbody>");
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
							innerHTML.append("<tr><td style='white-space: nowrap; vertical-align: middle; font-size: 18px;'>").append(wrapper.getInnerHTML()).append(lines.get(i)).append("</td></tr>");
						}
						index++;
					}
					innerHTML.append("</tbody>");
				}
				innerHTML.append("</table>");
				element.setInnerHTML(innerHTML.toString());
				element.getStyle().setLeft(model.getCaretX(), Unit.PX);
				element.getStyle().setTop(model.getCaretY(), Unit.PX);
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

		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}
	
	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		datasets.add(dataset);
		
		chart.update();

	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12){
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets){
				PieDataset pds = (PieDataset)ds;
				pds.setBackgroundColor(getSequenceColors(months, 1));	
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleremoveData(ClickEvent event) {
		removeData(chart);
	}

}
