/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.dao.impl;

import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.dto.MessageStatus;
import com.cnbitsols.adrobe.dto.OrderStatus;
import com.cnbitsols.adrobe.dto.SearchRelevanceScore;
import com.cnbitsols.adrobe.entities.AdPrint;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.CustomerCoupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.EmailQueue;
import com.cnbitsols.adrobe.entities.FavouriteCustomerProducts;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.OrderCancellation;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersStatus;
import com.cnbitsols.adrobe.entities.OrdersStatusHistory;
import com.cnbitsols.adrobe.entities.OrdersTotal;
import com.cnbitsols.adrobe.entities.PaymentTransactions;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.PushDetails;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.SmsQueue;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.entities.VendorNoftificationQueue;
import com.cnbitsols.adrobe.utils.SearchResultComaprator;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santosh.r
 */
@Repository("adrobeHibernateDAOImpl")
@Transactional
public class AdrobeHibernateDAOImpl implements AdrobeHibernateDAO {

    private static final Logger log = LoggerFactory.getLogger(AdrobeHibernateDAOImpl.class);

    int maxVendorOffers = 5;

    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    private void init() {
        //maxVendorOffers = NumberUtils.toInt(SettingsUtil.getSettings("MAX_VENDOR_OFFERS"), maxVendorOffers);
    }

