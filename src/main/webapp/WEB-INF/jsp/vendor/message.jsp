<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="menuNavigation" value="userRequests"/>
<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Send message</h3>
        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                         <div class="col-xs-8 pull-left">
                             
                                 <label for="orderId">Message</label>
                                 <div id="alertmessage" ></div>
                                 <textarea class="form-control" name="message" id="message" rows="10"></textarea>
                                 <button class="btn" id="submit" type="submit">Submit</button>
                        
                                 </div>   
                        
                        </div>      
                    </div>

                </div> 


            </div>
        </div>
    </section>
</section>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script type="text/javascript">
 $(document).ready(function(){
      
        var alertmessage = $("#alertmessage");       
         
    $("#submit").click(function(e){
            e.preventDefault();
            var message = $.trim($("#message").val());
            if(message==''){
                showAlert("alertmessage", "danger", "Please enter message ..!");
                return false;       
            }
            
            var url = "<%=request.getContextPath()%>/vendor/sendMessage";
                    
                    var data = {};
                    data.message=message;                   
                    $.ajax({
                        type: "POST",
                        url: url,
                        data:data,
                        success: function(data) {
                            if (data) {
                                showAlert("alertmessage", "success", "Message sent successfully..!");
                            }
                        },
                        dataType: "json"
                    });
    });    
   
        
    });

</script>

