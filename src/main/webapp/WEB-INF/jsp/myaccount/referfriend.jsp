<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9 col-md-9">
    <h3 class="page-heading head_block">REFER A FRIEND</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <div class="col-sm-12 col-md-12">
            <div class="box-authentication">
                   <div clss="msg" id="resp_msg" ></div>
                <form id="referfriend">
                    
                    <label for="mailId">To(for multiple send use semicolon as seperator):</label>
                    <input id="mailId"  name="mailId" type="text" class="form-control input-sm">

                    <label for="message">Message:</label>
                    <textarea class="form-control" rows="5" cols="30" name="message" id="message"></textarea>
                </form>
            </div>
        </div>
        <div class="cart_navigation">
            <a class="save-btn" >Refer to Friend</a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
       // $(".tree-menu").find("li").removeClass("active");
      // $(".referfriend_active").addClass("active");
        $(".save-btn").click(function () {
            //validations
             $("#resp_msg").html('');
            var mailId = $.trim($("#mailId").val());
            var message = $.trim($("#message").val());
            if(mailId===null ||mailId===""){
             $("#resp_msg").html("Please enter mailId's");
                return false;   
            }
            
            var params = $("#referfriend").serialize();
            $.ajax({
                type: "POST",
                url: "referfriend",
                data: params,
                cache: false,
                success: function (data) {
                    if (data)
                    {
                        if (data === 'success') {
                            $("#resp_msg").html("Successfully  refer friend.");
                            $("#mailId").val("");
                            $("#message").val("");
                            
                        } else {
                            $("#resp_msg").html("Unable refer a friend.");
                        }
                    }
                }
            });

        });

    });
</script>