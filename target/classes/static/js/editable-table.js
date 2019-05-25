var EditableTable = function () {

	    
	$("#fileupload").fileinput({
	    language: 'EN', //设置语言
	    //uploadUrl: 'v1/importFile', //上传的地址
	    uploadUrl: 'v1/apk_upload', //上传的地址
	    showUpload: true,
	    allowedFileExtensions : ['xls', 'xlsx'],
	    uploadAsync : false,
	    maxFileCount: 3,
	    browseOnZoneClick: true,
	    dropZoneEnabled : true,
	    showPreview : true,
	    enctype: 'multipart/form-data',
	    showCaption: true,//是否显示标题
	    browseClass: "btn btn-info", //按钮样式      
	    dropZoneEnabled: false,
	    showPreview: true,
	    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
	});


	
}();

$(document).ready(function() {

	getFilelist();
} );

function getFilelist() {
	
		$('#dynamic-table').dataTable( {
	        "aaSorting": [[ 4, "desc" ]]
	    } );

	    var nCloneTh = document.createElement( 'th' );
	    var nCloneTd = document.createElement( 'td' );
	    nCloneTd.innerHTML = '<img src="images/details_open.png">';
	    nCloneTd.className = "center";

	    var oTable = $('#hidden-table-info').dataTable( {
	        "aoColumnDefs": [
	            { "bSortable": false, "aTargets": [ 0 ] }
	        ],
	        "aaSorting": [[1, 'asc']]
	    });
	    
		  $.ajax({
				type : "GET",
				contentType : "application/json",
				url : "/v1/getFilelist",
				dataType : "json",
				cache : false,
				timeout : 60000,
				success : function(data) {
	        	  		$("#hidden-table-info>tbody").html("");
	        	  		var number = 1;
		        		for(var i=0;i<data.length;i++)
		        		{
		        			var html = '<tr><td>'+number+'</td>'+
		                        '<td><strong>'+data[i].fileName+'</strong></td>'+
		                        '<td><strong>'+data[i].date+'</strong></td>'+
		                        '<td><strong>'+data[i].status+'</strong></td></tr>';
		        			
		        			$("#hidden-table-info").append(html);
		        			number++;
		        		}	
	                   }
	      });

}
