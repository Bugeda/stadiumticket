$(document).ready(function () {
    $('#event_list').dataTable({
	"paging": false,
	"stateSave": true,
	"autoWidth": true,
	"columnDefs": [ { "orderable": false, "targets": 2 } ]
    });
    $('.event').mouseover( function() {
    	$(this).children('.action_list').show();
    });
    $('.event').mouseout( function() {
    	$(this).children('.action_list').hide();
    });

});
