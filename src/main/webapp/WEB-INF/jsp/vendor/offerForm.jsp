
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    $(document).ready(function () {
        $("#addcolor").click(function () {
          //  alert("jj");
            var txt1 = "<input type='text' name='colorstemp' /><br/>"; 
            $("#clrs").append(txt1);
            return;
        });
        $("#addsize").on("click",function(){
            var text1="<input type='text' name='sizetemp' /><br/>";
            $("#clrsize").append(text1);
            return;
        });
        var count=0;
        $("#addimage").click(function(){
           if(count === 3){$(this).attr('disabled',true);alert("upload 3 images only"); return;}
            var img="<input type='file' name='preview_img' multiple='multiple'>";
            $("#image_preview").append(img);
            count++; 
    });
    });
</script>

<div class="modal-content">
    <div class="modal-header">
        <a href="#" class="close" data-dismiss="modal">&times;</a>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${param.type=='add'}">
                    Add Offer
                </c:when>
                <c:otherwise>
                    Edit Offer
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <div class="modal-body"><p>
        <form id="addOfferForm"  method="POST" enctype="multipart/form-data">    
            <div class="col-md-12 well margin-left-10" >
                <c:if test="${param.type!='add'}">
                    <div class="offer-img well">

                        <c:choose>

                            <c:when test="${not empty offer.image}">
                                <img src="<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/${offer.image}"/>" class="img-thumbnail img-rounded img-responsive" alt="Offer-image">
                            </c:when>
                            <c:otherwise>
                                <img src="<spring:url value="/resources/assets_admin/images/no_image.jpg"/>" class="img-rounded img-responsive" alt="Offer-image">
                            </c:otherwise>    
                        </c:choose>

                    </div>
                </c:if>
                <div class="form-group">
                    <label for="offer-title">Title</label>
                    <c:if test="${not empty offer}">
                        <input type="hidden" name="offerID" value="${offer.offerID}"/>
                    </c:if>    
                    <input id="offer-title" name="title" type="text" class="form-control"  placeholder="" value="${offer.title}">
                </div>
                <div class="form-group">
                    <label for="offer-price">Offer price</label>
                    <input id="offer-price" type="text" name="offerPrice" class="form-control"  placeholder="" value="<fmt:formatNumber value="${offer.offerPrice}" maxFractionDigits="0" groupingUsed="false"/>">
                    <label for="coupon-price">Coupon price</label>
                    <input id="coupon-price" type="text" name="couponPrice" class="form-control"  placeholder="" value="<fmt:formatNumber value="${offer.couponPrice}" maxFractionDigits="0" groupingUsed="false"/>">
                    <label for="actual-price">Actual price</label>
                    <input id="actual-price" type="text" name="price" class="form-control"  placeholder="" value="<fmt:formatNumber value="${offer.price}" maxFractionDigits="0" groupingUsed="false"/>">
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity</label>
                    <input type="text" class="form-control" name="quantity" id="quantity" placeholder="" value="${offer.quantity}"/>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="2">${offer.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="quantity">Sort Order</label>
                    <input type="text" class="form-control" name="sort_order" id="sort_order" placeholder="" value="${offer.sort_order}"/>
                </div>
                <div class="form-group">
                    <label for="color">color</label>
                    <div id="clrs"></div><input type="button" id="addcolor"   value="Add Color"/>
                </div>
                  <div class="form-group">
                    <label for="size">sizes</label>
                    <div id="clrsize"></div><input type="button" id="addsize"   value="Add size"/>
                </div>
                  <div class="form-group">
                    <label for="subimages">Add Preview images</label>
                    <div id="image_preview"></div><input type="button" id="addimage"   value="Add prev imagages"/>
                </div>
                
                
                <div class="form-group form-inline">
                    <label for="browse">Upload Main Image</label>
                    <div id="browse" class="file-input btn btn-primary btn-sm btn-file btn-block" for="main_image">Browse&hellip; <input type="file" name="uploadImage" id="main_image"></div>
                    <br/>
                    <label for="add"></label>
                    <button id="saveOffer"  type="button" class="btn btn-warning btn-sm  btn-inline">Submit</button>                                   


                </div>    

            </div>
        </form>
    </div>
    <div class="modal-footer">

    </div>
</div>