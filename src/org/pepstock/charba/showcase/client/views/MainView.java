package org.pepstock.charba.showcase.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MainView> {
	}

	@UiField
	VerticalPanel content;

	HorizontalPanel currentPanel = null;

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
					currentPanel.getElement().getStyle().setBorderWidth(0, Unit.PX);
				}
				currentPanel = (HorizontalPanel) w.getParent();
				currentPanel.getElement().getStyle().setBorderColor("#d0d0d0");
				currentPanel.getElement().getStyle().setBorderWidth(2, Unit.PX);
				currentPanel.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
				return true;
			}
		}
		return false;
	}

	@UiHandler("title")
	protected void handleHome(ClickEvent event) {
		if (currentPanel != null) {
			currentPanel.getElement().getStyle().setBorderWidth(0, Unit.PX);
			currentPanel = null;
		}
		clearPreviousChart();
		content.add(new HomeView());
	}

	@UiHandler(value = { "labelCharts", "imgCharts" })
	protected void handleCharts(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ChartsView(content));
		}
	}

	@UiHandler(value = { "labelExtensions", "imgExtensions" })
	protected void handleExtensions(ClickEvent event) {
		if (changeSelection(event)) {
			clearPreviousChart();
			content.add(new ExtensionsView(content));
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

}
