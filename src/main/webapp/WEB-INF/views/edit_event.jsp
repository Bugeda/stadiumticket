<%@page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>stadiumticket - edit event</title>
  <!-- css -->
    <link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/bootstrap-theme.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.dataTables.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.datetimepicker.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/main.css" type="text/css" rel="stylesheet">

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
	<div class="col-xs-1 col-md-1"><a href="index"><img class="img-responsive" src="<%= request.getContextPath() %>/images/logo.png"></a></div>
	<div class="col-xs-6 col-md-9">
	   <h3>
		<a id="arrow_back" href="index"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>&nbsp;New event
	   </h3>
	   <h2 id="event_name">Event name</h2>
	 </div>
	 </div>
    <div class="row">
	<div class="col-md-5">
	 	<form:form class="form-horizontal"  method="post" action="${pageContext.request.contextPath}/edit_event" modelAttribute="editEvent">
	    <div class="form-group">
	      <label for="title">Title:</label>
	      <form:input class="form-control"  path="eventName" id="title"/>
	      <form:errors path="eventName" cssClass="alert-danger" />
	    </div>
	    <div class="form-group">
            <fmt:formatDate value="${editEvent.eventDate}" pattern="dd-MM-yyyy HH:mm" var="formattedDate"/>
	      <label for="eventDate">Start at:</label>
	      <input class="form-control"  name="eventDate"  id="start" value="${formattedDate}"/>
	      <form:errors path="eventDate" cssClass="alert-danger" />
	    </div>
	    <div class="form-group">
	      <label for="booking_time" id="label_booking">Booking cancel time (min):</label>
	      <form:input class="form-control" type="text" path="bookingCanceltime" id="booking_time"/>
	      <form:errors path="bookingCanceltime" cssClass="alert-danger" />
	    </div>
	    <div class="form-group">
	        <form:errors path="sectorPriceSet" cssClass="alert-danger" />
	      <c:forEach items="${editEvent.sectorPriceSet}" var="sectorPrice" varStatus="priceStatus">
	        <form:hidden  id="s${sectorPrice.sector.id}" path="sectorPriceSet[${priceStatus.index}].price"/>
	      </c:forEach>
	 	<form:hidden path="id"/>
	    </div>
	    <div class="form-group">
	      <input class="btn btn-primary" type="submit" name="submit" value="Save event" id="event_save">
	 	  <input class="btn btn-warning" type="submit" name="submit" value="Cancel changes" id="event_cancel">
   	      <input class="btn btn-danger" type="button" value="Delete event" id="event_delete">
	    </div>
      </form:form>

	  <form:form id="confirm_deletion_form"  method="post" action="${pageContext.request.contextPath}/delete_event" modelAttribute="editEvent">
	    <div class="form-group">
	      <div class="alert-warning" role="alert">
			Are you sure you want to delete event?<br>
			Please enter word DELETE here:<br>
			<form:hidden id="id" path="id"/>
			<input class="form-control" type="text" name="confirm_delete" id="confirm_deletion_text" maxlength="10" size="15" placeholder="enter DELETE here for confirmation"><br>
			<input class="btn btn-danger"  type="submit" value="Ok, delete event" id="confirm_deletion">
			<input class="btn btn-warning" type="button" value="Cancel deletion" id="cancel_deletion">
	      </div>
	      <div class="alert-error alert-dismissable" id="wrong_confirmation" role="alert">
		  You must enter the word DELETE(capitals, without spaces).
	      </div>
	    </div>
	  </form:form>

	</div>
	<div class="col-md-7">
          <img id="new_event_img" class="map" usemap="#stadium" src="<%= request.getContextPath() %>/images/stadium_plan.png">
	  <map name="stadium">

	 	<c:forEach items="${editEvent.sectorPriceSet}" var="sectorPrice">
	         <input id="price_${sectorPrice.sector.id}" size="4" maxlength="4" value="${sectorPrice.price}"/>
	    </c:forEach>

	    <area id="1" alt="1" title="1"  shape="poly" coords="320,93,364,92,364,54,364,33,436,34,437,47,422,47,423,119,384,120,384,108,321,110" />
	    <area id="2" alt="2" title="2"  shape="poly" coords="431,119,431,53,446,53,446,20,485,20,520,29,478,117" />
	    <area id="3" alt="3" title="3"  shape="poly" coords="485,122,526,32,564,55,586,92,497,137" />
	    <area id="4" alt="4" title="4"  shape="poly" coords="509,136,590,103,601,162,512,163" />
	    <area id="5" alt="5" title="5"  shape="rect" coords="500,166,602,218" />
	    <area id="6" alt="6" title="6" shape="rect" coords="499,222,603,278" />
	    <area id="7" alt="7" title="7"  shape="rect" coords="498,284,602,332" />
	    <area id="8" alt="8" title="8" shape="poly" coords="501,340,565,339,562,383,501,361" />
	    <area id="9" alt="9" title="9"  shape="poly" coords="486,378,498,364,559,389,541,418,511,438" />
	    <area id="10" alt="10" title="10"  shape="poly" coords="458,382,482,381,514,470,472,482,463,461,458,443" />
	    <area id="11" alt="11" title="11"  shape="rect" coords="398,380,453,467" />
	    <area id="12" alt="12" title="12"  shape="rect" coords="341,380,395,467" />
	    <area id="13" alt="13" title="13"  shape="rect" coords="285,381,336,468" />
	    <area id="14" alt="14" title="14"  shape="rect" coords="227,381,278,468" />
	    <area id="15" alt="15" title="15"  shape="rect" coords="169,382,220,469" />
	    <area id="16" alt="16" title="16"  shape="poly" coords="144,381,163,382,163,464,154,467,151,482,105,472" />
	    <area id="17" alt="17" title="17"  shape="poly" coords="35,406,126,366,138,377,100,470,61,445" />
	    <area id="18" alt="18" title="18"  shape="poly" coords="21,343,123,340,123,361,33,399,26,375" />
	    <area id="19" alt="19" title="19" shape="rect" coords="23,282,124,333" />
	    <area id="20" alt="20" title="20" shape="rect" coords="22,224,123,275" />
	    <area id="21" alt="21" title="21"  shape="rect" coords="22,167,123,218" />
	    <area id="22" alt="22" title="22" shape="poly" coords="31,103,123,141,123,160,23,161,24,130"/>
	    <area id="23" alt="23" title="23" shape="poly" coords="35,95,60,59,97,33,139,122,125,134"/>
	    <area id="24" alt="24" title="24" shape="poly" coords="105,29,141,19,178,17,178,52,191,54,192,119,145,120"/>
	    <area id="25" alt="25" title="25" shape="poly" coords="186,32,263,33,266,47,259,46,261,92,303,93,304,109,241,109,238,120,202,119,201,56,201,46,185,46"/>
	    <area id="26" alt="vipD" title="vipD" shape="rect" coords="152,472,468,501" />
	    <area id="27" alt="vipA" title="vipA" shape="rect" coords="266,32,356,83" />

	  </map>
	  <br>
	  <div class="row">
	    <div style="display:none;" class="alert-dismissible alert alert-danger" role="alert">
	      Only numbers allowed here!
	      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
	    </div>
	  </div>
	</div>
      </div>
    </div>
  </body>
</html>

