<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!--main content start-->
<section id="main-content">
    <div class="row mt">
        <div class="container" style="">
            <%--<div class="row">
                <!-- left column -->
                <div class="col-md-12 col-sm-6 col-xs-12">
                    <div class="row">
                        <img src="<spring:url value="/resources/images/add3.jpg"/>" class="profile-pick avatar img-thumbnail" alt="avatar">
                        <img src="<spring:url value="/resources/images/cover-promo.jpg"/>" class="image-responsive cover-promo" alt="bg">
                    </div>
                </div>
            </div>--%>
            <!-- edit form column -->
            <div class="col-md-5 col-sm-6 col-xs-12 personal-info">
                <h3>Personal info</h3>
                <form class="form-horizontal" role="form">
                    <div id="error" class="login_error"></div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Name:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.name}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Email:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.email}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Phone:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.phone}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Location:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.location}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">City:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.city}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">State:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.state}</span>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-lg-3 control-label">Country:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.country}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">Zipcode:</label>
                        <div class="col-lg-4">
                            <span style="alignment-adjust: central">${user.zipCode}</span>
                        </div>
                    </div>



                    <div class="form-group">
                        <label class="col-md-3 control-label">Password:</label>
                        <div class="col-lg-4">
                            <input class="form-control" value="" id="password" type="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">Confirm password:</label>
                        <div class="col-lg-4">
                            <input class="form-control" value="" id="cpassword" type="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label"></label>
                        <div class="col-lg-4">
                            <input class="btn btn-primary" value="Save Changes" id="updateProfile" type="button">
                            <span></span>
                            <%--<input class="btn btn-default" value="Cancel" type="reset">--%>
                        </div>
                    </div>

                </form>
            </div>
            <div class="col-md-5 col-sm-6 col-xs-12 personal-info">
                <h5>Cover photo</h5>

                <div class="col-md-12 well margin-left-10">
                    <div class="offer-img well">
                        <c:choose>
                            <c:when test="${not empty user.coverImage}">
                                <img id="coverImage" src="<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/${user.coverImage}"/>" class="img-rounded img-responsive" alt="Cover photo">
                            </c:when>
                            <c:otherwise>
                                <img id="coverImage" src="<spring:url value="/resources/assets_admin/images/no_image.jpg"/>" class="img-rounded img-responsive" alt="Cover photo">     
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="form-group">
                        <form id="uploadImageForm" method="POST" enctype="multipart/form-data">
                        <label for="browse"></label>
                        <button id="browse" type="button" class="file-input btn btn-primary btn-sm btn-file btn-block">Browse&hellip; <input type="file" name="coverFile"></button>
                        </form>
                        <label for="saveCoverImage"></label>
                        <button id="saveCoverImage" type="button" class="btn btn-warning btn-sm btn-file btn-upload">Upload</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</section><!-- /MAIN CONTENT -->

<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script>
    $(document).ready(function() {
        $("#updateProfile").click(function() {
            var password = $.trim($("#password").val());
            var cpassword = $.trim($("#cpassword").val());

            if (password == "") {
                $("#error").html("Please enter password");
                return false;
            }

            if (password != cpassword) {
                $("#error").html("Password and confirm password doesn't match");
                return false;
            }

            $("#error").html("");

            $.ajax({
                type: "GET",
                url: "<spring:url value="/vendor/updateProfile"/>",
                data: "password=" + password,
                cache: false,
                dataType: 'html',
                success: function(data) {
                    if (data && data == "success")
                    {
                        alert("password updated successfully");
                    } else {
                        alert("failed to update password");
                    }
                },
            });

            return false;
        });


        $("#saveCoverImage").click(function() {
            var options = {
                url: '<spring:url value="/vendor/uploadCoverImage"/>',
                dataType: 'json',
                type: 'POST',
                success: function(data) {
                   if(data && data.url){
                       var url = '<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/"/>'+data.url;
                        $("#coverImage").attr("src",url);                      
                   }
                }
            };
            $("#uploadImageForm").ajaxSubmit(options);
            return false;
        });


    });
</script>