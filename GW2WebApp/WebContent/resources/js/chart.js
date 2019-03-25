var seriesOptions = [],
    seriesCounter = 0,
    names1 = ['Sell Price', 'Buy Price'],
	getParams = ['sellPrice', 'buyPrice'];

var seriesOptions2 = [],
	names2 = ['Supply', 'Demand'],
	getParams2 = ['sellQuantity', 'buyQuantity'];

var	colors = ['green', 'red', 'black', 'purple'];

function createChart() {

  Highcharts.stockChart('container', {

    rangeSelector: {
      selected: 1
    },
    
    title: {
        text: 'Trading Post History'
    },
    
    yAxis: [
    	{
    		labels: {
    			useHTML: true,
    			align: 'right',
                x: -5,
    			formatter: function () {
    				var ret = getMoneyYAxis(this.value);
    				return (ret);
    			}
    		},
    		
    		title: {
                text: 'Price'
            },
            height: '50%',
            lineWidth: 2,
            resize: {
                enabled: true
            }
    	},
    	{
    		labels: {
    			useHTML: true,
    			align: 'right',
                x: -3,
    			formatter: function () {
    				return (this.value);
    			}
    		},
    		title: {
                text: 'Volume'
            },
            top: '55%',
            height: '45%',
            offset: 0,
            lineWidth: 2
        }
  	],
  	
	plotLines: [{
		value: 0,
		width: 0,
		color: 'silver',
//		color: 'white'
	}],
	
    plotOptions: {
      series: [{
        compare: 'value',
        showInNavigator: true
      },
      {
          compare: 'value',
          showInNavigator: true
      },
      {
          compare: 'value',
          showInNavigator: true
      },
      {
          compare: 'value',
          showInNavigator: true
      }
      ]
    },
    navigator: {
    	maskFill: 'rgba(102,133,194,0.3)',
    	series: {
            color: '#00FF00',
            lineWidth: 2
        }
    },
    tooltip: 
    	{
	      shared: false,
	      split: true,
	      useHTML: true,
	      backgroundColor: 'white',
	      borderWidth: 3,
//	      borderRadius: 3,
	      pointFormatter: function(){
	    	  var val = this.y;
	    	  
	    	  if(this.series.index < 2){
	    		  val = getMoneyToolTip(this.y);
	    	  } else val = this.y;
	    	  return '<span style="color:'+this.series.color+'">'+this.series.name+'</span>: '+val+'<br>';
	      },
    	},
    
    series: seriesOptions
  });
}

var JSON, buyPriceData, sellPriceData, demandData, supplyData, timestampsData;
var dataArr1 = [];
var dataArr2 = [];
var dataNew1 = [];
var dataNew2 = [];
var cnt = 0;

$.getJSON('http://localhost:8080/GW2TP/v1/item/chdata?id='+id, function(json){
	JSON = json;
	buyPriceData = json.buyPrice;
	sellPriceData = json.sellPrice;
	demandData = json.demand;
	supplyData = json.supply;
	timestampsData = json.timestamps;
	dataArr1 = [sellPriceData, buyPriceData];
	dataArr2 = [supplyData, demandData];
	cnt=0;
	
	$.each(names1, function (i, name) {
		
		var q;
		console.log("i = "+i+" begin"+dataArr1[0].length+"end");
		dataNew1[i] = [];
		for(q=0;q<dataArr1[i].length;q++){
			dataNew1[i][q] = [timestampsData[q], dataArr1[i][q]];
//			dataNew1[i][q] = dataArr1[i][q];
		}
		
	    seriesOptions[cnt] = {
	      name: name,
	      data: dataNew1[i],
	      color: colors[cnt],
	    };
	    cnt++;
	});
	
	$.each(names2, function (i, name) {
		
		var q;
		dataNew2[i] = [];
		for(q=0;q<dataArr2[i].length;q++){
			dataNew2[i][q] = [timestampsData[q], dataArr2[i][q]];
//			dataNew2[i][q] = dataArr2[i][q];
		}
		
	    seriesOptions[cnt] = {
	      name: name,
	      data: dataNew2[i],
	      color: colors[cnt],
	      yAxis: 1,
	    };
	    cnt++;
	});
	createChart();
});
