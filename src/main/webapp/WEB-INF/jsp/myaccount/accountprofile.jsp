<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery/jquery-1.11.2.min.js"/>"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="center_column col-xs-12 col-sm-9">
    <h3 class="page-heading head_block">MyAccount Information</h3>
    <div class="page-content page-order">

        <div class="col-sm-12 col-md-12">
            <div class="box-authentication" style="height:auto !important; margin-left:-18px !important;">
                <form class="form-horizontal" id="myaccount">
                    <div clss="msg" id="resp_msg" ></div>
                    <div class="form-group required">
                        <label for="gender" class="col-sm-3 cntrl-label radio-inline">Gender</label>
                        <div class="col-sm-2">
                            <input type="radio" name="gender" value="m" <c:if test="${USER.gender=='m'}">checked</c:if>>Male
                            </div>
                            <div class="col-sm-3">
                                <input type="radio" name="gender" value="f" <c:if test="${USER.gender=='f'}">checked</c:if>>Female
                            </div>
                        </div>
                        <div class="form-group required">
                            <label for="first_name" class="col-sm-3 cntrl-label">Name</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="firstname" id="first_name" value="${USER.firstname}">
                        </div>
                    </div>
                    <div class="form-group required">
                        <label for="last_name" class="col-sm-3 cntrl-label">Last Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="lastname"  id="last_name" value="${USER.lastname}">
                        </div>
                    </div>
                    <div class="form-group required">
                        <label for="datepicker" class="col-sm-3 cntrl-label">DOB</label>
                        <div class="col-sm-9">
                            <input readonly="true" id="datepicker" type="text" name="dob"  class="form-control" value="${USER.dob}">
                        </div>
                    </div>
                    <div class="form-group required">
                        <label for="email" class="col-sm-3 cntrl-label">Email address</label>
                        <div class="col-sm-9">
                            <input id="email" type="text"  name="emailaddress"  class="form-control" value="${USER.emailAddress}">
                        </div>
                    </div>
                    <div class="form-group required">
                        <label for="telephone" class="col-sm-3 cntrl-label">Telephone</label>
                        <div class="col-sm-9">
                            <input id="telephone" type="tel" name="telephone" class="form-control" value="${USER.telephone}">
                        </div>
                    </div>
                </form>

            </div>
        </div>
        <div class="cart_navigation">
            <a class="btn save-btn" href="#">Save Changes</a>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {

        $(".save-btn").click(function(e) {
            //validations
            e.preventDefault();
            $("#resp_msg").html('');
            var first_name = $.trim($("#first_name").val());
            var last_name = $.trim($("#last_name").val());
            var datepicker = $.trim($("#datepicker").val());
            var email = $.trim($("#email").val());
            var telephone = $.trim($("#telephone").val());
            if (first_name === undefined || first_name === "") {
                $("#resp_msg").html("Please enter first name");
                return false;
            }
            if (last_name === undefined || last_name === "") {
                $("#resp_msg").html("Please enter last name");
                return false;
            }
            if (datepicker === undefined || datepicker === "") {
                $("#resp_msg").html("Please enter dob");
                return false;
            }
            if (email === undefined || email === "") {
                $("#resp_msg").html("Please Enter email");
                return false;
            }
            if (telephone === undefined || telephone === "") {
                $("#resp_msg").html("Please Enter telephone number");
                return false;
            }
            var params = $("#myaccount").serialize();
            $.ajax({
                type: "POST",
                url: "accountprofile",
                data: params,
                cache: false,
                success: function(data) {
                    if (data)
                    {
                        if (data === 'success') {
                            $("#resp_msg").html("Successfully updated profile.");
                        } else {
                            $("#resp_msg").html("Failed to update profile.");
                        }
                    }
                }
            });

        });


     
            $("#datepicker").datepicker({
                showOn: "button",
                buttonImage: "<%=request.getContextPath()%>/resources/assets_admin/images/calendar.gif",
                buttonImageOnly: true,
                buttonText: "Select date",
                changeMonth: true,
                changeYear: true,
                yearRange: "1960:c-0"
            });
       

    });
</script>