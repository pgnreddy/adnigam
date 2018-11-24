<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Offers</h3>
        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-xs-8 pull-left">
                                <form class="form-inline" id="offerForm" action="<%=request.getContextPath()%>/vendor/orders">
                                    <label for="offerID">Offer Id</label>
                                    <input type="text" class="form-control" name="offerID" id="offerID" value="${param.offerID}"/>
                                    <label for="productName">Title</label>
                                    <input type="text" class="form-control" name="productName" id="productName" value="${param.productName}"/>
                                    <button class="btn"  type="submit">Submit</button>
                                </form>
                            </div>  

                            <div class="col-xs-2 pull-right">
                                <button class="btn" id="AddNewOffer" style="background-color:#ff7f00;color:#fff"><i class="glyphicon glyphicon-plus-sign"></i>&nbsp;Add New Offer</button> 
                            </div>
                        </div>
                        <br/>
                        <br/>

                        <c:set var="offersList" value="${searchObj.data}"/>
                        <c:choose>
                            <c:when test="${not empty offersList}">
                                <table class="table table-bordered table-condensed table-striped" style="border-collapse:collapse;">

                                    <thead>
                                        <tr><th>OfferId</th>
                                            <th>Title</th>
                                            <th>Hot</th>
                                            <th>Check for top</th>
                                            <th>Sort Order</th>
                                            <th>Price</th>
                                            <th>Offer price</th>
                                            <th>Coupon price</th>
                                            <th>Quantity</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>

                                    <tbody>                               
                                        <c:forEach var="offer" items="${offersList}" varStatus="status"> 
                                            <tr>
                                                <td>${offer.offerID}</td>
                                                <td>${offer.title}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${offer.ishot==1}">
                                                            Yes
                                                        </c:when>
                                                        <c:otherwise>         
                                                            No
                                                        </c:otherwise>
                                                    </c:choose>

                                                </td>
                                                <td>

                                                    <c:if test="${offer.ishot==0}">
                                                        <input id="top-offer" class="top-offer" name="top-offer" type="radio" pid="${offer.offerID}"/>
                                                    </c:if>

                                                </td>
                                                <td>${offer.sort_order}</td>
                                                <td><fmt:formatNumber value="${offer.price}" type="CURRENCY" pattern="Rs ###,##0" /></td>
                                                <td><fmt:formatNumber value="${offer.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></td>
                                                <td><fmt:formatNumber value="${offer.couponPrice}" type="CURRENCY" pattern="Rs ###,##0" /></td>
                                                <td>${offer.quantity}</td>
                                                <td>
                                                    <a href="#" class="edit" pid="${offer.offerID}" >Edit</a>&nbsp;
                                                    <a href="#" class="delete" pid="${offer.offerID}" >Delete</a>                                                    
                                                </td>

                                            </tr>
                                        </c:forEach>


                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                No records found.
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${not empty offersList}">
                            <span class="pagination" id="pagination">                                    
                            </span>
                            <span>Total : ${searchObj.count}</span>
                        </c:if>
                    </div>

                </div> 


            </div>
        </div>
    </section>


    <!-- Modal -->
    <div id="myModal" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Modal Header</h4>
                </div>
                <div class="modal-body">
                    <p>Some text in the modal.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>


</section>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script src="<spring:url value="/resources/assets_admin/js/bootstrap.min.js"/>"></script>
<!--<script>
    $(document).ready(function () {
        $("#addcolor").click(function () {
            alert("jj");
            var txt1 = "<input type='colorstemp' name='avcolors' />";              
            var v = $("#clrs").html();
            var v1 = v + txt1
            $("#clrs").html(v1);
            return;
        });
    });

</script>-->




