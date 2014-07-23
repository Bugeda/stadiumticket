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
    <title><spring:message code="booktickets.title" /></title>
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
<div class="container">
<div class="row">
	<div class="col-xs-1 col-md-1"><a href="<c:url value="../index"/>"><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	<div class="col-xs-6 col-md-9">
        <h3>
            <a id="arrow_back" href="<c:url value="../index"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>&nbsp;
            <spring:message code="booktickets.pageTitle" />
        </h3>
        <h2 id="event_name"><c:out value="${event.eventName}"></c:out>
        </h2>
    </div>
</div>
<div class="row">
  <div class="col-md-5">
   	<form:form method="post" action="${pageContext.request.contextPath}/tickets/book/${event.id}" modelAttribute="newCustomer">
  	    <label for="booking_name"><spring:message code="booking.customerNameTitle" /><img src="<%= request.getContextPath() %>/images/arrow_down.png"></label>
        <input class="form-control" type="text" name="customerName" id="booking_name" title="<spring:message code="booking.customerName" />" 
        placeholder="<spring:message code="booking.customerName" />">     
        <b><spring:message code="ticketlist.tickets" />:</b>              
        <table class="table" id="ticket_list">
            <thead>
            <tr>
                <td>№</td>
                <td><spring:message code="ticketlist.sector" /></td>
                <td><spring:message code="ticketlist.row" /></td>
                <td><spring:message code="ticketlist.seat" /></td>
                <td><spring:message code="ticketlist.price" /></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4"><b><spring:message code="ticketlist.totalPrice" />:</b></td>
                <td id="total_price"></td>
                <td>UAH</td>
            </tr>
            </tfoot>
        </table>
        <br>
        <!-- <input class="btn" type="button" id="add_random_ticket" value="add random ticket"> -->
        <input class="btn" type="submit" name="submit" value="Book tickets" id="book_tickets">
    </form:form>
  </div>
  <div class="col-md-7">
    <img id="new_event_img" class="map" usemap="#stadium" src="<%= request.getContextPath() %>/images/stadium_plan.png">
     <map id="disable_inputs" name="stadium">
	 	<c:forEach items="${sectorPrices}" var="sectorPrice">	  
	    <input type="text" id="price_${sectorPrice.getSector().getId()}" size="4" maxlength="4" value="${sectorPrice.getPrice()}">	  
	    </c:forEach> 
			  	
	  	<area id="1" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=1"/>" alt="1" title="1"  
	  		shape="poly" coords="320,93,364,92,364,54,364,33,436,34,437,47,422,47,423,119,384,120,384,108,321,110" />
	    <area id="2" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=2"/>" alt="2" title="2"  
	    	shape="poly" coords="431,119,431,53,446,53,446,20,485,20,520,29,478,117" />
	    <area id="3" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=3"/>" alt="3" title="3"  
	    	shape="poly" coords="485,122,526,32,564,55,586,92,497,137" />
	    <area id="4" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=4"/>" alt="4" title="4"  
	    	shape="poly" coords="509,136,590,103,601,162,512,163" />
	    <area id="5" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=5"/>" alt="5" title="5"  
	    	shape="rect" coords="500,166,602,218" />
	    <area id="6" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=6"/>" alt="6" title="6" 
			shape="rect" coords="499,222,603,278" />
	    <area id="7" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=7"/>" alt="7" title="7"  
	    	shape="rect" coords="498,284,602,332" />
	    <area id="8" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=8"/>" alt="8" title="8" 
	    	shape="poly" coords="501,340,565,339,562,383,501,361" />
	    <area id="9" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=9"/>" alt="9" title="9"  
	    	shape="poly" coords="486,378,498,364,559,389,541,418,511,438" />
	    <area id="10" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=10"/>" alt="10" title="10"  
	    	shape="poly" coords="458,382,482,381,514,470,472,482,463,461,458,443" />
	    <area id="11" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=11"/>" alt="11" title="11"  
	    	shape="rect" coords="398,380,453,467" />
	    <area id="12" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=12"/>" alt="12" title="12"  
	    	shape="rect" coords="341,380,395,467" />
	    <area id="13" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=13"/>" alt="13" title="13"  
	    	shape="rect" coords="285,381,336,468" />
	    <area id="14" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=14"/>" alt="14" title="14"  
	    	shape="rect" coords="227,381,278,468" />
	    <area id="15" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=15"/>" alt="15" title="15"  
	    	shape="rect" coords="169,382,220,469" />
	    <area id="16" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=16"/>" alt="16" title="16"  
	    	shape="poly" coords="144,381,163,382,163,464,154,467,151,482,105,472" />
	    <area id="17" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=17"/>" alt="17" title="17"  
	    	shape="poly" coords="35,406,126,366,138,377,100,470,61,445" />
	    <area id="18" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=18"/>" alt="18" title="18"  
	    	shape="poly" coords="21,343,123,340,123,361,33,399,26,375" />
	    <area id="19" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=19"/>" alt="19" title="19" 
	    	shape="rect" coords="23,282,124,333" />
	    <area id="20" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=20"/>" alt="20" title="20" 
	    	shape="rect" coords="22,224,123,275" />
	    <area id="21" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=21"/>" alt="21" title="21"  
	    	shape="rect" coords="22,167,123,218" />
	    <area id="22" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=22"/>" alt="22" title="22" 
	    	shape="poly" coords="31,103,123,141,123,160,23,161,24,130"/>
	    <area id="23" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=23"/>" alt="23" title="23" 
	    	shape="poly" coords="35,95,60,59,97,33,139,122,125,134"/>
	    <area id="24" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=24"/>" alt="24" title="24" 
	    	shape="poly" coords="105,29,141,19,178,17,178,52,191,54,192,119,145,120"/>
	    <area id="25" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=25"/>" alt="25" title="25" 
	    	shape="poly" coords="186,32,263,33,266,47,259,46,261,92,303,93,304,109,241,109,238,120,202,119,201,56,201,46,185,46"/>
	    <area id="26" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=26"/>" alt="VIP A" title="VIP A" 
	    	shape="rect" coords="266,32,356,83" />
	    <area id="27" href="<c:url value="/tickets/get_sector_seats?event=${event.id}&sector=27"/>" alt="VIP D" title="VIP D" 
	    	shape="rect" coords="152,472,468,501" />	    
	  </map>
  </div>
