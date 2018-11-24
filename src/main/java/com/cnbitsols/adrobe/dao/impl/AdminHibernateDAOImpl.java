/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.dao.impl;

import com.cnbitsols.adrobe.dao.AdminHibernateDAO;
import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Admin;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.OrderCancellation;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santosh.r
 */
@Repository("adminHibernateDAOImpl")
@Transactional
public class AdminHibernateDAOImpl implements AdminHibernateDAO {
    
    private static final int UNAPPROVED_DELETE_STATUS = -1;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public Admin findAdminByEmail(String userName) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Admin.findByEmail");
        query.setString("email", userName);
        return (Admin) query.uniqueResult();
    }
    
    @Override
    public List<Vendor> listVendors(int start, int max, String name, String Email, String Phone, String category) {
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM vendor WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(Email) || StringUtils.isNotBlank(Phone) || StringUtils.isNotBlank(category)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" name like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(Email)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" Email = '" + Email.trim() + "'");
            }
            if (StringUtils.isNotBlank(Phone)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" Phone = '" + Phone.trim() + "'");
            }
            if (StringUtils.isNotBlank(category)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" CategoryID = " + category);
            }
        }
        
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=1 ");
        searchQuery.append(" ORDER BY  VendorID DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(Vendor.class);

//        Query query = sessionFactory.getCurrentSession().getNamedQuery("Vendor.findByStatus");
//        query.setString("status", "1");
//        query.setFirstResult(start);
//        query.setMaxResults(max);
        return query.list();
    }
    
    @Override
    public List<Orders> listOrders(int start, int max, String orderid, String custormername, String city) {
//        StringBuilder searchQuery = new StringBuilder("SELECT * FROM orders");
        StringBuilder searchQuery = new StringBuilder("SELECT o.orders_id AS orderId, o.admin_read AS adminRead, os.orders_status_name "
                + "AS STATUS,o.date_purchased,o.customers_name,o.payment_method,o.customers_city,COUNT(op.orders_products_id) \n"
                + "		AS COUNT,ot.value AS amount \n"
                + "                FROM orders o,orders_products op,orders_total ot,orders_status os WHERE o.orders_id=op.orders_id \n"
                + "                AND o.orders_id=ot.orders_id AND ot.class='ot_total' AND os.orders_status_id=o.orders_status ");

//                GROUP BY o.orders_id ORDER BY o.orders_id DESC
        if (StringUtils.isNotBlank(orderid) || StringUtils.isNotBlank(custormername) || StringUtils.isNotBlank(city)) {
//            searchQuery.append(" WHERE ");
            boolean isinside = false;
            if (StringUtils.isNotBlank(orderid)) {
                searchQuery.append(" and ");
                searchQuery.append(" o.orders_id=" + orderid.trim());
            }
            if (StringUtils.isNotBlank(custormername)) {
//                if (isinside) {
                isinside = true;
                searchQuery.append(" and ");
//                }
                searchQuery.append(" o.customers_name like '%" + custormername.trim() + "%'");
            }
            if (StringUtils.isNotBlank(city)) {
//                if (isinside) {
                searchQuery.append(" and ");
//                }
                searchQuery.append(" o.orders_status = '" + city.trim() + "'");
            }
        }
        searchQuery.append(" GROUP BY o.orders_id, ot.value  ORDER BY  o.orders_id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        query.addEntity(Orders.class);
        return query.list();
    }
    
    @Override
    public List<OrderCancellation> listCancelOrders(int start, int max, String orderid) {
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM order_cancellation ");
        if (StringUtils.isNotBlank(orderid)) {
            searchQuery.append(" WHERE ");
            boolean isinside = false;
            if (StringUtils.isNotBlank(orderid)) {
                isinside = true;
                searchQuery.append(" order_id=" + orderid);
            }
        }
        searchQuery.append(" ORDER BY  cancel_id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(OrderCancellation.class);
        return query.list();
    }
    
    @Override
    public List<Offers> getOffers(int state, int start, int max, String title) {
//        Query query = sessionFactory.getCurrentSession().getNamedQuery("Offers.findByState");
//        query.setInteger("type", state);
//        query.setFirstResult(start);
//        query.setMaxResults(max);

        StringBuilder searchQuery = new StringBuilder("SELECT * FROM offers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(title)) {
            isinside = true;
            searchQuery.append(" Title like '%" + title.trim() + "%'");
            
        }
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" type=" + state);
        searchQuery.append(" ORDER BY  OfferID DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(Offers.class);
        return query.list();
    }
    
    @Override
    public List<PremiumOffers> getPremiumOffers(int state, int start, int max, String title) {
        
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM premiumoffers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(title)) {
            
            isinside = true;
            searchQuery.append(" title like '%" + title.trim() + "%'");
            
        }
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=" + state);
        searchQuery.append(" ORDER BY  id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(PremiumOffers.class);

//        Query query = sessionFactory.getCurrentSession().getNamedQuery("PremiumOffers.findByStatus");
//        query.setInteger("status", state);
//        query.setFirstResult(start);
//        query.setMaxResults(max);
        return query.list();
    }
    
    @Override
    public List<Categories> getCategories(int start, int limit) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Categories.findAllStatus").setInteger("status", 1);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }
    
    @Override
    public List<Admin> getAdminList(int start, int max) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Admin.findAll");
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.list();
    }
    
    @Override
    public List<Location> getLocationList(int start, int max, String name, String zipCode) {
        //SELECT l FROM Location l WHERE l.status = :status ORDER BY l.id DESC
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM location Where  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(zipCode)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" name like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(zipCode)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" zipCode = '" + zipCode.trim() + "'");
            }
        }
        
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=1 ");
        searchQuery.append(" ORDER BY  id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(Location.class);
        return query.list();
//        Query query = sessionFactory.getCurrentSession().getNamedQuery("Location.findByStatus").setInteger("status", 1);
//        query.setFirstResult(start);
//        query.setMaxResults(max);
//        return query.list();
    }
    
    @Override
    public void updateVendorProfile(Vendor vendor) {
        Session session = sessionFactory.getCurrentSession();
        session.update(vendor);
    }
    
    @Override
    public int getVendorCount(String name, String Email, String Phone) {
        
        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM vendor WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(Email) || StringUtils.isNotBlank(Phone)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" name like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(Email)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" Email = '" + Email.trim() + "'");
            }
            if (StringUtils.isNotBlank(Phone)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" Phone = '" + Phone.trim() + "'");
            }
        }
        
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=1 ");
//        searchQuery.append(" ORDER BY  VendorID DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
//        query.addEntity(Vendor.class);
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from vendor v  where status=1  ORDER BY v.vendorID DESC");

        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public int getOrderCount(String orderid, String custormername, String city) {
        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM orders");
        if (StringUtils.isNotBlank(orderid) || StringUtils.isNotBlank(custormername) || StringUtils.isNotBlank(city)) {
            searchQuery.append(" WHERE ");
            boolean isinside = false;
            if (StringUtils.isNotBlank(orderid)) {
                isinside = true;
                searchQuery.append(" orders_id=" + orderid.trim());
            }
            if (StringUtils.isNotBlank(custormername)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" customers_name like '%" + custormername.trim() + "%'");
            }
            if (StringUtils.isNotBlank(city)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                searchQuery.append(" orders_status = '" + city.trim() + "'");
            }
        }
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(searchQuery.toString());
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from Orders");
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public int getCancelOrderCount(String orderid) {
        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM order_cancellation ");
        if (StringUtils.isNotBlank(orderid)) {
            searchQuery.append(" WHERE ");
            boolean isinside = false;
            if (StringUtils.isNotBlank(orderid)) {
                isinside = true;
                searchQuery.append(" order_id=" + orderid.trim());
            }
        }
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(searchQuery.toString());
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from Orders");
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override//SELECT o FROM Offers o WHERE  o.status=:status order by offerID desc
    public int getOffersCount(int state, String title) {//o.status=:status order by offerID desc
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from Offers where type=:type order by offerID desc ");
//        query.setInteger("type", state);

        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM offers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(title)) {
            
            isinside = true;
            searchQuery.append(" Title like '%" + title.trim() + "%'");
            
        }
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" type=" + state);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override//SELECT o FROM Offers o WHERE  o.status=:status order by offerID desc
    public int getPremiumCount(int state, String title) {//o.status=:status order by offerID desc
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from PremiumOffers where status=:status order by id desc ");
//        query.setInteger("status", state);

        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM premiumoffers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(title)) {
            
            isinside = true;
            searchQuery.append(" title like '%" + title.trim() + "%'");
            
        }
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=" + state);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public int getCategoryCount() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from categories where status=1 order by categoryID desc");
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public int getAdminCount() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from Admin a  ORDER BY a.id DESC");
        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public int getLocationCount(String name, String zipCode) {
        
        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM location WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(zipCode)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" name like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(zipCode)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" zipCode = '" + zipCode.trim() + "'");
            }
        }
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status= 1");
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        return ((Number) query.uniqueResult()).intValue();

