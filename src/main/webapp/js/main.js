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

    // 3. dataTables
    $('#event_list').dataTable({
	language: {
	    "search": "Фильтровать список:",
	    "paginate": {
		"first":      "Первая",
		"previous":   "Предыдущая",
		"next":       "Следующая",
		"last":       "Последняя"
            },
	    "info": "Страница _PAGE_ из _PAGES_",
	    "lengthMenu": "Показать _MENU_ строк",
	    "zeroRecords":    "Ничего не найдено",
	    "infoFiltered":   "(отсеяно из _MAX_ событий)"
	},
	"paging": true,
	"stateSave": true,
	"autoWidth": true,
	"columnDefs": [ { "orderable": false, "targets": 2 } ]
    });

    $('#booking_search_results').dataTable({
	language: {
	    "search": "Фильтровать список:",
	    "paginate": {
		"first":      "Первая",
		"previous":   "Предыдущая",
		"next":       "Следующая",
		"last":       "Последняя"
            },
	    "info": "Страница _PAGE_ из _PAGES_",
	    "zeroRecords":    "Ничего не найдено",
	    "lengthMenu": "Показать _MENU_ строк",
	    "info":           "Показано _TOTAL_шт.  ",
	    "infoFiltered":   "(из _MAX_ билетов)"
	},
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
	var seat = (ticket[2]+'_'+ticket[3]);
	// remove selection from seats plan if it is currently displayed
	if ( ticket[1] == $('.sector_name').html() ) {
	    $('.'+seat).toggleClass('selected');
	    $(this).closest('.ticket').remove();
	}
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

    // select all checkbox handling
    $('#select_all').change( function() {
	var total_price = 0;
	var state = $(this)[0].checked;
	$('.ticket input').each(function (){
	    $(this)[0].checked = state;
	    if (state) {   total_price += parseInt($(this).parents().siblings('.ticket_price').html() ); }
	    else { total_price = 0; }
	});
	$('#total_price').html(total_price);
    });

    //get list of currently selected tickets
    function get_selected_ids() {
	var selected_ids = [];
	$('.ticket input[type=checkbox]:checked').each(function (){
	    selected_ids.push($(this).parents().siblings('.booking_id').html());
	});
	return selected_ids;
    }

    // send requests for cancel or sell tickets by booking id
    function manipulate_with_booked_tickets (action) {
	if (action == 'sell') {
	    var base_url = '/stadiumticket/booking/sell?'; //paste here the correct one
	}
	if (action == 'cancel_booking'){
	    var base_url = '/stadiumticket/booking/cancel?'; //paste here the correct one
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

	// fetch for data to server
	$.get( base_url, function(response) {
	    $('.alert').html('');
	    for (index in response) {
		if (response[index]) {
		    //remove ticket from list if we get true
		    $('.ticket')[index].remove();
		}
		else {
		    //leave ticket if we get false, fire up error message
		    $('.alert').append('Detected problem with booking id(s):<br> ');
		    $('.alert').append('<b>',ticket_ids[index], response[index],'</b><br>'); // output response to block
		    $('.modal').modal(); // show block with response
		    setTimeout(function(){
			$('.modal').modal('hide')
		    }, 2000);
		}
	    }
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
    $('#price_27').keydown( function(event) {
	if ( (event.keyCode == 9) && (event.shiftKey != true ) ) {
	    event.preventDefault();
	    $('#price_1').focus();
	}
    });
    $('#price_1').keydown( function(event) {
	if ( (event.keyCode == 9) && (event.shiftKey == true ) ) {
	    event.preventDefault();
	    $('#price_27').focus();
	}
    });

    // Draw sector from json object
    function draw_sector(sector_obj){
	$('.sector_name').html(sector_obj.name);
	// iterate through rows
	for (var row_index = 0; row_index < sector_obj.rows.length; row_index++) {
	    // iterate through seats in a row
	    for (var seat_index = 0; seat_index < sector_obj.rows[0].length; seat_index++) {
		$('.'+ parseInt(row_index+1) +'_'+parseInt(seat_index+1) ).attr('class',parseInt(row_index+1) +'_'+parseInt(seat_index+1)+" "+ sector_obj.rows[row_index][seat_index]);
	    }
	}
	// add currently selected tickets to sector plan when redraw it
	$('.ticket').each( function() {
	    if ( $(this).children('td').eq(1).html() == sector_obj.name ) {
		var row = $(this).children('td').eq(2).html();
		var seat = $(this).children('td').eq(3).html();
		$('.'+ row + '_' + seat).addClass('selected');
	    }
	});
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

});
