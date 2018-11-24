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
            <h3 class="page-header" style="">Orders</h3>
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
        <label class="column1">Order Id:</label>
        <span class="column2"> 
            <input type="text"  id="txt_orderId" name="txt_orderId" class="text_field_nor_mdl eventclssrch"/>
        </span>

        <label class="column1">Customer Name:</label>
        <span class="column2"> 
            <input type="text"  id="txt_customername" name="txt_customername" class="text_field_nor_mdl eventclssrch"/>
        </span>
        <label class="column1">Status:</label>
        <span class="column2"> 
            <!--<input type="text"  id="txt_city" name="txt_city" class="text_field_nor_mdl"/>-->
                    <select name="txt_city" id="txt_city" >
                        <option value="">--Select--</option>
                        <c:forEach var="ostatus" items="${orderStatCodes}" begin="0">
                            <option value="${ostatus.key}"
                                    <c:if test="${ostatus.key eq 7}">selected</c:if>
                                    >${ostatus.value}</option>
                        </c:forEach>
                    </select>
        </span>
    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>
    <div id="ordersGridDiv">
        <table id="ordersGrid" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </table>>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");

        $(".active_order").addClass("active");
        initDataGrid();
        $("#vendor-btn").click(function () {
            document.getElementById("addvendorform").reset();
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
            document.getElementById("addvendorform").reset();
            e.preventDefault();
        });
        $("#search-btn").click(function (e) {
            initDataGrid();
        });
        
         $('.eventclssrch').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                initDataGrid();
            }
        });


        function HideDialog() {
            $("#overlay").hide();
            $("#dialog").fadeOut(300);
        }

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
        var data = "orderid=" + $("#txt_orderId").val() + "&custormername=" + $("#txt_customername").val() + "&city=" + $("#txt_city").val();
        $('#ordersGrid').datagrid({
            url: 'ordersGrid?' + data,
            columns: [[
                    {field: 'orderId', title: 'OrderId', width: 100},
                    {field: 'customers_name', title: 'CustomersName', width: 100},
//                    {field: 'customersCompany', title: 'customersCompany', width: 100},
//                    {field: 'customersStreetAddress', title: 'Address', width: 100},
                    {field: 'STATUS', title: 'STATUS', width: 100},
                    {field: 'amount', title: 'Total', width: 100, formatter: function (cellvalue, row, rowIndex) {                           
                            return 'Rs '+cellvalue;
                        }},
//                    {field: 'customersPostcode', title: 'CustomersPostcode', width: 100},
//                    {field: 'customersState', title: 'CustomersState', width: 100},
                    {field: 'payment_method', title: 'PaymentMethod', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span title=\"" + cellvalue + "\">");
                            spanarray.push(cellvalue);
                            spanarray.push("</span>");
                            return spanarray.join(' ');
                        }
                    },
                    {field: 'date_purchased', title: 'PurchaseDate', width: 100,
                        formatter: function (cellvaule, row, rowIndex) {
                            var d = new Date(cellvaule);
                            var mnth = addZero(d.getMonth() + 1, 2);
                            var yr = d.getFullYear();
                            var dat = addZero(d.getDate(), 2);
                            var h = addZero(d.getHours(), 2);
                            var m = addZero(d.getMinutes(), 2);
                            var s = addZero(d.getSeconds(), 2);
                            var ms = addZero(d.getMilliseconds(), 3);
                            return yr + "-" + mnth + "-" + dat + " " + h + ":" + m + ":" + s;


                        }
                    },
                    {field: 'customers_city', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            spanarray.push("<a href='viewOrder?orderId=" + row.orderId + "'>View</a>");
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
                $('#ordersGrid').find('.pagination').hide();
            }
            //                   fit:true
        });
        $('#ordersGrid').datagrid('getPager').pagination({
            layout: ['list', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh'],
            displayMsg: 'Displaying {from} to {to} of {total} Groups'
        });
        
        $('#ordersGrid').datagrid({
            rowStyler: function (index, row) {
                if (row.adminRead>0) {
                    return 'background-color:#e6e6e6';
                }
            }
        });
        
        $("#ordersGrid").on("click", function () {
            $("#divPopStatus").html(' ');
            $("#statusDiv").html(' ');
        });
    }
    function checkRecordsInGrid() {
        $('#ordersGrid').find('.pagination').show();
        if ($('#ordersGrid').datagrid('getRows').length == "0") {
            $(".datagrid-header-check").hide();
            $(".pagination").html('<br/><p class="norecordsfound" ><span>No Records Found.</span></p>');
            $("#btnDeleteGroup").hide();
        } else {
            $("#btnDeleteGroup").show();
            $(".datagrid-header-check").show();
        }
    }

    function addZero(x, n) {
        while (x.toString().length < n) {
            x = "0" + x;
        }
        return x;
    }


    function updateVendor(rowIndex) {
//        $("#add_button").value("Update");
        var selectedRow = $('#ordersGrid').datagrid('getRows')[rowIndex];
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

