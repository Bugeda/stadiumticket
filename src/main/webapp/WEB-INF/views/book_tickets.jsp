<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <!--css rules-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/book_tickets.css"/>

    <!--google font-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.maphighlight.js"></script>
    <script type="text/javascript">
      $(function() {
      $('.map').maphilight();
      });
    </script>
    <title>Booked tickets</title>
</head>
<body>
<div class="global">
    <header>
        <h3>Events</h3>
        <h5>sell or book tickets for</h5>

        <h2 id="event_name">Event name</h2>
        <a id="logo" href=""><img src="<%= request.getContextPath() %>/images/logo.png"></a>
        <a id="arrow_back" href="<c:url value="/"/>"><img src="<%= request.getContextPath() %>/images/arrow_back.png"></a>
    </header>

    <div id="left_panel">
        <form action="" method="post" id="search_form">
            <span><b>Search for booked tickets:</b></span>
            <br/>
            <table class="total">
                <tr>
                    <th><label for="search">Name: </label></th>
                    <td><input type="search" id="search" value="please enter name or surname"></td>
                </tr>
            </table>
            <br/>
            <span><b>Ticket(s):</b></span>
            <br/>

            <div id="results_of_search">
                <span id="results">No tickets selected or found:</span>
            </div>
            <br/>
            <table class="buttons">
                <tr>
                    <td><input type="button" value="Sell tickets" id="sell_tickets"></td>
                    <td id="event_can"><input type="button" value="Save changes" id="save_changes"></td>
                </tr>
            </table>
            <table class="cancel_changes">
                <tr>
                    <td><input type="button" value="Cancel changes" id="cancel_changes"></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="right_panel">
        <img id="new_event_img" class="map" usemap="#stadium" src="<%= request.getContextPath() %>/images/stadium_plan.png">
	<map name="stadium">
	  <area id="vipD" alt="vipD" title="vipD" href="" shape="rect" coords="152,472,468,501" />
	  <area id="vipA" alt="vipA" title="vipA" href="" shape="rect" coords="266,32,356,83" />
	  <area id="1" alt="1" title="1" href="" shape="poly" coords="320,93,364,92,364,54,364,33,436,34,437,47,422,47,423,119,384,120,384,108,321,110" />
	  <area id="2" alt="2" title="2" href="" shape="poly" coords="431,119,431,53,446,53,446,20,485,20,520,29,478,117" />
	  <area id="3" alt="3" title="3" href="" shape="poly" coords="485,122,526,32,564,55,586,92,497,137" />
	  <area id="4" alt="4" title="4" href="" shape="poly" coords="509,136,590,103,601,162,512,163" />
	  <area id="5" alt="5" title="5" href="" shape="rect" coords="500,166,602,218" />
	  <area id="6" alt="6" title="6" href="" shape="rect" coords="499,222,603,278" />
	  <area id="7" alt="7" title="7" href="" shape="rect" coords="498,284,602,332" />
	  <area id="8" alt="8" title="8" href="" shape="poly" coords="501,340,565,339,562,383,501,361" />
	  <area id="9" alt="9" title="9" href="" shape="poly" coords="486,378,498,364,559,389,541,418,511,438" />
	  <area id="10" alt="10" title="10" href="" shape="poly" coords="458,382,482,381,514,470,472,482,463,461,458,443" />
	  <area id="11" alt="11" title="11" href="" shape="rect" coords="398,380,453,467" />
	  <area id="12" alt="12" title="12" href="" shape="rect" coords="341,380,395,467" />
	  <area id="13" alt="13" title="13" href="" shape="rect" coords="285,381,336,468" />
	  <area id="14" alt="14" title="14" href="" shape="rect" coords="227,381,278,468" />
	  <area id="15" alt="15" title="15" href="" shape="rect" coords="169,382,220,469" />
	  <area id="19" alt="19" title="19" href="" shape="rect" coords="23,282,124,333" />
	  <area id="20" alt="20" title="20" href="" shape="rect" coords="22,224,123,275" />
	  <area id="21" alt="21" title="21" href="" shape="rect" coords="22,167,123,218" />
	  <area id="16" alt="16" title="16" href="" shape="poly" coords="144,381,163,382,163,464,154,467,151,482,105,472" />
	  <area id="17" alt="17" title="17" href="" shape="poly" coords="35,406,126,366,138,377,100,470,61,445" />
	  <area id="18" alt="18" title="18" href="" shape="poly" coords="21,343,123,340,123,361,33,399,26,375" />
	  <area id="22" alt="22" title="22" href="" shape="poly" coords="31,103,123,141,123,160,23,161,24,130"/>
	  <area id="23" alt="23" title="23" href="" shape="poly" coords="35,95,60,59,97,33,139,122,125,134"/>
	  <area id="24" alt="24" title="24" href="" shape="poly" coords="105,29,141,19,178,17,178,52,191,54,192,119,145,120"/>
	  <area id="25" alt="25" title="25" href="" shape="poly" coords="186,32,263,33,266,47,259,46,261,92,303,93,304,109,241,109,238,120,202,119,201,56,201,46,185,46"/>
	</map>
    </div>
</div>
</body>
</html>
