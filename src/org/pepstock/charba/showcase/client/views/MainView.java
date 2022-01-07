package org.pepstock.charba.showcase.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MainView> {
	}

	@UiField
	VerticalPanel content;

	@UiField
	Label labelCharts;
	
	HorizontalPanel currentPanel = null;
	
	ChartsView charts = null;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		content.add(new HomeView());
	}

	private void clearPreviousChart() {
		int count = content.getWidgetCount();
		for (int i = 0; i < count; i++) {
			content.remove(i);
		}
	}

	private boolean changeSelection(ClickEvent event) {
		if (event.getSource() instanceof Widget) {
			Widget w = (Widget) event.getSource();
			if (w.getParent() instanceof HorizontalPanel) {
				if (currentPanel != null) {
					currentPanel.getElement().getStyle().setBorderColor("rgba(0,0,0,0)");
				}
				currentPanel = (HorizontalPanel) w.getParent();
				currentPanel.getElement().getStyle().setBorderColor("#d0d0d0");
				return true;
			}
		}
		return false;
	}

	@UiHandler("title")
	protected void handleHome(ClickEvent event) {
		if (currentPanel != null) {
			currentPanel.getElement().getStyle().setBorderColor("rgba(0,0,0,0)");
			currentPanel = null;
		}
		clearPreviousChart();
		content.add(new HomeView());
	}

	@UiHandler(value = { "labelCharts", "imgCharts" })
	protected void handleCharts(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			charts = new ChartsView(content);
			content.add(charts);
		}
	}

	@UiHandler(value = { "labelExtPlugins", "imgExtPlugins" })
	protected void handleExtPlugins(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ExtPluginsView(content));
		}
	}

	@UiHandler(value = { "labelExtCharts", "imgExtCharts" })
	protected void handleExtCharts(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ExtChartsView(content));
		}
	}

	@UiHandler(value = { "labelElements", "imgElements" })
	protected void handleElements(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ElementsView(content));
		}
	}

	@UiHandler(value = { "labelColoring", "imgColoring" })
	protected void handleColoring(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ColoringView(content));
		}
	}

	@UiHandler(value = { "labelPlugins", "imgPlugins" })
	protected void handlePlugins(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new PluginsView(content));
		}
	}

	@UiHandler(value = { "labelMiscellaneous", "imgMiscellaneous" })
	protected void handleMiscellaneous(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new MiscellaneousView(content));
		}
	}
	
	public void setGallery(String gallery) {
		if (gallery != null) {
			NativeEvent event = Document.get().createClickEvent(labelCharts.getAbsoluteLeft(), labelCharts.getOffsetWidth(), labelCharts.getOffsetHeight(), labelCharts.getAbsoluteTop(), labelCharts.getAbsoluteLeft(), true, true, 
					true, labelCharts.isVisible());
			DomEvent.fireNativeEvent(event, labelCharts);
			charts.setGallery(gallery);
		}
	}

}
