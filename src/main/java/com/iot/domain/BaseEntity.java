package com.iot.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1L;
    private Long id;
    private Date addTime;
    private Date updateTime;
    private String addUser;
    private String updateUser;

}