</div>
</div>
<br/>

<div id="normal_sector" class="container seats">
<table class="table table-condensed table-responsive sell_tickets_header">
    <tbody>
    <tr>        
        <td><spring:message code="ticketlist.sector" /> <span class="sector_name"></span> <spring:message code="ticketlist.seats" />:</td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.vacant" /></td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.booked" /></td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.occupied" /></td>
    </tr>
    </tbody>
</table>
<hr>
<div class="table-responsive">
<table class="table table-condensed table-responsive sell_tickets_table">
<tbody>
<% for (int i=1;i<21;i++) {%>
<tr>
   <td> <div><%=i %></div> </td>
   <% for (int j=1;j<51;j++) {%>
   <td id="<%=i %>_<%=j %>"><div><%=j %></div></td>
   <%} %>
</tr>
<%} %>
</tbody>
</table>
</div>
</div>
<br/>
<div id="vip_sector" class="container seats">
<table class="table table-condensed table-responsive sell_tickets_header">
    <tbody>
    <tr>        
        <td><spring:message code="ticketlist.sector" /> <span class="sector_name"></span> <spring:message code="ticketlist.seats" />:</td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.vacant" /></td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.booked" /></td>
        <td><div>42</div></td>
        <td><spring:message code="sectorstatus.occupied" /></td>
    </tr>
    </tbody>
</table>
<hr>
<div class="table-responsive">
<table class="table table-condensed table-responsive sell_tickets_table">
<tbody>
<% for (int i=1;i<11;i++) {%>
<tr>
   <td> <div><%=i %></div> </td>
   <% for (int j=1;j<21;j++) {%>
   <td id="<%=i %>_<%=j %>"><div><%=j %></div></td>
   <%} %>
</tr>
<%} %>
</tbody>
</table>
</div>
</div>
</body>
</html>