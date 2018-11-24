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
            <h3 class="page-header" style="">Administrators</h3>
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

    <form id="addadminform" >  
        <button type="button" id="vendor-btn" style="background-color: #68DFF0;padding: 10px 35px;">Add Admin</button>
        <br />  
        <div id="output"></div>  
        <div id="overlay" class="web_dialog_overlay"></div>  
        <div id="dialog" class="web_dialog">  
            <div class="pop_main" id="add_vendor">
                <div style="cursor: pointer; border: 0px solid red; height: 20px; width: 20px;
                     position: absolute; right: 10px; top: 10px" class="right">
                    <a id="closePopupImg">
                        <%--<img class="losePopupImg" src="<spring:url value="/resources/assets_admin/images/btn-close.gif"/>">--%>
                        <img class="losePopupImg" src='<spring:url value="/resources/assets_admin/images/btn-close.png"/>'>
                    </a>
                </div>
                <div id="id_Title" class="pop_header">Add Admin</div>
                <div id="resp_msg"></div>
                <div class="pop_container" >
                    <br/>
                    <div id="divPopStatus"></div>
                    <div class="rows_pop">
                        <label class="column1">Name:</label>
                        <span class="column2"> 
                            <input type="text"  id="txt_vendorname" name="vendorname" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Email:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_vendoremail" name="email" class="text_field_nor_mdl"  />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Role:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_role" name="role" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>

                    <input type="hidden" name="vendorId" id="vendorId"/>
                    <div class="right" id="grouppopupoperation" style="float:right;padding:23px 103px 0px 0px;">
                        <input type="button" id="btnCancel" class="logout" style="padding: 10px 20px;"  value="Cancel"/>
                        <input type="button" id="add_button" class="btn btn-default" style="padding: 10px 20px;" value="Add"/>
                    </div>
                </div>
            </div>
        </div>  
    </form>  
    <br style="clear:both;"/>
    <div id="groupGridDiv">
        <div id="groupGrid" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </div>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $(".sidebar-menu").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_admin").addClass("active");
        initDataGrid();
        $("#vendor-btn").click(function () {
            document.getElementById("addadminform").reset();
            $("#add_button").val("Add");
            ShowDialog(true);
            e.preventDefault();
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
            document.getElementById("addadminform").reset();
            e.preventDefault();
        });
        $("#btnSubmit").click(function (e) {
            if ($("#brands").find("input:checked").length > 0) {
                var brand = $("#brands input:radio:checked").val();
                $("#output").html("<b>Your Country Is : </b>" + brand);
                HideDialog();
                e.preventDefault();
            }
            else {
                ShowDialog(true);
                e.preventDefault();
            }
        });


        function HideDialog() {
            $("#overlay").hide();
            $("#dialog").fadeOut(300);
        }


        $("#add_button").click(function () {
            var params = $("#addadminform").serialize();
//            if (validate()) {
            $.ajax({
                type: "POST",
                url: "addAdmin",
                data: params,
                cache: false,
//                    beforeSend: function () {
//                        $("#add_vendor").val('Adding....');
//                    },
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {
                        if ($("#add_button").val() == 'Add') {
                            $("#resp_msg").html("<p style='color:red'>Failed to create Admin.</p>");
                        } else if ($("#add_button").val() == 'Update') {
                            $("#resp_msg").html("<p style='color:red'>Failed to update Admin.</p>");
                        }
                    }
                }
            });
//            }
        });
    });

    function ShowDialog(modal) {
        $("#overlay").show();
        $("#dialog").fadeIn(300);
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
        $('#groupGrid').datagrid({
            url: 'admingrid',
            columns: [[
//                            {field: 'id', checkbox: true},
                    {field: 'name', title: 'Name', width: 100},
                    {field: 'email', title: 'Email', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span >");
                            if (cellvalue > 0) {
                                spanarray.push("<a style='text-decoration:underline' href='javascript:loaddata(\"contactPage.htm?gname=" + row.group_name + "\");'>" + cellvalue + "</a>");
                            } else {
                                spanarray.push(cellvalue);
                            }
                            spanarray.push("</span>");
                            return spanarray.join(' ');
                        }
                    },
                    {field: 'role', title: 'Role', width: 100}
//                    {field: 'ZipCode', title: 'Pallavi', width: 100}
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
//        $("#add_button").value("Update");
        var selectedRow = $('#groupGrid').datagrid('getRows')[rowIndex];
        $("#vendorId").val(selectedRow.vendorID);
        $("#txt_vendorname").val(selectedRow.name);
        $("#txt_vendoremail").val(selectedRow.email);
        $("#txt_mobile").val(selectedRow.phone);
        $("#txt_password").val(selectedRow.password);
        $("txt_location").val(selectedRow.location).attr('selected', true);
        $("#txt_latitude").val(selectedRow.latitude);
        $("#txt_longitude").val(selectedRow.longitude);
        $("txt_category").val(selectedRow.categoryID).attr('selected', true);
        $("#txt_zipcode").val(selectedRow.zipCode);
        $("#add_button").val("Update");
        ShowDialog(true);
    }
</script>

