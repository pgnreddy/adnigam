/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var iframe = document.getElementById("new-login-signup-iframe");
var iframeName = "new-login-signup-iframe";

var callback = null;
function initIframeLogin() {
    if (iframe) {
        iframe.onload = windowNameCallback;
        iframe.onreadystatechange = function() {
            try {
                if (iframe.readyState == "complete") {
                    windowNameCallback()
                }
            } catch (w) {
            }
        };
    }
    window.iframe_message = function(data) {
        processMessage(data)
    };
    //addEvent(window, "message", onMessage)

    if (window.addEventListener) {
        addEventListener("message", onMessage, false);
    } else {
        attachEvent("onmessage", onMessage);
    }

}



function onMessage(input) {
    console.log(input.origin);
    if (input.origin == acceptDomain) {
        processMessage(input.data)
    }
}


function processMessage(data) {
    console.log(data);
    callback(data);
}

function windowNameCallback() {
    if (iframe.contentWindow && iframe.contentWindow.name) {
        var data = iframe.contentWindow.name;
        if (data && data != iframeName) {
            processMessage(data);
            if (iframe && iframe.contentWindow.name) {
                iframe.contentWindow.name = iframeName;
            }
        }
    }
}




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
            },
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
    initIframeLogin();

});



function clearHover() {
    $('#cart-block .cart-block').removeClass("hover");
}
$(document).ready(function() {

    var cart_block = $('#cart-block');
    var successCallback = function(html) {
        cart_block.html(html);
        cart_block.find(".cart-block").addClass("hover");
        setTimeout(clearHover, 5000);
        cart_block.find(".btn-check-out").focus();
    };

    $(document).delegate(".addToCart", "click", function(e) {
        e.preventDefault();
        var $this = $(this);
        var offerId = $this.attr("pid")

        var isReserve = $this.hasClass("reserve");
        var isGetCoupon = $this.hasClass("coupon");
        if (isGetCoupon) {
            window.alert('The coupon will be valid for 7 days since purchase.');
        }
        //var url = '<spring:url value="/cart/addItem"/>'+ "?p=" + offerId;
        var url = baseUrl + "cart/addItem?p=" + offerId + (isReserve ? "&reserve=true" : "") + (isGetCoupon ? "&coupon=true" : "");

        $.ajax({
            url: url,
            success: successCallback
        });

    });
});

