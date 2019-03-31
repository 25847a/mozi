package com.fadl.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author:wangjing
 * @Description:分页帮助类
 * @Date:2018-04-18
 */
public class PageBean<T> implements Serializable {

    private Integer pageNum;//页码
    private Integer pageSize;//条数
    private List<T> pageList;
    public PageBean(int pageSize){
        this.pageNum=1;
        this.pageSize=pageSize;
    }
    public PageBean(){
        this(10);
    }

}
