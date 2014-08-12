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
	dayOfWeekStart: 1
    });

    // 3. dataTables
    // date sorting plug-in
    jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"ru_datetime-asc": function ( a, b ) {
            var x, y;
            if ($.trim(a) !== '') {
		var deDatea = $.trim(a).split(' ');
		var deTimea = deDatea[1].split(':');
		var deDatea2 = deDatea[0].split('-');
		x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1]) * 1;
            } else {
		x = Infinity; // = l'an 1000 ...
            }

	    if ($.trim(b) !== '') {
		var deDateb = $.trim(b).split(' ');
		var deTimeb = deDateb[1].split(':');
		deDateb = deDateb[0].split('-');
		y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1]) * 1;
            } else {
		y = Infinity;
            }
            var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
            return z;
	},

	"ru_datetime-desc": function ( a, b ) {
            var x, y;
            if ($.trim(a) !== '') {
		var deDatea = $.trim(a).split(' ');
		var deTimea = deDatea[1].split(':');
		var deDatea2 = deDatea[0].split('-');
		x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1]) * 1;
            } else {
		x = Infinity;
            }

            if ($.trim(b) !== '') {
		var deDateb = $.trim(b).split(' ');
		var deTimeb = deDateb[1].split(':');
		deDateb = deDateb[0].split('-');
		y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1]) * 1;
            } else {
		y = Infinity;
            }
            var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
            return z;
	}
    } );
    // date sorting plug-in

    // load russian translation depending on browser language
    if (navigator.language == 'ru-RU' ) {
	var lang = {
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
	    "infoFiltered":   "(отсеяно из _MAX_ строк)"
	};
    }
    else lang = {};

    $('#event_list').dataTable({
	"language": lang,
	"paging": true,
	"stateSave": true,
	"autoWidth": true,
	"columnDefs": [
	    { "orderable": false, "targets": 2 },
	    { "type": 'ru_datetime', "targets": 1 }
	]
    });

    $('#booking_search_results').dataTable({
	"language": lang,
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
    // fill sector prices from hiddens if form validation didn't pass
    $('.hidden_sector_price').each( function () {
	var sector_number = $(this).attr('id').split('s')[1];
	var hidden_price = $(this).val()
	if ( hidden_price ) {
	    $('#price_'+sector_number).val(hidden_price);
	}
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
    $('#event_list').on('mouseover', '.event' ,function() {
	$(this).children('.action_list').show();
    });
    $('#event_list').on('mouseout', '.event', function() {
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
	var ticket_index = 0;
	$('.ticket').each( function () {
	    $(this).children('.ticket_number').html(parseInt(ticket_index + 1)+'.');
	    $(this).find('input').each( function () {
		$(this).attr('name', $(this).attr('name').replace( /\[.*?\]/g, '['+ticket_index+']') );
	    });
	    total_price += parseFloat($(this).children('.ticket_price').html());
	    ticket_index += 1;
	});
	$('#total_price').html(total_price.toFixed(2));
    };

    //add ticket to list
    function add_ticket(sector, sector_number, row, seat,price){
	$('#ticket_list').append("<tr class=\"ticket\"><td class=\"ticket_number\"></td>"+"<td>"
				 + sector +"</td>"
				 + "<td>"+ row + "</td>"
				 + "<td>" + seat + "</td>"
				 + "<td class=\"ticket_price\">"+ price + "</td>"
				 + "<td><img class=\"delete_ticket\" src=\"\/stadiumticket\/images\/delete.png\"></td>"
				 + "<td><input name=\"seat\" type=\"hidden\" value="+sector_number+"_"+row+"_"+seat+"></td></tr>" );
	
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
	    add_ticket(sector,sector_number,row,seat,price);
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


    
    //send tickets to sell
    $('#sell_tickets').click(function (e) {
	e.preventDefault();
	if (navigator.language == 'ru-RU' ) {
	    var response_codes = ['ok', 'уже забронирован', 'уже продан', 'непредвиденная ошибка продажи','ошибочный билет'];
	}
	else {
	    var response_codes = ['ok', 'already booked', 'already sold', 'unexpected sell error', 'error ticket'];
	}
	// prepare url
	var len = $('.ticket').length;
	var url_base = '/stadiumticket/tickets/setsell?id='+ $('input[name="id"]').val() + '&';
	$('input[name="seat"]').each( function (index, element) {
	    var sector_row_seat =$(this).val();	 
	    if (index == len - 1) {
		var ticket_string = 'tk='+ sector_row_seat;
	    }
	    else {
		var ticket_string = 'tk='+ sector_row_seat + '&';
	    }
	    if ((url_base + ticket_string).length >= 2000) {
		console.log('url too long');
	    }
	    else {
		url_base = url_base + ticket_string;
	    }
	});
	// actual fetch for data
	$.get( url_base, function(response) {
	    console.log('sell response',response);
	    $('div#ticket_status').remove();
	    var res=true;
        for (index in response) {        
	    	if (response[index] != 0) {
	    		res=false;	    	
	    		var status = 'danger';
	    	}
	    	else{
	    		 var status = "success";
	    	}
	    	// append alert block, hide it and slooowly show :)
			// remove hide().show('slow') form the end, for just append block  
			$('.ticket').eq(index).append('<div id="ticket_status" class="alert-'+status+'" role="alert">&nbsp;' + response_codes[response[index]] + '</div>').hide().show('slow');
	    }
    	//find sector and reload seats plan
    	var sector =  $('.sector_name').html();	      	   
    	var sector_number = 0;
	    if ((sector != 'VIP A') && (sector != 'VIP D')){
		sector_number = parseInt(sector);
	    };
	    if (sector == 'VIP A') {sector_number= 26;}
	    if (sector == 'VIP D') {sector_number= 27;}
        $('area[id="'+sector_number+'"]').click();
	    if (res){
	    	// remove booked tickets from list in 2s	
	    	setTimeout(function(){	        	   
  		    	// remove booked tickets from list in 2s	
  	    	    $('.alert-success').each(function (){
  	   	    	 	$('.alert-success').parent('.ticket').remove();  
	  	   	 	});
  	   	    	 recalculate_price_and_index();
  	    	}, 2000);
	        $('#success').modal('show');
	        setTimeout(function(){
	        	$('#success').modal('hide')
	       	    	}, 2000);		    		
	    } else {
			$('#danger').modal(); 
		}	
	});
    });

    
    
    //send tickets to book
    $('#book_tickets').click(function (e) {
	e.preventDefault();
	if (navigator.language == 'ru-RU' ) {
	    var response_codes = ['ok', 'уже забронирован', 'уже продан', 'непредвиденная ошибка бронирования','ошибочный билет'];
	    var customerIsMissing = 'Клиент не указан';
	}
	else {
	    var response_codes = ['ok', 'already booked', 'already sold', 'unexpected booking error', 'error ticket'];
	    var customerIsMissing = 'Customer field is empty';
	}
	// prepare url
	var len = $('.ticket').length;
	// we need to encode booking person's name using encodeURIComponent
		
	var url_base = '';
	if ($('#customerName').val().trim()==""){
		url_base='';
		$('#customerError').html(customerIsMissing);
		//$('#customerError').after('<div id="customer_status" &nbsp;' + customerIsMissing + '</div>').hide().show('slow');
	}
	   
	else	{
		$('#customerError').html('');
		url_base = '/stadiumticket/tickets/setbook?id='+ $('input[name="id"]').val() + '&customerName=' + encodeURIComponent($('#customerName').val() )+'&';
	}
	 console.log(url_base);
	$('input[name="seat"]').each( function (index, element) {
	    var sector_row_seat =$(this).val();	 
	    if (index == len - 1) {
		var ticket_string = 'tk='+ sector_row_seat;
	    }
	    else {
		var ticket_string = 'tk='+ sector_row_seat + '&';
	    }
	    if ((url_base + ticket_string).length >= 2000) {
		console.log('url too long');
	    }
	    else {
		url_base = url_base + ticket_string;
	    }
	});

	// actual fetch for data
	$.get( url_base, function(response) {
	    console.log('book response',response);
	
	    $('div#ticket_status').remove();
	    var res=false;
	    if (url_base!=''){
	    	res=true;	   	    
	    	for (index in response) {     	
	    		if (response[index] != 0) {
	    			res=false;	    	
	    			var status = 'danger';
	    		}
	    		else{
	    			var status = "success";
	    		}	 
	    		// append alert block, hide it and slooowly show :)
	    		// remove hide().show('slow') form the end, for just append block
	    		$('.ticket').eq(index).append('<div id="ticket_status" class="alert-'+status+'" role="alert">&nbsp;' + response_codes[response[index]] + '</div>').hide().show('slow');
	    	}
	    }
	    //find sector and reload seats plan
    	var sector = $('.sector_name').html();	      	   
    	var sector_number = 0;
	    if ((sector != 'VIP A') && (sector != 'VIP D')){
		sector_number = parseInt(sector);
	    };
	    if (sector == 'VIP A') {sector_number= 26;}
	    if (sector == 'VIP D') {sector_number= 27;}
        $('area[id="'+sector_number+'"]').click();
	    if (res){
	  	    	// remove booked tickets from list in 2s
	  	    	setTimeout(function(){	        	   
	  		    	// remove booked tickets from list in 2s	
	  	    	    $('.alert-success').each(function (){
	  	   	    	 	$('.alert-success').parent('.ticket').remove();  
		  	   	 	});
	  	   	    	 recalculate_price_and_index();
	  	    	}, 2000);
		        $('#success').modal('show');
		        setTimeout(function(){
		        	$('#success').modal('hide')
		       	    	}, 2000);		    		
		} else {
 			$('#danger').modal(); 
 		}	
	});
    });
  
    
    
    $('.ticket').each(function (){
    	 $('.ticket').removeClass('alert-danger');     	 
 	}); 
    // enable form-control class for booking search results table
    $('#booking_search_results_filter input').addClass('form-control');

    // handle ticket selection for booking and selling at booking search results
    $('.ticket input').click( function() {
	var total_price = 0;
	$('input[type=checkbox]:checked').each(function (){
	    total_price += parseFloat($(this).parents().siblings('.ticket_price').html() );
	});
	$('#total_price').html(total_price.toFixed(2));
    });

    // select all checkbox handling
    $('#select_all').change( function() {
	var total_price = 0;
	var state = $(this)[0].checked;
	$('.ticket input').each(function (){
	    $(this)[0].checked = state;
	    if (state) {   total_price += parseFloat($(this).parents().siblings('.ticket_price').html() ); }
	    else { total_price = 0; }
	});
	$('#total_price').html(total_price.toFixed(2));
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
	    var base_url = '/stadiumticket/booking/sell?';
	}
	if (action == 'cancel_booking'){
	    var base_url = '/stadiumticket/booking/cancel?'; 
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
	    
	    console.log(response); 
	    var res=true;
	    for (index in response) {
		var id_to_remove =  ticket_ids[index];
		if (response[index]) {		
		    //remove ticket from list if we get true				
		    $('.booking_id:contains('+ id_to_remove +')').closest('.ticket').remove();
		}else {
			res=false;
		    $('.booking_id:contains('+ id_to_remove +')').closest('.ticket').addClass('alert-danger');  		
		}
	    }
	    if (res){
		    $('#success').modal('show');
	        setTimeout(function(){
	       	 $('#success').modal('hide')
	       	    }, 2000);
	    } else {
			$('#danger').modal(); 
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
	//initialise sector stats
	var total_free = 0;
	var total_booked = 0;
	var total_occupied = 0;

	// iterate through rows
	for (var row_index = 0; row_index < sector_obj.rows.length; row_index++) {
	    // iterate through seats in a row
	    for (var seat_index = 0; seat_index < sector_obj.rows[0].length; seat_index++) {
		$('.'+ parseInt(row_index+1) +'_'+parseInt(seat_index+1) ).attr('class',parseInt(row_index+1) +'_'+parseInt(seat_index+1)+" "+ sector_obj.rows[row_index][seat_index]);
		switch (sector_obj.rows[row_index][seat_index]) {
		case 'vacant':
		    total_free += 1;
		    break;
		case 'booked':
		    total_booked += 1;
		    break;
		case 'occupied':
		    total_occupied += 1;
		    break;
		}
	    }
	}
	// fill total free/booked/occupied numbers
	$('.total_free').html(total_free);
	$('.total_booked').html(total_booked);
	$('.total_occupied').html(total_occupied);
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
