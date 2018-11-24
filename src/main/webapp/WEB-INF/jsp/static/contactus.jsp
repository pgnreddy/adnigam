<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <div class="container" id="columns">
	<div id="map" style="float:left; width:65%;"></div>
						<div  style="float:right; width:30%;">
                            <div><b>Address:</b></div>
                            <div>SCB #3-14-022,1st Floor<br/>SP Road, Begumpet, Secunderabad,<br/> Hyderabad, Telangana - 500003.</div>
                            <div><b>Phone</b>:040-48516780</div>
                            <div><b>Email:</b><a href="mailto:info@adnigam.com" target="_top">info@adnigam.com</a></div>
                       
						</div>
	
    </div>
	
	

<style>
#map {
	height: 300px;
	width : 600px;
	}
</style>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAgCsy9D_34h1vfq3BQwgjVioUufvegyDA&callback=initMap">
</script>
<script>
      function initialize() {
        var mapCanvas = document.getElementById('map');
        var mapOptions = {
          center: new google.maps.LatLng(17.443926, 78.481398),
          zoom: 17,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        var map = new google.maps.Map(mapCanvas, mapOptions)
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(17.443926, 78.481398),
        });
        marker.setMap(map);

      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>	
