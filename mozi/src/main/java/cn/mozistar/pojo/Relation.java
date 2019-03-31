package cn.mozistar.pojo;

/**
 * 用户关系表
 * @author Admin
 *
 */
public class Relation {
	
    private Integer id;

    //我自己的id
    private Integer userId;

    //我关注的用户的id
    private Integer observeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getObserveId() {
        return observeId;
    }

    public void setObserveId(Integer observeId) {
        this.observeId = observeId;
    }
}