<script type="text/javascript">


    function applyValidation() {
        $("#addOfferForm").validate({
            rules: {
                title: {required: true},
                offerPrice: {required: true,renumber: true},
                couponPrice: {required: true,number: true},
                price: {required: true, number: true},
                quantity: {required: true, number: true},
                description: {required: true}
            },
            messages: {
                title: "Please enter title",
                price: "Please enter price",
                offerPrice:"Please enter offer price",
                couponPrice:"Please enter couponPrice price",
                quantity: "Please enter quantity",
                description: "Please enter description"
            },
            errorPlacement: function (error, element) {
                // Append error within linked label
                $(element)
                        .closest("form")
                        .find("label[for='" + element.attr("id") + "']")
                        .append(error);
            },
            errorElement: "span"
        });
    }

    $(document).ready(function () {

        var myModal = $("#myModal");
        myModal.modal('hide');
        var paginationCallback = function (pageNumber, event) {
            event.preventDefault();
            //console.log(pageNumber);
            var url = "<%=request.getContextPath()%>/vendor/offers?limit=${searchObj.limit}&status=${param.status}&page=" + pageNumber + "&offerID=${param.offerID}&productName=${param.productName}";
            //console.log(url);        
            window.location.href = url;
        };

        $("#pagination").pagination({
            items: ${searchObj.count},
            itemsOnPage: ${searchObj.limit},
            currentPage:${searchObj.page},
            cssStyle: 'light-theme',
            onPageClick: paginationCallback
        });



        $("#offerForm").submit(function (e) {
            e.preventDefault();
            var offerID = $(this).find("#offerID").val();
            var productName = $(this).find("#productName").val();
            if (isNaN(offerID)) {
                return false;
            }
            var url = "<%=request.getContextPath()%>/vendor/offers?offerID=" + offerID + "&productName=" + productName;
            //console.log(url);
            window.location.href = url;
        });

        $("#AddNewOffer").click(function () {
            myModal.find(".modal-dialog").load("<%=request.getContextPath()%>/vendor/offerForm?type=add", function () {
                myModal.modal('show');
                applyValidation();
            });
        });
        $(document).delegate(".edit", "click", function (e) {
            $this = $(this);
            var pid = $this.attr("pid");
            myModal.find(".modal-dialog").load("<%=request.getContextPath()%>/vendor/offerForm?type=edit&offerID=" + pid, function () {
                myModal.modal('show');
                applyValidation();
            });
        });



        $(document).delegate(".delete", "click", function (e) {
            e.preventDefault();
            $this = $(this);
            var pid = $this.attr("pid");

            if (window.confirm("Do you want to delete the offer")) {

                $.ajax({
                    type: "GET",
                    url: "<spring:url value="/vendor/deleteOffer"/>",
                    data: "offerID=" + pid,
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data && data.offerID)
                        {
                            window.location.reload();
                        }
                    },
                });

            }

            return false;
        });

    });


    //***************************

    $(document).ready(function () {
        bindEvents();
    });


    function bindEvents() {
        $("#main-content").delegate("#saveOffer", "click", function () {

            var valid = $("#addOfferForm").valid();

            if (!valid) {
                console.log("success call back not valid");
                return false;
            }else{
          $.ajax({
                url: '<spring:url value="/vendor/saveOffer"/>',
                dataType: 'json',
                async:false,
                type: 'POST',
                success: function (data) {
                    $("#myModal").modal('hide');
                      console.log("success call back not valid");
                    var url = '<%=request.getContextPath()%>/vendor/offers';
                    window.location.href = url;
                }
            });
            }
//           var t= $("#addOfferForm").ajaxSubmit(options);
//           console.log("test "+JSON.stringify(t));
//            return true;
        });


        $("#main-content").delegate(".top-offer", "click", function (e) {
            var $this = $(this);
            var pid = $this.attr("pid");

            $.ajax({
                type: "GET",
                url: "<spring:url value="/vendor/setIsHot"/>",
                data: "offerID=" + pid,
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data && data.status && data.status == 'success') {
                        window.location.reload();
                    }
                },
            });

        });
    }


</script>