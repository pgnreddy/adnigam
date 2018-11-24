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
            <h3 class="page-header" style="">Locations</h3>
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
        <label class="column1">Location Name:</label>
        <span class="column2"> 
            <input type="text"  id="srch_name" name="srch_name" class="text_field_nor_mdl eventclssrch"/>
        </span>

        <label class="column1">ZipCode:</label>
        <span class="column2"> 
            <input type="text"  id="srch_zipcode" name="srch_zipcode" class="text_field_nor_mdl eventclssrch"/>
        </span>
    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>

    <form id="locationform" >  
        <button type="button" id="vendor-btn" style="background-color: #68DFF0;padding: 10px 35px;">Add Location</button>
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
                <div id="id_Title" class="pop_header">Add Location</div>
                <div id="resp_msg"></div>
                <div class="pop_container" >
                    <br style="clear: both;"/>
                    <div id="divPopStatus"></div>
                    <div class="rows_pop" style="width: 450px;">
                        <label class="column1">Name:</label>
                        <span class="column2"> 
                            <input type="text"  id="name" name="name" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <br style="clear: both;"/>
                    <div class="rows_pop" style="width: 450px;">
                        <label class="column1">ZipCode:</Emaillabel>
                            <span class="column2"> 
                                <input type="text" id="zipCode" name="zipCode" class="text_field_nor_mdl"  />
                            </span>
                    </div>
                    <div class="rows_pop" style="width: 450px;">
                        <label class="column1">latitude:</Emaillabel>
                            <span class="column2"> 
                                <input type="text" id="latitude" name="latitude" class="text_field_nor_mdl"  />
                            </span>
                    </div>
                    <div class="rows_pop" style="width: 450px;">
                        <label class="column1">longitude:</Emaillabel>
                            <span class="column2"> 
                                <input type="text" id="longitude" name="longitude" class="text_field_nor_mdl"  />
                            </span>
                    </div>
                    <br style="clear: both;" />
                    <input type="hidden" name="status" id="txt_status" value="1"/>
                    <input type="hidden" name="id" id="id"/>
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
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_locmen").addClass("active");
        initDataGrid();

        $('.eventclssrch').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
        });

        $("#search-btn").click(function (e) {
            initDataGrid();
        });
        $("#vendor-btn").click(function () {
            $("#output").html("");
            $("#id").val("");
            document.getElementById("locationform").reset();
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
            document.getElementById("locationform").reset();
            e.preventDefault();
        });

        function HideDialog() {
            $("#id").val("");
            $("#overlay").hide();
            $("#dialog").hide();
        }


        $("#add_button").click(function () {
            $("#output").html("");
            var params = $("#locationform").serialize();
//            if (validate()) {
            $.ajax({
                type: "POST",
                url: "addLocation",
                dataType: 'json',
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
                            $("#resp_msg").html("<p style='color:red'> Failed to add Location.</p>");
                        } else if ($("#add_button").val() == 'Update') {
                            $("#resp_msg").html("<p style='color:red'> Failed to update Location.</p>");
                        }
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
        var data = "name=" + $("#srch_name").val() + "&zipCode=" + $("#srch_zipcode").val();
        $('#groupGrid').datagrid({
            url: 'locationGrid?' + data,
            columns: [[
//                            {field: 'id', checkbox: true},
                    {field: 'name', title: 'Name', width: 100},
                    {field: 'latitude', title: 'Latitude', width: 100},
                    {field: 'longitude', title: 'Longitude', width: 100},
                    {field: 'zipCode', title: 'ZipCode', width: 100},
                    {field: 'status', title: 'Status', width: 100},
                    {field: 'id', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            //                                    spanarray.push("<a href='javascript:opengrouppopup("+rowIndex+");'>" + cellvalue + "</a>");
                            spanarray.push("<button type='button'  onClick='updateLocation(" + rowIndex + ");'>edit</button>");
                            spanarray.push("   ");
                            spanarray.push("<button type='button' onClick='deleteLocation(" + cellvalue + ");'>delete</button>");
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

    function updateLocation(rowIndex) {
        $("#output").html("");
        var selectedRow = $('#groupGrid').datagrid('getRows')[rowIndex];
        $("#name").val(selectedRow.name);
        $("#zipCode").val(selectedRow.zipCode);
        $("#latitude").val(selectedRow.latitude);
        $("#longitude").val(selectedRow.longitude);
        $("#id").val(selectedRow.id);
        $("#add_button").val("Update");
        ShowDialog(true);
    }

    function deleteLocation(locId) {
        var test = confirm("Are you suere You want to delete!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "deleteLocId?locId=" + locId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        initDataGrid();
                        $("#output").html("<p style='color:green'>Location deleted successfully.</p>");
                    } else {
                        $("#output").html("<p style='color:red'>Failed to delete Location.</p>");
                    }
                }
            });
        }
    }

</script>

