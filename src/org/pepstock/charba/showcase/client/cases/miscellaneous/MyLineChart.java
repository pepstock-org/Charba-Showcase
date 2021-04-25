package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.Controller;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerProvider;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.items.DatasetElement;
import org.pepstock.charba.client.items.DatasetItem;

public class MyLineChart extends LineChart {

	public static final ControllerType TYPE = new ControllerType("stock", ChartType.LINE, new ControllerProvider() {
		
		@Override
		public Controller provide(ControllerType controllerType) {
			return new AbstractController() {

				@Override
				public ControllerType getType() {
					return MyLineChart.TYPE;
				}

				@Override
				public void draw(ControllerContext jsThis, IsChart chart) {
					super.draw(jsThis, chart);

					DatasetItem item = chart.getDatasetItem(jsThis.getIndex());
					List<DatasetElement> elements = item.getElements();
					for (DatasetElement elem : elements) {
						Context2dItem ctx = chart.getCanvas().getContext2d();
						ctx.save();
						ctx.setStrokeColor(elem.getOptions().getBorderColorAsString());
						ctx.setLineWidth(1D);
						ctx.strokeRect(elem.getX() - 10, elem.getY() - 10, 20, 20);
						ctx.restore();
					}
				}
			};
		}
	});

	public MyLineChart() {
		super(TYPE);
	}

	@Override
	public MyLineDataset newDataset() {
		return newDataset(false);
	}

	@Override
	public MyLineDataset newDataset(boolean hidden) {
		return new MyLineDataset();
	}

}