//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from Location WHERE status = 1 ORDER BY id DESC");
//        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public void addVendor(Admin admin, Vendor vendor) {
        Session session = sessionFactory.getCurrentSession();
        Vendor original = null;
        if (vendor.getVendorID() == null || vendor.getVendorID() == 0) {
            session.saveOrUpdate(vendor);
        } else {
            original = (Vendor) session.getNamedQuery("Vendor.findByVendorID").setInteger("vendorID", vendor.getVendorID()).uniqueResult();
            if (original != null) {
                /* if (StringUtils.isNotBlank(vendor.getName())) {
                 original.setName(vendor.getName());
                 }
                 if (StringUtils.isNotBlank(vendor.getEmail())) {
                 original.setEmail(vendor.getEmail());
                 }
                 if (StringUtils.isNotBlank(vendor.getPhone())) {
                 original.setPhone(vendor.getPhone());
                 }
                 if (StringUtils.isNotBlank(vendor.getLocation())) {
                 original.setLocation(vendor.getLocation());
                 }
                 if (StringUtils.isNotBlank(vendor.getZipCode())) {
                 original.setZipCode(vendor.getZipCode());
                 }
                 if (StringUtils.isNotBlank(vendor.getPassword())) {
                 original.setPassword(vendor.getPassword());
                 }
                 if (StringUtils.isNotBlank(vendor.getCity())) {
                 original.setCity(vendor.getCity());
                 }
                 if (StringUtils.isNotBlank(vendor.getState())) {
                 original.setState(vendor.getState());
                 }
                 if (StringUtils.isNotBlank(vendor.getCountry())) {
                 original.setCountry(vendor.getCountry());
                 }
                 original.setLocationId(vendor.getLocationId());
                 original.setStatus(vendor.getStatus());
                 original.setCategoryID(vendor.getCategoryID());
                 original.setLatitude(vendor.getLatitude());
                 original.setLongitude(vendor.getLongitude());
                 */
                
                vendor.setCoverImage(original.getCoverImage());
                BeanUtils.copyProperties(vendor, original);
                session.saveOrUpdate(original);
            }
        }
    }
    
    @Override
    public Offers saveOffer(Offers offer) {
        Session session = sessionFactory.getCurrentSession();
        if (StringUtils.isBlank(offer.getSeo_title())) {
            offer.setSeo_title(Util.toSeoString(offer.getTitle()) + System.currentTimeMillis());
        }
        Offers original = null;
        if (offer.getOfferID() == null) {
            session.saveOrUpdate(offer);
        } else {
            original = (Offers) session.getNamedQuery("Offers.findByOfferID").setInteger("offerID", offer.getOfferID()).setInteger("vendorID", offer.getVendorID()).uniqueResult();
            if (original != null) {
                if (StringUtils.isBlank(offer.getImage())) {
                    offer.setImage(original.getImage());
                }
                BeanUtils.copyProperties(offer, original);
                session.saveOrUpdate(original);
            }
        }
        return offer;
    }
    
    @Override
    public PremiumOffers savePremiumOffer(PremiumOffers preOffer) {
        Session session = sessionFactory.getCurrentSession();
        PremiumOffers original = null;
        if (preOffer.getId() == null) {
            session.saveOrUpdate(preOffer);
        } else {
            original = (PremiumOffers) session.getNamedQuery("PremiumOffers.findById").setInteger("id", preOffer.getId()).uniqueResult();
            if (original != null) {
                if (StringUtils.isBlank(preOffer.getImage())) {
                    preOffer.setImage(original.getImage());
                }
                BeanUtils.copyProperties(preOffer, original);
                session.saveOrUpdate(original);
            }
        }
        return preOffer;
    }
    
    @Override
    public Categories saveCategory(Categories category) {
        category.setStatus(1);
        Session session = sessionFactory.getCurrentSession();
        category.setSeo_title(Util.toSeoString(category.getName()) + System.currentTimeMillis());
        Categories original = null;
        if (category.getCategoryID() == null) {
            session.saveOrUpdate(category);
        } else {
            original = (Categories) session.getNamedQuery("Categories.findByCategoryID").setInteger("categoryID", category.getCategoryID()).uniqueResult();
            if (original != null) {
                if (StringUtils.isBlank(category.getCategoryIcon())) {
                    category.setCategoryIcon(original.getCategoryIcon());
                }
                BeanUtils.copyProperties(category, original);
                session.saveOrUpdate(original);
            }
        }
        return category;
    }
    
    @Override
    public Location saveLocation(Location location) {
        Session session = sessionFactory.getCurrentSession();
        Location original = null;
        if (location.getId() == null) {
            session.saveOrUpdate(location);
        } else {
            original = (Location) session.getNamedQuery("Location.findById").setInteger("id", location.getId()).uniqueResult();
            if (original != null) {
                BeanUtils.copyProperties(location, original);
                session.saveOrUpdate(original);
            }
        }
        return location;
    }
    
    @Override
    public void deleteVendor(Integer adminID, int vendorID) {
        Session session = sessionFactory.getCurrentSession();
        Vendor vendor = (Vendor) session.getNamedQuery("Vendor.findByVendorID").setInteger("vendorID", vendorID).uniqueResult();
        if (vendor != null) {
            vendor.setStatus(0);
            session.saveOrUpdate(vendor);
        }
    }
    
    @Override
    public void deleteOffer(Integer adminID, int offerId) {
        Session session = sessionFactory.getCurrentSession();
        Offers offer = (Offers) session.getNamedQuery("Offers.findByOfferIDOnly").setInteger("offerID", offerId).uniqueResult();
        if (offer != null) {
            offer.setType(0);
            session.saveOrUpdate(offer);
        }
    }
    
    @Override
    public void deletePremiumOffer(Integer adminID, int preOfferId) {
        Session session = sessionFactory.getCurrentSession();
        PremiumOffers preOffer = (PremiumOffers) session.getNamedQuery("PremiumOffers.findById").setInteger("id", preOfferId).uniqueResult();
        if (preOffer != null) {
            preOffer.setStatus(0);
            session.saveOrUpdate(preOffer);
        }
    }
    
    @Override
    public void deleteCategory(Integer adminID, int catId) {
        Session session = sessionFactory.getCurrentSession();
        Categories category = (Categories) session.getNamedQuery("Categories.findByCategoryID").setInteger("categoryID", catId).uniqueResult();
        if (category != null) {
            category.setStatus(0);
            session.saveOrUpdate(category);
        }
    }
    
    @Override
    public void deleteLocation(Integer adminID, int locId) {
        Session session = sessionFactory.getCurrentSession();
        Location location = (Location) session.getNamedQuery("Location.findById").setInteger("id", locId).uniqueResult();
        if (location != null) {
            location.setStatus(0);
            session.saveOrUpdate(location);
        }
    }
    
    @Override
    public void deleteCustomer(Integer adminID, int customerId) {
        Session session = sessionFactory.getCurrentSession();
        Customers customer = (Customers) session.getNamedQuery("Customers.findByCustomersId").setInteger("customersId", customerId).uniqueResult();
        if (customer != null) {
            customer.setStatus(0);
            session.saveOrUpdate(customer);
        }
    }
    
    @Override
    public Map<Integer, String> getVendorNames() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Vendor.findAll");
        List<Vendor> vendorCodes = query.list();
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (Vendor o : vendorCodes) {
            for (Vendor v : vendorCodes) {
                map.put(v.getVendorID(), v.getName() + "~@~" + v.getPhone());
            }
        }
        return map;
    }
    
    @Override
    public List<Customers> listCustomers(int start, int max, String name, String Email, String Phone) {
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM customers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(Email) || StringUtils.isNotBlank(Phone)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" firstname like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(Email)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" email_address = '" + Email.trim() + "'");
            }
            if (StringUtils.isNotBlank(Phone)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" telephone = '" + Phone.trim() + "'");
            }
        }
        
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=1 ");
        searchQuery.append(" ORDER BY  customers_id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(Customers.class);
        return query.list();
    }
    
    @Override
    public int getCustomersCount(String name, String Email, String Phone) {
        
        StringBuilder searchQuery = new StringBuilder("SELECT count(*) FROM customers WHERE  ");
        boolean isinside = false;
        if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(Email) || StringUtils.isNotBlank(Phone)) {
            if (StringUtils.isNotBlank(name)) {
                isinside = true;
                searchQuery.append(" firstname like '%" + name.trim() + "%'");
            }
            if (StringUtils.isNotBlank(Email)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" email_address = '" + Email.trim() + "'");
            }
            if (StringUtils.isNotBlank(Phone)) {
                if (isinside) {
                    searchQuery.append(" and ");
                }
                isinside = true;
                searchQuery.append(" telephone = '" + Phone.trim() + "'");
            }
        }
        
        if (isinside) {
            searchQuery.append(" and ");
        }
        searchQuery.append(" status=1 ");
