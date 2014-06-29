<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>stadiumticket - list of events</title>
    <!-- css -->
    <link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/bootstrap-theme.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.dataTables.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/main.css" type="text/css" rel="stylesheet">

    <!-- js -->
    <script src="<%= request.getContextPath() %>/js/jquery.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.dataTables.js"></script>
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
	<div class="col-xs-1 col-md-1"><a href=""><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	<div class="col-xs-6 col-md-9">
	   <h3>
		<a id="arrow_back" href="<c:url value="index"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>&nbsp;Past events
	   </h3>
	 </div>
	 </div>
       </div>
       <div class="row">
	 <div>
	   <table class="hover" id="event_list">
	     <thead>
	       <tr>
		 <th>
		   <p>Name</p>
		 </th>
		<th>
		  <p>Date & time</p>
		</th>
	      </tr>
	    </thead>
	    <c:forEach items="${events}" var="events">
	    <tr class="event">
			<td><c:out value="${events.getEventName()}"></c:out></td>
	      	<td><fmt:formatDate value="${events.getEventDate()}" pattern="dd.MM.yy HH:mm" /></td>
	      	<td></td><td>	      	
	    </tr>
	    </c:forEach> 
	   </table>
	 </div>
       </div>
     </div>

  </body>
</html>
