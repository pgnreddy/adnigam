<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %>--%> 
<link href="<spring:url value="/resources/assets_admin/css/admin/jquery-ui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/grayeasyui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/custom.css"/>" rel="stylesheet">
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script src="<spring:url value="/resources/assets_admin/js/admin/login.js"/>"></script>

<!--<custom:setting var="imagePhysicalPath" key="IMAGES_PHYSICAL_PATH" />-->
<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="">Exclusive Offers</h3>
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
        <label class="column1">Offer Title:</label>
        <span class="column2"> 
            <input type="text"  id="srch_title" name="srch_title" class="text_field_nor_mdl"/>
        </span>

    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>
    <!--<form id="addvendorform"  >-->  
    <form id="addvendorform"  method="POST" enctype="multipart/form-data">    
        <button type="button" id="vendor-btn" style="background-color: #68DFF0;padding: 10px 35px;">Add Exclusive Offer</button>
        <br/>  
        <div id="output"></div>  
        <div id="overlay" class="web_dialog_overlay"></div>  
        <div id="dialog" class="web_dialog" style="height: auto !important">  
            <div class="pop_main" id="add_vendor">
                <div style="cursor: pointer; border: 0px solid red; height: 20px; width: 20px;
                     position: absolute; right: 10px; top: 10px" class="right">
                    <a id="closePopupImg">
                        <img class="losePopupImg" src='<spring:url value="/resources/assets_admin/images/btn-close.png"/>'>
                    </a>
                </div>
                <div id="id_Title" class="pop_header">Add Exclusive offer</div>
                <div id="resp_msg"></div>
                <div class="pop_container" >
                    <br/>
                    <div id="divPopStatus"></div>
                    <div class="rows_pop">
                        <label class="column1">Title:</label>
                        <span class="column2"> 
                            <input type="text"  id="txt_title" name="title" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Description:</label>
                        <span class="column2"> 
                            <textarea id="txt_vendoremail" name="description"   ></textarea>
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Price:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_price" name="price" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop" id="image_preview"></div>
                    <div class="rows_pop">
                        <label class="column1">Image:</label>  
                        <span class="column2"> 
                            <input type="file" id="image_id" name="uploadImage" />
                        </span>
                    </div>

                    <%--                    <div class="rows_pop">
                                            <label class="column1">Vendor:</label>
                                            <span class="column2"> 
                                                <select name="vendorID" id="txt_vendorID" name="location"  maxlength="32">
                                                    <option value="">--Select--</option>
                                                    <c:forEach var="ven" items="${vendors}" begin="1">
                                                        <option value="${ven.vendorID}">${ven.name}</option>
                                                    </c:forEach>
                                                </select>

                        </span>
                    </div>--%>
                    <div class="rows_pop">
                        <label class="column1">Quantity:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_quantity" name="quantity" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">OfferPrice:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_offerPrice" name="offerPrice" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">CouponPrice:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_couponPrice" name="couponPrice" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Location:</label>
                        <span class="column2">
                            <select id="sel_locationId" name="locationId" class="select2-dropdown">
                                <c:forEach var="location" items="${locations}">
                                    <option value="${location.id}">${location.name}</option>
                                </c:forEach>
                            </select>
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Sort Order:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_sortOrder" name="sort_order" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        
                        <span class="column1">
                            <div class='input-group date col-lg-1' id='startDatePicker'>
                                <input type='text' id="txt_startDate" name="startDate" class="text_field_nor_mdl" placeholder="Start Date" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </span>
                        
                        <span class="column2">
                            <div class='input-group date col-lg-1' id='endDatePicker'>
                                <input type='text' id="txt_endDate" name="endDate" class="text_field_nor_mdl" placeholder="End Date"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </span>
                    </div>

                    
                    <%--                    <div class="rows_pop">
                                            <label class="column1">Category:</label>
                                            <span class="column2"> 
                                                <select name="categoryID" id="txt_category"  maxlength="32">
                                                    <option value="">--Select--</option>
                                                    <c:forEach var="cat" items="${category}" begin="1">
                                                        <option value="${cat.categoryID}">${cat.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </span>
                                        </div>
                                        <div class="rows_pop">
                                            <label class="column1">Seo Title:</label>
                                            <span class="column2"> 
                                                <input type="text" name="seo_title" id="txt_title" class="text_field_nor_mdl" maxlength="32" />
                                            </span>
                                        </div>--%>
                    <input type="hidden" name="type" id="type" value="1"/>
                    <input type="hidden" name="offerID" id="hdn_offid" />
                    <div class="right" id="grouppopupoperation" style="float:right;padding:0px 103px 2px 0px;">
                        <input type="button" id="btnCancel" class="btn logout" style="padding: 0px 20px 0px 20px;"  value="Cancel"/>
                        <input type="button" id="add_button" class="btn btn-default" style="padding: 0px 20px 0px 20px;" value="Add"/>
                    </div>
                </div>
            </div>
        </div>  
    </form>  
    <br style="clear:both;"/>
    <div id="exclusiveOffGrdDiv">
        <div id="exclusiveOffGrd" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </div>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_exoff").addClass("active");
        initDataGrid();
        $("#vendor-btn").click(function () {
            $("#output").html('');
            document.getElementById("addvendorform").reset();
            $("#add_button").val("Add");
            $("#hdn_offid").val("");
            $("#image_preview").val("");
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
        $("#search-btn").click(function (e) {
            initDataGrid();
        });
         $('#srch_title').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
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
            $("#image_preview").html('');
            $("#hdn_offid").val('');
            $("#overlay").hide();
            $("#dialog").hide();
        }
        
        $('#startDatePicker').datetimepicker();
        $('#endDatePicker').datetimepicker({
            useCurrent: false //Important! See issue #1075
        });
        $("#startDatePicker").on("dp.change", function (e) {
            $('#endDatePicker').data("DateTimePicker").minDate(e.date);
        });
        $("#endDatePicker").on("dp.change", function (e) {
            $('#startDatePicker').data("DateTimePicker").maxDate(e.date);
        });

        $("#add_button").click(function () {
            $("#output").html('');
            var success = false;
            var options = {
                url: 'adminOffersAdd',
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {
                        if ($("#add_button").val() == 'Add') {
                            $("#resp_msg").html("<p style='color:red'> Failed to add Offer.</p>");
                        } else if ($("#add_button").val() == 'Update') {
                            $("#resp_msg").html("<p style='color:red'> Failed to update Offer.</p>");
                        }
                    }
                }
            };
            $("#addvendorform").ajaxSubmit(options);
            return false;
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
        $("#output").html('');
        var data = "title=" + $("#srch_title").val()
        $('#exclusiveOffGrd').datagrid({
            url: 'exclusiveOffGrd?' + data,
            columns: [[
//                            {field: 'id', checkbox: true},
                    {field: 'price', title: 'Price', width: 100},
                    {field: 'offerPrice', title: 'OfferPrice', width: 100},
                    {field: 'couponPrice', title: 'CouponPrice', width: 100},
                    {field: 'title', title: 'Title', width: 100},
                   // {field: 'Ishot', title: 'Ishot', width: 100},
                   // {field: 'description', title: 'Description', width: 100},
                    {field: 'quantity', title: 'Quantity', width: 100},
                    {field: 'locationId', title: 'Location', width: 100},
                    {field: 'sort_order', title: 'Sort Order', width: 100},
                    {field: 'offerID', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            //                                    spanarray.push("<a href='javascript:opengrouppopup("+rowIndex+");'>" + cellvalue + "</a>");
                            spanarray.push("<button type='button'  onClick='updateOffer(" + rowIndex + ");'>edit</button>");
                            spanarray.push("   ");
                            spanarray.push("<button type='button' onClick='deleteOffer(" + cellvalue + ");'>delete</button>");
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
                $('#exclusiveOffGrd').find('.pagination').hide();
            }
            //                   fit:true
        });
        $('#exclusiveOffGrd').datagrid('getPager').pagination({
            layout: ['list', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh'],
            displayMsg: 'Displaying {from} to {to} of {total} Groups'
        });
        $("#exclusiveOffGrd").on("click", function () {
            $("#divPopStatus").html(' ');
            $("#statusDiv").html(' ');
        });
    }
    function checkRecordsInGrid() {
        $('#exclusiveOffGrd').find('.pagination').show();
        if ($('#exclusiveOffGrd').datagrid('getRows').length == "0") {
            $(".datagrid-header-check").hide();
            $(".pagination").html('<br/><p class="norecordsfound" ><span>No Records Found.</span></p>');
            $("#btnDeleteGroup").hide();
        } else {
            $("#btnDeleteGroup").show();
            $(".datagrid-header-check").show();
        }
    }

    function updateOffer(rowIndex) {
        $("#image_preview").html("");
        $("#output").html('');
        var selectedRow = $('#exclusiveOffGrd').datagrid('getRows')[rowIndex];
        $("#txt_title").val(selectedRow.title);
        $("#txt_vendoremail").val(selectedRow.description);
        $("#txt_price").val(selectedRow.price);
        var image = '<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/"/>' + selectedRow.image;
        $("#image_preview").append('<label class="column1">Image Preview:</label> <img src="' + image + '" style="height: 100px; width: 166px;"/>');
//        $("txt_vendorID").val(selectedRow.vendorID).attr('selected', true);
        $("#txt_quantity").val(selectedRow.quantity);
        $("#txt_offerPrice").val(selectedRow.offerPrice);
        $("#hdn_offid").val(selectedRow.offerID);
        $("#txt_couponPrice").val(selectedRow.couponPrice);
        $("#sel_locationId").val(selectedRow.locationId);
        $("#txt_sortOrder").val(selectedRow.sort_order);
        $("#txt_startDate").val(selectedRow.startDate);
        $("#txt_endDate").val(selectedRow.endDate);
//        $("txt_category").val(selectedRow.categoryID).attr('selected', true);
        $("#add_button").val("Update");
        ShowDialog(true);
    }


    function deleteOffer(offerId) {
        var test = confirm("Are you suere You want to delete!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "deleteOffer?offerId=" + offerId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {

                        initDataGrid();
                        $("#output").html("<p style='color:green'>Offer deleted successfully.</p>");
                    } else {
                        $("#output").html("<p style='color:red'>Failed to delete Offer.</p>");
                    }
                }
            });
        }
    }
</script>


