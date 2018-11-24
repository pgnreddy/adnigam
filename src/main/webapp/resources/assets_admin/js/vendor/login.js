/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    $("#sign-in").submit(function(e) {
        e.preventDefault();
    //$("#sign-in #login-btn").click(function() {
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
                beforeSend: function() {
                    $("#login-btn").val('Connecting...');
                },
                success: function(data) {
                    if (data)
                    {   
                        if(data=='success'){
                            window.location.href = "offers";
                            return;
                        }
                    }
                    $("#login-btn").val('SIGN IN');                     
                    $("#error").html("Invalid mobile number and password.");
                    
                }
            });

        }else{
            $("#error").html("Please enter valid mobile number and password.");
            return false;
        }
        
        return false;
       
    });
});

