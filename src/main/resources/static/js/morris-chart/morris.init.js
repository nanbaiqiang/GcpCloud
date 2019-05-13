$(function() {

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/v1/getBar",
		// data: JSON.stringify(pram),
		dataType : "json",
		cache : false,
		timeout : 60000,
		success : function(data) {

			var bar = Morris.Bar({
			    element: 'graph-bar',
			    data:data,
			    xkey: 'year',
			    ykeys: ['life1Num', 'life2Num', 'life3Num'],
			    labels: ['life1', 'life2', 'life3'],
			    barColors:['#414e62','#788ba0','#6dc5a3']

				});
		}
	});
	
	
	 var name ='line';
	 var pram = {};
	 pram["name"]="line";

	  $.ajax({
          type: "GET",
          contentType:"application/json",
          url: "/v1/getLine?sysname="+name,
          //data: JSON.stringify(pram),
          dataType: "json",
          cache:false,
          timeout:60000,
          success: function(data){
        	  
        	              var line = Morris.Line({
        	                  element: 'graph-line',
        	                  data: data,
        	                  xkey: 'key',
        	                  ykeys: ['value'],
        	                  labels: ['value'],
        	                  lineColors:['#1FB5AD'],
        	                  parseTime: false
        	              });
         	    
                   }
      });

		
	  $.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/v1/getArea",
		// data: JSON.stringify(pram),
		dataType : "json",
		cache : false,
		timeout : 60000,
		success : function(data) {

			// Use Morris.Area instead of Morris.Line
			var area = Morris.Area({
				element : 'graph-area-line',
				behaveLikeLine : false,
				data : data,
				xkey : 'year',
				ykeys : [ 'pay', 'sumIns' ],
				labels : [ 'pay', 'sumIns' ],
				lineColors : [ '#414e62', '#6dc5a3' ]

			});

		}
	});



	  $.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/v1/getDonut",
			// data: JSON.stringify(pram),
			dataType : "json",
			cache : false,
			timeout : 60000,
			success : function(data) {
				var donut = Morris.Donut({
				    element: 'graph-donut',
				    data : data,
				    backgroundColor: '#fff',
				    labelColor: '#1fb5ac',
				    colors: [
				        '#414e62','#788ba0','#6dc5a3','#95D7BB','#13D7BB','#57D7BB','#588ba0','#714e62'
				    ],
				    formatter: function (x, data) { return data.formatted; }
				});
			}
		});

	  
// Use Morris.Area instead of Morris.Line
var area1 = Morris.Area({
    element: 'graph-area',
    behaveLikeLine: true,
    gridEnabled: false,
    gridLineColor: '#dddddd',
    axes: true,
    fillOpacity:.7,
    data: [
        {period: '2010 Q1', iphone: 10, ipad: 10, itouch: 10},
        {period: '2010 Q2', iphone: 1778, ipad: 7294, itouch: 18441},
        {period: '2010 Q3', iphone: 4912, ipad: 12969, itouch: 3501},
        {period: '2010 Q4', iphone: 3767, ipad: 3597, itouch: 5689},
        {period: '2011 Q1', iphone: 6810, ipad: 1914, itouch: 2293},
        {period: '2011 Q2', iphone: 5670, ipad: 4293, itouch: 1881},
        {period: '2011 Q3', iphone: 4820, ipad: 3795, itouch: 1588},
        {period: '2011 Q4', iphone: 25073, ipad: 5967, itouch: 5175},
        {period: '2012 Q1', iphone: 10687, ipad: 34460, itouch: 22028},
        {period: '2012 Q2', iphone: 1000, ipad: 5713, itouch: 1791}


    ],
    lineColors:['#414e62','#788ba0','#6dc5a3'],
    xkey: 'period',
    ykeys: ['iphone', 'ipad', 'itouch'],
    labels: ['iPhone', 'iPad', 'iPod Touch'],
    pointSize: 0,
    lineWidth: 0,
    hideHover: 'auto'
	
	});
});





