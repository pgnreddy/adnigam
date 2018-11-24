
$(function() {
	
delay			= 30000;
	
	(function ping() {
		$.ajax({
			type:	'GET',
			url: 	'ping.php'
		})
		setTimeout(ping, delay);
	})();
	
})