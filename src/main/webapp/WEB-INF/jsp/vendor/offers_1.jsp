<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="margin-left:7%;">Add Offers</h3>
            <div class="col-xs-7 col-sm-7 pull-right form-category">
                <form class="form-inline">
                    <%-- <div class="form-group">
                         <select class="select-category" onchange="location = this.options[this.selectedIndex].value;">
                             <option value="#">Select Category</option>
                             <option value="#">Fashion</option>                                        
                         </select>
                     </div>--%>
                </form>
            </div>
            <div class="row">
                <div class="clearfix" id="offersContent">


                </div>
                <%--<button type="submit" class="btn btn-primary pull-right btn-upload">Upload</button>--%>


            </div>
        </div>
    </div>

</section><!-- /MAIN CONTENT -->

<script id="entry-template" type="text/x-handlebars-template">
    <form id="offerForm_{{index}}" index="{{index}}" method="POST" enctype="multipart/form-data">
    {{#if first}}
    <div class="col-md-2 col-md-offset-1 well" >
    {{else}}
    <div class="col-md-2 well margin-left-10" >
    {{/if}}


    <div class="offer-img well">
    {{#if image}}
    <img src="<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/"/>{{image}}" class="img-rounded img-responsive" alt="Offer-image">
    {{else}}
    <img src="<spring:url value="/resources/assets_admin/images/no_image.jpg"/>" class="img-rounded img-responsive" alt="Offer-image">
    {{/if}}

    </div>
    <div class="form-group">
    <label for="offer-title"></label>
    {{#if offerID}}
    <input type="hidden" name="offerID" value="{{offerID}}"/>
    {{/if}}    
    <input id="offer-title" name="title" type="text" class="form-control"  placeholder="offer-title" value="{{title}}">
    </div>
    <div class="form-group">
    <label for="offer-price"></label>
    <input id="offer-price" type="text" name="offerPrice" class="form-control"  placeholder="offer price" value="{{offerPrice}}">
    <label for="actual-price"></label>
    <input id="actual-price" type="text" name="price" class="form-control"  placeholder="actual price" value="{{price}}">
    </div>
    <div class="form-group">
    <label for="quantity"></label>
    <input type="text" class="form-control" name="quantity" id="quantity" placeholder="quantity" value="{{quantity}}"/>
    </div>

    <div class="form-group">
    <label for="description"></label>
    <textarea id="description" name="description" class="form-control" rows="2">{{description}}</textarea>
    </div>

    <div class="form-group">
    <label for="browse"></label>
    <button id="browse" type="button" class="file-input btn btn-primary btn-sm btn-file btn-block">Browse&hellip; <input type="file" name="uploadImage"></button>
    {{#if offerID}}
    <label for="update"></label>
    <button id="update" index="{{index}}" type="button" class="btn btn-warning btn-sm btn-file btn-block">Update</button>
    <label for="remove"></label>
    <button id="remove" index="{{index}}" type="button" class="btn btn-warning btn-sm btn-file btn-block">Remove</button>

    {{else}}
    <label for="add"></label>
    <button id="add" index="{{index}}" type="button" class="btn btn-warning btn-sm btn-file btn-block">Add</button>                                   
    {{/if}}            

    </div>

    <div class="checkbox ">
    <label for="top-offer">
    {{#if offerID}}
    <input id="top-offer" class="top-offer" type="radio" pid="{{offerID}}" {{#if ishot}} checked=true {{/if}} > Check me for top
    {{/if}} 
    </label>
    </div>
    </div>
    </form>
</script>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script type="text/javascript">
var offers = ${offers};
$(document).ready(function() {
    processOffers();
    bindEvents();
});

var prevSelectedPID = 0;
function bindEvents() {
    $("#offersContent").delegate("button", "click", function() {
        var id = $(this).attr("id");
        var index = $(this).attr("index");

        if (id == "browse") {
            return;
        }



        if (id == "add" || id == "update") {

            var options = {
                url: '<spring:url value="/vendor/saveOffer"/>',
                dataType: 'json',
                type: 'POST',
                success: function(data) {
                    offers[index]=data;
                    processOffers();
                }
            };
            $("#offerForm_" + index).ajaxSubmit(options);
            return false;
        } else if (id == "remove") {
            var offer = offers[index];
            if(offer){
        
                $.ajax({
                    type: "GET",
                    url: "<spring:url value="/vendor/deleteOffer"/>",
                    data: "offerID="+offer.offerID,
                    cache: false, 
                    dataType: 'json',
                    beforeSend: function() {
                        //alert('sending');
                    },
                    complete:function( jqXHR, textStatus ){
                       //alert('complete-'+textStatus);    
                    },
                    error:function(jqXHR, textStatus,ee){
                       //alert('error'+textStatus+":"+ee);    
                    },
                    success: function(data) {                        
                        if (data && data.offerID)
                        {                           
                            offers.splice(index,1);
                            processOffers();                            
                        }
                    },
                    
                });
            }
        }


    });
    
    
    $("#offersContent").delegate(".top-offer", "click", function(e) {
       var $this = $(this);
       var pid = $this.attr("pid");
       if(pid==prevSelectedPID){
         return false;  
        }
        
         $.ajax({
                    type: "GET",
                    url: "<spring:url value="/vendor/setIsHot"/>",
                    data: "offerID="+pid,
                    cache: false, 
                    dataType: 'json',                    
                    success: function(data) {                           
                        if (data && data.status && data.status=='success')
                        {                           
                          prevSelectedPID=pid; 
                          $("#offersContent .top-offer").each(function(){
                              var $this = $(this)
                                      if($this.attr("pid")!=prevSelectedPID){
                                          $this.attr("checked",false);
                                       }
                            });
                        }
                    },
                    
                });
        
    });
}

function processOffers() {
    var offersContent = $("#offersContent");
    offersContent.empty();
    var source = $("#entry-template").html();
    
    var template = Handlebars.compile(source);
    for (var i = 0; i < 5; i++) {
        var offer = offers[i];
        if (!offer) {
            offer = {};
        }
        if (i == 0) {
            offer.first = true;
        }

        offer.index = i;
        var html = template(offer);
        offersContent.append(html);
    }


}
;


</script>