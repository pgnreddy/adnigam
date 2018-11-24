/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.dao.impl;

import com.cnbitsols.adrobe.dao.VendorHibernateDAO;
import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersProductsStatusHistory;
import com.cnbitsols.adrobe.entities.OrdersStatus;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.UserRequests;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.utils.Util;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santosh.r
 */
@Repository("vendorHibernateDAOImpl")
@Transactional
public class VendorHibernateDAOImpl implements VendorHibernateDAO {

    private static Logger log = LoggerFactory.getLogger(VendorHibernateDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override

    public Vendor findVendorByEmail(String userName) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Vendor.findByMobile");
        query.setString("phone", userName);
        return (Vendor) query.uniqueResult();
    }

    @Override
    public SearchObj listVendorOffers(Integer vendorID, int page, int limit, String productName, int offerID) {
        Session session = sessionFactory.getCurrentSession();

        StringBuilder commonBuilder = new StringBuilder();
        commonBuilder.append(" FROM Offers o WHERE o.vendorID = :vendorID and o.status=1 ");

        if (StringUtils.isNotBlank(productName)) {
            commonBuilder.append(" and o.title like :title ");
        }
        if (offerID > 0) {
            commonBuilder.append(" and o.offerID=:offerID ");
        }

        StringBuilder countBuilder = new StringBuilder();
        countBuilder.append("SELECT count(o.offerID) ");
        countBuilder.append(commonBuilder.toString());

        Query countQuery = session.createQuery(countBuilder.toString())
                .setInteger("vendorID", vendorID);

        if (StringUtils.isNotBlank(productName)) {
            countQuery.setParameter("title", "%" + productName + "%");
        }
        if (offerID > 0) {
            countQuery.setInteger("offerID", offerID);
        }

        long totalCount = (Long) countQuery.uniqueResult();

        List<Object[]> list = null;

        if (totalCount > 0) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT o ");
            queryBuilder.append(commonBuilder.toString());
            queryBuilder.append(" ORDER BY o.offerID DESC ");

            Query q = session.createQuery(queryBuilder.toString())
                    .setInteger("vendorID", vendorID);

            if (StringUtils.isNotBlank(productName)) {
                q.setParameter("title", "%" + productName + "%");
            }
            if (offerID > 0) {
                q.setInteger("offerID", offerID);
            }

            if (page > 0 && limit > 0) {
                int start = (page - 1) * limit;
                q.setFirstResult(start);
            }
            if (limit > 0) {
                q.setMaxResults(limit);
            }
            list = q.list();
        }

