<%@page import="logic.bean.BarUserBean"%>
<%@page import="logic.controller.FindBarUserController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <% FindBarUserController barController;%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Search bar page</title>

 	<style>
       
     	#map {
       		height: 450px;  
      	  	width: 100%;  
       	}
    </style>

</head>
<body>
	<jsp:include page="/topBan.jsp"/>
	<h4>->Search bar</h4><hr>
	<div id="map"></div>
	
	<script>
	var map, infoWindow;
	function initMap() {
		<%	barController = FindBarUserController.getInstance();%>
		var map = new google.maps.Map(document.getElementById('map'), {
	          center: new google.maps.LatLng(<%=Float.parseFloat(barController.getLa().toString())%>, <%=Float.parseFloat(barController.getLn().toString())%>),
	          zoom: 8
	        });
		infoWindow = new google.maps.InfoWindow;
		
		if (navigator.geolocation) {
	          navigator.geolocation.getCurrentPosition(function(position) {
	            var pos = {
	              lat: position.coords.latitude,
	              lng: position.coords.longitude
	            };

	            infoWindow.setPosition(pos);
	            infoWindow.setContent('Your current position');
	            infoWindow.open(map);
	            map.setCenter(pos);
	          }, function() {
	            handleLocationError(true, infoWindow, map.getCenter());
	          });
	        } else {
	          // Browser doesn't support Geolocation
	          handleLocationError(false, infoWindow, map.getCenter());
	        }
		
		infoWindow = new google.maps.InfoWindow;
		<%	barController = FindBarUserController.getInstance();
	  	for(BarUserBean ub : barController.getBars()) {%>
			var infow =  new google.maps.InfoWindow;
	  		var p = {lat: <%=Float.parseFloat(ub.getLatitudine().toString())%>, lng: <%=Float.parseFloat(ub.getLongitudine().toString())%>};
			var marker = new google.maps.Marker({position: p, map: map}); 
			infow.setContent("<h3>" + '<%= ub.getBarUsername().toString()%>' + "<br>" + "<h5>" + '<%= ub.getBarAddress().toString()%>' );
			infow.open(map,marker);
		<%}%>
  		
			 map.setCenter(pos);
	}
	
	function handleLocationError(browserHasGeolocation, infoWindow, pos) {
		infoWindow.setContent('Your sign in position')
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
                              'Error: The Geolocation service failed.' :
                              'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
      }
    </script>
    
    <script async defer
    	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBa9OhzI_r8hilU0D9TzQf44mipf2W63pY&callback=initMap">
    </script>
    
</body>
</html>