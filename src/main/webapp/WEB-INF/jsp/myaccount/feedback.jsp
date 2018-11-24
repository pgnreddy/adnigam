<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9 col-md-9">
    <h3 class="page-heading head_block">FEEDBACK</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <div class="col-sm-12 col-md-12">
            <div class="box-authentication">
                <form id="feedbackfrm">
                    <div clss="msg" id="resp_msg" ></div>
                    <label for="feedback">Feedback:</label>
                    <textarea class="form-control" name="feedback" rows="10" cols="50" id="feedback"></textarea>
                </form>

            </div>
        </div>
        <div class="cart_navigation">
            <a class="save-btn" href="#">Send Feedback</a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {

 //$(".tree-menu").find("li").removeClass("active");
  //      $(".feedback_active").addClass("active");
        $(".save-btn").click(function () {
            e.preventDefault();

            var feedback = $.trim($("#feedback").val());
            if (feedback === null || feedback === "") {
                $("#resp_msg").html("Please enter new password");
                return false;
            }
            var params = {'feedback': feedback};
            $.ajax({
                type: "POST",
                url: "feedback",
                data: params,
                cache: false,
                success: function (data) {
                    if (data === 'success') {
                        $("#resp_msg").html("Successfully  submitted feedback.");
                    } else {
                        $("#resp_msg").html("Failed to submit feedback.");
                    }

                }
            });


        });

    });
</script>