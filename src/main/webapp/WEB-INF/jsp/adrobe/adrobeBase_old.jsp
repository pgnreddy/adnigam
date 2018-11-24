<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="541556104633-afu4splaieuqtnkro9ps4nch5dpp7hlt.apps.googleusercontent.com">
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/bootstrap/css/bootstrap.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/font-awesome/css/font-awesome.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/select2/css/select2.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/jquery.bxslider/jquery.bxslider.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/owl.carousel/owl.carousel.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/jquery-ui/jquery-ui.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/animate.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/reset.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/style.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/responsive.css"/>" />
        <%--<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/jquery.wm-zoom-1.0.min.css"/>" />--%>
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/zoom/jquery.lighter.css"/>" />
        <link rel="shortcut icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">
        <link rel="icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery/jquery-1.11.2.min.js"/>"></script>
        <meta name="google-site-verification" content="UA0pemseykFmw5uPJYJr1c1yLSUgBKIJ_kxgJSlqe0E" />
        <meta name="google-site-verification" content="hECuilLa4yTwIupcfHV5W2VyXNpTIafkn8qI9e8Wg0o" />
        <meta name="msvalidate.01" content="DB16814C6F7BEDC6D34F06B6A7EC8F74" />
        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-72951558-1', 'auto');
            ga('send', 'pageview');

        </script>
        <!-- bing adwords -->
        <script>(function (w, d, t, r, u) {
                var f, n, i;
                w[u] = w[u] || [], f = function () {
                    var o = {ti: "5284145"};
                    o.q = w[u], w[u] = new UET(o), w[u].push("pageLoad")
                }, n = d.createElement(t), n.src = r, n.async = 1, n.onload = n.onreadystatechange = function () {
                    var s = this.readyState;s && s !== "loaded" && s !== "complete" || (f(), n.onload = n.onreadystatechange = null)
                }, i = d.getElementsByTagName(t)[0], i.parentNode.insertBefore(n, i)
            })(window, document, "script", "//bat.bing.com/bat.js", "uetq");</script><noscript><img src="//bat.bing.com/action/0?ti=5284145&Ver=2" height="0" width="0" style="display:none; visibility: hidden;" /></noscript>
    <!-- Facebook Pixel Code -->
    <script>
        !function (f, b, e, v, n, t, s) {
            if (f.fbq)
                return;
            n = f.fbq = function () {
                n.callMethod ?
                        n.callMethod.apply(n, arguments) : n.queue.push(arguments)
            };
            if (!f._fbq)
                f._fbq = n;
            n.push = n;
            n.loaded = !0;
            n.version = '2.0';
            n.queue = [];
            t = b.createElement(e);
            t.async = !0;
            t.src = v;
            s = b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t, s)}(window,
                document, 'script', 'https://connect.facebook.net/en_US/fbevents.js');

        fbq('init', '137840149950227');
        fbq('track', "PageView");
        // ViewContent
        // Track key page views (ex: product page, landing page or article)
        fbq('track', 'ViewContent');


        // Search
        // Track searches on your website (ex. product searches)
        fbq('track', 'Search');


        // AddToCart
        // Track when items are added to a shopping cart (ex. click/landing page on Add to Cart button)
        fbq('track', 'AddToCart');


        // AddToWishlist
        // Track when items are added to a wishlist (ex. click/landing page on Add to Wishlist button)
        fbq('track', 'AddToWishlist');


        // InitiateCheckout
        // Track when people enter the checkout flow (ex. click/landing page on checkout button)
        fbq('track', 'InitiateCheckout');


        // AddPaymentInfo
        // Track when payment information is added in the checkout flow (ex. click/landing page on billing info)
        fbq('track', 'AddPaymentInfo');


        // Purchase
        // Track purchases or checkout flow completions (ex. landing on "Thank You" or confirmation page)
        fbq('track', 'Purchase', {value: '1.00', currency: 'INR'});


        // Lead
        // Track when a user expresses interest in your offering (ex. form submission, sign up for trial, landing on pricing page)
        fbq('track', 'Lead');


        // CompleteRegistration
        // Track when a registration form is completed (ex. complete subscription, sign up for a service)
        fbq('track', 'CompleteRegistration');


        // Other
        // 
        fbq('track', 'Other');
    </script>
    <noscript><img height="1" width="1" style="display:none"
                   src="https://www.facebook.com/tr?id=137840149950227&ev=PageView&noscript=1"
                   /></noscript>
    <!-- End Facebook Pixel Code -->
</head>
<c:set var="homecss" value=""/>
<c:if test="${not empty premiumOffers}">
    <c:set var="homecss" value="home"/>
</c:if>


<body class="${homecss}">
    <!-- TOP BANNER -->
    <!--<div id="top-banner" class="top-banner">
        <div class="bg-overlay"></div>
        <div class="container">
            <h1>Special Offer!</h1>
            <h2>Additional 40% OFF For Men & Women Clothings</h2>
            <span>This offer is for online only 7PM to middnight ends in 30th July 2015</span>
            <span class="btn-close"></span>
        </div>
    </div>-->
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="body" />



    <div id="content-wrap">
        <div class="container">
            <hr/>
        </div> <!-- /.container -->
    </div>

    <tiles:insertAttribute name="footer" />

    <a href="#" class="scroll_top" title="Scroll to Top" style="display: inline;">Scroll</a>
    <!-- Script-->

    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.validate.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/bootstrap/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/select2/js/select2.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery.bxslider/jquery.bxslider.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/owl.carousel/owl.carousel.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery.countdown/jquery.countdown.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.actual.min.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/theme-script.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/adrb.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/accounting.min.js"/>"></script>

    <script>

    
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      testAPI();
    } else {
//        alert("response"+response);
      // The person is not logged into your app or we are unable to tell.
//      document.getElementById('status').innerHTML = 'Please log ' +
//        'into this app.';
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '407981469567975',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.8' // use graph api version 2.8
  });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me',{ locale: 'en_US', fields: 'name, email, id' }, function(response) {
        alert(response);
      console.log('Successful login for: ' + response.name);
var ctx = "${pageContext.request.contextPath}";
       console.log('response'+response);

       $.ajax({
                type: "get",
                url: "/sociallogin?name="+response.name+"&lastname="+response.name+"&soocialsite=facebook&email="+response.email+"&accountkey="+response.id,
                dataType: 'json',
                cache: false,
                success: function (data) {
                  location.reload(); 
                }
            });
//      document.getElementById('status').innerHTML =
//        'Thanks for logging in, ' + response.name + '!';
    });
  }
</script>
<script>
      function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
          var url = baseUrl + "googlelogin?username=" + profile.getId()) +"&Email="+profile.getEmail());

       $.ajax({
            url: url,
           success: function(result){
                console.log("loged in " + profile.getId()); // Don't send this directly to your server!

    }});
        });
        
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());
          $.ajax({
                type: "get",
                url: "/sociallogin?name="+ profile.getName()+"&lastname="+profile.getFamilyName()"+&soocialsite=google&email="+profile.getEmail()"+&accountkey="+profile.getId(),
                dataType: 'json',
                cache: false,
                success: function (data) {
    location.reload();
                }
            });
        

        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
      };
    </script>
    
</body>
</html>

<script>
        var baseUrl = '<spring:url value="/"/>';
</script>