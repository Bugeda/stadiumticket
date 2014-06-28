$(document).ready(function () {
    if ($('.map').length != 0) {
	$('.map').maphilight();
    };

    // $('.close').click( function() { $(this).hide();} );

    $('map > input').change( function () {
	var source_id = $(this).attr('id');
	var source_content = $(this).val();

	// var reg = /^\d+$/;
	// if (!reg.test(source_content)) { $(".alert").show();}

	if ( source_id.substr(6) == 'vipA' || source_id.substr(6) == 'vipD' ) {
	    var dest_name = source_id.substr(6);
	}
	else var dest_name = 's'+ source_id.substr(6);
	$('#'+ dest_name).val(source_content);
    });

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
