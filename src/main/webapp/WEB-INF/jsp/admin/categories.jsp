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
            <h3 class="page-header" style="">Categories</h3>
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

    <!--<form id="addCategory form"  >-->  
    <form id="addvendorform"  method="POST" enctype="multipart/form-data">    
        <button type="button" id="vendor-btn" style="background-color: #68DFF0;padding: 10px 35px;">Add Category</button>
        <br/>  
        <div id="output"></div>  
        <div id="overlay" class="web_dialog_overlay"></div>  
        <div id="dialog" class="web_dialog">  
            <div class="pop_main" id="add_vendor">
                <div style="cursor: pointer; border: 0px solid red; height: 20px; width: 20px;
                     position: absolute; right: 10px; top: 10px" class="right">
                    <a id="closePopupImg">
                        <img class="losePopupImg" src='<spring:url value="/resources/assets_admin/images/btn-close.png"/>'>
                    </a>
                </div>
                <div id="id_Title" class="pop_header">Add Category</div>
                <div id="resp_msg"></div>
                <div class="pop_container" >
                    <br/>
                    <div id="divPopStatus"></div>
                    <div class="rows_pop">
                        <label class="column1">Name:</label>
                        <span class="column2"> 
                            <input type="text"  id="txt_title" name="name" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Description:</label>
                        <span class="column2"> 
                            <textarea id="txt_vendoremail" name="description"   ></textarea>
                        </span>
                    </div>
                    <div class="rows_pop" id="image_preview"></div>
                    <div class="rows_pop">
                        <label class="column1">CategoryIcon:</label>  
                        <span class="column2"> 
                            <input type="file" id="image_id" name="uploadImage" />
                        </span>
                    </div>
                     <div class="rows_pop">
                        <label class="column1">Parent Category:</label>
                        <span class="column2"> 
                            <select name="parentID" id="txt_parentcategory"  maxlength="32" style="width:200px">
                                <option value="0">--Select--</option>
                                <c:forEach var="cat" items="${category}">
                                    <option value="${cat.categoryID}">${cat.name}</option>
                                </c:forEach>
                            </select>

                        </span>
                    </div>
                    <div class="rows_pop">
                        <label class="column1">Sort Order:</label>
                        <span class="column2"> 
                            <input type="text"  id="txt_sortOrder" name="sortOrder" class="text_field_nor_mdl" maxlength="32" />
                        </span>
                    </div>

                    <%--                    <div class="rows_pop">
                                            <label class="column1">Seo Title:</label>
                                            <span class="column2"> 
                                                <input type="text" name="seo_title" id="txt_title" class="text_field_nor_mdl" maxlength="32" />
                                            </span>
                                        </div>--%>
                    <input type="hidden" name="status" id="status" value="1"/>
                    <input type="hidden" name="categoryID" id="categoryID"/>
                    <div class="right" id="grouppopupoperation" style="float:right;padding:23px 103px 0px 0px;">
                        <input type="button" id="btnCancel" class="logout" style="padding: 10px 20px;"  value="Cancel"/>
                        <input type="button" id="add_button" class="btn btn-default" style="padding: 10px 20px;" value="Add"/>
                    </div>
                </div>
            </div>
        </div>  
    </form>  
    <br style="clear:both;"/>
    <div id="categoryGridDiv">
        <div id="categoryGrid" class="easyui-datagrid" rownumbers="true" pagination="true" style="width: auto">
        </div>
    </div>

</section>
<!-- /MAIN CONTENT -->
<script>
    
    var categories =[];
    <c:forEach var="cat" items="${category}">
        categories['${cat.categoryID}']='${cat.name}';        
    </c:forEach>
                                    
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_cat").addClass("active");

        initDataGrid();
        $("#vendor-btn").click(function () {
            document.getElementById("addvendorform").reset();
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
            $("#categoryID").val('')
            $("#image_preview").html("");
            $("#overlay").hide();
            $("#dialog").hide();
        }

        $("#add_button").click(function () {
            $("#output").html("");
            var options = {
                url: 'addCategory',
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        HideDialog();
                        initDataGrid();
                    } else {
                        if ($("#add_button").val() == 'Add') {
                            $("#resp_msg").html("<p style='color:red'> Failed to add Category.</p>");
                        } else if ($("#add_button").val() == 'Update') {
                            $("#resp_msg").html("<p style='color:red'> Failed to update Category.</p>");
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
        $("#output").html("");
        $('#categoryGrid').datagrid({
            url: 'categoryGrid',
            columns: [[
//                            {field: 'id', checkbox: true},
                    {field: 'name', title: 'Name', width: 100},
                    {field: 'description', title: 'Description', width: 100},
//                    {field: 'seo_title', title: 'SeoTitle', width: 100},
                    {field: 'status', title: 'Status', width: 100},
                    {field: 'parentID', title: 'Parent Category', width: 100,formatter: function (cellvalue, row, rowIndex) {                           
                            return categories[cellvalue];
                        }},
                    {field: 'categoryID', title: 'Actions', width: 100,
                        formatter: function (cellvalue, row, rowIndex) {
                            var spanarray = [];
                            spanarray.push("<span>");
                            //                                    spanarray.push("<a href='javascript:opengrouppopup("+rowIndex+");'>" + cellvalue + "</a>");
                            spanarray.push("<button type='button'  onClick='updateOffer(" + rowIndex + ");'>edit</button>");
                            spanarray.push("   ");
                            spanarray.push("<button type='button' onClick='deleteCategory(" + cellvalue + ");'>delete</button>");
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
                $('#categoryGrid').find('.pagination').hide();
            }
            //                   fit:true
        });
        $('#categoryGrid').datagrid('getPager').pagination({
            layout: ['list', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh'],
            displayMsg: 'Displaying {from} to {to} of {total} Groups'
        });
        $("#categoryGrid").on("click", function () {
            $("#divPopStatus").html(' ');
            $("#statusDiv").html(' ');
        });
    }
    function checkRecordsInGrid() {
        $('#categoryGrid').find('.pagination').show();
        if ($('#categoryGrid').datagrid('getRows').length == "0") {
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
        $("#output").html("");
        var selectedRow = $('#categoryGrid').datagrid('getRows')[rowIndex];
        $("#txt_title").val(selectedRow.name);
        $("#txt_vendoremail").val(selectedRow.description);
        $("#txt_price").val(selectedRow.price);
        $("#txt_parentcategory").val(selectedRow.parentID).prop('selected', true);
        
        var image = '<spring:url value="/resources/assets_admin/assets/categories/"/>' + selectedRow.categoryIcon;
        $("#image_preview").append('<label class="column1">Image Preview:</label> <img src="' + image + '" style="height: 60px; width: 85px;"/>');
//        $("txt_title").val(selectedRow.seo_title);
        $("#categoryID").val(selectedRow.categoryID);
        $("#txt_sortOrder").val(selectedRow.sortOrder);
        $("#add_button").val("Update");
        ShowDialog(true);
    }

    function deleteCategory(catId) {
        var test = confirm("Are you suere You want to delete!");
        if (test) {
            $.ajax({
                type: "POST",
                url: "deleteCategory?catId=" + catId,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        initDataGrid();
                        $("#output").html("<p style='color:green'>Category deleted successfully.</p>");
                    } else {
                        $("#resp_msg").html("<p style='color:red'>Failed to delete Category.</p>");
                    }
                }
            });
        }

    }
</script>

