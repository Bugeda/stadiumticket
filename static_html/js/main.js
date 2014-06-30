$(document).ready(function () {
    // load maphiglight if <map> block present
    if ($('.map').length != 0) {
	$('.map').maphilight();
    }

    // copy data from sector plan to hidden form, in order to send it to server
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

    if ($('#event_list').length != 0) {

	// dataTable configuration
	$('#event_list').dataTable({
	    "paging": false,
	    "stateSave": true,
	    "autoWidth": true,
	    "columnDefs": [ { "orderable": false, "targets": 2 } ]
	});
	// display action buttons for event in list
	$('.event').mouseover( function() {
	    $(this).children('.action_list').show();
	});
	$('.event').mouseout( function() {
	    $(this).children('.action_list').hide();
	});
	$('#event_list_filter input').addClass('form-control');
    };
    // delete event process
    $('#event_delete').click( function () {
	$('#confirm_deletion_form').show();
    });
    $('#confirm_deletion_form').submit( function (event) {
	if ($('#confirm_deletion_text').val() != 'DELETE') {
	    $('#wrong_confirmation').show();
	    event.preventDefault();
	};
    });
    $('#cancel_deletion').click( function () {
	$('#confirm_deletion_warning').hide();
    });

    // Working with tickets

    // recalculate price of all selected tickets
    function recalculate_price() {
	var total_price = 0;
	$('.ticket').each( function () {
	    total_price = parseInt(total_price) + parseInt($(this).children('.ticket_price').html());
	});
	$('#total_price').html(total_price);
    };

    // delete ticket by clicking '-' button
    $('.delete_ticket').click( function() {
	$(this).closest('.ticket').remove();
	recalculate_price();
    });
});
