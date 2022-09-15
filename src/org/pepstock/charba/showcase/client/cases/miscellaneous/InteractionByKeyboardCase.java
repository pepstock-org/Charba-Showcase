package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.CallbackProxy;
import org.pepstock.charba.client.commons.JsHelper;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.dom.BaseEventTarget.EventListenerCallback;
import org.pepstock.charba.client.dom.DOM;
import org.pepstock.charba.client.dom.DOMRectangle;
import org.pepstock.charba.client.dom.elements.Div;
import org.pepstock.charba.client.dom.enums.IsKeyboardKey;
import org.pepstock.charba.client.dom.enums.KeyboardEventType;
import org.pepstock.charba.client.dom.enums.KeyboardNavigationKey;
import org.pepstock.charba.client.dom.enums.KeyboardUiKey;
import org.pepstock.charba.client.dom.enums.KeyboardWhitespaceKey;
import org.pepstock.charba.client.dom.enums.MouseEventType;
import org.pepstock.charba.client.dom.events.KeyboardEventInit;
import org.pepstock.charba.client.dom.events.MouseEventInit;
import org.pepstock.charba.client.dom.events.NativeBaseEvent;
import org.pepstock.charba.client.dom.events.NativeKeyboardEvent;
import org.pepstock.charba.client.dom.events.NativeMouseEvent;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.ActiveDatasetElement;
import org.pepstock.charba.client.items.ChartElement;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.PluginEventArgument;
import org.pepstock.charba.client.plugins.SmartPlugin;
import org.pepstock.charba.client.plugins.hooks.BeforeDestroyHook;
import org.pepstock.charba.client.plugins.hooks.BeforeEventHook;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class InteractionByKeyboardCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, InteractionByKeyboardCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	Button randomize;
	
	@UiField
	HTMLPanel help;
	
	public InteractionByKeyboardCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Line chart");
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().setEvents(MouseEventType.CLICK, KeyboardEventType.KEY_UP);
		
		chart.addHandler(new DatasetSelectionEventHandler() {

			@Override
			public void onSelect(DatasetSelectionEvent event) {
				IsChart chart = (IsChart) event.getSource();
				Labels labels = chart.getData().getLabels();
				List<Dataset> datasets = chart.getData().getDatasets();
				if (datasets != null && !datasets.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append("Dataset index: <b>").append(event.getItem().getDatasetIndex()).append("</b><br>");
					sb.append("Dataset label: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getLabel()).append("</b><br>");
					sb.append("Dataset data: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getData().get(event.getItem().getIndex())).append("</b><br>");
					sb.append("Index: <b>").append(event.getItem().getIndex()).append("</b><br>");
					sb.append("Value: <b>").append(labels.getStrings(event.getItem().getIndex()).get(0)).append("</b><br>");
					new Toast("Dataset Selected!", sb.toString()).show();
				}
			
			}
		}, DatasetSelectionEvent.TYPE);
		
		List<Dataset> datasets = chart.getData().getDatasets(true);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setPointHoverBackgroundColor(HtmlColor.YELLOW);
		dataset1.setPointHoverRadius(20);
		
		double[] values = getRandomDigits(months);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);
		chart.getData().setLabels(getLabels());
		
		
		CallbackProxy<EventListenerCallback> keyboardCallbackProxy = JsHelper.get().newCallbackProxy();
		keyboardCallbackProxy.setCallback(new EventListenerCallback() {
			
			@Override
			public void call(NativeBaseEvent event) {
				event.preventDefault();
				if (event instanceof NativeKeyboardEvent && !event.getTarget().equals(chart.getCanvas())) {
					NativeKeyboardEvent newEvent = NativeKeyboardEvent.createKeyboardEvent(KeyboardEventType.KEY_UP, new KeyboardEventInit((NativeKeyboardEvent) event));
					chart.getCanvas().dispatchEvent(newEvent);
				}
			}
		});

		DOM.getDocument().addEventListener(KeyboardEventType.KEY_UP, keyboardCallbackProxy.getProxy());
		
		SmartPlugin plugin = new SmartPlugin("keyboard");
		plugin.setBeforeDestroyHook(new BeforeDestroyHook() {
			
			@Override
			public void onBeforeDestroy(IsChart chart) {
				DOM.getDocument().removeEventListener(KeyboardEventType.KEY_UP, keyboardCallbackProxy.getProxy());
			}
		});
		plugin.setBeforeEventHook(new BeforeEventHook() {
			
			@Override
			public boolean onBeforeEvent(IsChart chart, PluginEventArgument argument) {
				NativeBaseEvent event = argument.getEventContext().getNativeEvent();
				event.preventDefault();
				List<ActiveDatasetElement> activeElements = chart.getActiveElements();
				if (event instanceof NativeKeyboardEvent) {
					int dataSize = chart.getData().getDatasets().get(0).getData().size();
					if (KeyboardUiKey.ESCAPE.is(event)) {
						chart.resetActiveElements();
						chart.update();
					} else if (KeyboardNavigationKey.ARROW_RIGHT.is(event)) {
						int pos = currentActiveElementIndex(activeElements) + 1;
						int index = pos == dataSize ? 0 : pos;
						setActiveElement(chart, index);
					} else if (KeyboardNavigationKey.ARROW_LEFT.is(event)) {
						int pos = currentActiveElementIndex(activeElements) - 1;
						int index = pos < 0 ? dataSize - 1 : pos;
						setActiveElement(chart, index);
					} else if (KeyboardWhitespaceKey.ENTER.is(event) && !activeElements.isEmpty()) {
					    ActiveDatasetElement current = activeElements.get(0);
					    DatasetItem meta = chart.getDatasetItem(current.getDatasetIndex());
					    ChartElement data = meta.getElements().get(current.getIndex());
					    dispatchClick(chart, data);
					} else if (KeyboardWhitespaceKey.ENTER.is(event) && !activeElements.isEmpty()) {
					    ActiveDatasetElement current = activeElements.get(0);
					    DatasetItem meta = chart.getDatasetItem(current.getDatasetIndex());
					    ChartElement data = meta.getElements().get(current.getIndex());
					    dispatchClick(chart, data);
					}
				}
				return MouseEventType.CLICK.is(event);
			}
		});
		chart.getPlugins().add(plugin);
		
		Div aLeft = IsKeyboardKey.getElement(KeyboardNavigationKey.ARROW_LEFT);
		Div aRight = IsKeyboardKey.getElement(KeyboardNavigationKey.ARROW_RIGHT);
		String move = "Press " + aLeft.getInnerHTML() + " and " + aRight.getInnerHTML() + " to move on dataset elements;  ";
		Div aEnter = IsKeyboardKey.getElement(KeyboardWhitespaceKey.ENTER);
		String select = "Press " + aEnter.getInnerHTML() + " to select the highlighted dataset element;  ";
		Div aEsc = IsKeyboardKey.getElement(KeyboardUiKey.ESCAPE);
		String clean = "Press " + aEsc.getInnerHTML() + " to clean";

		HTML html = new HTML(move + select + clean);
		help.add(html);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
		randomize.getElement().blur();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private int currentActiveElementIndex(List<ActiveDatasetElement> elements) {
		if (!elements .isEmpty()) {
			ActiveDatasetElement current = elements.get(0);
			return current.getIndex();
		}
		return -1;
	}
	
	private void setActiveElement(IsChart chart, int index) {
		ActiveDatasetElement newElement = new ActiveDatasetElement(0, index);
		chart.setActiveElements(newElement);
		chart.update();
	}
	
	private void dispatchClick(IsChart chart, ChartElement element) {
		DOMRectangle rect = chart.getCanvas().getBoundingClientRect();
		double clientX = rect.getLeft() + element.getX();
		double clientY = rect.getTop() + element.getY();
		MouseEventInit init = new MouseEventInit(clientX, clientY);
		init.setCancelable(true);
		init.setBubbles(true);
		init.setRelatedTarget(chart.getCanvas());
		NativeMouseEvent event = NativeMouseEvent.createMouseEvent(MouseEventType.CLICK, init);
		chart.getCanvas().dispatchEvent(event);
	}
}
