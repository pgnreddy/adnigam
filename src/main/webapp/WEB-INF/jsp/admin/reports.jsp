<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="<spring:url value="/resources/assets_admin/css/admin/jquery-ui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/grayeasyui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/custom.css"/>" rel="stylesheet">
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script src="<spring:url value="/resources/assets_admin/js/admin/login.js"/>"></script>

<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="">Reports</h3>
            <div class="col-xs-7 col-sm-7 pull-right form-category">
                <form class="form-inline">
                </form>
            </div>
            <div class="row">
                <div class="clearfix" id="offersContent">
                </div>
            </div>
        </div>
    </div>

    <form id="reportsForm" method="GET" action="reports.xls">
        <select id="reportType" name="reportType" >
            <option value="locations">Locations</option>
            <option value="categories">Categories</option>
            <option value="customers">Customers</option>
            <option value="todaysOffers">Todays Offers</option>
            <option value="exclusiveOffers">Exclusive Offers</option>
            <option value="premiumOffers">Premium Offers</option>
            <option value="vendors">Vendors</option>
            <option value="cancelOrders">Cancel Orders</option>
            <option value="orders">Orders</option>
            <option value="premiumOrders">Premium Orders</option>
            <option value="exclusiveOrders">Todays &amp; Exclusive Orders</option>
        </select>
        
        <span id="dates" >
            Start :<input type="text" name="start" class="datepicker"/>
            End :<input type="text" name="end" class="datepicker"/>
        </span>
        <button type="submit" id="submit" >Download</button>
       
         
    </form>  
    <br style="clear:both;"/>
  

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $(".sidebar-menu").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_reports").addClass("active");
       
        var datesDiv = $("#dates");
        datesDiv.hide();
         $(".datepicker").datepicker({
                showOn: "button",
                buttonImage: "<%=request.getContextPath()%>/resources/assets_admin/images/calendar.gif",
                buttonImageOnly: true,
                buttonText: "Select date",
                changeMonth: true,
                changeYear: true,
                dateFormat: 'dd/mm/yy'
            });
            
            var arr = ["customers","cancelOrders","orders","premiumOrders", "exclusiveOrders"];
            $("#reportType").change(function(){
                var value = $(this).val();
                                
                if(arr.indexOf(value)>-1){
                    datesDiv.show();
                }else{
                    datesDiv.hide();           
               }
        
            });
        
    });
</script>

