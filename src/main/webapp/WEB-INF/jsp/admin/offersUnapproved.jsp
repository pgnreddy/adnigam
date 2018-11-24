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
            <h3 class="page-header" style="">Unapproved Offers</h3>
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
    <form id="addvendorform" method="POST" enctype="multipart/form-data">    


        <div id="overlay" class="web_dialog_overlay"></div>  
        <div id="dialog" class="web_dialog">  
            <div class="pop_main" id="add_vendor">
                <div style="cursor: pointer; border: 0px solid red; height: 20px; width: 20px;
                     position: absolute; right: 10px; top: 10px" class="right">
                    <a id="closePopupImg">
                        <img class="losePopupImg" src='<spring:url value="/resources/assets_admin/images/btn-close.png"/>'>
                    </a>
                </div>
                <div id="id_Title" class="pop_header">Update and approve</div>
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
                        <label class="column1">Sort Order:</label>
                        <span class="column2"> 
                            <input type="text" id="txt_sortOrder" name="sort_order" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <%--<div class="rows_pop">
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
                    </div> --%>
                    <input type="hidden" name="type" id="type" value="0"/>
                    <input type="hidden" name="offerID" id="hdn_offid" />
                    <input type="hidden" name="vendorID" id="hdn_vendid" />
                    <div class="right" id="grouppopupoperation" style="float:right;padding:23px 103px 0px 0px;">
                        <input type="button" id="btnCancel" class="logout" style="padding: 10px 20px;"  value="Cancel"/>
                        <input type="button" id="add_button" class="btn btn-default" style="padding: 10px 20px;" value="Add"/>
                    </div>
                </div>
            </div>
        </div>  
    </form>
    <br style="clear:both;"/>
    <div id="unapprovedOffGridDiv">
        <div id="unapprovedOffGrid" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </div>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_pendOff").addClass("active");
        initDataGrid();

        $("#search-btn").click(function (e) {
            initDataGrid();
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

        $('#srch_title').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
        });
        $("#add_button").click(function () {
            $("#output").html('');
            var data = "title=" + $("#txt_title").val()
            var options = {
                url: 'adminOffersAdd?' + data,
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data && data.respmsg === "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {
                        if ($("#add_button").val() === 'Add') {
                            $("#resp_msg").html("<p style='color:red'> Failed to add Offer.</p>");
                        } else if ($("#add_button").val() === 'Approve') {
                            $("#resp_msg").html("<p style='color:red'> Failed to update Offer.</p>");
                        }
                    }
                }
            };
            $("#addvendorform").ajaxSubmit(options);
            return false;
        });
        function HideDialog() {
            $("#image_preview").html("");
            $("#hdn_offid").val("");
            $("#resp_msg").html("");
            $("#overlay").hide();
            $("#dialog").hide();
        }
    });

    function ShowDialog(modal) {
        $("#overlay").show();
        $("#dialog").show();
        if (modal) {
            $("#overlay").unbind("click");
        } else {
            $("#overlay").click(function (e) {
                HideDialog();
            });
        }
    }


    function initDataGrid() {
        var data = "title=" + $("#srch_title").val();
        $('#unapprovedOffGrid').datagrid({
            url: 'unapprovedOffGrid?' + data,
            columns: [[
                    {field: 'price', title: 'Price', width: 100},
                    {field: 'offerPrice', title: 'OfferPrice', width: 100},
                    {field: 'couponPrice', title: 'CouponPrice', width: 100},
                    {field: 'title', title: 'Title', width: 100},
                   // {field: 'Ishot', title: 'Ishot', width: 100},
                //    {field: 'description', title: 'Description', width: 100},
                    {field: 'quantity', title: 'Quantity', width: 100},
                    {field: 'offerID', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            //                                    spanarray.push("<a href='javascript:opengrouppopup("+rowIndex+");'>" + cellvalue + "</a>");
                            spanarray.push("<button type='button'  onClick='updateOffer(" + rowIndex + ");'>edit/approve</button>");
                            spanarray.push("<button type='button'  onClick='deleteOffer(" + cellvalue + ");'>delete</button>");
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
                $('#unapprovedOffGrid').find('.pagination').hide();
            }
            //                   fit:true
        });
        $('#unapprovedOffGrid').datagrid('getPager').pagination({
            layout: ['list', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh'],
            displayMsg: 'Displaying {from} to {to} of {total} Groups'
        });
        $("#unapprovedOffGrid").on("click", function () {
            $("#divPopStatus").html(' ');
            $("#statusDiv").html(' ');
        });

    }
    function checkRecordsInGrid() {
        $('#unapprovedOffGrid').find('.pagination').show();
        if ($('#unapprovedOffGrid').datagrid('getRows').length == "0") {
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
        var selectedRow = $('#unapprovedOffGrid').datagrid('getRows')[rowIndex];
        $("#txt_title").val(selectedRow.title);
        $("#txt_vendoremail").val(selectedRow.description);
        $("#txt_price").val(selectedRow.price);
        var image = '<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/"/>' + selectedRow.image;
        $("#image_preview").append('<label class="column1">Image Preview:</label> <img src="' + image + '" style="height: 100px; width: 166px;"/>');
//        $("txt_vendorID").val(selectedRow.vendorID).attr('selected', true);
        $("#txt_quantity").val(selectedRow.quantity);
        $("#txt_offerPrice").val(selectedRow.offerPrice);
        $("#hdn_offid").val(selectedRow.offerID);
        $("#hdn_vendid").val(selectedRow.vendorID);
        $("#txt_couponPrice").val(selectedRow.couponPrice);
        $("#txt_sortOrder").val(selectedRow.sort_order);
//        $("txt_category").val(selectedRow.categoryID).attr('selected', true);
        $("#add_button").val("Approve");
        ShowDialog(true);
    }

    function approveOffer(offerId) {
        var test = confirm("Are you sure You want to approve!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "adminOffersApprove?offerId=" + offerId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg === "success")
                    {
                        initDataGrid();
                    }
                }
            });
        }
    }

    function deleteOffer(offerId) {
        var test = confirm('Are you sure you want to delete the offer?');
        if (test) {
            $.ajax({
                type: "POST",
                url: "adminDeleteUnapprovedOff?offerId=" + offerId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg === "success") {
                        initDataGrid();
                    }
                }
            });
        }
    }


</script>

