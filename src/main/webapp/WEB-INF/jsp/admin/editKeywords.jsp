<%-- 
    Document   : editKeywords
    Created on : May 26, 2016, 11:05:41 AM
    Author     : rajeshk
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
            <h3 class="page-header" style="">Edit Offer Keywords</h3>
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
        <label class="column1">Offer SEO Title</label>
        <span class="column2"> 
            <input type="text"  id="srch_seotitle" name="srch_seotitle" class="text_field_nor_mdl"/>
        </span>

    </div>

    <button type="button" id="search-btn" style="background-color: #68DFF0;padding: 10px 35px;float:right;">Search</button>
    <br style="clear:both;"/>
    <br style="clear: both;"/>
    <div style="background-color: white">
        <form id="editKeywordsForm" method="POST" >
            <div class="rows_pop">
                <label class="column1">Seo title:</label>
                <span class="column2">
                    <input type="text" id="seo_title" name="seo_title" class="text_field_nor_mdl" style="min-width: 20%"/>
                </span>
            </div>
            <br style="clear: both;"/>
            <div class="rows_pop">
                <label class="column1">Keywords:</label>
                <span class="column2">
                    <textarea id="keywords" name="keywords" style="min-width: 50%" > </textarea>
                </span>
            </div>

            <div class="centered" style="padding:23px 103px 0px 0px;">
                <button type="submit" class="btn btn-primary" style="padding: 10px 20px;">Update</button>
            </div>
        </form>
    </div>
    <br style="clear:both;"/>

</section>

<script>
    $(document).ready(function () {
        $("#nav-accordion").find("li").removeClass("active");
        $(".active_order").removeClass("active");
        $(".active_seoOff").addClass("active");
        $("#search-btn").click(function (e) {
            initDataGrid();
        });
        $("form#editKeywordsForm").submit(function (event) {
            event.preventDefault();
            var data = {};
            $.each(this.elements, function (i, v) {
                var input = $(v);
                data[input.attr("name")] = input.val();
                delete data["undefined"];
            });
            var dataString = JSON.stringify(data);
            var urlString = 'adminUpdateKeywords';
            $.ajax({
                type: 'POST',
                header: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                contentType: 'application/json',
                dataType: 'json',
                url: urlString,
                data: dataString,
                success: function (data) {
                    if (data) {
                        window.location.reload();
                    }
                }
            });
        });

        $('#srch_seotitle').keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode === '13') {
                initDataGrid();
            }
        });
        function initDataGrid() {
            var data = "seo_title=" + $("#srch_seotitle").val();
            $.get("adminSearchSeoOffer?" + data, function (data, status) {
                var jsObj = jQuery.parseJSON(data);
                updateOffer(jsObj.seo_title, jsObj.keywords);
            });
        }

        function updateOffer(seo_title, keywords) {
            $("#seo_title").val(seo_title);
            $("#keywords").val(keywords);
        }
    });

</script>