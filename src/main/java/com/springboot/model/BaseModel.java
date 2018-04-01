package com.springboot.model;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 22:28 2018/3/27
 * @Modified By:
 */
@MappedSuperclass
public class BaseModel {

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
