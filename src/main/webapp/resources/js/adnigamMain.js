   /****** 
 *
 *  THE FOLLOEING SCRIPT CODE IS HANDEL THE VALIDATING THE LOGIN / SIGNUP /FORGOT PASS ETC
 * 
 * 
 * ***/

$(document).ready(function() {
    $("#loginForm").validate({
        rules: {
            userName: {required: true, email: true},
            password: "required"
        },
        messages: {
            userName: "Please enter a valid email address",
            password: "Please enter your password"
        },
        errorPlacement: function(error, element) {
            // Append error within linked label
            $(element)
                    .closest("form")
                    .find("label[for='" + element.attr("id") + "']")
                    .append(error);
        },
        errorElement: "span"
    });
    
    $("#createaccount").validate({
        rules: {
            firstname: {required: true},
            lastname: {required: true},
            emailAddress: {required: true, email: true},
            telephone: {required: true, number: true, minlength: 10, maxlength: 12},
            password: {required: true},
            confirm: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            firstname: "Please enter your firstname",
            lastname: "Please enter your lastname",
            emailAddress: {
                required: "Please enter a valid email address"
            },
            telephone: "Please enter a valid mobile number",
            password: {
                required: "Please enter a password"
            },
            confirm: {
                required: "Please enter a confirm password",
                equalTo: "Please enter the same password as above"
            }
        },
        errorPlacement: function(error, element) {
            // Append error within linked label
            $(element)
                    .closest("form")
                    .find("label[for='" + element.attr("id") + "']")
                    .append(error);
        },
        errorElement: "span"
    });
    $("#adrobeForgotPassForm").validate({
        rules: {
            email: {required: true, email: true}
        },
        messages: {
            email: "Please enter a valid email address"
        },
        errorPlacement: function(error, element) {
            // Append error within linked label
            $(element)
                    .closest("form")
                    .find("label[for='" + element.attr("id") + "']")
                    .append(error);
        },
        errorElement: "span"
    });
    $("#addressForm").validate({
        rules:{
            entryFirstname:"required",
            entryLastname:"required",
            entryStreetAddress:"required",
            entryCity:"required",
            entryState:"required",
            entryPostcode:"required"
        },
        messages: {
            entryFirstname:"Please enter your firstname",
            entryLastname:"Please enter your lastname",
            entryStreetAddress:"Please enter your address",
            entryCity:"Please enter your city",
            entryState:"Please enter your state",
            entryPostcode:"Please enter your postalcode"
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


/**** 
 * 
 * THE FOLLOWING SCRIPT CODE IS TO HANDELE THE ADD-MIN-CART AND QUICK VIEW 
 * 
 * 
 * ***/


$(document).ready(function(){
     console.log("sucess cart");
      var cart_block = $('#minAddCart');
    var successCallback = function(html) {
       cart_block.html(html);
         return;
    };
    var removeCallback= function(html) {
       cart_block.html(html);
         return;
    };
 $(document).delegate(".add-to-cart", "click", function(e) {
        e.preventDefault();
        var $this = $(this);
        var offerId = $this.attr("pid");
        var isReserve = $this.hasClass("reserve");
        var isGetCoupon = $this.hasClass("coupon");
        if (isGetCoupon) {
            window.alert('The coupon will be valid for 7 days since purchase.');
        }
        //var url = '<spring:url value="/cart/addItem"/>'+ "?p=" + offerId;
        var url = methodUrl.baseUrl+ "cart/addItem?p=" + offerId + (isReserve ? "&reserve=true" : "") + (isGetCoupon ? "&coupon=true" : "");

        $.ajax({
            url: url,
            success: successCallback,
            complete: function() {
          cart_block.find('#modal-cart').modal('show');
             return;
              }
           
        });
      
    });
 
 $(document).delegate("a.cart-pr-remove","click",function(e){
     e.preventDefault();
       $('#modal-cart').modal('hide');
       $('body').removeClass('modal-open');
       $('.modal-backdrop').remove();
      var $this = $(this);
        var offerId = $this.attr("pid");
        var isReserve =$this.attr("reserve");
        var url = methodUrl.baseUrl+ '/cart/removeItem' + "?cP=true&p=" + offerId + (isReserve === "true" ? "&reserve=true" : "");

            $.ajax({
                url: url,
                success: removeCallback, 
                complete: function() {
                    $('#modal-cart').modal('show');
                    $('body').attr("style","");
                    return;
                   
              }
            });
 });
 /* Quick view script  */ 
 var quickCallback=function(html){
     $('#quick_view_id').html(html);
     $('#modal-quickview').modal('show');
 };
 $(document).delegate('a.quik_view_htm','click' ,function(e){
     e.preventDefault();
     console.log("sucess");
     var seo_title=$(this).attr('title');
     console.log("sucess"+seo_title);
     var url= methodUrl.baseUrl+'/pd/'+seo_title+ ' ?quick=true';
     $.ajax({
                url: url,
                success: quickCallback
            });
 });
/********************
 * ADD WISH LIST ITEMS BY CLICKING THE ADD_WISH LIST ADD ITEM INTO WISH LIST
 * AUTHUR : Venkat Meesala
 * DATE : 18-08-2017 
 * 
 */
$(document).delegate('.add-to-wishlist','click',function(){
 var session_user=JSON.parse($('#header').attr('data-session-user'));
  var data=$(this).attr('pid');
 if(!session_user){
 swal(
  'Oops...',
  'Please Login To Continue..!',
  'error'
    );}else{
         var url= methodUrl.baseUrl+'addFavourite?productId='+data;
                $.ajax({
                url: url,
                success: function(data){
                    var res=data;
                    if(data.status==="failure"){
                        swal('Opps..',   
                           'Already Add Wish List:'+res.status,  
                            'error'  );
                    }else{
                         swal( 'Success',    
                             'Add Wish List:'+res.status,  
                            'success' );
                    }
                    
                }
            });
    }
});
});