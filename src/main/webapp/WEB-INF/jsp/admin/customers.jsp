<%-- 
    Document   : customers
    Created on : Sep 24, 2015, 6:21:37 AM
    Author     : gopal
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="<spring:url value="/resources/assets_admin/css/admin/jquery-ui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/grayeasyui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/custom.css"/>" rel="stylesheet">
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script src="<spring:url value="/resources/assets_admin/js/admin/login.js"/>"></script>

<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="">Customers</h3>
            <div class="col-xs-7 col-sm-7 pull-right form-category">
                <form class="form-inline">
                </form>
            </div>
            <div class="row">
                <div class="clearfix" id="offersContent">
                </div>
            </div>
        </div>
    </div>



    <div class="rows_pop">
        <label class="column1">First Name:</label>
        <span class="column2"> 
            <input type="text"  id="srch_name" name="srch_name" class="text_field_nor_mdl eventclssrch"/>
        </span>

        <label class="column1">Mobile:</label>
        <span class="column2"> 
            <input type="text"  id="srch_mobile" name="srch_mobile" class="text_field_nor_mdl eventclssrch"/>
        </span>

        <label class="column1">Email:</label>
        <span class="column2"> 
            <input type="text"  id="srch_emil" name="srch_emil" class="text_field_nor_mdl eventclssrch"/>
        </span>
    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>
    <form id="updatecustomerform" name="updatecustomerform">  
        <div id="overlay" class="web_dialog_overlay"></div>  
        <div id="dialog" class="web_dialog" style="width:450px">  
            <div class="pop_main" id="add_vendor" style="width: 445px;">
                <div style="cursor: pointer; border: 0px solid red; height: 20px; width: 20px;
                     position: absolute; right: 10px; top: 10px" class="right">
                    <a id="closePopupImg">
                        <%--<img class="losePopupImg" src="<spring:url value="/resources/assets_admin/images/btn-close.gif"/>">--%>
                        <img class="losePopupImg" src='<spring:url value="/resources/assets_admin/images/btn-close.png"/>'>
                    </a>
                </div>
                <div id="id_Title" class="pop_header" style="width:436px;">Update Customer</div>
                <div id="resp_msg"></div>
                <div class="pop_container" >
                    <br/>
                    <div id="divPopStatus"></div>
                    <div class="rows_pop">
                        <label class="column1">First Name:</label>
                        <span class="column2"> 
                            <input type="text"  id="firstname" name="firstname" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Last Name:</label>
                        <span class="column2"> 
                            <input type="text"  id="lastname" name="lastname" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Email:</label>
                        <span class="column2"> 
                            <input type="text" id="emailAddress" name="emailAddress" class="text_field_nor_mdl"  />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Mobile:</label>
                        <span class="column2"> 
                            <input type="text" id="telephone" name="telephone" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <!--                    <div class="rows_pop" id="pwddiv">
                                            <label class="column1">Password:</label>  
                                            <span class="column2"> 
                                                <input type="text" id="password" name="password" class="text_field_nor_mdl" maxlength="32" />
                                            </span>
                                        </div>-->
                    <input type="hidden" name="customersId" id="customersId"/>
                    <input type="hidden" name="gender" id="gender"/>
                    <input type="hidden" name="dob" id="dob"/>
                    <input type="hidden" name="fax" id="fax"/>
                    <input type="hidden" name="password" id="password"/>
                    <input type="hidden" name="status" id="status"/>
                    <input type="hidden" name="dateOfLastLogon" id="dateOfLastLogon"/>
                    <input type="hidden" name="numberOfLogons" id="numberOfLogons"/>
                    <input type="hidden" name="accountCreated" id="accountCreated"/>
                    <input type="hidden" name="accountLastModified" id="accountLastModified"/>
                    <input type="hidden" name="globalProductNotifications" id="globalProductNotifications"/>
                    <input type="hidden" name="aboutme" id="aboutme"/>
                    <input type="hidden" name="currency" id="currency"/>
                    <input type="hidden" name="language" id="language"/>
                    <div class="right" id="grouppopupoperation" style="float:right;padding:23px 103px 0px 0px;">
                        <input type="button" id="btnCancel" class="logout" style="padding: 10px 20px;"  value="Cancel"/>
                        <input type="button" id="update_button" class="btn btn-default" style="padding: 10px 20px;" value="Update"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div id="output"></div>
    <br style="clear:both;"/>
    <div id="groupGridDiv">
        <div id="groupGrid" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </div>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_custmen").addClass("active");
        initDataGrid();
        $("#search-btn").click(function (e) {
            initDataGrid();
        });
        $('.eventclssrch').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
        });
        $("#btnShowModal").click(function (e) {
            ShowDialog(true);
            e.preventDefault();
        });
        $("#btnCancel").click(function (e) {
            HideDialog();
            e.preventDefault();
        });
        $("#closePopupImg").click(function (e) {
            HideDialog();
            e.preventDefault();
        });
        $("#btnClose").click(function (e) {
            HideDialog();
            document.getElementById("addvendorform").reset();
            e.preventDefault();
        });

        function HideDialog() {
            $("#overlay").hide();
            $("#dialog").hide();
        }
        $("#update_button").click(function () {
            $("#output").html("");
            var params = $("#updatecustomerform").serialize();
//            if (validate()) {
            $.ajax({
                type: "POST",
                url: "updateCustomer",
                dataType: 'json',
                data: params,
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {
                        $("#resp_msg").html("<p style='color:red'> Failed to update Customers.</p>");
                    }
                }
            });
//            }
        });
    });

    function ShowDialog(modal) {
        $("#overlay").show();
        $("#dialog").show();
        if (modal) {
            $("#overlay").unbind("click");
        }
        else {
            $("#overlay").click(function (e) {
                HideDialog();
            });
        }
    }

    function initDataGrid() {
        $("#output").html("");
        var data = "srch_name=" + $("#srch_name").val() + "&srch_mobile=" + $("#srch_mobile").val() + "&srch_emil=" + $("#srch_emil").val();
        $('#groupGrid').datagrid({
            url: 'customersGrid?' + data,
            columns: [[
                    {field: 'firstname', title: 'First Name', width: 100},
                    {field: 'lastname', title: 'Last Name', width: 100},
                    {field: 'emailAddress', title: 'Email', width: 100},
                    {field: 'telephone', title: 'Mobile', width: 100, formatter: function (cellvalue, row, rowindex) {
                            return '<span title="' + cellvalue + '">' + cellvalue + '</span>';
                        }},
                    {field: 'defaultAddress', title: 'Address', width: 100},
                    {field: 'customersId', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            spanarray.push("<button type='button'  onClick='updateVendor(" + rowIndex + ");'>Update</button>");
                            spanarray.push("   ");
                            spanarray.push("<button type='button' onClick='deleteCustomer(" + cellvalue + ");'>delete</button>");
                            spanarray.push("</span>");
                            return spanarray.join(' ');
                        }
                    }
                ]],
            fitColumns: true,
            rownumbers: false,
            selectOnCheck: false,
            checkOnSelect: false,
            singleSelect: true,
            pageList: [10, 20, 30],
            onLoadSuccess: function () {
                checkRecordsInGrid();
            },
            loadMsg: 'Refreshing please wait..',
            onBeforeLoad: function () {
                $('#groupGrid').find('.pagination').hide();
            }
            //                   fit:true
        });
        $('#groupGrid').datagrid('getPager').pagination({
            layout: ['list', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh'],
            displayMsg: 'Displaying {from} to {to} of {total} Groups'
        });
        $("#groupGrid").on("click", function () {
            $("#divPopStatus").html(' ');
            $("#statusDiv").html(' ');
        });
    }
    function checkRecordsInGrid() {
        $('#groupGrid').find('.pagination').show();
        if ($('#groupGrid').datagrid('getRows').length == "0") {
            $(".datagrid-header-check").hide();
            $(".pagination").html('<br/><p class="norecordsfound" ><span>No Records Found.</span></p>');
            $("#btnDeleteGroup").hide();
        } else {
            $("#btnDeleteGroup").show();
            $(".datagrid-header-check").show();
        }
    }

    function updateVendor(rowIndex) {
        
        var selectedRow = $('#groupGrid').datagrid('getRows')[rowIndex];
        $("#customersId").val(selectedRow.customersId);
        $("#gender").val(selectedRow.gender);
        $("#dob").val(selectedRow.dob);
        $("#fax").val(selectedRow.fax);
        $("#password").val(selectedRow.password);
        $("#status").val(selectedRow.status);
        $("#dateOfLastLogon").val(selectedRow.dateOfLastLogon);
        $("#numberOfLogons").val(selectedRow.numberOfLogons);
        $("#accountCreated").val(selectedRow.accountCreated);
        $("#accountLastModified").val(selectedRow.accountLastModified);
        $("#globalProductNotifications").val(selectedRow.globalProductNotifications);
        $("#aboutme").val(selectedRow.aboutme);
        $("#currency").val(selectedRow.currency);
        $("#language").val(selectedRow.language);
        $("#telephone").val(selectedRow.telephone);
        $("#emailAddress").val(selectedRow.emailAddress);
        $("#lastname").val(selectedRow.lastname);
        $("#firstname").val(selectedRow.firstname);
        ShowDialog(true);
    }

    function deleteCustomer(customerId) {
        var test = confirm("Are you suere You want to delete!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "deleteCustomer?customerId=" + customerId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        initDataGrid();
                        $("#output").html("<p style='color:green'>Customer deleted successfully.</p>");
                    } else {
                        $("#output").html("<p style='color:red'>Failed to delete Customer.</p>");
                    }
                }
            });
        }
    }

</script>

