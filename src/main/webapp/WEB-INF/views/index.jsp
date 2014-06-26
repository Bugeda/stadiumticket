<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <!--css rules-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/jquery.dataTables.css"/>

    <!--google font-->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css">

    <!--jQuery-->
    <script src="<%= request.getContextPath() %>/js/jquery.js" language="javascript" type="text/javascript"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.dataTables.js" language="javascript" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#event_list').dataTable({
                "paging": false,
                "stateSave": true,
                "autoWidth": true
            });
        });
    </script>
    <style type="text/css">
    </style>
    <title>Main page</title>
</head>
  <body>
  <div class="global">
    <header>
    
        <h1>Events<a id="add_event" href="<c:url value="new_event"/>">&nbsp;<img src="<%= request.getContextPath() %>/images/add_event_button.png"></a></h1>

        <!-- <form action="" method="post" id="search_form"> -->
        <!--     <input type="search" id="search" value="please search upcoming events here"> -->
        <!-- </form> -->
        <a id="past_events" href=""><img src="<%= request.getContextPath() %>/images/past_events.png"> past events</a>
        <a id="statistics" href=""><img src="<%= request.getContextPath() %>/images/stats.png"> statistics</a>
        <a id="logo" href=""><img src="<%= request.getContextPath() %>/images/logo.png"></a>
    </header>
    <div id="center">
        <table class="hover" id="event_list">
            <thead>
            <tr>
                <th>
                    <p>Name</p>
                </th>
                <th>
                    <p>Date</p>
                </th>
                <th width="40%">
                    <p>Actions</p>
                </th>
            </tr>
            </thead>
            <c:forEach items="${events}" var="events">
			<tr>
			
			
		  		<td><c:out value="${events.getEventName()}"></c:out></td>
		    	<td><fmt:formatDate value="${events.getEventDate()}" pattern="dd.MM.yy HH:mm" /></td>	
         		<td class="action_list" ><img src="<%= request.getContextPath() %>/images/sell_ticket.png"><img src="<%= request.getContextPath() %>/images/book_ticket.png"><img src="<%= request.getContextPath() %>/images/edit_event.png"></td>
            </tr>      
            </c:forEach>     
        </table>
    </div>
</div>
  </body>
</html>


