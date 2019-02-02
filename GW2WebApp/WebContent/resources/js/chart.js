var seriesOptions = [],
    seriesCounter = 0,
    names = ['Sell Price', 'Buy Price', 'Sell Quantity', 'Buy Quantity'],
	getParams = ['sellPrice', 'buyPrice', 'sellQuantity', 'buyQuantity'];

/**
 * Create the chart when all data is loaded
 * @returns {undefined}
 */
function createChart() {

  Highcharts.stockChart('container', {

    rangeSelector: {
      selected: 4
    },

    yAxis: {
      labels: {
        formatter: function () {
          return (this.value > 0 ? ' + ' : '') + this.value + '%';
        }
      },
      plotLines: [{
        value: 0,
        width: 2,
        color: 'silver'
      }]
    },

    plotOptions: {
      series: {
        compare: 'percent',
        showInNavigator: true
      }
    },

    tooltip: {
      pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
      valueDecimals: 2,
      split: true
    },

    series: seriesOptions
  });
}

var JSON, buyPriceData, sellPriceData, demandData, supplyData, timestampsData;
var dataArr = [];
var dataNew = [];

$.getJSON('http://localhost:8080/GW2TP/v1/item/chdata?id='+id, function(json){
	JSON = json;
	buyPriceData = json.buyPrice;
	sellPriceData = json.sellPrice;
	demandData = json.demand;
	supplyData = json.supply;
	timestampsData = json.timestamps;
	dataArr = [sellPriceData, buyPriceData, supplyData, demandData];
	
	$.each(names, function (i, name) {
		
		var q;
		dataNew[i] = [];
		for(q=0;q<dataArr[i].length;q++){
			dataNew[i][q] = [timestampsData[q], dataArr[i][q]];
		}
		
	    seriesOptions[i] = {
	      name: name,
	      data: dataNew[i]
	    };

	    // As we're loading the data asynchronously, we don't know what order it will arrive. So
	    // we keep a counter and create the chart when all the data is loaded.
	    seriesCounter += 1;

	    if (seriesCounter === names.length) {
	      createChart();
	    }
	});
});
