<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-md-9">
    <h3 class="page-heading head_block">Address Book Entries</h3>

    <div class="page-content page-order">
        <div class="cart_navigation">
            <h6>*Note: Only 5 addresses are allowed to add.</h6>
        </div>
        <div class="left">
            <c:choose>
                <c:when test="${empty addressEntry}">
                    <p>No records found</p>
                </c:when>
                <c:otherwise>
                <div class="row">
                    <c:forEach var="address" items="${addressEntry}">
                    
                        <div class="col-md-3 col-xs-12" >
                            <div class="box-authentication">
                                <a href="javascript:void(0)"><span class="edit-addressbook-entry" id="${address.addressBookId}" addrObj="${address}" >Edit</span></a>
                                <a href="deletaddress?addressBookId=${address.addressBookId}">Delete</a>
                                <hr/>
                                <address>
                                    <strong>First Name :${address.entryFirstname}</strong><br/>
                                    <p>Street: ${address.entryStreetAddress}</p>
                                    <p>Address: ${address.entrySuburb}</p>
                                    <p>City: ${address.entryCity}</p>
                                    <p>Pincode: ${address.entryPostcode}</p>
                                </address>
                            </div>
                        </div>
                        
                    </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="right">
            <c:if test="${empty addressEntry || addressEntry.size() < 5}">
                <div class="cart_navigation" >
                    <a class="add-address-btn" id="add-address-btn" href="#" >Add Address</a>
                </div>
            </c:if>

            <div id="newaddr"></div>
        </div>
        
        <div id="addadress" style="display: none;" >
            <div class="col-sm-4" style="width:100%;float:left;">
                <div class="box-authentication">
                    <h3>Enter New Address</h3>
                    <hr/>
                    <form id="addressform" method="POST">
                        <div clss="msg" id="resp_msg" ></div>
                        <input name="fullname" id="full_name" type="text" class="form-control input-cm" placeholder="Full Name">
                        <input  name="streetname" id="street_name" type="text" class="form-control input-cm" placeholder="Street Name">
                        <input  name="address" id="address" type="text" class="form-control input-cm" placeholder="Address">
                        <input  name="cityname" id="city_name" type="text" class="form-control input-cm" placeholder="City">
                        <input name="zipcode"  id="zip_code" type="text" class="form-control input-cm" placeholder="zipCode">
                    </form>
                </div>
            </div>
            <div class="cart_navigation">
                <a class="save-btn" href="#">Save Changes</a>
            </div>
        </div>

    </div>
</div>
<script>
    $(document).ready(function() {
       // $(".tree-menu").find("li").removeClass("active");
       // $(".addressbook_active").addClass("active");
        $("#add-address-btn").click(function(e) {
            e.preventDefault();
            $("#addressform").attr("action", "addadress");
//            $("#addadress").show();
            $("#newaddr").empty();
            $("#newaddr").html($("#addadress").html());
        });
        $(".edit-addressbook-entry").each(function() {
            $(this).click(function() {
                var id = $(this).attr("id");
//                alert(id);
                $.ajax({
                    type: "Get",
                    url: "addressbook/" + id,
                    cache: false,
                    success: function(data) {
                        $("#newaddr").html(data);
                    }
                });
            });
        });

        $("#newaddr").delegate(".save-btn", "click", function(e) {
            //$(".save-btn").click(function () {
            e.preventDefault();
            //validations
            $("#resp_msg").html('');
            var full_name = $.trim($("#full_name").val());
            var street_name = $.trim($("#street_name").val());
            var address = $.trim($("#address").val());
            var city_name = $.trim($("#city_name").val());
            var zip_code = $.trim($("#zip_code").val());
            if (full_name === null || full_name === "") {
                $("#resp_msg").html("Enter fullname");
                return false;
            }
            if (street_name === null || street_name === "") {
                $("#resp_msg").html("Enter street name");
                return false;
            }
            if (address === null || address === "") {
                $("#resp_msg").html("Enter adress");
                return false;
            }
            if (city_name !== city_name) {
                $("#resp_msg").html("Enter city name");
                return false;
            }
            if (zip_code !== zip_code) {
                $("#resp_msg").html("Enter Zip code");
                return false;
            }
            $("#addressform").submit();
        });
        $("#newaddr").delegate(".update-address-btn", "click", function() {
            //validations
            $("#resp_msg").html('');
            var full_name = $.trim($("#full_name").val());
            var street_name = $.trim($("#street_name").val());
            var address = $.trim($("#address").val());
            var city_name = $.trim($("#city_name").val());
            var zip_code = $.trim($("#zip_code").val());
            if (full_name === null || full_name === "") {
                $("#resp_msg").html("Enter fullname");
                return false;
            }
            if (street_name === null || street_name === "") {
                $("#resp_msg").html("Enter street name");
                return false;
            }
            if (address === null || address === "") {
                $("#resp_msg").html("Enter adress");
                return false;
            }
            if (city_name !== city_name) {
                $("#resp_msg").html("Enter city name");
                return false;
            }
            if (zip_code !== zip_code) {
                $("#resp_msg").html("Enter Zip code");
                return false;
            }
            $("#updateaddressform").submit();
        });



    });
</script>