/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;

/**
 *
 * @author santosh.r
 */
public class SearchObj {
    public int page= 1;
    public int limit=10;
    public long count;
    public Object data;

    public int start;
    public int end;
    
    
     public int getStart(){
     if(page<1){
         page=1;
     }
     return (page-1)*limit;
    }
    
    public int getEnd(){     
     return getStart()+limit-1;
    }
    
    
    public int getPageEnd(){
       int pageend=0;
        if(count%limit==0){
            pageend = (int)count/limit;
        }else{
           pageend =(int) Math.floor((count/limit)+1);
        }
       
       return pageend;
    }
    /**
     * @return the page
     */
    public SearchObj(int page, int limit, long count, Object data) {
        this.page = page;
        this.limit = limit;
        this.count = count;
        this.data = data;
    }

    public SearchObj() {
        
    }

    
    
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    
}
