<div>
    <div class="col-sm-4" id="addadress">
        <div class="box-authentication">
            <h3>Update Address</h3>
            <hr/>
            <form id="updateaddressform" method="POST" action="updateAddressBook">
                <div clss="msg" id="resp_msg" ></div>
                <input name="fullname" id="full_name" type="text" class="form-control input-cm" placeholder="Full Name" value="${address.entryFirstname}">
                <input  value="${address.entryStreetAddress}" name="streetname" id="street_name" type="text" class="form-control input-cm" placeholder="Street Name">
                <input  value="${address.entrySuburb}" name="address" id="address" type="text" class="form-control input-cm" placeholder="Address">
                <input  value="${address.entryCity}" name="cityname" id="city_name" type="text" class="form-control input-cm" placeholder="City">
                <input value="${address.entryPostcode}" name="zipcode"  id="zip_code" type="text" class="form-control input-cm" placeholder="zipCode">
                <input value="${address.addressBookId}" name="addressBookId"  id="addressBookId" type="hidden" class="form-control input-cm" placeholder="addressBookId">
            </form>
        </div>
    </div>
    <div class="cart_navigation">
        <a class="update-address-btn" >Update Changes</a>
    </div>
</div>