<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9 col-md-9">
    <h3 class="page-heading head_block">Change Password</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <div class="box-authentication">
            <form class="form-horizontal" id="savepassword">
                <div clss="msg" id="resp_msg" ></div>
                <div class="form-group required">
                    <label for="current_password" class="col-sm-3 cntrl-label">Current Password</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="currentpassword" id="current_password" placeholder="Enter Current Password">
                    </div>
                </div>
                <div class="form-group required">
                    <label for="new_password" class="col-sm-3 cntrl-label">New Password</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="newpassword" id="new_password" placeholder="Enter New Password">
                    </div>
                </div>
                <div class="form-group required">
                    <label for="conform_password" class="col-sm-3 cntrl-label">Confirm Password</label>
                    <div class="col-sm-6">
                        <input id="conform_password" type="password" name="conformpassword" class="form-control" placeholder="Confirm Password">
                    </div>
                </div>

            </form>

        </div>
        <div class="cart_navigation">
            <a class="save-btn" href="#">Save Changes</a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
       // $(".tree-menu").find("li").removeClass("active");
       // $(".changepasword_active").addClass("active");
        $(".save-btn").click(function (e) {
            e.preventDefault();
            //validations
             $("#resp_msg").html('');
            var current_password = $.trim($("#current_password").val());
            var new_password = $.trim($("#new_password").val());
            var conform_password = $.trim($("#conform_password").val());
            if(current_password===null ||current_password===""){
             $("#resp_msg").html("Please enter current password");
                return false;   
            }
            if(new_password===null ||new_password===""){
             $("#resp_msg").html("Please enter new password");
                return false;   
            }
            if(conform_password===null ||conform_password===""){
             $("#resp_msg").html("Please enter confirm password");
                return false;   
            }
            if(new_password!==conform_password){
                $("#resp_msg").html("New password and confirm password not matched ");
                return false;
            }
            var params = $("#savepassword").serialize();
            $.ajax({
                type: "POST",
                url: "changepassword",
                data: params,
                cache: false,
                success: function (data) {
                    if (data)
                    {
                        if (data === 'success') {
                            $("#resp_msg").html("Successfully  Changed password.");
                            $("#current_password").val("");
                            $("#new_password").val("");
                            $("#conform_password").val("");
                        } else {
                            $("#resp_msg").html("Failed to Change password.");
                        }
                    }
                }
            });

        });

    });
</script>