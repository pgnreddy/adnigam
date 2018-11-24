<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="columns-container">
    <div class="container" id="columns">
        <!-- row -->
        <div class="row">
            <!-- Center colunm-->
            <div class="col-xs-12">
                
                <!-- view-product-list-->
                <div class="view-product-list">
                    <h2 class="page-heading">
                        <span class="page-heading-title">Sell with Us (for Vendors)</span>
                    </h2>
                    
                    <!-- Page Content -->
           				 <!-- Page Content -->
    <div class="container">

        <div class="row">

                <br/>
				<div class="col-md-12">
                 
                <div class="box-authentication">
                   <c:if test="${param.success=='true'}">
                        <div class="alert alert-success">Request successfully submitted ..!</div>   
                    </c:if>
                    <form class="form-horizontal" action="<spring:url value="/sendVendorRequest"/>" id="vendorsignupform">

                        <label for="firstName">*Name of the Person:</label>
                        <input id="firstName" type="text" placeholder="Name" name="name" class="form-control" > 
                        <label for="business">	*Business name/ shop name:</label>
                        <input id="business" type="text"  placeholder="Business" name="business" class="form-control" >


                        <label for="businesstype">	*Nature of business:</label>
                        <input id="businesstype" name="businessType" placeholder="Nature of business" type="text" class="form-control" >

                        <label for="telephone_number">	*Mobile no:</label>
                        <input id="telephone_number" placeholder="Mobile" name="telephone" type="tel" class="form-control" >

                        <label for="email">	Email ID:</label>
                        <input id="email" placeholder="Email" name="email" type="text" class="form-control" >

                        <label for="address">	*Address:</label>
                        <textarea id="address" placeholder="Address" name="address" type="text" class="form-control" ></textarea>                  


                        <label for="licence">		License No.</label>
                        <input id="licence" placeholder="Licence No" name="licence" type="text" class="form-control" >                   


                        <label for="services">	*Services Required:</label>
                        <textarea id="services" name="services" type="text" class="form-control" ></textarea>
                        <label for="comments">	Comments:</label>
                        <textarea id="comments" placeholder="Comments" name="comments" type="text" class="form-control" ></textarea>                
 


                        <button class="button"><i class="fa fa-user"></i> Submit </button>
                    </form>
                </div>
           
                
             </div>

             
              </div>
                </div>

                    <!-- ./Page Content -->
                </div>
            </div>
            <!-- ./ Center colunm -->
        </div>
        <!-- ./row-->
    </div>
</div>
                    
                    <script>
                        $(document).ready(function(){
                        $("#vendorsignupform").validate({
        rules:{
            name:"required",
            business:"required",
            businessType:"required",
            telephone:"required",
            address:"required",
            services:"required"
        },
        messages: {
            name:"Please enter your name",
            business:"Please enter your business name/shop name",
            businessType:"Please enter nature of business",
            telephone:"Please enter your mobile number",
            address:"Please enter your address",
            services:"Please enter services required"
        },errorPlacement: function(error, element) {
            // Append error within linked label
            $(element)
                    .closest("form")
                    .find("label[for='" + element.attr("id") + "']")
                    .append(error);
        },
        errorElement: "span"
    });
                        });
    
    
                        </script>