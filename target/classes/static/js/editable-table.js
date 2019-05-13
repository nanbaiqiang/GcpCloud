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

//function upload() {
//	var formData = new FormData();
//	var name = $("#input-file-1").val();
//	formData.append("file", $("#input-file-1")[0].files[0]);
//	formData.append("name", name);
// 
//	$.ajax({
//		url : 'v1/importFile',
//		type : 'POST',
//		cache : false,
//		data : formData,
//		// 告诉jQuery不要去处理发送的数据
//		processData : false,
//		// 告诉jQuery不要去设置Content-Type请求头
//		contentType : false,
//		beforeSend : function() {
//			console.log("正在进行，请稍候");
//		},
//		success : function(responseStr) {
//			alert("导入成功");
//		}
//	});
//}
