package com.wangzunbin.quartz.helper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * ClassName:BooleanToBitConverter  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/14 12:17   <br/>
 */

@Converter
public class BooleanToBitConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return 1 == dbData;
    }
}