    @Override
    public List getConfiguration() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT c FROM Configuration c").list();
    }

    @Override
    public void incrementRefCount(String adRef) {
        log.debug("inside.... increment ref count*****");
        Session session = sessionFactory.getCurrentSession();
        AdPrint adPrint = (AdPrint) session.getNamedQuery("AdPrint.findByRef")
                .setString("adRef", adRef)
                .uniqueResult();

        adPrint.setAdReferrerCount(adPrint.getAdReferrerCount() + 1);
        session.saveOrUpdate(adPrint);
    }

    @Override
    public List<Categories> listCategories() {
        log.debug("inside......hdao categories*****");
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT c FROM Categories c where c.status=1 order by c.sortOrder asc,c.categoryID asc").list();
    }

    @Override
    public List<Location> listLocations() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT c FROM Location c where c.status=1 order by c.name asc").list();
    }

    @Override
    public List<PremiumOffers> listPremiumOffers(int locationId) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT c FROM PremiumOffers c where status=1");
        if (locationId > 0) {
            queryBuilder.append(" and c.locationId=");
            queryBuilder.append(locationId);
        }
        queryBuilder.append(" order by c.sort_order");
        return session.createQuery(queryBuilder.toString()).list();
    }

    /*@Override
    public List<Offers> listProducts(String category, int locationId, int page, int limit, String searchTerm) {
        Session session = sessionFactory.getCurrentSession();
        String locationCondition = (locationId > -1) ? " and v.locationId = :locationId" : "";
        StringBuilder searchCondition = new StringBuilder("");
        String ishotCondition = " and o.ishot=1 ";
        if(StringUtils.isNotBlank(searchTerm)) {
            //searchTerm = Util.toSeoString(searchTerm);
            String[] arr = searchTerm.split(" ");
            
            searchCondition.append(" and (");
            for(int i =0 ; i<arr.length ;i++){
                if(StringUtils.isNotBlank(arr[i])){
                    if(i!=0){
                        searchCondition.append(" or ");
                    }
                    searchCondition.append(" o.title like '%"+arr[i]+"%'");
                }
            }
            searchCondition.append(" )");
            
            ishotCondition = "";
        }
        String queryStr = null;

        if (StringUtils.isBlank(category) || "all".equals(category)) {
            queryStr = "select o from Offers o ,Vendor v where v.vendorID=o.vendorID " + locationCondition +" "+searchCondition.toString()+ " and o.status=1 order by o.offerID desc";
        } else {
            queryStr = "select o from Offers o ,Vendor v ,Categories c where c.seo_title=:category " + locationCondition +" "+searchCondition.toString()+ " and c.categoryID = v.categoryID and v.vendorID=o.vendorID and o.status=1 "+ishotCondition+" order by o.offerID desc";
        }
        
        log.debug("query :: "+queryStr);
        int firstResult = (page - 1) * limit;
        log.debug("firstResult:" + firstResult);
        Query query = session.createQuery(queryStr);
        
        if(StringUtils.isNotBlank(category) && !"all".equals(category)) {
            query.setString("category", category);
        }
        
        if (locationId > -1) {
            query.setInteger("locationId", locationId);
        }
        query.setFirstResult((page - 1) * limit).setMaxResults(limit);

        return query.list();
    }*/
    @Override
    public List<Object[]> listProducts(String category, List<Integer> subCategoriesIds, int locationId, int page, int limit, String searchTerm) {
        Session session = sessionFactory.getCurrentSession();
        String locationCondition = (locationId > -1) ? " and v.locationId = :locationId" : "";
        StringBuilder searchCondition = new StringBuilder("");
        String ishotCondition = " and o.ishot=1 ";
        if (StringUtils.isNotBlank(searchTerm)) {
            //searchTerm = Util.toSeoString(searchTerm);
            String[] arr = searchTerm.split(" ");

            searchCondition.append(" and (");
            for (int i = 0; i < arr.length; i++) {
                if (StringUtils.isNotBlank(arr[i])) {
                    if (i != 0) {
                        searchCondition.append(" or ");
                    }
                    searchCondition.append(" o.title like '%" + arr[i] + "%'");
                }
            }
            searchCondition.append(" )");

            ishotCondition = "";
        }
        String queryStr = null;

        if (StringUtils.isBlank(category) || "all".equals(category)) {
            queryStr = "select {o.*},{v.*} from offers o ,vendor v where v.vendorID=o.vendorID " + locationCondition + " " + searchCondition.toString() + " and o.status=1 and v.status=1 order by v.sort_order asc,o.offerID desc";
        } else {
            queryStr = "select {o.*},{v.*} from offers o ,vendor v ,categories c where c.CategoryID in :category " + locationCondition + " " + searchCondition.toString() + " and c.categoryID = v.categoryID and v.vendorID=o.vendorID and o.status=1 and v.status=1 " + ishotCondition + " order by v.sort_order asc,o.offerID desc";
        }

        log.debug("query :: " + queryStr);
        int firstResult = 0;
        if (page > 0) {
            firstResult = (page - 1) * limit;
        }
        log.debug("firstResult:" + firstResult);
        Query query = session.createSQLQuery(queryStr)
                .addEntity("o", Offers.class)
                .addEntity("v", Vendor.class);

        if (StringUtils.isNotBlank(category) && !"all".equals(category) && subCategoriesIds != null) {
            //query.setString("category", category);
            query.setParameterList("category", subCategoriesIds);
        }

        if (locationId > -1) {
            query.setInteger("locationId", locationId);
        }
        query.setFirstResult(firstResult).setMaxResults(limit);

        return query.list();
    }

    @Override
    public List<Object[]> listNearByProducts(double latitude, double longitude, int page, int limit, int distance) {
        List<Integer> vendorIds = new ArrayList<Integer>();
        Session session = sessionFactory.getCurrentSession();

        String queryString = "select v.vendorID,(6373 *acos(cos( radians( " + latitude + ") ) *"
                + "cos( radians( v.latitude ) ) *cos(radians( v.longitude ) - radians( " + longitude + ")) +"
                + "sin(radians(" + latitude + ")) *sin(radians(v.latitude)))) dist from vendor v having dist < " + distance;

        int firstResult = 0;
        if (page > 0) {
            firstResult = (page - 1) * limit;
        }
        log.debug("firstResult:" + firstResult);

        Query query = session.createSQLQuery(queryString);

        query.setFirstResult(firstResult).setMaxResults(limit);

        List<Object[]> vendorArray = query.list();
        if (vendorArray != null && vendorArray.size() > 0) {
            for (int i = 0; i < vendorArray.size(); i++) {
                Object[] obj = vendorArray.get(i);
                vendorIds.add((Integer) obj[0]);
            }
            String ishotCondition = " and o.ishot=1 ";
            String queryStr = "select {o.*},{v.*} from offers o ,vendor v ,categories c where v.vendorID in :vendorid and c.categoryID = v.categoryID and v.vendorID=o.vendorID and o.status=1 and v.status=1 " + ishotCondition + " order by v.sort_order asc,o.offerID desc";
            query = session.createSQLQuery(queryStr)
                    .addEntity("o", Offers.class)
                    .addEntity("v", Vendor.class);

            query.setParameterList("vendorid", vendorIds);
            query.setFirstResult(firstResult).setMaxResults(limit);
            return query.list();
        }
        return new ArrayList<Object[]>();
    }

    private void populateDummyVendor(String name, Vendor target) {
        target.setName(name);
        target.setEmail(name + "@adrobe.com");
        target.setPhone("040-40044442");
        target.setLocation("Hyderabad");
        target.setCategoryID(0);
    }

    @Override
    public List<Object[]> searchProducts(String category, List<Integer> subCategoriesIds, int page, int limit, String searchTerm) {
        log.debug("Inside searchProducts");
        Session session = sessionFactory.getCurrentSession();
        Integer categoryID = null;

        List<Object[]> resultList = new ArrayList<>();

        if (StringUtils.isNotBlank(category) && !"all".equals(category)) {
            Categories cat = (Categories) session.getNamedQuery("Categories.findBySEOTITLE")
                    .setString("seo_title", category)
                    .uniqueResult();

            categoryID = cat.getCategoryID();
        }

        StringBuilder todaysOffersQueryBuilder = new StringBuilder();
        todaysOffersQueryBuilder.append("SELECT o.OfferID, o.Title, o.Description, o.Ishot, o.Price, o.Image, o.quantity, o.offerPrice, o.coupon_price, o.seo_title, o.type, ");
        todaysOffersQueryBuilder.append("MATCH (o.Title) AGAINST ('").append(searchTerm);
        todaysOffersQueryBuilder.append("' IN NATURAL LANGUAGE MODE) as score1, MATCH (o.keywords) AGAINST ('+").append(searchTerm);
        todaysOffersQueryBuilder.append("' IN BOOLEAN MODE) AS score2 FROM offers as o WHERE o.status=1 and o.type=2 ");
        todaysOffersQueryBuilder.append("AND (MATCH (o.Title) AGAINST ('").append(searchTerm);
        todaysOffersQueryBuilder.append("' IN NATURAL LANGUAGE MODE) + MATCH (o.keywords) AGAINST ('+").append(searchTerm);
        todaysOffersQueryBuilder.append("' IN BOOLEAN MODE)) > 0");
        if (categoryID != null) {
            todaysOffersQueryBuilder.append(" and o.CategoryID=").append(categoryID);
        }
        todaysOffersQueryBuilder.append(" ORDER BY (score1 + score2) DESC");

        Query todaysOffersQuery = session.createSQLQuery(todaysOffersQueryBuilder.toString())
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] os, String[] strings) {
                        Offers offers = new Offers();
                        Vendor vendor = new Vendor();
                        SearchRelevanceScore weight = new SearchRelevanceScore();

                        try {
                            offers.setOfferID((Integer) os[0]);
                            offers.setTitle((String) os[1]);
                            offers.setDescription((String) os[2]);
                            offers.setIshot((int) os[3]);
                            offers.setPrice((BigDecimal) os[4]);
                            offers.setImage((String) os[5]);
                            offers.setVendorID(-1);
                            offers.setQuantity((int) os[6]);
                            offers.setOfferPrice((BigDecimal) os[7]);
                            offers.setCouponPrice((BigDecimal) os[8]);
                            offers.setSeo_title((String) os[9]);
                            offers.setType((Integer) os[10]);
                            weight.setScore((Double) os[11] + (Double) os[12]);

                            populateDummyVendor("Todays Offer", vendor);
                        } catch (Exception ex) {

                        }

                        return new Object[]{offers, vendor, weight};
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        List<Object[]> todaysOffers = todaysOffersQuery.list();
        resultList.addAll(todaysOffers);

        StringBuilder exclusiveOffersQueryBuilder = new StringBuilder();
        exclusiveOffersQueryBuilder.append("SELECT o.OfferID, o.Title, o.Description, o.Ishot, o.Price, o.Image, o.quantity, o.offerPrice, o.coupon_price, o.seo_title, o.type, ");
        exclusiveOffersQueryBuilder.append("MATCH (o.Title) AGAINST ('").append(searchTerm);
        exclusiveOffersQueryBuilder.append("' IN NATURAL LANGUAGE MODE) as score1, MATCH (o.keywords) AGAINST ('+").append(searchTerm);
        exclusiveOffersQueryBuilder.append("' IN BOOLEAN MODE) AS score2 FROM offers as o WHERE o.status=1 and o.type=1 ");
        exclusiveOffersQueryBuilder.append("AND (MATCH (o.Title) AGAINST ('").append(searchTerm);
        exclusiveOffersQueryBuilder.append("' IN NATURAL LANGUAGE MODE) + MATCH (o.keywords) AGAINST ('+").append(searchTerm);
        exclusiveOffersQueryBuilder.append("' IN BOOLEAN MODE)) > 0");
        if (categoryID != null) {
            exclusiveOffersQueryBuilder.append(" and o.CategoryID=").append(categoryID);
        }
        exclusiveOffersQueryBuilder.append(" ORDER BY (score1 + score2) DESC");

        Query exclusiveOffersQuery = session.createSQLQuery(exclusiveOffersQueryBuilder.toString())
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] os, String[] strings) {
                        Offers offers = new Offers();
                        Vendor vendor = new Vendor();
                        SearchRelevanceScore weight = new SearchRelevanceScore();

                        try {
                            offers.setOfferID((Integer) os[0]);
                            offers.setTitle((String) os[1]);
                            offers.setDescription((String) os[2]);
                            offers.setIshot((int) os[3]);
                            offers.setPrice((BigDecimal) os[4]);
                            offers.setImage((String) os[5]);
                            offers.setVendorID(-1);
                            offers.setQuantity((int) os[6]);
                            offers.setOfferPrice((BigDecimal) os[7]);
                            offers.setCouponPrice((BigDecimal) os[8]);
                            offers.setSeo_title((String) os[9]);
                            offers.setType((Integer) os[10]);
                            weight.setScore((Double) os[11] + (Double) os[12]);

                            populateDummyVendor("Exclusive Offer", vendor);
                        } catch (Exception ex) {

                        }

                        return new Object[]{offers, vendor, weight};
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        List<Object[]> exclusiveOffers = exclusiveOffersQuery.list();
        resultList.addAll(exclusiveOffers);

        // process todays offers to match the columns to be similar to the final query with vendors
        StringBuilder queryStr = new StringBuilder();
        queryStr.append("SELECT o.OfferID, o.Title, o.Description, o.Ishot, o.Price, o.Image, o.VendorID, o.quantity, o.offerPrice, o.coupon_price, o.seo_title, o.type, v.name, v.email, v.Phone, v.Location, v.CategoryID, MATCH (o.Title) AGAINST ('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE) AS score1, MATCH(o.keywords) AGAINST ('+");
        queryStr.append(searchTerm);
        queryStr.append("' IN BOOLEAN MODE) AS score4, MATCH (v.name) AGAINST ('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE) AS score2, MATCH(v.Location) AGAINST ('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE) AS score3 FROM offers AS o LEFT JOIN vendor AS v ON v.vendorID=o.vendorID WHERE o.status=1 AND v.status=1");
        if (categoryID != null) {
            queryStr.append(" AND o.CategoryID=").append(categoryID);
        }
        queryStr.append(" AND (MATCH (o.Title) AGAINST ('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE) OR MATCH (o.keywords) AGAINST ('+").append(searchTerm).append("' IN BOOLEAN MODE) OR MATCH (v.name) AGAINST ('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE) OR MATCH(v.Location) AGAINST('");
        queryStr.append(searchTerm);
        queryStr.append("' IN NATURAL LANGUAGE MODE)) ORDER BY (score1 + score2 + score4) DESC, score3 DESC");
        log.debug("query :: " + queryStr);
        int firstResult = 0;
        if (page > 0) {
            firstResult = (page - 1) * limit;
        }
        log.debug("firstResult:" + firstResult);
        Query query = session.createSQLQuery(queryStr.toString())
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] os, String[] strings) {
                        Offers offers = new Offers();
                        Vendor v = new Vendor();
                        SearchRelevanceScore weight = new SearchRelevanceScore();

                        try {
                            offers.setOfferID((Integer) os[0]);
                            offers.setTitle((String) os[1]);
                            offers.setDescription((String) os[2]);
                            offers.setIshot((int) os[3]);
                            offers.setPrice((BigDecimal) os[4]);
                            offers.setImage((String) os[5]);
                            offers.setVendorID((int) os[6]);
                            offers.setQuantity((int) os[7]);
                            offers.setOfferPrice((BigDecimal) os[8]);
                            offers.setCouponPrice((BigDecimal) os[9]);
                            offers.setSeo_title((String) os[10]);
                            offers.setType((Integer) os[11]);

                            v.setName((String) os[12]);
                            v.setEmail((String) os[13]);
                            v.setPhone((String) os[14]);
                            v.setLocation((String) os[15]);
                            v.setCategoryID((int) os[16]);

                            weight.setScore((Double) os[17] + (Double) os[18] + (Double) os[19]);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        return new Object[]{offers, v, weight};

                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });
        //.addEntity("o", Offers.class);

