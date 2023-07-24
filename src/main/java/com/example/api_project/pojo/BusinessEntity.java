package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 业务主体(BusinessEntity)实体类
 *
 */
public class BusinessEntity implements Serializable {
    private static final long serialVersionUID = -74513736341616305L;
    /**
     * 业务主体编码
     */
    private String entityCode;
    /**
     * 业务主体名称
     */
    private String entityName;


    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

}

