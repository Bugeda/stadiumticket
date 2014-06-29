<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>stadiumticket - new\edit event</title>
    <!-- css -->
    <link href="<%= request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/bootstrap-theme.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/jquery.dataTables.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/main.css" rel="stylesheet">

    <!-- js -->
    <script src="<%= request.getContextPath() %>/js/jquery.js"></script>
    <script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.dataTables.js"></script>
    <script src="<%= request.getContextPath() %>/js/jquery.maphighlight.js"></script>
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
		<a id="arrow_back" href="<c:url value="index"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>&nbsp;New event
	   </h3>
	 </div>
	 </div>
       </div>
      <div class="row">
	<div class="col-md-5">
          <form class="form-horizontal" action="" method="post">
	    <div class="form-group">
	      <label for="title">Title:</label>
	      <input class="form-control" type="text" id="title" placeholder="please enter event name"/>
	    </div>
	    <div class="form-group">
	      <label for="title">Date:</label>
	      <input class="form-control" type="date" id="date" placeholder="please enter date"/>
	    </div>
	    <div class="form-group">
	      <label for="start_time">Start time:</label>
	      <input class="form-control" type="text" id="start_time" placeholder="please enter time"/>
	    </div>
	    <div class="form-group">
	      <label for="booking_time" id="label_booking">Booking cancel time (min):</label>
	      <input class="form-control" type="text" name="booking" id="booking_time">
	    </div>
	    <div class="form-group">
	      <input type="hidden" id="vipA" placeholder="vipA">
	      <input type="hidden" id="vipD" placeholder="vipD">
	      <input type="hidden" id="s1" placeholder="sector1">
	      <input type="hidden" id="s2" placeholder="sector2">
	      <input type="hidden" id="s3" placeholder="sector3">
	      <input type="hidden" id="s4" placeholder="sector4">
	      <input type="hidden" id="s5" placeholder="sector5">
	      <input type="hidden" id="s6" placeholder="sector6">
	      <input type="hidden" id="s7" placeholder="sector7">
	      <input type="hidden" id="s8" placeholder="sector8">
	      <input type="hidden" id="s9" placeholder="sector9">
	      <input type="hidden" id="s10" placeholder="sector10">
	      <input type="hidden" id="s11" placeholder="sector11">
	      <input type="hidden" id="s12" placeholder="sector12">
	      <input type="hidden" id="s13" placeholder="sector13">
	      <input type="hidden" id="s14" placeholder="sector14">
	      <input type="hidden" id="s15" placeholder="sector15">
	      <input type="hidden" id="s16" placeholder="sector16">
	      <input type="hidden" id="s17" placeholder="sector17">
	      <input type="hidden" id="s18" placeholder="sector18">
	      <input type="hidden" id="s19" placeholder="sector19">
	      <input type="hidden" id="s20" placeholder="sector20">
	      <input type="hidden" id="s21" placeholder="sector21">
	      <input type="hidden" id="s22" placeholder="sector22">
	      <input type="hidden" id="s23" placeholder="sector23">
	      <input type="hidden" id="s24" placeholder="sector24">
	      <input type="hidden" id="s25" placeholder="sector25">
	    </div>
	    <div class="form-group">
	      <input class="btn btn-primary" type="button" value="Save event" id="event_save">
	      <input class="btn btn-warning" type="button" value="Cancel changes" id="event_cancel">
	      <input class="btn btn-danger" type="button" value="Delete event" id="event_delete">
	    </div>
            <!-- <table> -->
            <!--   <tr> -->
	    <!-- 	<th>Title:</th> -->
	    <!-- 	<td><input type="text" id="title" placeholder="please enter match name"></td> -->
            <!--   </tr> -->
            <!--   <tr> -->
	    <!-- 	<th>Date:</th> -->
	    <!-- 	<td><input type="text" id="date" placeholder="please enter date"/></td> -->
            <!--   </tr> -->
            <!--   <tr> -->
	    <!-- 	<th>Time:</th> -->
	    <!-- 	<td><input type="text" id="time" placeholder="please enter time"/></td> -->
            <!--   </tr> -->
            <!-- </table> -->
            <!-- <table class="booking"> -->
            <!--   <tr> -->
	    <!-- 	<th><label for="booking_time" id="label_booking">Booking cancel time (min):</label></th> -->
	    <!-- 	<td><select name="booking" id="booking_time"> -->
            <!--         <option value="30">30</option> -->
            <!--         <option value="60">60</option> -->
            <!--         <option value="90">90</option> -->
            <!--         <option value="120">120</option> -->
	    <!-- 	</select></td> -->
            <!--   </tr> -->
            <!-- </table> -->
            <!-- <br/> -->
            <!-- <span>event changed, but not saved (have to make change of text on JavaScript)</span> -->
            <!-- <br/> -->

            <!-- <table class="buttons"> -->
            <!--   <tr> -->
	    <!-- 	<td><input type="button" value="Save event" id="event_save"></td> -->
	    <!-- 	<td id="event_can"><input type="button" value="Cancel changes" id="event_cancel"></td> -->
            <!--   </tr> -->
            <!-- </table> -->
            <!-- <br/> -->
            <!-- <table class="delete_button"> -->
            <!--   <tr> -->
	    <!-- 	<td><input type="button" value="Delete event" id="event_delete"></td> -->
            <!--   </tr> -->
            <!-- </table> -->

          </form>
	</div>
	<div class="col-md-7">
          <img id="new_event_img" class="map" usemap="#stadium" src="<%= request.getContextPath() %>/images/stadium_plan.png">
	  <map name="stadium">
	    <input type="number" id="price_vipD" size="4" maxlength="4" placeholder="price">
	    <area id="vipD" alt="vipD" title="vipD" href="" shape="rect" coords="152,472,468,501" />

	    <input type="number" id="price_vipA" size="4" maxlength="4" placeholder="price">
	    <area id="vipA" alt="vipA" title="vipA" href="" shape="rect" coords="266,32,356,83" />

	    <input type="number" id="price_1" size="4" maxlength="4" placeholder="price">
	    <area id="1" alt="1" title="1" href="" shape="poly" coords="320,93,364,92,364,54,364,33,436,34,437,47,422,47,423,119,384,120,384,108,321,110" />

	    <input type="number" id="price_2" size="4" maxlength="4" placeholder="price">
	    <area id="2" alt="2" title="2" href="" shape="poly" coords="431,119,431,53,446,53,446,20,485,20,520,29,478,117" />

	    <input type="number" id="price_3" size="4" maxlength="4" placeholder="price">
	    <area id="3" alt="3" title="3" href="" shape="poly" coords="485,122,526,32,564,55,586,92,497,137" />

	    <input type="number" id="price_4" size="4" maxlength="4" placeholder="price">
	    <area id="4" alt="4" title="4" href="" shape="poly" coords="509,136,590,103,601,162,512,163" />

	    <input type="number" id="price_5" size="4" maxlength="4" placeholder="price">
	    <area id="5" alt="5" title="5" href="" shape="rect" coords="500,166,602,218" />

	    <input type="number" id="price_6" size="4" maxlength="4" placeholder="price">
	    <area id="6" alt="6" title="6" href="" shape="rect" coords="499,222,603,278" />

	    <input type="number" id="price_7" size="4" maxlength="4" placeholder="price">
	    <area id="7" alt="7" title="7" href="" shape="rect" coords="498,284,602,332" />

	    <input type="number" id="price_8" size="4" maxlength="4" placeholder="price">
	    <area id="8" alt="8" title="8" href="" shape="poly" coords="501,340,565,339,562,383,501,361" />

	    <input type="number" id="price_9" size="4" maxlength="4" placeholder="price">
	    <area id="9" alt="9" title="9" href="" shape="poly" coords="486,378,498,364,559,389,541,418,511,438" />

	    <input type="number" id="price_10" size="4" maxlength="4" placeholder="price">
	    <area id="10" alt="10" title="10" href="" shape="poly" coords="458,382,482,381,514,470,472,482,463,461,458,443" />

	    <input type="number" id="price_11" size="4" maxlength="4" placeholder="price">
	    <area id="11" alt="11" title="11" href="" shape="rect" coords="398,380,453,467" />

	    <input type="number" id="price_12" size="4" maxlength="4" placeholder="price">
	    <area id="12" alt="12" title="12" href="" shape="rect" coords="341,380,395,467" />

	    <input type="number" id="price_13" size="4" maxlength="4" placeholder="price">
	    <area id="13" alt="13" title="13" href="" shape="rect" coords="285,381,336,468" />

	    <input type="number" id="price_14" size="4" maxlength="4" placeholder="price">
	    <area id="14" alt="14" title="14" href="" shape="rect" coords="227,381,278,468" />

	    <input type="number" id="price_15" size="4" maxlength="4" placeholder="price">
	    <area id="15" alt="15" title="15" href="" shape="rect" coords="169,382,220,469" />

	    <input type="number" id="price_16" size="4" maxlength="4" placeholder="price">
	    <area id="16" alt="16" title="16" href="" shape="poly" coords="144,381,163,382,163,464,154,467,151,482,105,472" />

	    <input type="number" id="price_17" size="4" maxlength="4" placeholder="price">
	    <area id="17" alt="17" title="17" href="" shape="poly" coords="35,406,126,366,138,377,100,470,61,445" />

	    <input type="number" id="price_18" size="4" maxlength="4" placeholder="price">
	    <area id="18" alt="18" title="18" href="" shape="poly" coords="21,343,123,340,123,361,33,399,26,375" />

	    <input type="number" id="price_19" size="4" maxlength="4" placeholder="price">
	    <area id="19" alt="19" title="19" href="" shape="rect" coords="23,282,124,333" />

	    <input type="number" id="price_20" size="4" maxlength="4" placeholder="price">
	    <area id="20" alt="20" title="20" href="" shape="rect" coords="22,224,123,275" />

	    <input type="number" id="price_21" size="4" maxlength="4" placeholder="price">
	    <area id="21" alt="21" title="21" href="" shape="rect" coords="22,167,123,218" />

	    <input type="number" id="price_22" size="4" maxlength="4" placeholder="price">
	    <area id="22" alt="22" title="22" href="" shape="poly" coords="31,103,123,141,123,160,23,161,24,130"/>

	    <input type="number" id="price_23" size="4" maxlength="4" placeholder="price">
	    <area id="23" alt="23" title="23" href="" shape="poly" coords="35,95,60,59,97,33,139,122,125,134"/>

	    <input type="number" id="price_24" size="4" maxlength="4" placeholder="price">
	    <area id="24" alt="24" title="24" href="" shape="poly" coords="105,29,141,19,178,17,178,52,191,54,192,119,145,120"/>

	    <input type="number" id="price_25" size="4" maxlength="4" placeholder="price">
	    <area id="25" alt="25" title="25" href="" shape="poly" coords="186,32,263,33,266,47,259,46,261,92,303,93,304,109,241,109,238,120,202,119,201,56,201,46,185,46"/>
	  </map>
	  <br>
	  <div class="row">
	    <div style="display:none;" class="alert-dismissible alert alert-danger" role="alert">
	      Only numbers allowed here!
	      <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;
	    </div>
	  </div>
	</div>
      </div>
    </div>
  </body>
</html>