//        query.setFirstResult(firstResult).setMaxResults(limit);
        List<Object[]> list = query.list();
        resultList.addAll(list);

        Collections.sort(resultList, new SearchResultComaprator());

        if (firstResult + limit > resultList.size()) {
            limit = resultList.size() - firstResult;
        }

        return resultList.subList(firstResult, firstResult + limit);
    }

    @Override
    public List searchProductsByPrice(String category, List<Integer> subCategoriesIds, int page, int limit, String priceRange, String discountRange) {

        String priceCondition = getPriceConditionQuery(priceRange, discountRange);

        String queryStr = null;

        if (StringUtils.isBlank(category) || "all".equals(category)) {
            queryStr = "select {o.*},{v.*} from offers o ,vendor v where v.vendorID=o.vendorID and o.status=1 and v.status=1 " + priceCondition + "";
        } else {
            queryStr = "select {o.*},{v.*} from offers o ,vendor v ,categories c where c.CategoryID in :category  and c.categoryID = v.categoryID and v.vendorID=o.vendorID and o.status=1 and v.status=1 " + priceCondition + "";
        }

        log.debug("query :: " + queryStr);
        int firstResult = 0;
        if (page > 0) {
            firstResult = (page - 1) * limit;
        }
        log.debug("firstResult:" + firstResult);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(queryStr)
                .addEntity("o", Offers.class)
                .addEntity("v", Vendor.class);

        if (StringUtils.isNotBlank(category) && !"all".equals(category) && subCategoriesIds != null) {
            //query.setString("category", category);
            query.setParameterList("category", subCategoriesIds);
        }

        query.setFirstResult(firstResult).setMaxResults(limit);

        return query.list();
    }

    @Override
    public Offers getProduct(String productName) {
        return (Offers) sessionFactory.getCurrentSession()
                .getNamedQuery("Offers.findBySEOTITLE")
                .setString("seo_title", productName)
                .uniqueResult();
    }

    @Override
    public Categories getCategoryBySeoName(String category) {
        return (Categories) sessionFactory.getCurrentSession()
                .getNamedQuery("Categories.findBySEOTITLE")
                .setString("seo_title", category)
                .uniqueResult();
    }

    @Override
    public Vendor getVendor(int vendorId) {
        return (Vendor) sessionFactory.getCurrentSession()
                .getNamedQuery("Vendor.findByVendorID")
                .setInteger("vendorID", vendorId)
                .uniqueResult();
    }

    @Override
    public List<Offers> listVendorProducts(int vendorId) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("Offers.findByVendorID")
                .setInteger("vendorID", vendorId)
                //.setMaxResults(maxVendorOffers)
                .list();
    }

    @Override
    public Offers getOffer(int offerId) {
        return (Offers) sessionFactory.getCurrentSession()
                .createQuery("SELECT o FROM Offers o WHERE o.offerID = :offerID")
                .setInteger("offerID", offerId)
                .uniqueResult();
    }

    @Override
    public Orders getOrder(int orderId) {
        return (Orders) sessionFactory.getCurrentSession()
                .getNamedQuery("Orders.findByOrdersId")
                .setInteger("ordersId", orderId)
                .uniqueResult();
    }

    @Override
    public Map<Integer, Offers> getProducts(Set<Integer> productsIds) {
        List<Offers> offers = sessionFactory.getCurrentSession()
                .createQuery("SELECT o FROM Offers o WHERE o.offerID in :offerID and o.status=1 and o.quantity>0")
                .setParameterList("offerID", productsIds)
                .list();

        Map<Integer, Offers> map = new HashMap<Integer, Offers>();
        for (Offers o : offers) {
            map.put(o.getOfferID(), o);
        }

        return map;
    }

    @Override
    public List<Offers> getTodaysOffers(int locationId, int limit) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select o from Offers o where o.status=1 and o.type=2");
        if (locationId > 0) {
            queryBuilder.append(" and o.locationId=");
            queryBuilder.append(locationId);
        }
        queryBuilder.append(" order by o.sort_order");
        String queryStr = queryBuilder.toString();

        Query query = session.createQuery(queryStr);
        if (limit > 0) {
            query.setMaxResults(limit);
        }

        return query.list();
    }

    @Override
    public Customers getCustomer(String userName) {
        return (Customers) sessionFactory.getCurrentSession().getNamedQuery("Customers.findByEmailAddress")
                .setString("emailAddress", userName)
                .uniqueResult();
    }

    @Override
    public Customers getCustomerByPhone(String phoneNumber) {
        return (Customers) sessionFactory.getCurrentSession().getNamedQuery("Customers.findByTelephone")
                .setString("telephone", phoneNumber)
                .uniqueResult();
    }

    @Override
    public void addNewCustomer(Customers customer) {
        customer.setAccountCreated(new Date());
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
    }

    @Override
    public List<AddressBook> getCustomerAddresses(Integer customersId) {
        return sessionFactory.getCurrentSession()
                .getNamedQuery("AddressBook.findByCustomersId")
                .setInteger("customersId", customersId)
                .list();
    }

    @Override
    public void save(Object obj) {
        sessionFactory.getCurrentSession().saveOrUpdate(obj);
    }

    @Override
    public Object getById(Serializable key, Class clazz) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(clazz, key);
    }

    @Override
    public AddressBook getAddressById(int selectedAddress, Integer customersId) {
        return (AddressBook) sessionFactory.getCurrentSession()
                .getNamedQuery("AddressBook.findByAddressBookId")
                .setInteger("addressBookId", selectedAddress)
                .setInteger("customersId", customersId)
                .uniqueResult();
    }

    @Override
    public List<Offers> getExclusiveOffers(int locationId) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select o from Offers o where o.status=1 and o.type=1");
        if (locationId > 0) {
            queryBuilder.append(" and o.locationId=");
            queryBuilder.append(locationId);
        }
        queryBuilder.append(" order by o.sort_order");
        String queryStr = queryBuilder.toString();

        Query query = session.createQuery(queryStr);
        return query.list();
    }

    @Override
    public Location getLocationByZip(String zip) {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "select o from Location o where o.zipCode=:zipCode and status=1";

        Query query = session.createQuery(queryStr).setString("zipCode", zip);
        List<Location> loc = query.list();
        if (loc != null && loc.size() > 0) {
            return loc.get(0);
        }
        return null;
    }

    @Override
    public Location getDefaultLocation() {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "FROM Location l where l.name='HYDERABAD' and status=1";
        Location location = (Location) session.createQuery(queryStr).uniqueResult();

        return location;
    }

    @Override
    public List<EmailQueue> getPendingEmails() {
        return sessionFactory.getCurrentSession()
                .createQuery("from EmailQueue where status=" + MessageStatus.PENDING.status())
                .setMaxResults(25).list();
    }

    @Override
    public List<SmsQueue> getPendingSMSs() {
        return sessionFactory.getCurrentSession()
                .createQuery("from SmsQueue where status=" + MessageStatus.PENDING.status())
                .setMaxResults(25).list();
    }

    @Override
    public Orders updateOrderPayment(Map<String, Object> paymentResponeMap) {
        Integer orderId = (Integer) paymentResponeMap.get("orderId");
        String status = (String) paymentResponeMap.get("status");
        //String transactionId = (String) paymentResponeMap.get("transactionId");

        Session session = sessionFactory.getCurrentSession();
        Orders order = (Orders) session.get(Orders.class, orderId);
        if (order != null) {
            int statusId = OrderStatus.PAYMENT_FAILED.status();
            if ("success".equalsIgnoreCase(status)) {
                statusId = OrderStatus.ORDER_PLACED.status();
            }

            order.setOrdersStatus(statusId);
            order.setLastModified(new Date());

            session.saveOrUpdate(order);

            PaymentTransactions pt = new PaymentTransactions();
            pt.setCreatedDate(new Date());
            pt.setOrderId(orderId);
            pt.setRequest(paymentResponeMap.toString());

            session.save(pt);

            session.getNamedQuery("OrdersProducts.updateStatus")
                    .setInteger("ordersProductsStatus", statusId)
                    .setInteger("ordersId", orderId)
                    .executeUpdate();

            if ("success".equalsIgnoreCase(status) && order.getCouponId() > 0) {
                try {
                    updateCouponInfo(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return order;

    }

    private void updateCouponInfo(Orders order) {
        log.debug("Inside updateCouponInfo");

        Coupons coupon = getCoupons(order.getCouponId());
        if (coupon == null) {
            return;
        }

        CustomerCoupons customerCoupon = getCustomerCoupon(coupon.getId(), order.getCustomersId());

        Session session = sessionFactory.getCurrentSession();
        if (coupon.getNumber_of_times_to_use() <= 1 || coupon.getAllow_multiple_redeems_by_same_user() == 1 || (coupon.getNumber_of_times_to_use() > 1 && customerCoupon == null)) {
            coupon.setNumber_of_redeems(coupon.getNumber_of_redeems() + 1);
            session.saveOrUpdate(coupon);

        }
        try {
            Date now = new Date();
            if (customerCoupon == null) {
                customerCoupon = new CustomerCoupons();
                customerCoupon.setCouponId(coupon.getId());
                customerCoupon.setCustomerId(order.getCustomersId());
                customerCoupon.setNumberOfTimesUsed(0);
                customerCoupon.setCouponStartDate(now);
            }

            customerCoupon.setNumberOfTimesUsed(customerCoupon.getNumberOfTimesUsed() + 1);
            session.saveOrUpdate(customerCoupon);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Orders> getOrders(Integer customersId, int start, int max) {
        Query ordersQuery = sessionFactory.getCurrentSession()
                .getNamedQuery("Orders.findByCustomersId");
        ordersQuery.setInteger("customersId", customersId);
        ordersQuery.setFirstResult(start);
        ordersQuery.setMaxResults(max);
        return ordersQuery.list();
    }

    /*@Override
    public List<Map> getOrdersSummary(Integer customersId, int page, int limit) {
        
        String query = "select new map(o.ordersId as orderId,os.ordersStatusName as status,o.datePurchased as date,count(op.ordersProductsId) as count,ot.value as amount "
                + ") from Orders o,OrdersProducts op,OrdersTotal ot,OrdersStatus os where o.ordersId=op.ordersId "
                + " and o.ordersId=ot.ordersId and ot.class1='ot_total' and os.ordersStatusId=o.ordersStatus and o.customersId=:customerId group by o.ordersId order by o.ordersId desc";
                
        Query ordersQuery = sessionFactory.getCurrentSession()
                .createQuery(query);
        ordersQuery.setInteger("customerId", customersId);
        ordersQuery.setFirstResult((page - 1) * limit).setMaxResults(limit);
      
        return ordersQuery.list();
    }*/
    @Override
    public List<Map> getOrdersSummary(Integer customersId, int page, int limit) {

        String query = "select o.orders_id as orderId,os.orders_status_name as status,o.date_purchased as date,count(op.orders_products_id) as count,ot.value as amount "
                + " from orders o,orders_products op,orders_total ot,orders_status os where o.orders_id=op.orders_id "
                + " and o.orders_id=ot.orders_id and ot.class='ot_total' and os.orders_status_id=o.orders_status "
                + " and o.customers_id=:customerId group by o.orders_id order by o.orders_id desc";

        Query ordersQuery = sessionFactory.getCurrentSession()
                .createSQLQuery(query);
        ordersQuery.setInteger("customerId", customersId);
        ordersQuery.setFirstResult((page - 1) * limit).setMaxResults(limit);
        ordersQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return ordersQuery.list();
    }

    @Override
    public int getOrderCountByCustomerId(Integer customersId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(ordersId) from Orders o where o.customersId=:customerId");
        query.setInteger("customerId", customersId);
        return ((Number) query.uniqueResult()).intValue();
    }

    @Override
    public Orders getOrderById(Integer orderId) {
        return (Orders) sessionFactory.getCurrentSession()
                .getNamedQuery("Orders.findByOrdersId")
                .setInteger("ordersId", orderId)
                .uniqueResult();
    }

    @Override
    public OrdersProducts getProductByOrderId(Integer orderId) {
        return (OrdersProducts) sessionFactory.getCurrentSession()
                .getNamedQuery("OrdersProducts.findByOrdersId")
                .setInteger("ordersId", orderId)
                .uniqueResult();
    }

    @Override
    public List<OrdersProducts> getOrderProducts(int orderId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("OrdersProducts.findByOrdersId").setInteger("ordersId", orderId);
        return query.list();
    }

    @Override
    public List<OrdersStatusHistory> getOrderedHistory(int orderId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("OrdersStatusHistory.findByOrdersId").setInteger("ordersId", orderId);
        return query.list();
    }

    @Override
    public Orders getCustomerOrder(Integer orderId, Integer customersId) {
        Orders order = (Orders) sessionFactory.getCurrentSession().createQuery("select o from Orders o where o.ordersId=:ordersId and o.customersId=:customersId")
                .setInteger("ordersId", orderId)
                .setInteger("customersId", customersId)
                .uniqueResult();

        if (order == null) {
            return null;
        }

        order.setOrdersProducts(getOrderProducts(orderId));
        order.setOrderTotalList(getOrdersTotals(orderId));
        order.setOrderStatus(getOrdersStatus(order.getOrdersStatus()));

        return order;
    }

    @Override
    public List<OrdersTotal> getOrdersTotals(int orderId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("OrdersTotal.findByOrdersId").setInteger("ordersId", orderId);
        return query.list();
    }

    public OrdersStatus getOrdersStatus(int statusId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("OrdersStatus.findByOrdersStatusId").setInteger("ordersStatusId", statusId);
        return (OrdersStatus) query.uniqueResult();
    }

    @Override
    public void cancelCustomerOrder(Integer customersId, Integer orderId, String comments) {

        Session session = sessionFactory.getCurrentSession();

        Orders order = (Orders) session.createQuery("select o from Orders o where o.ordersId=:ordersId and o.customersId=:customersId")
                .setInteger("ordersId", orderId)
                .setInteger("customersId", customersId)
                .uniqueResult();

        if (order == null) {
            return;
        }

        Date now = new Date();
        OrderCancellation cancel = new OrderCancellation();
        cancel.setOrderId(orderId);
        cancel.setComments(comments);
        cancel.setCreatedDate(now);

        session.save(cancel);

        order.setOrdersStatus(OrderStatus.CANCEL_REQUEST.status());
        order.setLastModified(now);
        session.save(order);

        OrdersStatusHistory history = new OrdersStatusHistory();
        history.setOrdersId(orderId);
        history.setOrdersStatusId(OrderStatus.CANCEL_REQUEST.status());
        history.setCustomerNotified(0);
        history.setComments(comments);
        history.setDateAdded(now);

        session.save(history);

    }

    @Override
    public List<VendorNoftificationQueue> getVendorNotifications() {
        return sessionFactory.getCurrentSession()
                .createQuery("from VendorNoftificationQueue")
                .setMaxResults(10).list();
    }

    @Override
    public void delete(Object obj) {
        sessionFactory.getCurrentSession().delete(obj);
    }

    @Override
    public Map<Integer, String> getOrderStatusCodes() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("OrdersStatus.findAll");
        List<OrdersStatus> orderStatus = query.list();
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (OrdersStatus o : orderStatus) {
            map.put(o.getOrdersStatusId(), o.getOrdersStatusName());
        }
        return map;
    }

    /*@Override
    public Map<Integer, Long> categoryOffersCount(int locationId) {
        String locationCondition = (locationId > -1) ? " and v.locationId = :locationId" : "";
        Query query = sessionFactory.getCurrentSession().createQuery("select o.categoryID,count(o.offerID) from Offers o ,Vendor v WHERE  v.vendorID=o.vendorID "+locationCondition+" AND o.categoryID IS NOT NULL and o.status=1 GROUP BY o.categoryID");
        
        if(locationId > -1){
            query.setInteger("locationId", locationId);
        }
        
        List<Object[]> list = query.list();
        Map<Integer, Long> map = new HashMap<Integer, Long>();
        for (Object[] o : list) {
            map.put((Integer)o[0], (Long)o[1]);
        }
        return map;
    }*/
    @Override
    public Map<Integer, Long> categoryOffersCount(int locationId) {
        String locationCondition = (locationId > -1) ? " and v.locationId = :locationId" : "";
        Query query = sessionFactory.getCurrentSession().
                createSQLQuery("select o.categoryID as catID,count(o.offerID) as count from offers o ,vendor v WHERE  v.vendorID=o.vendorID " + locationCondition + " AND o.categoryID IS NOT NULL and o.status=1 GROUP BY o.categoryID")
                .addScalar("catID", IntegerType.INSTANCE)
                .addScalar("count", LongType.INSTANCE);

        if (locationId > -1) {
            query.setInteger("locationId", locationId);
        }

        List<Object[]> list = query.list();
        Map<Integer, Long> map = new HashMap<Integer, Long>();
        for (Object[] o : list) {
            map.put((Integer) o[0], (Long) o[1]);
        }
        return map;
    }

    @Override
    public void changeOrderStatus(int orderId, String comments, String orderState) {

        Session session = sessionFactory.getCurrentSession();

        Orders order = (Orders) session.createQuery("select o from Orders o where o.ordersId=:ordersId ")
                .setInteger("ordersId", orderId)
                .uniqueResult();

        if (order == null || orderState == null) {
            return;
        }

        Date now = new Date();
//        OrderCancellation cancel = new OrderCancellation();
//        cancel.setOrderId(orderId);
//        cancel.setComments(comments);
//        cancel.setCreatedDate(now);
//
//        session.save(cancel);

        order.setOrdersStatus(Integer.parseInt(orderState));
        order.setLastModified(now);
        session.save(order);

        OrdersStatusHistory history = new OrdersStatusHistory();
        history.setOrdersId(orderId);
        history.setOrdersStatusId(Integer.parseInt(orderState));
        history.setCustomerNotified(0);
        history.setComments(comments);
        history.setDateAdded(now);
        session.save(history);

    }

    @Override
    public Customers getCustomerById(int consumerId) {
        return (Customers) sessionFactory.getCurrentSession().getNamedQuery("Customers.findByCustomersId")
                .setInteger("customersId", consumerId)
                .uniqueResult();
    }

    @Override
    public Object getObject(Class entityClass, String[] fields, Object[] values) {
        Object obj = null;
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(entityClass);

        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                c.add(Restrictions.eq(fields[i], values[i]));
            }
        }

        obj = c.uniqueResult();
        return obj;
    }

    @Override
    public List<Object[]> listCustomerCoupons(Integer customersId) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select coupons,customercoupons from Coupons coupons,CustomerCoupons customercoupons where customercoupons.couponId=coupons.id and customercoupons.customerId=:customer_id   and coupons.status=1  order by customercoupons.customerCouponId desc");

        query.setInteger("customer_id", customersId);

        List<Object[]> list = (List<Object[]>) query.list();

        return list;
    }

    @Override
    public List<Offers> getFavouriteProducts(Integer customersId) {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "select {o.*} from offers o ,favourite_customer_products f where f.products_id=o.OfferID and o.status=1 and f.customers_id=:customers_id order by f.favourites_id desc";
        Query query = session.createSQLQuery(queryStr)
                .addEntity("o", Offers.class)
                .setInteger("customers_id", customersId);

        return query.list();
    }

    @Override
    public boolean deleteFavouriteProducts(Integer customersId, int favProductId) {
        Session session = sessionFactory.getCurrentSession();
        String queryStr = "delete from FavouriteCustomerProducts where productsId =:products_id and customersId=:customers_id";
        Query query = session.createQuery(queryStr)
                .setInteger("products_id", favProductId)
                .setInteger("customers_id", customersId);

        query.executeUpdate();

        return true;
    }

    @Override
    public Object[] getCoupons(String couponCode, Integer customerId) {

        if (StringUtils.isBlank(couponCode)) {
            return null;
        }
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("select coupons.*,customercoupons.* from coupons coupons left outer join customer_coupons customercoupons on  customercoupons.coupon_id=coupons.id and customercoupons.customer_id=:customer_id  where coupons.code = :searchTerm and coupons.status=1  order by customercoupons.customer_coupon_id desc")
                .addEntity("coupons", Coupons.class)
                .addEntity("customercoupons", CustomerCoupons.class);

        query.setString("searchTerm", couponCode.toUpperCase());
        query.setInteger("customer_id", customerId);

        List list = (List) query.list();
        Object[] tuplesArray = null;
        if (list != null) {
            for (Object tuples : list) {
                tuplesArray = (Object[]) tuples;
                break;
            }
        }
        return tuplesArray;
    }

    private Coupons getCoupons(Integer ordersId) {
        return (Coupons) sessionFactory.getCurrentSession().getNamedQuery("Coupons.findById").setInteger("id", ordersId).uniqueResult();
    }

    private CustomerCoupons getCustomerCoupon(Integer id, int customersId) {
        return (CustomerCoupons) sessionFactory.getCurrentSession().getNamedQuery("CustomerCoupons.findByCustomerIdAndCouponId").setInteger("customerId", customersId).setInteger("couponId", id).uniqueResult();
    }

    @Override
    public boolean addUserPushDetails(Integer customersId, String pushkey, String deviceos, int status) {
        boolean result = false;
        Session session = sessionFactory.getCurrentSession();
        Query qry = session.createSQLQuery("select * from push_details where customer_id = :customer_id");
        qry.setInteger("customer_id", customersId);

        List list = (List) qry.list();

        if (list != null && list.size() > 0) {
            qry = session.createSQLQuery("update push_details set pushkey =:pushkey ,deviceos=:deviceos ,status =:status where customer_id=:customer_id");
            qry.setInteger("customer_id", customersId);
            qry.setString("pushkey", pushkey);
            qry.setString("deviceos", deviceos);
            qry.setInteger("status", new Integer(status));
            qry.executeUpdate();
        } else {
            PushDetails p = new PushDetails(customersId, pushkey, deviceos, status);
            try {
                sessionFactory.getCurrentSession().save(p);
            } catch (Exception e) {
                log.error("Error while adding push details", e);
            }

        }

        return true;
    }

    @Override
    public boolean addCustomerFavourite(Integer customersId, int productId) {
        FavouriteCustomerProducts p = new FavouriteCustomerProducts();
        p.setCustomersId(customersId);
        p.setProductsId(productId);
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(p);
        } catch (Exception e) {
            log.error("Error while adding favourite", e);
        }
        return true;
    }

    @Override
    public boolean addCustomerReview(Customers customers, int productId, int rating, String description) {
        Session session = sessionFactory.getCurrentSession();

        Reviews review = (Reviews) session.createQuery("select r from Reviews r where r.customersId=:customerId and r.productsId=:productId")
                .setInteger("customerId", customers.getCustomersId())
                .setInteger("productId", productId)
                .uniqueResult();

        Date now = new Date();
        if (review == null) {
            review = new Reviews();
            review.setCustomersId(customers.getCustomersId());
            review.setProductsId(productId);
        }
        review.setDateAdded(now);
        review.setCustomersName(customers.getFirstname() + " " + customers.getLastname());
        review.setLastModified(now);
        review.setReviewsRating(rating);
        review.setReviewsText(description);
        review.setApproved((short) 0);

        session.saveOrUpdate(review);
        return true;
    }

    @Override
    public List<Reviews> getReviews(int productId) {
        Session session = sessionFactory.getCurrentSession();
        return session.getNamedQuery("Reviews.findByProductsId").setInteger("productsId", productId).list();
    }

    private String getPriceConditionQuery(String priceRange, String discountRange) {
        StringBuilder pb = new StringBuilder();
        try {
            if (StringUtils.isNotBlank(priceRange)) {
                String[] pr = priceRange.split("-");
                double minPrice = NumberUtils.toDouble(pr[0], 0);
                double maxPrice = NumberUtils.toDouble(pr[1], 0);

                pb.append(" and (o.offerPrice>=" + minPrice + " and o.offerPrice<=" + maxPrice + ") order by offerPrice asc");

            } else if (StringUtils.isNotBlank(discountRange)) {
                String[] dr = discountRange.split("-");
                double minDisc = NumberUtils.toDouble(dr[0], 0);
                double maxDisc = NumberUtils.toDouble(dr[1], 0);

                pb.append(" and  ((o.Price=0 and o.offerPrice=0) || (((o.Price-o.offerPrice)/o.Price)>=" + (minDisc / 100) + " and ((o.Price-o.offerPrice)/o.Price)<=" + (maxDisc / 100) + ")) order by ((o.Price-o.offerPrice)/o.Price) desc");
            }
        } catch (Exception e) {
            log.error("Error in getPriceConditionQuery", e);
        }

        return pb.toString();
    }

    @Override
    public boolean sendPush(int offerId, String offerMsg) {

        String offerStr = offerId + "";
        Session session = sessionFactory.getCurrentSession();
        Query qry = session.createSQLQuery("select pushkey from push_details where status = 1");
        List<String> deviceArray = qry.list();

        if (deviceArray.size() > 0) {
            try {
                Sender sender = new Sender("AIzaSyCdJcFeMEzFRXqPk2bc4XK5D_PMeRlKpMM");
                Message message = new Message.Builder()
                        .collapseKey("message")
                        .timeToLive(3)
                        .delayWhileIdle(true)
                        .addData("orderid", offerStr) //you can get this message on client side app
                        .addData("message", offerMsg)
                        .build();
                //Use this code for multicast messages    
                MulticastResult multicastResult = sender.send(message, deviceArray, 0);
                System.out.println("Message Result: " + multicastResult.toString());//Print multicast message result on console
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void addProductCoupon(ProductCoupon productCoupon) {
        sessionFactory.getCurrentSession().saveOrUpdate(productCoupon);
    }

    @Override
    public ProductCoupon getProductCouponsByOrderId(Integer orderID, Integer prodcutID) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("ProductCoupon.findByOrderIdAndProductId");
        query.setParameter("orderID", orderID);
        query.setParameter("productID", prodcutID);
        return (ProductCoupon) query.uniqueResult();
    }

    @Override
    public boolean isSocialUserExist(String accountkey, String sitename, String email) {
        Criteria crr = sessionFactory.getCurrentSession().createCriteria(Customers.class).add(Restrictions.eq("accountId", accountkey));
        List<Customers> mlist = (List<Customers>) crr.list();
        log.debug("mlist" + mlist.size());
        if (mlist.size() < 1) {
            return false;
        } else {
            return true;
        }

//    Query qry= sessionFactory.getCurrentSession()
//                .createQuery("SELECT count(*) FROM customers c WHERE socialaccountId='"+accountkey+"' and `socialsite`='"+sitename+"'");
//                 int cmrcnt=(int)qry.uniqueResult();
//                 if(cmrcnt<1){
//                     log.error("cmr cnt <1"+cmrcnt);
//                     return false;
//                 }else{
//              log.error(" cnt >1"+cmrcnt);
//
//                     return true;
//                 }
    }

    @Override
    public Customers getSocialUser(String accountkey, String sitename, String email) {
        Criteria crr = sessionFactory.getCurrentSession().createCriteria(Customers.class).add(Restrictions.eq("accountId", accountkey));
        Customers cmr = (Customers) crr.uniqueResult();
        return cmr;

    }

    @Override
    public boolean insertSocialUser(Customers cmr) {
        sessionFactory.getCurrentSession().saveOrUpdate(cmr);
        return true;
    }

}
