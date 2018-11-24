/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dao.AdminHibernateDAO;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.services.OffersScheduler;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rajeshk
 */
@Service("offersSchedulerImpl")
public class OffersSchedulerImpl implements OffersScheduler {

    @Autowired
    private AdminHibernateDAO hdao = null;

    public OffersSchedulerImpl() {
        System.out.println("****com.cnbitsols.adrobe.services.impl.OffersSchedulerImpl.<init>()****");
    }
    
    @Override
    public void schedulePremiumOffers() {
        try {
            deactivatePremiumOffers();
            activatePremiumOffers();
        } catch (ParseException ex) {
            Logger.getLogger(OffersSchedulerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void activatePremiumOffers() throws ParseException {
        List<PremiumOffers> unactivatedPremiumOffersList = hdao.getPremiumOffersByScheduleStatus(1);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        for (PremiumOffers premiumOffer : unactivatedPremiumOffersList) {
            Date startDate = formatter.parse(premiumOffer.getStartDate());
            
            if (startDate.equals(now) || startDate.after(now)) {
                premiumOffer.setScheduleStatus(2);
                premiumOffer.setStatus(1);
                hdao.savePremiumOffer(premiumOffer);
            }
        }
    }
    
    private void deactivatePremiumOffers() throws ParseException {
        List<PremiumOffers> unactivatedPremiumOffersList = hdao.getPremiumOffersByScheduleStatus(2);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        for (PremiumOffers premiumOffer : unactivatedPremiumOffersList) {
            Date endDate = formatter.parse(premiumOffer.getEndDate());
            
            if (endDate.equals(now) || now.after(endDate)) {
                premiumOffer.setScheduleStatus(0);
                premiumOffer.setStatus(0);
                hdao.savePremiumOffer(premiumOffer);
            }
        }
    }

    @Override
    public void scheduleExclusiveOffers() {
        try {
            activateExclusiveOffers();
            deactivateExclusiveOffers();
        } catch (ParseException ex) {
            Logger.getLogger(OffersSchedulerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void activateExclusiveOffers() throws ParseException {
        List<Offers> exclusiveOffers = hdao.getExclusiveOffersByScheduleStatus(1);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        
        for (Offers offer : exclusiveOffers) {
            Date startDate = formatter.parse(offer.getStartDate());
            
            if (startDate.equals(now) || startDate.after(now)) {
                offer.setScheduleStatus(2);
                offer.setStatus(1);
                hdao.saveOffer(offer);
            }
        }
    }
    
    private void deactivateExclusiveOffers() throws ParseException {
        List<Offers> exclusiveOffers = hdao.getExclusiveOffersByScheduleStatus(2);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        
        for (Offers offer : exclusiveOffers) {
            Date endDate = formatter.parse(offer.getEndDate());
            
            if (endDate.equals(now) || now.after(endDate)) {
                offer.setScheduleStatus(0);
                offer.setStatus(0);
                hdao.saveOffer(offer);
            }
        }
    }

    @Override
    public void scheduleTodaysOffers() {
        try {
            activateTodaysOffers();
            deactivateTodaysOffers();
        } catch (ParseException ex) {
            Logger.getLogger(OffersSchedulerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void activateTodaysOffers() throws ParseException {
        List<Offers> todaysOffers = hdao.getTodaysOffersByScheduleStatus(1);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        
        for (Offers offer : todaysOffers) {
            Date startDate = formatter.parse(offer.getStartDate());
            
            if (startDate.equals(now) || startDate.after(now)) {
                offer.setScheduleStatus(2);
                offer.setStatus(1);
                hdao.saveOffer(offer);
            }
        }
    }
    
    private void deactivateTodaysOffers() throws ParseException {
        List<Offers> todaysOffers = hdao.getTodaysOffersByScheduleStatus(2);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String nowString = formatter.format(new Date());
        Date now = formatter.parse(nowString);
        
        for (Offers offer : todaysOffers) {
            Date endDate = formatter.parse(offer.getEndDate());
            
            if (endDate.equals(now) || now.after(endDate)) {
                offer.setScheduleStatus(0);
                offer.setStatus(0);
                hdao.saveOffer(offer);
            }
        }
    }
    
}