        return new SearchObj(page, limit, totalCount, list);
    }

    @Override
    public Offers saveOffer(Vendor vendor, Offers offer) {
        Session session = sessionFactory.getCurrentSession();
        Offers original = null;
        offer.setCategoryID(vendor.getCategoryID());
        offer.setVendorID(vendor.getVendorID());
        offer.setSeo_title(Util.toSeoString(offer.getTitle()) + System.currentTimeMillis());
        if (offer.getOfferID() == null) {
            session.saveOrUpdate(offer);
        } else {
            original = (Offers) session.getNamedQuery("Offers.findByOfferID").setInteger("offerID", offer.getOfferID()).setInteger("vendorID", vendor.getVendorID()).uniqueResult();
            if (original != null) {
                if (StringUtils.isBlank(offer.getImage())) {
                    offer.setImage(original.getImage());
                    offer.setIshot(original.getIshot());
                    offer.setImage_original(original.getImage_original());
                }
                BeanUtils.copyProperties(offer, original);
                session.saveOrUpdate(original);
                offer = original;
            }
        }

        offer.setSeo_title(Util.toSeoString(offer.getTitle()) + offer.getOfferID());
        session.saveOrUpdate(offer);

        return offer;

    }

    @Override
    public void deleteOffer(Integer vendorID, int offerID) {
        Session session = sessionFactory.getCurrentSession();
        Offers original = (Offers) session.getNamedQuery("Offers.findByOfferID").setInteger("offerID", offerID).setInteger("vendorID", vendorID).uniqueResult();
        if (original != null) {
            original.setStatus(0);
            session.saveOrUpdate(original);
        }
    }

    @Override
    public void updateVendorProfile(Vendor vendor) {
        Session session = sessionFactory.getCurrentSession();
        session.update(vendor);
    }

    @Override
    public void setIsHot(Integer vendorID, int offerID) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update Offers set ishot=0 where status=1 and vendorID=:vendorID")
                .setInteger("vendorID", vendorID).executeUpdate();
        session.createQuery("update Offers set ishot=1 where offerID=:offerID and vendorID=:vendorID")
                .setInteger("vendorID", vendorID).setInteger("offerID", offerID).executeUpdate();

    }

    /*@Override
     public List<Object[]> listVendorRequests(Integer vendorID, int page, int limit) {
     Session session = sessionFactory.getCurrentSession();
     Query q = session.createQuery("select u,op,o from UserRequests u,OrdersProducts op,Orders o where u.vendorId=:vendorId and u.orderProductId=op.ordersProductsId and op.ordersId=o.ordersId order by u.id desc,u.read desc");
     q.setInteger("vendorId", vendorID);

     if (page > 0 && limit > 0) {
     int start = (page - 1) * limit;
     q.setFirstResult(start);
     }
     if (limit > 0) {
     q.setMaxResults(limit);
     }
     List<Object[]> list = q.list();
     return list;
     }*/
    @Override
    public SearchObj listVendorRequests(Integer vendorID, int page, int limit, int status) {
        Session session = sessionFactory.getCurrentSession();

        Query countQuery = session.createSQLQuery("select count(u.id) as total FROM orders_products op "
                + " INNER JOIN orders o ON (op.orders_Id=o.orders_Id)"
                + " INNER JOIN user_requests u  ON (u.orderProductId=op.orders_products_id ) "
                + " WHERE u.vendorId=:vendorId and u.isRead=:status"
        )
                .addScalar("total", LongType.INSTANCE)
                .setInteger("status", status)
                .setInteger("vendorId", vendorID);

        long totalCount = (Long) countQuery.uniqueResult();
        List<Object[]> list = null;

        if (totalCount > 0) {

            Query q = session.createSQLQuery("select {u.*},{op.*},{o.*} FROM orders_products op "
                    + " INNER JOIN orders o ON (op.orders_Id=o.orders_Id)"
                    + " INNER JOIN user_requests u  ON (u.orderProductId=op.orders_products_id ) "
                    + " WHERE u.vendorId=:vendorId and u.isRead=:status"
                    + " ORDER BY u.id desc,u.isread desc ")
                    .addEntity("u", UserRequests.class)
                    .addEntity("op", OrdersProducts.class)
                    .addEntity("o", Orders.class)
                    .setInteger("status", status)
                    .setInteger("vendorId", vendorID);

            if (page > 0 && limit > 0) {
                int start = (page - 1) * limit;
                q.setFirstResult(start);
            }
            if (limit > 0) {
                q.setMaxResults(limit);
            }
            list = q.list();
        }

        return new SearchObj(page, limit, totalCount, list);
    }

    @Override
    public SearchObj listVendorOrders(Integer vendorID, int page, int limit, int status, int orderId) {
        Session session = sessionFactory.getCurrentSession();

        StringBuilder commonBuilder = new StringBuilder();
        commonBuilder.append(" FROM orders_products op INNER JOIN orders o ON (op.orders_Id=o.orders_Id) INNER JOIN offers p ON (op.products_Id=p.offerID)\n");
        commonBuilder.append(" INNER JOIN orders_status os ON (o.orders_status=os.orders_status_id ) ");
        commonBuilder.append(" LEFT OUTER JOIN user_requests u  ON (u.orderProductId=op.orders_products_id ) ");
        commonBuilder.append(" WHERE op.vendorId=:vendorId  ");
        if (status > 0) {
            commonBuilder.append(" and op.orders_products_status=" + status);
        }
        if (orderId > 0) {
            commonBuilder.append(" and o.orders_Id=" + orderId);
        }

        StringBuilder countBuilder = new StringBuilder();
        countBuilder.append("SELECT count(op.orders_products_id) as total");
        countBuilder.append(commonBuilder.toString());

        Query countQuery = session.createSQLQuery(countBuilder.toString())
                .addScalar("total", LongType.INSTANCE)
                .setInteger("vendorId", vendorID);

        long totalCount = (Long) countQuery.uniqueResult();
        List<Object[]> list = null;

        if (totalCount > 0) {

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT {op.*},{o.*},{p.*},{u.*},{os.*} ");
            queryBuilder.append(commonBuilder.toString());
            queryBuilder.append(" ORDER BY op.orders_products_id DESC ");

            Query q = session.createSQLQuery(queryBuilder.toString())
                    .addEntity("op", OrdersProducts.class)
                    .addEntity("o", Orders.class)
                    .addEntity("p", Offers.class)
                    .addEntity("u", UserRequests.class)
                    .addEntity("os", OrdersStatus.class)
                    .setInteger("vendorId", vendorID);

        //Query q = session.createQuery("select op,o,u,p from UserRequests u ,OrdersProducts op,Orders o,Offers p where op.vendorId=:vendorId and u.orderProductId=op.ordersProductsId and op.ordersId=o.ordersId and op.productsId=p.offerID order by op.ordersProductsId desc");
            // q.setInteger("vendorId", vendorID);
            if (page > 0 && limit > 0) {
                int start = (page - 1) * limit;
                q.setFirstResult(start);
            }
            if (limit > 0) {
                q.setMaxResults(limit);
            }
            list = q.list();

        }
        return new SearchObj(page, limit, totalCount, list);
    }

    @Override
    public boolean updateUserRequest(Integer vendorID, int requestId, int status) {
        Session session = sessionFactory.getCurrentSession();
        UserRequests req = (UserRequests) session.get(UserRequests.class, requestId);
        if (req != null) {
            req.setRead(status);
            session.save(req);
        }

        return true;
    }

    @Override
    public Offers getVendorOffer(Integer vendorID, int offerID) {
        Session session = sessionFactory.getCurrentSession();
        Offers original = (Offers) session.getNamedQuery("Offers.findByOfferID").setInteger("offerID", offerID).setInteger("vendorID", vendorID).uniqueResult();
        return original;
    }
    
    @Override
    public Object[] updateOrderProductStatus(Integer vendorID, int orderProductId, int status, String comment) {
        Session session = sessionFactory.getCurrentSession();
        
        OrdersProductsStatusHistory history = new OrdersProductsStatusHistory();
        history.setOrdersProductsId(orderProductId);
        history.setOrdersStatusId(status);
        history.setComments(comment);
        history.setDateAdded(new Date());
        
        session.save(history);
        
        OrdersProducts op = (OrdersProducts)session.get(OrdersProducts.class, orderProductId);
        op.setOrdersProductsStatus(status);        
       
        return new Object[]{op,history};
    }

    @Override
    public SearchObj listProductCouponCode(Integer vendorID, int page, int limit, int orderID, String productCouponCode) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM product_coupon p WHERE p.VendorID=" + vendorID);
        if (StringUtils.isNotBlank(productCouponCode)) {
            queryBuilder.append(" AND p.product_coupon_code='" + productCouponCode + "'");
        }
        if (orderID != 0) {
            queryBuilder.append(" AND p.orderID=" + orderID);
        }
        
        queryBuilder.append(" AND p.sold=0 AND p.end_date >'" + Timestamp.valueOf(LocalDateTime.now()) + "'");
        
        Session session = sessionFactory.getCurrentSession();
        
        SQLQuery query = session.createSQLQuery(queryBuilder.toString());
        query.addEntity(ProductCoupon.class);
        
        if (page > 0 && limit > 0) {
            int start = (page - 1) * limit;
            query.setFirstResult(start);
        }
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        List<ProductCoupon> list = query.list();
        
        SearchObj obj = new SearchObj(page, limit, list.size(), list);

        return obj;
    }

    @Override
    public void sellProductCoupon(Vendor vendor, String productCouponCode) {
        String hql = "FROM ProductCoupon pc WHERE pc.productCouponCode = :productCouponCode";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("productCouponCode", productCouponCode);
        
        ProductCoupon pc = (ProductCoupon)query.uniqueResult();
        
        pc.setSold(1);
        
        session.saveOrUpdate(pc);
    }

    @Override
    public boolean validateProductCoupon(Integer vendorID, String productCouponCode) {
        
        Session session = sessionFactory.getCurrentSession();
        
        Query query = session.getNamedQuery("ProductCoupon.findByCode");
        query.setParameter("productCouponCode", productCouponCode);
        ProductCoupon productCoupon = (ProductCoupon)query.uniqueResult();
        boolean valid = productCoupon!=null && productCoupon.getSold()==0 && productCoupon.getEndDate().after(new Date());
        return valid;
    }

    @Override
    public List<ProductCoupon> getProductCouponsForCustomerByVendor(String mobileNumber, Integer vendorID) {
        String sql = "SELECT * FROM product_coupon p WHERE p.VendorID = "+ vendorID +"and p.orderID in ( select orders.orders_id from orders where orders.customers_telephone='"+mobileNumber+"') AND p.sold=0 AND p.end_date >'" + Timestamp.valueOf(LocalDateTime.now()) + "'";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(ProductCoupon.class);
        return query.list();
    }

}
