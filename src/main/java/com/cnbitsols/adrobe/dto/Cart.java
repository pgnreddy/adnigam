/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;


import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Coupons;
import java.util.List;
import java.util.Map;

/**
 *
 * @author santosh
 */
public class Cart {

    private long id;
    private List<OrderProductItem> items;
    private double totalprice;  
    private Map<String,OrderProductItem> itemsMap;
    private int totalItems = 0;
    private AddressBook shippingAddress = null;
    private Integer orderId ;
    private Payment payment;
    private Coupons coupon;

    public Coupons getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupons coupon) {
        this.coupon = coupon;
    }
    
    
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderProductItem> getItems() {
        return items;
    }

    public void setItems(List<OrderProductItem> items) {
        this.items = items;
    }

    //actual price
    public double getActualTotalprice() {
        if(itemsMap!=null){
            totalprice = 0;
            for(OrderProductItem item:itemsMap.values()){
                totalprice += item.getSubprice();
            }
        }
        return totalprice;        
    }
   
    //after discount
    public double getTotalprice() {
        double actualPrice = getActualTotalprice();
        return actualPrice-getCouponDiscountPrice(actualPrice);             
    }
    
    
    
   
    

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Map<String, OrderProductItem> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(Map<String, OrderProductItem> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public int getTotalItems() {
         if(itemsMap!=null){
            totalItems = 0;
            for(OrderProductItem item:itemsMap.values()){
                totalItems += item.getQuantity();
            }
        }       
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public AddressBook getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressBook shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public double getCouponDiscountPrice(double actualPrice){
        double couponPrice = 0;
        if(coupon!=null){
            if(coupon.getDiscount_type()==1){//flat
                if(coupon.getNumber_of_times_to_use()>1){
                    couponPrice = coupon.getDiscount_value()/coupon.getNumber_of_times_to_use();
                }else{
                    couponPrice = coupon.getDiscount_value();
                }
            }else{
                couponPrice = (actualPrice*(coupon.getDiscount_value()/100d));
            }
        }
        
        return Math.floor(couponPrice);
    }
    
    public double getCouponDiscountPrice(){
       return getCouponDiscountPrice(getActualTotalprice());
    }
    
       
}
