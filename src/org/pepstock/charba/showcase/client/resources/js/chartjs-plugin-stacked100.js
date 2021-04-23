(function(Chart) {
  var isObject = function(obj) {
    var type = typeof obj;
    return type === 'object' && !!obj;
  }

  var dataValue = function(dataPoint, isHorizontal) {
    if (isObject(dataPoint)) {
      return isHorizontal ? dataPoint.x : dataPoint.y;
    }
    return dataPoint;
  }

  var cloneArray = function(srcAry) {
    var dstAry = [];
    var length = srcAry.length;

    for (var i = 0; i < length; i++) {
      dstAry.push(srcAry[i]);
    }
    return dstAry;
  };

  var setOriginalData = function(data) {
    data.originalData = data.datasets.map(function(dataset) {
      return cloneArray(dataset.data);
    });
  };

  // set calculated rate (xx%) to data.calculatedData
  var calculateRate = function(chart, isHorizontal, precision) {
    const data = chart.data;
	let i = 0;
    var visibles = data.datasets.map(function(dataset) {
	  return chart.isDatasetVisible(i++);	
    });
    
    var datasetDataLength = 0;
    if (data && data.datasets && data.datasets[0] && data.datasets[0].data) {
      datasetDataLength = data.datasets[0].data.length;
    }
    var totals = Array.apply(null, new Array(datasetDataLength)).map(function(el, i) {
      return data.datasets.reduce(function(sum, dataset, j) {
        var key = 'stack';
        if (!sum[key]) sum[key] = 0;
        sum[key] += dataValue(dataset.data[i], isHorizontal) * visibles[j];
        return sum;
      }, {});
    });

    data.calculatedData = data.datasets.map(function(dataset, i) {
      return dataset.data.map(function(val, i) {
        var total = totals[i].stack;
        var dv = dataValue(val, isHorizontal);
        return dv && total ? round(dv / total, precision) : 0;
      });
    });
  };

  var getPrecision = function(pluginOptions) {
    // return the (validated) configured precision from pluginOptions or default 1
    var defaultPrecision = 1;
    if (!pluginOptions.hasOwnProperty('precision')) return defaultPrecision;
    if (!pluginOptions.precision) return defaultPrecision;
    var optionsPrecision = Math.floor(pluginOptions.precision);
    if (isNaN(optionsPrecision)) return defaultPrecision;
    if (optionsPrecision < 0 || optionsPrecision > 16) return defaultPrecision; 
    return optionsPrecision;
  };

  var round = function(value, precision) {
    var multiplicator = Math.pow(10, precision);
    return Math.round(value * 100 * multiplicator) / multiplicator;
  };

  var tooltipLabel = function(isHorizontal) {
    return function(context) {
	  var data = context.chart.data;
      var datasetIndex = context.datasetIndex;
      var index = context.dataIndex;
      var datasetLabel = context.label || '';
      var originalValue = data.originalData[datasetIndex][index];
      var rateValue = data.calculatedData[datasetIndex][index];

      return '' + datasetLabel + ': ' + rateValue + '% (' + dataValue(originalValue, isHorizontal) + ')';
    }
  };

  var reflectData = function(srcData, datasets) {
    if (!srcData) return;
	
    srcData.forEach(function(data, i) {
	  datasets[i].data = data;
    });
  };

  var isHorizontalChart = function(chart) {
    return chart.config.type === 'bar' && chart.options.indexAxis === 'y';
  }

  var Stacked100Plugin = {
    id: 'stacked100',

    beforeInit: function(chart, args, options) {
      if (!options.enable) return;

      var isVertical = (chart.config.type === 'bar' && chart.options.indexAxis === 'x') || chart.config.type === 'line';

      Object.keys(chart.options.scales).forEach(key => {
        const value = chart.options.scales[key];
        if (isObject(value)) {
          value.stacked = true;
		  const applyLimit = (isVertical && value.axis === 'y') || (!isVertical && value.axis === 'x');
		  if (applyLimit){
            if (!value.min) value.min = 0;
            if (!value.max) value.max = 100;
		  }
        }
      });

      // Replace tooltips
      if (options.hasOwnProperty('replaceTooltipLabel') && !options.replaceTooltipLabel) return;
      chart.options.plugins.tooltip.callbacks.label = tooltipLabel(isHorizontalChart(chart));
    },

    beforeUpdate: function(chart, args, options) {
      if (!options.enable) return;

      setOriginalData(chart.data);
      var precision = getPrecision(options);
      calculateRate(chart, isHorizontalChart(chart), precision);
	  reflectData(chart.data.calculatedData, chart.data.datasets);
    },

    afterDraw: function(chart, args, options) {
      if (!options.enable) return;
      reflectData(chart.data.originalData, chart.data.datasets);
    }
  };

  Chart.register(Stacked100Plugin);
}.call(this, Chart));