//        searchQuery.append(" ORDER BY  customers_id DESC  LIMIT " + max).append(" OFFSET " + start);
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
//        query.addEntity(Vendor.class);
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(*) from vendor v  where status=1  ORDER BY v.vendorID DESC");

        return ((Number) query.uniqueResult()).intValue();
    }
    
    @Override
    public void deleteCoupon(int couponId) {
        Session session = sessionFactory.getCurrentSession();
        Coupons coupons = (Coupons) session.get(Coupons.class, couponId);
        if (coupons != null) {
            coupons.setStatus(0);
            session.saveOrUpdate(coupons);
        }
    }
    
    @Override
    public List<Coupons> getCoupons(SearchObj searchObj, String searchType, String searchTerm) {
        Session session = sessionFactory.getCurrentSession();
        
        Query query = null;
        List<Coupons> couponsList = new ArrayList<Coupons>();
        
        StringBuilder sb = new StringBuilder();
        sb.append(" from Coupons coupons ");
        if (StringUtils.isNotBlank(searchTerm)) {
            sb.append(" where coupons.code = :searchTerm");
        } else if (StringUtils.isNotBlank(searchType)) {
            sb.append(" where ");
            if ("active".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.status = 1 ");
            } else if ("inactive".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.status = 0 ");
            } else if ("expiredCount".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.number_of_redeems >= coupons.max_redeem_count and coupons.max_redeem_count > 0");
            } else if ("expiredDate".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.end_date <= :date");
            } else if ("once".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.usage_type = 0 ");
            } else if ("multiple".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.usage_type = 1 ");
            } else if ("unlimited".equalsIgnoreCase(searchType)) {
                sb.append(" coupons.usage_type = 2 ");
            }
        }
        sb.append(" order by coupons.code asc");
        query = session.createQuery(sb.toString());
        
        if (StringUtils.isNotBlank(searchTerm)) {
            query.setString("searchTerm", searchTerm.toUpperCase());
        } else if (StringUtils.isNotBlank(searchType) && "expiredDate".equalsIgnoreCase(searchType)) {
            query.setTimestamp("date", new Date());
        }
        
        if (searchObj != null) {
            int firstResult = 0;
            if (searchObj.page > 0) {
                firstResult = (searchObj.page - 1) * searchObj.limit;
            }
            query.setFirstResult(firstResult);
            query.setMaxResults(searchObj.getLimit());
        }
        
        couponsList = (List) query.list();
        
        if (searchObj != null) {
            StringBuilder countQuery = new StringBuilder("select count(coupons.id) ");
            countQuery.append(sb);
            Query countquery = session.createQuery(countQuery.toString());
            if (StringUtils.isNotBlank(searchTerm)) {
                countquery.setString("searchTerm", searchTerm);
            } else if (StringUtils.isNotBlank(searchType) && "expiredDate".equalsIgnoreCase(searchType)) {
                countquery.setTimestamp("date", new Date());
            }
            
            long totalcount = (Long) countquery.uniqueResult();
            searchObj.count = totalcount;
        }
        
        return couponsList;
    }
    
    @Override
    public Customers updateCustomer(Customers custmers) {
        Session session = sessionFactory.getCurrentSession();
        Customers original = null;
        original = (Customers) session.getNamedQuery("Customers.findByCustomersId").setInteger("customersId", custmers.getCustomersId()).uniqueResult();
        if (original != null) {
            BeanUtils.copyProperties(custmers, original);
            session.saveOrUpdate(original);
        }
        return custmers;
    }
    
    @Override
    public double getProductAverageRating(int productsId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        
        query = session.createQuery("select (avg(reviews.reviewsRating) / 5 * 100) as average_rating from Reviews reviews where reviews.productsId = " + productsId);
        
        double rating = (Double) query.uniqueResult();
        return rating;
    }
    
    @Override
    public List<Reviews> getReviews(SearchObj searchObj) {
        List list = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        List<Reviews> reviewsList = new ArrayList<Reviews>();
        
        query = session.createSQLQuery("select {r.*},{o.*} from reviews r,offers o where r.products_id = o.OfferID order by r.date_added desc")
                .addEntity("r", Reviews.class)
                .addEntity("o", Offers.class);
        
        if (searchObj != null) {
            int firstResult = 0;
            if (searchObj.page > 0) {
                firstResult = (searchObj.page - 1) * searchObj.limit;
            }
            query.setFirstResult(firstResult);
            query.setMaxResults(searchObj.getLimit());
        }
        
        list = (List) query.list();
        if (list != null) {
            for (Object tuples : list) {
                Object[] tuplesArray = (Object[]) tuples;
                Reviews r = (Reviews) tuplesArray[0];
                Offers p = (Offers) tuplesArray[1];
                r.setProducts(p);
                reviewsList.add(r);
            }
        }
        
        if (searchObj != null) {
            StringBuilder countQuery = new StringBuilder("select count(r.reviews_id) as count from reviews r,offers o where r.products_id = o.OfferID");
            Query countquery = session.createSQLQuery(countQuery.toString()).addScalar("count", LongType.INSTANCE);
            long totalcount = (Long) countquery.uniqueResult();
            searchObj.count = totalcount;
        }
        
        return reviewsList;
        
    }
    
    @Override
    public Map<String, Object> getReports(Map<String, String> requestMap) {
        Map<String, Object> dataMap = null;
        String reportType = requestMap.get("reportType");
        if (StringUtils.isBlank(reportType)) {
            return null;
        }
        
        String titles = null;
        String fieldTypes = null;
        String queryStr = null;
        Map<String, Object> parameters = null;
        
        Date start = Util.getStartDate(requestMap.get("start"));
        Date end = Util.getEndDate(requestMap.get("end"));
        
        if (start != null || end != null) {
            parameters = new HashMap<String, Object>();
            if (start != null) {
                parameters.put("start", start);
            }
            
            if (end != null) {
                parameters.put("end", end);
            }
        }
        
        String condition = null;
        switch (reportType) {
            case "locations":
                titles = "LOCATION_NAME|LONGITUDE|LATITUDE|ZIPCODE|STATUS";
                fieldTypes = "string|double|double|string|int";
                queryStr = "SELECT NAME,longitude,latitude,ZipCode,STATUS FROM location ORDER BY NAME asc";
                
                break;
            case "categories":
                titles = "CATEGORY_NAME|CATEGORY_ID|DESCRIPTION|STATUS";
                fieldTypes = "string|int|string|int";
                queryStr = "SELECT NAME,categoryId,description,STATUS FROM categories ORDER BY categoryid asc";
                
                break;
            case "customers":
                titles = "FIRST_NAME|LAST_NAME|CONTACT_NUMBER|E-MAIL_ID|CREATED DATE";
                fieldTypes = "string|string|string|string|date";
                
                condition = " where customers_id>0 ";
                if (start != null) {
                    condition += " and account_created>=:start ";
                }
                
                if (end != null) {
                    condition += " and account_created<:end ";
                }
                queryStr = "SELECT firstname,lastname,telephone,email_address,account_created  FROM customers " + condition + " ORDER BY firstname,lastname";
                
                break;
            case "todaysOffers":
                titles = "TITLE|OFFER_ID|DESCRIPTION|ACTUAL_PRICE|OFFER_PRICE|IMAGE|QUANTITY|STATUS";
                fieldTypes = "string|int|string|bigint|bigint|string|int|int";
                
                queryStr = "SELECT title,offerId,description,price,offerPrice,image,quantity,STATUS FROM offers WHERE TYPE=2 order by offerId desc";
                
                break;
            case "exclusiveOffers":
                titles = "TITLE|OFFER_ID|DESCRIPTION|ACTUAL_PRICE|OFFER_PRICE|IMAGE|QUANTITY|STATUS";
                fieldTypes = "string|int|string|bigint|bigint|string|int|int";
                
                queryStr = "SELECT title,offerId,description,price,offerPrice,image,quantity,STATUS FROM offers WHERE TYPE=1 order by offerId desc";
                
                break;
            case "premiumOffers":
                titles = "TITLE|DESCRIPTION|IMAGE|URL|STATUS";
                fieldTypes = "string|string|string|string|int";
                
                queryStr = "SELECT title,description,image,url,STATUS FROM premiumoffers ORDER BY id desc";
                
                break;
            case "vendors":
                titles = "NAME|VENDOR_ID|E-MAIL|CONTACT_NUMBER|LOCATION|LONGITUDE|LATITUDE|CATEGORY|ADDRESS|ZIPCODE|SORT ORDER";
                fieldTypes = "string|int|string|string|string|double|double|string|string|string|int";
                
                queryStr = "SELECT v.name as vendorName,v.VendorID,v.email,v.phone,l.name as location,v.longitude,v.latitude,c.name as category,v.location as address,v.zipcode,v.sort_order FROM vendor v,location l,categories c WHERE v.locationId=l.id AND v.CategoryID=c.CategoryID ORDER BY v.name asc";
                
                break;
            case "cancelOrders":
                titles = "ORDER_ID|CUSTOMER_NAME|CUSTOMER_ADDRESS|CUSTOMER_CONTACT_NUMBER|CUSTOMER_EMAIL|VENDOR_NAME|VENDOR_EMAIL|VENDOR_CONTACT_NUMBER|PRODUCT|PRODUCT_PRICE|PRODUCT_FINAL_PRICE|QUANTITY|ORDER_TOTAL|COMMENTS|PURCHASED_DATE|CANCEL_DATE";
                fieldTypes = "int|string|string|string|string|string|string|string|string|bigint|bigint|int|bigint|string|date|date";
                
                condition = " ";
                if (start != null) {
                    condition += " and oc.created_date>=:start ";
                }
                
                if (end != null) {
                    condition += " and oc.created_date<:end ";
                }
                
                queryStr = "SELECT o.orders_id,o.customers_name,CONCAT_WS(',',o.customers_street_address,o.customers_suburb,\n"
                        + " o.customers_city,o.customers_postcode,o.customers_state) AS address ,o.customers_telephone,o.customers_email_address,\n"
                        + " v.name AS vendorName,v.Email,v.Phone,\n"
                        + " op.products_name,op.products_price,op.final_price,op.products_quantity,ot.value AS total,\n"
                        + " oc.comments,o.date_purchased,oc.created_date \n"
                        + " FROM order_cancellation oc,orders o ,orders_products op,orders_total ot,vendor v,offers p\n"
                        + " WHERE oc.order_id=o.orders_id AND o.orders_id=op.orders_id AND ot.orders_id=o.orders_id AND op.products_id=p.OfferID \n"
                        + " AND p.VendorID=v.VendorID AND ot.class='ot_total' " + condition + " ORDER BY oc.created_date DESC ";
                break;
            case "orders":
                titles = "ORDER_ID|CUSTOMER_NAME|CUSTOMER_ADDRESS|CUSTOMER_CONTACT_NUMBER|CUSTOMER_EMAIL|VENDOR_NAME|VENDOR_EMAIL|VENDOR_CONTACT_NUMBER|PRODUCT|PRODUCT_PRICE|PRODUCT_FINAL_PRICE|QUANTITY|ORDER_TOTAL|PAYMENT_METHOD|PURCHASED_DATE|ORDER_STATUS";
                fieldTypes = "int|string|string|string|string|string|string|string|string|bigint|bigint|int|bigint|string|date|string";
                
                condition = " ";
                if (start != null) {
                    condition += " and o.date_purchased>=:start ";
                }
                
                if (end != null) {
                    condition += " and o.date_purchased<:end ";
                }
                
                queryStr = "SELECT o.orders_id,o.customers_name,CONCAT_WS(',',o.customers_street_address,o.customers_suburb,\n"
                        + " o.customers_city,o.customers_postcode,o.customers_state) AS address ,o.customers_telephone,o.customers_email_address,\n"
                        + " v.name AS vendorName,v.Email,v.Phone,\n"
                        + " op.products_name,op.products_price,op.final_price,op.products_quantity,ot.value AS total,\n"
                        + " o.payment_method,o.date_purchased,os.orders_status_name  \n"
                        + " FROM orders o ,orders_products op,orders_status os,orders_total ot,vendor v,offers p\n"
                        + " WHERE o.orders_id=op.orders_id AND o.orders_status=os.orders_status_id AND ot.orders_id=o.orders_id AND op.products_id=p.OfferID \n"
                        + " AND p.VendorID=v.VendorID AND ot.class='ot_total' " + condition + " ORDER BY o.date_purchased DESC ";
                break;
            case "premiumOrders": 
                titles = "ORDER_ID|CUSTOMER_NAME|CUSTOMER_ADDRESS|CUSTOMER_CONTACT_NUMBER|CUSTOMER_EMAIL|PRODUCT|PRODUCT_PRICE|PRODUCT_FINAL_PRICE|QUANTITY|ORDER_TOTAL|PAYMENT_METHOD|PURCHASED_DATE|ORDER_STATUS";
                fieldTypes = "int|string|string|string|string|string|bigint|bigint|int|bigint|string|date|string";
                
                condition = " ";
                if (start != null) {
                    condition += " and o.date_purchased>=:start ";
                }
                
                if (end != null) {
                    condition += " and o.date_purchased<:end ";
                }
                
                queryStr = "SELECT o.orders_id,o.customers_name,CONCAT_WS(',',o.customers_street_address,o.customers_suburb,\n"
                        + " o.customers_city,o.customers_postcode,o.customers_state) AS address ,o.customers_telephone,o.customers_email_address,\n"
                        + " op.products_name,op.products_price,op.final_price,op.products_quantity,ot.value AS total,\n"
                        + " o.payment_method,o.date_purchased,os.orders_status_name  \n"
                        + " FROM orders o ,orders_products op,orders_status os,orders_total ot,premiumoffers p\n"
                        + " WHERE o.orders_id=op.orders_id AND o.orders_status=os.orders_status_id AND ot.orders_id=o.orders_id AND op.products_id=p.id \n"
                        + " AND ot.class='ot_total' " + condition + " ORDER BY o.date_purchased DESC ";
                break;
            case "exclusiveOrders" : 
                titles = "ORDER_ID|CUSTOMER_NAME|CUSTOMER_ADDRESS|CUSTOMER_CONTACT_NUMBER|CUSTOMER_EMAIL|PRODUCT|PRODUCT_PRICE|PRODUCT_FINAL_PRICE|QUANTITY|ORDER_TOTAL|PAYMENT_METHOD|PURCHASED_DATE|ORDER_STATUS";
                fieldTypes = "int|string|string|string|string|string|bigint|bigint|int|bigint|string|date|string";
                
                condition = " ";
                if (start != null) {
                    condition += " and o.date_purchased>=:start ";
                }
                
                if (end != null) {
                    condition += " and o.date_purchased<:end ";
                }
                
                queryStr = "SELECT o.orders_id,o.customers_name,CONCAT_WS(',',o.customers_street_address,o.customers_suburb,\n"
                        + " o.customers_city,o.customers_postcode,o.customers_state) AS address ,o.customers_telephone,o.customers_email_address,\n"
                        + " op.products_name,op.products_price,op.final_price,op.products_quantity,ot.value AS total,\n"
                        + " o.payment_method,o.date_purchased,os.orders_status_name  \n"
                        + " FROM orders o ,orders_products op,orders_status os,orders_total ot,offers p\n"
                        + " WHERE o.orders_id=op.orders_id AND o.orders_status=os.orders_status_id AND ot.orders_id=o.orders_id AND op.products_id=p.OfferID \n"
                        + " AND ot.class='ot_total' " + condition + " ORDER BY o.date_purchased DESC ";
                break;
        }
        
        if (titles != null) {
            Session session = sessionFactory.getCurrentSession();
            
            System.out.println("query : " + queryStr);
            Query query = session.createSQLQuery(queryStr);
            if (parameters != null && !parameters.isEmpty()) {
                query.setProperties(parameters);
            }
            
            List list = (List) query.list();
            
            dataMap = new HashMap<String, Object>();
            dataMap.put("data", list);
            dataMap.put("titles", titles);
            dataMap.put("fieldTypes", fieldTypes);
        }
        return dataMap;
    }
    
    @Override
    public List<Offers> getPendingOffers(int start, int limit, String title) {
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM offers WHERE");
        boolean isInside = false;
        
        if (StringUtils.isNotBlank(title)) {
            isInside = true;
            searchQuery.append(" Title like '%" + title.trim() + "%'");
        }
        
        if (isInside) {
            searchQuery.append(" and");
        }
        
        searchQuery.append(" status = 0");
        searchQuery.append(" ORDER BY OfferID DESC LIMIT ").append(limit).append(" OFFSET ").append(start);
        
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(searchQuery.toString());
        query.addEntity(Offers.class);
        return query.list();
    }
    
    @Override
    public int getPendingOffersCount() {
        String hql = "SELECT count(*) FROM Offers o WHERE o.status=0";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        int count = ((Number) query.uniqueResult()).intValue();
        return count;
    }
    
    @Override
    public void approveOffer(int offerId) {
        String hql = "FROM Offers o WHERE o.offerID=:offerID";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("offerID", offerId);
        Offers offer = (Offers) query.uniqueResult();
        offer.setStatus(1);
        session.update(offer);
    }
    
    @Override
    public void deleteUnapprovedOffer(int offerId) {
        String hql = "FROM Offers o WHERE o.offerID=:offerID AND o.status=0";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("offerID", offerId);
        Offers offer = (Offers) query.uniqueResult();
        offer.setStatus(UNAPPROVED_DELETE_STATUS);
        session.update(offer);
    }
    
    @Override
    public List<Offers> getAllApprovedOffers(int start, int max, String seo_title) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * from offers WHERE status=1");
        
        if (StringUtils.isNotBlank(seo_title)) {
            queryBuilder.append(" and seo_title like '%");
            queryBuilder.append(seo_title.trim());
            queryBuilder.append("%'");
        }
        
        queryBuilder.append(" ORDER BY OfferID DESC LIMIT ").append(max).append(" OFFSET ").append(start);
        
        String queryStr = queryBuilder.toString();
        
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryStr);
        query.addEntity(Offers.class);
        return query.list();
    }
    
    @Override
    public int getAllApprovedOffersCount() {
        String hql = "SELECT count(*) FROM Offers o WHERE o.status=1";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        int count = ((Number) query.uniqueResult()).intValue();
        return count;
    }
    
    @Override
    public Offers getOfferBySeoTitle(String seo_title) {
        String hql = "FROM Offers o WHERE o.seo_title=:seo_title";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("seo_title", seo_title);
        Offers offer = (Offers) query.uniqueResult();
        return offer;
    }
    
    @Override
    public List<PremiumOffers> getPremiumOffersByScheduleStatus(int scheduleStatus) {
        String hql = "FROM PremiumOffers po WHERE po.scheduleStatus=:scheduledStatus";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("scheduledStatus", scheduleStatus);
        return query.list();
    }
    
    @Override
    public List<Offers> getExclusiveOffersByScheduleStatus(int scheduleStatus) {
        // type = 1
        String hql = "FROM Offers o WHERE o.scheduleStatus=:scheduleStatus AND o.type=1";
        return sessionFactory.getCurrentSession().createQuery(hql).setParameter("scheduleStatus", scheduleStatus).list();
    }
    
    @Override
    public List<Offers> getTodaysOffersByScheduleStatus(int scheduleStatus) {
        //type = 2
        String hql = "FROM Offers o WHERE o.scheduleStatus=:scheduleStatus AND o.type=2";
        return sessionFactory.getCurrentSession().createQuery(hql).setParameter("scheduleStatus", scheduleStatus).list();
    }
    
}
