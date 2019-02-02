//var seriesOptions = [],
//    seriesCounter = 0,
//    names = ['Sell Price', 'Buy Price', 'Quantity'];
//
///**
// * Create the chart when all data is loaded
// * @returns {undefined}
// */
//function createChart() {
//
//    Highcharts.stockChart('container', {
//
//        rangeSelector: {
//            selected: 4
//        },
//
//        yAxis: {
//            labels: {
//                formatter: function () {
//                    return (this.value > 0 ? ' + ' : '') + this.value + '%';
//                }
//            },
//            plotLines: [{
//                value: 0,
//                width: 2,
//                color: 'silver'
//            }]
//        },
//
//        plotOptions: {
//            series: {
//                compare: 'percent',
//                showInNavigator: true
//            }
//        },
//
//        tooltip: {
//            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
//            valueDecimals: 2,
//            split: true
//        },
//
//        series: seriesOptions
//    });
//}
//
//seriesOptions[0] = {
//        name: 'Sell Price',
//        data: [ 
//				<c:forEach var="price" items="${priceInfo}" varStatus="i">
//				   [
//					   ${price.getNano() },
//					   ${price.getSellPrice() },
//					],
//				</c:forEach>
//        ],
//};
//
//
//seriesOptions[1] = {
//        name: 'Buy Price',
//        data: [ 
//			<c:forEach var="price" items="${priceInfo}" varStatus="i">
//			   [
//				   ${price.getNano() },
//				   ${price.getBuyPrice() },
//				],
//			</c:forEach>
//    ],
//};
//
//seriesOptions[2] = {
//        name: 'Buy Quantity',
//        data: [ 
//			<c:forEach var="price" items="${priceInfo}" varStatus="i">
//			   [
//				   ${price.getNano() },
//				   ${price.getBuyQuantity() },
//				],
//			</c:forEach>
//    ],
//};
//
//seriesOptions[3] = {
//        name: 'Sell Quantity',
//        data: [ 
//			<c:forEach var="price" items="${priceInfo}" varStatus="i">
//			   [
//				   ${price.getNano() },
//				   ${price.getSellQuantity() },
//				],
//			</c:forEach>
//    ],
//};
//createChart();
//
//
//
//
//var seriesOptions = [],
//  seriesCounter = 0,
//  names = ['MSFT', 'AAPL', 'GOOG'];
//
///**
// * Create the chart when all data is loaded
// * @returns {undefined}
// */
//function createChart() {
//
//  Highcharts.stockChart('container', {
//
//    rangeSelector: {
//      selected: 4
//    },
//
//    yAxis: {
//      labels: {
//        formatter: function () {
//          return (this.value > 0 ? ' + ' : '') + this.value + '%';
//        }
//      },
//      plotLines: [{
//        value: 0,
//        width: 2,
//        color: 'silver'
//      }]
//    },
//
//    plotOptions: {
//      series: {
//        compare: 'percent',
//        showInNavigator: true
//      }
//    },
//
//    tooltip: {
//      pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
//      valueDecimals: 2,
//      split: true
//    },
//
//    series: seriesOptions
//  });
//}
//
//$.each(names, function (i, id) {
//
//  $.getJSON('http://localhost:8080/GW2TP/v1/items?ids='+id,  function (data) {
//
//    seriesOptions[i] = {
//      name: name,
//      data: data
//    };
//
//    // As we're loading the data asynchronously, we don't know what order it will arrive. So
//    // we keep a counter and create the chart when all the data is loaded.
//    seriesCounter += 1;
//
//    if (seriesCounter === names.length) {
//      createChart();
//    }
//  });
//});
//
///*
//var seriesOptions = [],
//    seriesCounter = 0,
//    names = ['Sell Price', 'Buy Price', 'Quantity'];
//
//function createChart() {
//
//    Highcharts.stockChart('chart', {
//
//        rangeSelector: {
//            selected: 4
//        },
//
//        yAxis: {
//            labels: {
//                formatter: function () {
//                    return (this.value > 0 ? ' + ' : '') + this.value + '%';
//                }
//            },
//            plotLines: [{
//                value: 0,
//                width: 2,
//                color: 'silver'
//            }]
//        },
//
//        plotOptions: {
//            series: {
//                compare: 'percent',
//                showInNavigator: true
//            }
//        },
//
//        tooltip: {
//            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
//            valueDecimals: 2,
//            split: true
//        },
//
//        series: seriesOptions
//    });
//}
//
//$.each(names, function (i, name) {
//
////    $.getJSON('https://www.highcharts.com/samples/data/' + name.toLowerCase() + '-c.json',    function (data) {
//        seriesOptions[i] = {
//            name: name,
//            data: ['23', '24', '25']
//        };
//
//        // As we're loading the data asynchronously, we don't know what order it will arrive. So
//        // we keep a counter and create the chart when all the data is loaded.
//        seriesCounter += 1;
//
//        if (seriesCounter === names.length) {
//            createChart();
//        }
////    });
//});
//*/