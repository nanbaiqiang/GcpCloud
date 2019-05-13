function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData( nTr );
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    sOut += '<tr><td>Client Number:</td><td>'+aData[1]+'  /  '+aData[3]+'</td></tr>';
    var clientNum = aData[1].toString();
    sOut += '<tr><td>Policy:</td><td><a onclick=titleclick("'+clientNum+'") data-toggle="modal" data-target="#myModal" >Policy info</a></td></tr>';
    sOut += '<tr><td>Remark:</td><td>And any further details here (images etc)</td></tr>';
    sOut += '</table>';
    
    //var soot = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;padding-top:30px;" class="table table-bordered table-invoice"><thead><tr><th>#</th><th>Item Description</th> <th class="text-center">Unit Cost</th><th class="text-center">Quantity</th><th class="text-center">Total</th></tr></thead></table>';

    return sOut;
}

function titleclick(e) 
{
	 var prama = {};
	 prama["clientNum"]=e;

	  $.ajax({
          type: "POST",
          contentType:"application/json",
          url: "/v1/findPolicy",
          data: JSON.stringify(prama),
          dataType: "json",
          cache:false,
          timeout:60000,
          success: function(data){
        	  		$("#policy_table>tbody").html("");
        	  		var number = 1;
	        		for(var i=0;i<data.length;i++)
	        		{
	        
	        			
	        			var html = '<tr><td>'+number+'</td>'+
	        			   '<td><h4>'+data[i].policyId+'</h4>'+
	                            '<p>'+data[i].policyName+'</p>'+
	                        '</td>'+
	                        '<td class="text-center"><strong>'+data[i].status+'</strong></td>'+
	                        '<td class="text-center"><strong>'+data[i].currency+'</strong></td>'+
	                        '<td class="text-center"><strong>$'+data[i].summary+'</strong></td></tr>';
	        			
	        			$("#policy_table").append(html);
	        			number++;
	        		}	
	        		
	        		var htmlLabel=  
      			  '<tr><td colspan="2" class="payment-method">'+
                          '<h4>Remark</h4>'+
                          '<p>1. .</p>'+
                          '<p>2. .</p>'+
                          '  <p>3. jade User.</p><br>'+
                          '<h3 class="inv-label">Total</h3></td>'+
                      '<td class="text-right" colspan="2">'+
                      '<p>Sub Total</p>'+
                      '<p>Jade (VAT 10%)</p>'+
                      '<p>NoJade (5%)</p>'+
                      '<p><strong>GRAND TOTAL</strong></p></td>'+
                      '<td class="text-center">'+
                      '<p>$ 6589.00</p>'+
                      '<p>$ 120.00</p>'+
                      '<p>$ 60.00</p>'+
                      '<p><strong>$ 5120.00</strong></p></td></tr>';
	        		
	        			$("#policy_table").append(htmlLabel);
	        		
                   }
      });
	
}

function initTable()
{
	   $('#dynamic-table').dataTable( {
	        "aaSorting": [[ 4, "desc" ]]
	    } );

	    /*
	     * Insert a 'details' column to the table
	     */
	    var nCloneTh = document.createElement( 'th' );
	    var nCloneTd = document.createElement( 'td' );
	    nCloneTd.innerHTML = '<img src="images/details_open.png">';
	    nCloneTd.className = "center";

	    $('#hidden-table-info thead tr').each( function () {
	        this.insertBefore( nCloneTh, this.childNodes[0] );
	    } );

	    $('#hidden-table-info tbody tr').each( function () {
	        this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
	    } );

	    /*
	     * Initialse DataTables, with no sorting on the 'details' column
	     */
	    var oTable = $('#hidden-table-info').dataTable( {
	        "aoColumnDefs": [
	            { "bSortable": false, "aTargets": [ 0 ] }
	        ],
	        "aaSorting": [[1, 'asc']]
	    });

	    /* Add event listener for opening and closing details
	     * Note that the indicator for showing which row is open is not controlled by DataTables,
	     * rather it is done here
	     */
	    $(document).on('click','#hidden-table-info tbody td img',function () {
	        var nTr = $(this).parents('tr')[0];
	        if ( oTable.fnIsOpen(nTr) )
	        {
	            /* This row is already open - close it */
	            this.src = "images/details_open.png";
	            oTable.fnClose( nTr );
	        }
	        else
	        {
	            /* Open this row */
	            this.src = "images/details_close.png";
	            oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
	        }
	    } );
}
function searchData ()
{
	 var name ='line';
	 var prama = {};
	 prama["searchType"]="1";

	  $.ajax({
          type: "POST",
          contentType:"application/json",
          url: "/v1/findClient",
          data: JSON.stringify(prama),
          dataType: "json",
          cache:false,
          timeout:60000,
          success: function(data){
        	  
	        		for(var i=0;i<data.length;i++)
	        		{
	        			var html = '<tr class="gradeX">'+
	        		    '<td>'+data[i].clientNum +'</td>'+
	        		    '<td>'+data[i].idNum +'</td>'+
	        		    '<td class="hidden-phone">'+data[i].name +'</td>'+
	        		    '<td class="center hidden-phone">'+data[i].gender +'</td>'+
	        		    '<td class="center hidden-phone">'+data[i].brith +'</td>'+
	        		   // '<td class="center hidden">'+data[i].brith +'</td>'+
	        		     '</tr>';
	        			$('#hidden-table-info').append(html);
	        		}
         	    
	        		initTable(data);
	        		
                   }
      });
	

}

$(document).ready(function() {

    searchData ();
} );