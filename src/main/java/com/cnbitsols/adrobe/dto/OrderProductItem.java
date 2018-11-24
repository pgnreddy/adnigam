/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;

import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import org.apache.commons.lang.math.NumberUtils;


/**
 *
 * @author santosh
 */
public class OrderProductItem {
    private Orders order;
    private Offers product;
    private int quantity;
    private double subprice;    
    private String formattedSubPrice;
    boolean reserved = false;
    boolean coupon = false;
    String userRequest = null;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }
    Integer orderProductId = null;
    
    

    public Orders getOrder() {
        return order;
    }

    public void setOrders(Orders order) {
        this.order = order;
    }

    public Offers getProduct() {
        return product;
    }

    public void setProduct(Offers product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubprice() {
        if(product!=null){
            if(reserved){
                //If reserved then percent of amount
                double reservePercent = NumberUtils.toDouble(SettingsUtil.getSettings("RESERVE_AMOUNT_FRACTION"),0.1);
                return product.getOfferPrice().doubleValue()*reservePercent * quantity;
            } else if (coupon) {
                return product.getCouponPrice().doubleValue() * quantity;
            } else{
                return product.getOfferPrice().doubleValue() * quantity;
            }
        }
        return 0;
    }
    
     public double getOfferPrice() {
        if(product!=null){
            if(reserved){
                //If reserved then percent of amount
                double reservePercent = NumberUtils.toDouble(SettingsUtil.getSettings("RESERVE_AMOUNT_FRACTION"),0.1);
                return product.getOfferPrice().doubleValue()*reservePercent;
            } else if (coupon) {
              return product.getCouponPrice().doubleValue();
            } else{
                return product.getOfferPrice().doubleValue();
            }
        }
        return 0;
    }
    
    
    public void setSubprice(double subprice) {
        this.subprice = subprice;
    }

    

    public String getFormattedSubPrice() {
        return formattedSubPrice;
    }

    public void setFormattedSubPrice(String formattedSubPrice) {
        this.formattedSubPrice = formattedSubPrice;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isCoupon() {
        return coupon;
    }

    public void setCoupon(boolean coupon) {
        this.coupon = coupon;
    }
    
    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }
     
     
     
}
