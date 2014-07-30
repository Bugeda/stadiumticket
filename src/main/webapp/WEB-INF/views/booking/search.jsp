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

<c:if test="${!empty message}">
       	<script type="text/javascript">
     	 $(document).ready(function() {
                 $('#success').modal('show');
                 setTimeout(function(){
                	 $('#success').modal('hide')
                	    }, 2000);
     	 })
     	</script>
    </c:if>
    <c:if test="${!empty warning}">
       	<script type="text/javascript">
     	 $(document).ready(function() {
                 $('#warning').modal('show');
                 setTimeout(function(){
                	 $('#warning').modal('hide')
                	    }, 2000);
     	 })
     	</script>
    </c:if>

  <div id="success" class="modal fade">
    <div class="modal-dialog">    
        <div class="alert alert-success" role="alert">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="modal.close" /></span></button>
               <c:out value="${message}"></c:out>
        </div>
      </div><!-- /.modal-content -->
  </div><!-- /.modal --> 
  
  <div id="warning" class="modal fade">
    <div class="modal-dialog">    
        <div class="alert alert-warning" role="alert">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="modal.close" /></span></button>
               <c:out value="${warning}"></c:out>
        </div>
      </div><!-- /.modal-content -->
  </div><!-- /.modal --> 
<div class="container">
<div class="row">
	<div class="col-xs-1 col-md-1"><a href="<c:url value="../index"/>"><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	<div class="col-xs-6 col-md-6">
        <h3>
            <a id="arrow_back" href="<c:url value="../index"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png" title="<spring:message code="index.pageTitle" />"></a>&nbsp;
            <spring:message code="searchbooked.pageTitle" />
        </h3>
        <h2 id="event_name"><c:out value="${event.eventName}"></c:out>
        </h2>
    </div>
</div>
<div class="row">
  <div class="col-md-5">
  		<form:form class="form-horizontal"  method="post" action="${pageContext.request.contextPath}/booking/search?id=${event.id}" modelAttribute="customer">
            <label for="search_booking_name"><spring:message code="search.Name" /><img src="<%= request.getContextPath() %>/images/arrow_down.png"></label>
            <spring:message code="search.Name.hint" var="msg"/>
	        <input class="form-control" type="text" name="customerName" id="search_booking_name" placeholder="${msg}" maxlength="50" value="${customerName}">
	        <input type="hidden" name="id" value="${event.id}">
        </form:form>
        </div>
      </div>
      <br>
      <h4><spring:message code="search.results" /></h4>
      <div class="row">
        <div class="col-md-12">
          <table class="table hover" id="booking_search_results">
            <thead>
              <tr style="font-weight: bold;">
                <td>№</td>
                <td><spring:message code="ticketlist.sector" /></td>
                <td><spring:message code="ticketlist.row" /></td>
                <td><spring:message code="ticketlist.seat" /></td>
                <td><spring:message code="ticketlist.price" /></td>
                <td><spring:message code="search.client" /></td>
				<td><spring:message code="search.markTicket" /></td>
              </tr>
            </thead>
            <tbody>
  			<c:forEach items="${bookingSet}" var="booking">
  			   <tr class="ticket">
                <td class="booking_id"><c:out value="${booking.id}"></c:out></td>
                <td><c:out value="${booking.ticket.seat.sector.name}"></c:out></td>
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
                <td colspan="4"><b><spring:message code="ticketlist.totalPrice" />:</b></td>
                <td style="font-weight:bold"><span id="total_price">0</span><spring:message code="money" /></td>
				<td></td>
				<td><input id="select_all" type="checkbox"><spring:message code="search.markAll" /></td>
              </tr>
            </tfoot>
          </table>
            <spring:message code="search.sell" var="msg"/>
			<input class="btn btn-primary" type="submit" name="submit" value="${msg}" id="sell_selected_tickets">
			<spring:message code="search.cancel" var="msg"/>
			<input class="btn btn-danger" type="submit" name="submit" value="${msg}" id="cancel_booking_selected_tickets">
	  	<br><br>
        </div>
      </div>
    </div>
  </body>
</html>
            
            
            