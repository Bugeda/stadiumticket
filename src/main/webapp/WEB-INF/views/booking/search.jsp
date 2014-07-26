<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="searchbooked.title" /></title>
	<!-- css -->
    <link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/bootstrap-theme.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.dataTables.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.datetimepicker.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/main.css" rel="stylesheet">

    <!-- js -->
    <script src="<%= request.getContextPath() %>/js/jquery.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.dataTables.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.maphighlight.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.datetimepicker.js"></script>
    <script src="<%= request.getContextPath() %>/js/main.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
 </head>
<body>
<div class="modal fade bs-example-modal-sm" role="dialog" aria-hidden="true">
      <div class="modal-dialog modal-sm">
	<div class="modal-content">
	  <div class="alert alert-warning" role="alert">OK</div>
	</div>
      </div>
    </div>

<div class="container">
<div class="row">
	<div class="col-xs-1 col-md-1"><a href="<c:url value="../index"/>"><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	<div class="col-xs-6 col-md-9">
        <h3>
            <a id="arrow_back" href="<c:url value="../index"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>&nbsp;
            <spring:message code="searchbooked.pageTitle" />
        </h3>
        <h2 id="event_name"><c:out value="${event.eventName}"></c:out>
        </h2>
    </div>
</div>
<div class="row">
  <div class="col-md-5">
  	<form:form class="form-horizontal"  method="post" action="" modelAttribute="">
            <label for="search_booking_name">Search booked tickets: <img src="<%= request.getContextPath() %>/images/arrow_down.png"></label>
            <input class="form-control" type="text" name="search_booking_name" id="search_booking_name" placeholder="Full name of person">
          </form:form>
        </div>
      </div>
      <br>
      <h4>Search results:</h4>
      <div class="row">
        <div class="col-md-12">
          <table class="table hover" id="booking_search_results">
            <thead>
              <tr style="font-weight: bold;">
                <td>Booking id</td>
                <td>Sector</td>
                <td>Row</td>
                <td>Seat</td>
                <td>Price</td>
                <td>Booked for</td>
				<td>Mark ticket</td>
              </tr>
            </thead>
            <tbody>
  			<c:forEach items="${bookingSet}" var="booking">
  			   <tr class="ticket">
                <td class="booking_id"><c:out value="${booking.id}"></c:out></td>
                <td><c:out value="${booking.ticket.seat.sector.id}"></c:out></td>
                <td><c:out value="${booking.ticket.seat.rowNumber}"></c:out></td>
                <td><c:out value="${booking.ticket.seat.seatNumber}"></c:out></td>
                <td class="ticket_price"><c:out value="${event.sectorPriceSet[booking.ticket.seat.sector.id].price}"></c:out></td>
                <td><c:out value="${booking.customer.customerName}"></c:out></td>
                <td><input type="checkbox"></td>
              </tr>  			
            </c:forEach> 
            </tbody>
            <tfoot>
              <tr>
                <td colspan="4"><b>Total price:</b></td>
                <td style="font-weight:bold"><span id="total_price">0</span> UAH</td>
				<td></td>
				<td><input id="select_all" type="checkbox"> All tickets</td>
              </tr>
            </tfoot>
          </table>
			<input class="btn btn-primary" type="submit" name="submit" value="Sell selection" id="sell_selected_tickets">
			<input class="btn btn-danger" type="submit" name="submit" value="Cancel booking for selection" id="cancel_booking_selected_tickets">
	  	<br><br>
        </div>
      </div>
    </div>
  </body>
</html>
            
            
            