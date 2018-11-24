/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

/**
 *
 * @author santosh.r
 */
public class CurrencySerializer extends SerializerBase<BigDecimal> {

public CurrencySerializer() {
    super(BigDecimal.class, true);
}

@Override
public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException {
    NumberFormat f = new DecimalFormat("Rs ###,##0");
    String format = f.format(value);
    jgen.writeString(format);
}

}