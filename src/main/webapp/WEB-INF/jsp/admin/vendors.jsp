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
            <h3 class="page-header" style="">Vendors</h3>
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
        <label class="column1">Name:</label>
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
        
        <label class="column1">Category</label>
        <span class="column2">
            <select id="srch_category">
                <option value="all">All</option>
                <c:forEach items="${category}" var="cat">
                    <option value="${cat.categoryID}">${cat.name}</option>
                </c:forEach>
            </select>
        </span>
    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>

    <form id="addvendorform" >  
        <button type="button" id="vendor-btn" style="background-color: #68DFF0;padding: 10px 35px;">Add Vendor</button>
        <br />  
        <div id="output"></div>  
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
                <div id="id_Title" class="pop_header" style="width:436px;">Add Vendor</div>
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
                            <input type="text" id="txt_vendoremail" name="email" class="text_field_nor_mdl check_exist_val"  />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Mobile:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_mobile" name="mobile" class="text_field_nor_mdl check_exist_val" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop" id="pwddiv">
                        <label class="column1">Password:</label>  
                        <span class="column2"> 
                            <input type="text" id="txt_password" name="password" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Location:</label>
                        <span class="column2"> 
                            <select name="location" id="txt_location" name="location"  maxlength="32">
                                <option value="">--Select--</option>
                                <c:forEach var="loc" items="${locations}" >
                                    <option value="${loc.id}">${loc.name}</option>
                                </c:forEach>
                            </select>

                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Latitude:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_latitude" name="latitude" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Longitude</label>
                        <span class="column2"> 
                            <input type="text" id="txt_longitude" name="longitude" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Category:</label>
                        <span class="column2"> 
                            <select name="category" id="txt_category"  maxlength="32">
                                <option value="">--Select--</option>
                                <c:forEach var="cat" items="${category}">
                                    <option value="${cat.categoryID}">${cat.name}</option>
                                </c:forEach>
                            </select>

                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Address:</label>
                        <span class="column2"> 
                            <textarea id="txt_address" name="address"   ></textarea>
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">ZipCode:</label>
                        <span class="column2"> 
                            <input type="text" name="zipcode" id="txt_zipcode" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Sort Order:</label>
                        <span class="column2"> 
                            <input type="text" name="sortOrder" id="txt_sortOrder" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <input type="hidden" name="vendorID" id="vendorId"/>
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
        $(".active_vendor").addClass("active");
        initDataGrid();
        $("#search-btn").click(function () {
            initDataGrid();
        });
        $('.eventclssrch').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
        });
        $("#vendor-btn").click(function () {
            document.getElementById("addvendorform").reset();
//            $("#pwddiv").show();
            $("#vendorId").val('')
            $("#resp_msg").html('');
            $("#add_button").val("Add");
            $("#output").html("");
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
            document.getElementById("addvendorform").reset();
            e.preventDefault();
        });

        function HideDialog() {
            $("#overlay").hide();
            $("#dialog").hide();
        }
        $(".check_exist_val").focusout(function () {
            var testvar = $(this).val();
            $("#resp_msg").html('');
            var jsondata=$('#groupGrid').datagrid('getData').existVendorsList;
            $.each(jsondata, function (idx, obj) {
                if (obj.email.toLowerCase() == testvar.toLowerCase()) {
                    $("#resp_msg").html("<p style='color:red'>Email already exist</p>");
                }
                if (obj.phone== testvar) {
                    $("#resp_msg").html("<p style='color:red'>Mobile number already exist</p>");
                }
            });
        });


        $("#add_button").click(function () {
            $("#output").html("");
            var params = $("#addvendorform").serialize();
//            if (validate()) {
            $.ajax({
                type: "POST",
                url: "addVendor",
                dataType: 'json',
                data: params,
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {

                        if ($("#add_button").val() == 'Add') {

                            $("#resp_msg").html("<p style='color:red'>Failed to add Vendor.</p>");

                        } else if ($("#add_button").val() == 'Update') {
                            $("#resp_msg").html("<p style='color:red'> Failed to update Vendor.</p>");
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
        var data = "srch_name=" + $("#srch_name").val() + "&srch_mobile=" + $("#srch_mobile").val() + "&srch_emil=" + $("#srch_emil").val() + "&srch_cat=" + $("#srch_category").val();
        $('#groupGrid').datagrid({
            url: 'vendorgrid?' + data,
            columns: [[
//                            {field: 'id', checkbox: true},
                    {field: 'name', title: 'Name', width: 100},
                    {field: 'email', title: 'Email', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            if (cellvalue > 0) {
                                spanarray.push("<a style='text-decoration:underline' href='javascript:loaddata(\"contactPage.htm?gname=" + row.group_name + "\");'>" + cellvalue + "</a>");
                            } else {
                                spanarray.push(cellvalue);
                            }
                            spanarray.push("</span>");
                            return spanarray.join(' ');
                        }
                    },
                    {field: 'phone', title: 'Mobile', width: 100, formatter: function (cellvalue, row, rowindex) {
                            return '<span title="' + cellvalue + '">' + cellvalue + '</span>';
                        }},
                    {field: 'location', title: 'Location', width: 100},
                    {field: 'latitude', title: 'Latitude', width: 100},
                    {field: 'longitude', title: 'Longitude', width: 100},
                    {field: 'sortOrder', title: 'SortOrder', width: 100},
//                    {field: 'city', title: 'City', width: 100},
//                    {field: 'state', title: 'State', width: 100},
//                    {field: 'country', title: 'Country', width: 100},
                    {field: 'vendorID', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            //                                    spanarray.push("<a href='javascript:opengrouppopup("+rowIndex+");'>" + cellvalue + "</a>");
                            spanarray.push("<button type='button'  onClick='updateVendor(" + rowIndex + ");'>edit</button>");
                            spanarray.push("   ");
                            spanarray.push("<button type='button' onClick='deleteVendor(" + cellvalue + ");'>delete</button>");
                            spanarray.push("</span>");
                            return spanarray.join(' ');
                        }
                    }
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
            displayMsg: 'Displaying {from} to {to} of {total} Vendors'
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
        $("#resp_msg").html("");
        $("#output").html("");
        //$("#pwddiv").hide();
        var selectedRow = $('#groupGrid').datagrid('getRows')[rowIndex];
        $("#vendorId").val(selectedRow.vendorID);
        $("#txt_vendorname").val(selectedRow.name);
        $("#txt_vendoremail").val(selectedRow.email);
        $("#txt_mobile").val(selectedRow.phone);
        $("#txt_password").val(selectedRow.password);
        $("#txt_location").val(selectedRow.locationId).prop('selected', true);
        $("#txt_latitude").val(selectedRow.latitude);
        $("#txt_longitude").val(selectedRow.longitude);
        $("#txt_category").val(selectedRow.categoryID).prop('selected', true);
        $("#txt_address").val(selectedRow.location);
        $("#txt_zipcode").val(selectedRow.zipCode);
        $("#txt_sortOrder").val(selectedRow.sortOrder);
        $("#add_button").val("Update");
        ShowDialog(true);
    }

    function deleteVendor(vendorId) {
        var test = confirm("Are you suere You want to delete!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "deleteVendor?vendorId=" + vendorId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        initDataGrid();
                        $("#output").html("<p style='color:green'>Vendor deleted successfully.</p>");
                    } else {
                        $("#output").html("<p style='color:red'>Failed to delete Vendor.</p>");
                    }
                }
            });
        }
    }

</script>

