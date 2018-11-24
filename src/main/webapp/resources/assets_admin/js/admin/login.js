/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $("#sign-in #login-btn").click(function () {
        loginFunction();
    });


    $('#password').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            loginFunction();
        }

    });

    $("#dialog-confirm").dialog({
        resizable: false,
        height: 140,
        modal: true,
        buttons: {
            "Delete all items": function () {
                $(this).dialog("close");
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        }
    });
});


function validate() {
    if ($.trim($("#txt_vendorname")).length < 1) {
        $("#resp_msg").html("Please enter name.");
        return false;
    }
    if ($.trim($("#txt_mobile")).length < 1) {
        $("#resp_msg").html("Please enter mobile number.");
        return false;
    }
    if ($.trim($("#txt_password")).length < 1) {
        $("#resp_msg").html("Please enter password.");
        return false;
    }
    if ($.trim($("#txt_zipcode")).length < 1) {
        $("#resp_msg").html("Please enter zipcode.");
        return false;
    }

    return true;
}



function loginFunction() {
    var username = $("#username").val();
    var password = $("#password").val();
    var dataString = 'username=' + username + '&password=' + password;
    if ($.trim(username).length > 0 && $.trim(password).length > 0)
    {
        $.ajax({
            type: "POST",
            url: "login",
            data: dataString,
            cache: false,
            beforeSend: function () {
                $("#login-btn").val('Connecting...');
            },
            success: function (data) {
                if (data)
                {
                    if (data == 'success') {
                        window.location.href = "orders";
                        return;
                    }
                }
                $("#login-btn").val('SIGN IN');
                $("#error").html("Invalid username and password.");

            }
        });

    } else {
        $("#error").html("Please enter valid email and password.");
        return false;
    }

}

function deleteVendor(param) {
    $.ajax({
        type: "POST",
        url: "deleteVendor",
        data: "vendorId=" + param,
        cache: false,
        beforeSend: function () {
            $("#resp_data").val('Deleting....');
        },
        success: function (data) {
            if (data)
            {
                if (data == 'success') {
                    $("#resp_data").html("Vendor deleted successfully.");
                } else {
                    $("#resp_data").html("Failed to remove vendor.");
                }
            }
        }
    });
}



function deleteVendor(param) {
    $.ajax({
        type: "POST",
        url: "deleteVendor",
        data: "vendorId=" + param,
        cache: false,
        beforeSend: function () {
            $("#resp_data").val('Deleting....');
        },
        success: function (data) {
            if (data)
            {
                if (data == 'success') {
                    $("#resp_data").html("Vendor deleted successfully.");
                    initDataGrid();
                } else {
                    $("#resp_data").html("Failed to remove vendor.");
                }
            }
        }
    });
}
