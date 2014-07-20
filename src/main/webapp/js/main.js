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
	"paging": true,
	"stateSave": true,
	"autoWidth": true,
	"ordering": false
    });

    // BEGIN Edit/new event, event list section
    // copy data from sector plan to hidden form on edit/new event page
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
    $('#booking_search_results_filter input').addClass('form-control');


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
	if ($(this).is('[id]')
	    && !($(this).hasClass('booked'))
	    && !($(this).hasClass('occupied'))
	    && !($(this).hasClass('selected')) ){
	    $(this).addClass('selected');
	    var id = $(this).attr('id').split('_');
	    var sector = $('#sector_name').html(); //.substr(6).slice(0,-6)
	    var sector_number = 0;
	    if ((sector != 'VIP A') && (sector != 'VIP D')){
		sector_number = parseInt(sector);
	    };
	    if (sector == 'VIP A') {sector_number= 26;}
	    if (sector == 'VIP D') {sector_number= 27;}
	    var price = $("#price_" + sector_number).val();
	    var row = id[0];
	    var seat = id[1];
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
    // recalculate_price_and_index();

    // END Working with tickets section


    // BEGIN Working with staduim plan section
    // restrict changing price when selling or booking
    $('#disable_inputs > input').attr('disabled','disabled');

    // Draw sector from json object
    function draw_sector(sector_obj){
	$('#sector_name').html(sector_obj.name);
	// iterate through rows
	for (var row_index = 0; row_index < sector_obj.rows.length; row_index++) {
	    // iterate through seats in a row
	    for (var seat_index = 0; seat_index < sector_obj.rows[0].length; seat_index++) {
		if (sector_obj.rows[row_index][seat_index] != 'vacant') {
		    $('#'+ parseInt(row_index+1) +'_'+parseInt(seat_index+1) ).attr('class',sector_obj.rows[row_index][seat_index]);
		}
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
