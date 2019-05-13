/**
 * 
 */
$(document).ready(function(){
    	   
//       $('#datatable').DataTable();
//        //Buttons examples
//        var table = $('#datatable-buttons').DataTable({
//            lengthChange: false,
//            buttons: ['copy', 'excel', 'pdf']
//        });
//
//        table.buttons().container()
//                .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');

});

function userTable(){//取得当前点击索引
	$("#contentBody").load("user.html");
	setTimeout('alert("延迟1s")',1000); 
    EditableTable.init();
};
