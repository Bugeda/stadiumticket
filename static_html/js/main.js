$(document).ready(function () {
    // load maphiglight if <map> block present
    if ($('.map').length != 0) {
	$('.map').maphilight();
    }

    // copy data from sector plan to hidden form on edit/new event page
    $('map > input').change( function () {
	var source_id = $(this).attr('id');
	var source_content = $(this).val();
	// var reg = /^\d+$/;
	// if (!reg.test(source_content)) { $(".alert").show();}
	var dest_name = 's'+ source_id.substr(6);
	$('#'+ dest_name).val(source_content);
    });

     // initialize input widgets first
    $('#start_time').timepicker({
        'showDuration': true,
        'timeFormat': 'g:ia'
    });

    $('#date').datepicker({
        'format': 'm/d/yyyy',
        'autoclose': true
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
	$('#confirm_deletion_form').hide();
    });

    // Working with tickets

    // recalculate price of all selected tickets
    // and set numbers for all of them
    function recalculate_price_and_index() {
	var total_price = 0;
	var ticket_index = 1;
	$('.ticket').each( function () {
	    $(this).children('.ticket_number').html(ticket_index+'.');
	    total_price += parseInt($(this).children('.ticket_price').html());
	    ticket_index += 1;
	});
	$('#total_price').html(total_price);
    };

    //add ticket to list
    function add_ticket(sector, row, seat,price) {
	$('#ticket_list').append("<tr class=\"ticket\"><td class=\"ticket_number\"></td>"+"<td>"
				 + sector +"</td>"
				 + "<td>"+ row + "</td>"
				 + "<td>" + seat + "</td>"
				 + "<td class=\"ticket_price\">"+ price + "</td>"
				 + "<td><img class=\"delete_ticket\" src=\"images\/delete.png\"></td></tr>");
	recalculate_price_and_index();
    };

    //for tests, generate random ticket
    $('#add_random_ticket').click( function() {
	var random_sector = Math.floor(Math.random()*24+1);
	var random_row = Math.floor(Math.random()*20);
	var random_seat = Math.floor(Math.random()*50);
	var random_price = Math.floor(Math.random()*550);
	add_ticket(random_sector, random_row, random_seat, random_price);
    });

    //add ticket by clicking seat on sector plan
    $('.sell_tickets_table td').click(function() {
	if ($(this).is('[id]')
	    && !($(this).hasClass('booked'))
	    && !($(this).hasClass('occupied'))
	    && !($(this).hasClass('selected')) ){
	    $(this).addClass('selected');
	    var id = $(this).attr('id').split('_');
	    var sector = $('#sector_name').html().substr(6).slice(0,-6);
	    if (sector == 'VIP A') {sector = 27}
	    else sector = parseInt(sector);
	    if (sector == 'VIP D') {sector = 26}
	    else sector = parseInt(sector);
	    var row = id[0];
	    var seat = id[1];
	    var price = $("#price_" + sector).val();
	    add_ticket(sector,row,seat,price);
	};
    });

    // delete ticket by clicking '-' button
    $('#ticket_list').on('click', '.delete_ticket', function () {
        var ticket = $(this).closest('td').siblings().map(function () { return $(this).text()});
	var id = (ticket[2]+'_'+ticket[3]);
	$('#'+id).toggleClass('selected');
	$(this).closest('.ticket').remove();
	recalculate_price_and_index();
    });

    // initial run to set numbers for all tickets
    // possibly move this functionality to backend
    recalculate_price_and_index();

    // restrict changing price when selling or booking
    $('#disable_inputs > input').attr('disabled','disabled');

});
