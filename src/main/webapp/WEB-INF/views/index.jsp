<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="index.title" /></title>
    <!-- css -->
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
     <style type="text/css">
     </style>
   </head>
   <body>
     <div class="container">
       <div class="row">
	 <div class="col-xs-1 col-md-1"><a href="<c:url value="index"/>"><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	 <div class="col-xs-6 col-md-9">
	   <h1><spring:message code="index.pageTitle" />
	     <a href="<c:url value="/new_event"/>"><img src="<%= request.getContextPath() %>/images/add_event_button.png" title="<spring:message code="newevent.pageTitle" />"></a>
	   </h1>
	 </div>
	 <div class="col-xs-4 col-md-2">
	   <a id="past_events" href="<c:url value="/past_events"/>"><img src="<%= request.getContextPath() %>/images/past_events.png"><spring:message code="pastevents.pageTitle" /></a><br>
	   <a id="statistics" href="<c:url value=""/>"><img src="<%= request.getContextPath() %>/images/stats.png"> statistics</a>
	 </div>
       </div>
       <div class="row">
	 <div>
	   <table class="hover" id="events_list">
	     <thead>
	       <tr>
		 <th>
		   <p><spring:message code="event.hName" /></p>
		 </th>
		<th>
		  <p><spring:message code="event.hDatetime" /></p>
		</th>
		<th>
		  <p><spring:message code="event.hActions" /></p>
		</th>
	      </tr>
	    </thead>
	    <c:forEach items="${events}" var="event">
	    <tr class="event">
		<td class="event_name"><c:out value="${event.eventName}"></c:out></td>
		<td class="event_datetime"><fmt:formatDate value="${event.eventDate}" pattern="dd-MM-yyyy HH:mm" /></td>
	      	<td class="action_list" >	      			      	
				<a href="<c:url value="/tickets/sell?id=${event.id}"/>" title="<spring:message code="selltickets.pageTitle" />">
					<img src="<%= request.getContextPath() %>/images/sell_ticket.png"></a>
				<a href="<c:url value="/tickets/book?id=${event.id}"/>" title="<spring:message code="booktickets.pageTitle" />">
					<img src="<%= request.getContextPath() %>/images/book_ticket.png"></a>
				<a href="<c:url value="booking/search?id=${event.id}"/>" title="<spring:message code="searchbooked.pageTitle" />">
					<img src="<%= request.getContextPath() %>/images/search_booked.png"></a>				
				<a href="<c:url value="/edit_event?id=${event.id}"/>" title="<spring:message code="editevent.pageTitle" />">
					<img src="<%= request.getContextPath() %>/images/edit_event.png">
				</a>
	      	</td>
	    </tr>
	    </c:forEach> 
	   </table>
	 </div>
       </div>
     </div>

  </body>
</html>
