/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dao.impl;

import com.cnbitsols.adrobe.dao.AdrobeJdbcDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author santosh.r
 */
@Repository
public class AdrobeJdbcDAOImpl implements AdrobeJdbcDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
}
