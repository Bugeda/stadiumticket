$(document).ready(function () {
    // Plugins' init
    // 1. maphiglight
    $('.map').maphilight();

    // 2. datetimepicker
    $('#start').datetimepicker({
	lang:'en',
	timepicker:true,
	step:15,
	format:'d-m-Y H:i',
	minDate : '-1969/12/31',
	startDate : '-1969/12/31',
	dayOfWeekStart: 1
    });

    // $('#booking_time').datetimepicker({
    // 	lang:'en',
    // 	datepicker:false,
    // 	value: 30,
    // 	step:5,
    // 	format: 'i',
    // 	formatTime: 'i'
    // });

    // 3. dataTables
    $('#event_list').dataTable({
	"paging": true,
	"stateSave": true,
	"autoWidth": true,
	"columnDefs": [ { "orderable": false, "targets": 2 } ]
    });

    $('#booking_search_results').dataTable({
	"paging": false,
	"stateSave": true,
	"autoWidth": true,
	"ordering": false
    });

    // BEGIN Edit/new event, event list section
    // copy data from sector plan to hidden form on edit/new event page
    $('#title').keyup( function () {
	var title = ($(this).val());
	$('#event_name').html(title);
    });

    $('map > input').change( function () {
	var source_id = $(this).attr('id');
	var source_content = $(this).val();
	// var reg = /^\d+$/;
	// if (!reg.test(source_content)) { $(".alert").show();}
	var dest_name = 's'+ source_id.substr(6);
	$('#'+ dest_name).val(source_content);
    });

    // display action buttons for event in list
    $('.event').mouseover( function() {
	$(this).children('.action_list').show();
    });
    $('.event').mouseout( function() {
	$(this).children('.action_list').hide();
    });
    $('#event_list_filter input').addClass('form-control');

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
    // END Edit/new event, event list section

    // BEGIN Working with tickets section

    // recalculate price of all selected tickets
    // and set numbers for all of them
    function recalculate_price_and_index() {
	var total_price = 0;
	var ticket_index = 1;
	$('.ticket').each( function () {
	    $(this).children('.ticket_number').html(ticket_index+'.');
	    $(this).find('input').each( function () {
		$(this).attr('name', $(this).attr('name').replace('[i]', '['+ticket_index+']') );
	    });
	    // attr = attr.replace('[i]', '['+ticket_index+']');
	    // $(this).find('input').attr('name', attr);
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
				 + "<td><img class=\"delete_ticket\" src=\"images\/delete.png\"></td>"
				 + "<td><input name=\"chosenSectorsNums[i]\" type=\"hidden\" value="+sector+">"
				 + "<input name=\"chosenSeats[i].rowNumber\" type=\"hidden\" value="+row+">"
 				 + "<input name=\"chosenSeats[i].seatNumber\" type=\"hidden\" value="+seat+"></td></tr>" );
	recalculate_price_and_index();
    	};
    //add ticket by clicking seat on sector plan
    $('.sell_tickets_table td').click(function() {
	if ($(this).is('[class]')
	    && !($(this).hasClass('booked'))
	    && !($(this).hasClass('occupied'))
	    && !($(this).hasClass('selected')) ){
	    $(this).addClass('selected');
	    var id = $(this).attr('class').split('_');
	    var sector = $(this).closest('.container').find('.sector_name').html();
	    var sector_number = 0;
	    if ((sector != 'VIP A') && (sector != 'VIP D')){
		sector_number = parseInt(sector);
	    };
	    if (sector == 'VIP A') {sector_number= 26;}
	    if (sector == 'VIP D') {sector_number= 27;}
	    var price = $("#price_" + sector_number).val();
	    var row = id[0];
	    var seat = parseInt(id[1]);
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

    // enable form-control class for booking search results table
    $('#booking_search_results_filter input').addClass('form-control');

    // handle ticket selection for booking and selling at booking search results
    $('.ticket input').click( function() {
	var total_price = 0;
	$('input[type=checkbox]:checked').each(function (){
	    total_price += parseInt($(this).parents().siblings('.ticket_price').html() );
	});
	$('#total_price').html(total_price);
    });

    //get list of currently selected ticket
    function get_selected_ids() {
	var selected_ids = [];
	$('input[type=checkbox]:checked').each(function (){
	    selected_ids.push($(this).parents().siblings('.booking_id').html());
	});
	return selected_ids;
    }


    // send requests for cancel or sell tickets by booking id
    function manipulate_with_booked_tickets (action) {
	if (action == 'sell') {
	    var base_url = 'url_base_for_selling_tickets_by_id?'; //paste here the correct one
	}
	if (action == 'cancel_booking'){
	    var base_url = 'url_base_for_cancel_booking_by_id?'; //paste here the correct one
	};
	var ticket_ids = get_selected_ids();
	for (id in ticket_ids) {
	    if (id == ticket_ids.length-1) {
		base_url = base_url + 'id=' + ticket_ids[id];
	    }
	    else {
		base_url = base_url + 'id=' + ticket_ids[id]+ '&';
	    };
	};
	// fetch for data
	$.get( base_url, function(response) {
	    $('.response').html(response); // output response to block
	    $('.response').slideDown(); // show block with response
	});
    };

    //send ticket ids when 'sell' button clicked
    $('#sell_selected_tickets').click( function() {
	manipulate_with_booked_tickets('sell');
    });

    //send ticket ids when 'cancel booking' button clicked
    $('#cancel_booking_selected_tickets').click( function() {
	manipulate_with_booked_tickets('cancel_booking');
    });

    // initial run to set numbers for all tickets
    // recalculate_price_and_index();

    // END Working with tickets section


    // BEGIN Working with staduim plan section
    // restrict changing price when selling or booking
    $('#disable_inputs > input').attr('disabled','disabled');

    // cycle switching between sector price inputs
    $('#price_25').keypress( function(event) {
	if (event.keyCode == 9) {
	    $('#price_26').focus();
	}
    });

    // Draw sector from json object
    function draw_sector(sector_obj){
	$('.sector_name').html(sector_obj.name);
	// iterate through rows
	for (var row_index = 0; row_index < sector_obj.rows.length; row_index++) {
	    // iterate through seats in a row
	    for (var seat_index = 0; seat_index < sector_obj.rows[0].length; seat_index++) {
		$('.'+ parseInt(row_index+1) +'_'+parseInt(seat_index+1) ).attr('class',sector_obj.rows[row_index][seat_index]);
	    }
	}
    }

    // Fetch to server for state of tickets in sector
    $('area').click( function(e) {
	e.preventDefault();
	// actual fetch of data first
	$.get( $(this).attr('href'), function(response) {
	    console.log(response); // debug:print response to console
	    draw_sector(response);
	});

	// swap sector plans if necessary
	if ($(this).attr('id') >= 26) {
	    $('#normal_sector').hide();
	    $('#vip_sector').show();
	}
	else {
	    $('#vip_sector').hide();
	    $('#normal_sector').show();
	}
	// TEST DATA! remove when ajax wil be working properly
	// this is dry-run of function draw_sector()
	// draw_sector(vip_sector);
	// draw_sector(normal_sector);
    });
    // END Working with staduim plan section

    // TEST DATA! remove when ajax wil be working properly
    var vip_sector = {
	name: 'VIP D',
	rows: [
	    ['occupied', 'booked','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'occupied'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant'],
	    ['occupied', 'booked','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'occupied']
	]
    };

    var normal_sector = {
	name: '15',
	rows: [
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'occupied'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['vacant', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['booked', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant'],
	    ['occupied', 'occupied','occupied', 'booked', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'vacant', 'vacant', 'booked', 'occupied','vacant','booked','occupied','vacant', 'vacant', 'vacant', 'vacant','vacant', 'booked','occupied', 'vacant', 'booked', 'occupied','vacant','vacant','vacant', 'occupied'],
	]
    };
    // TEST DATA! remove when ajax wil be working properly

});
