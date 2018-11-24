/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

import com.cnbitsols.adrobe.dto.SearchRelevanceScore;
import java.util.Comparator;

/**
 *
 * @author rajeshk
 */
public class SearchResultComaprator implements Comparator<Object[]>{

    private static final Integer LESS_THAN = -1;
    
    private static final Integer EQUAL_TO = 0;
    
    private static final Integer GREATER_THAN = 1;
    
    @Override
    public int compare(Object[] o1, Object[] o2) {
        SearchRelevanceScore weight1 = (SearchRelevanceScore) o1[2];
        SearchRelevanceScore weight2 = (SearchRelevanceScore) o2[2];
        
        double difference = weight1.getScore() - weight2.getScore();
        
        if (difference < 0)
            return GREATER_THAN;
        else if (difference == 0)
            return EQUAL_TO;
        
        return LESS_THAN;
    }
    
}
