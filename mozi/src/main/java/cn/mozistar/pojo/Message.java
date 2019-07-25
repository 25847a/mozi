package cn.mozistar.pojo;

import java.util.Date;
/**
 * 消息中心表
 * @author Administrator
 *
 */
public class Message{
    private Long id;

    private Integer userId;
    
	private Integer observeId;

	private String title;

	private String content;

	private Integer read;

	private Integer type;

	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getObserveId() {
		return observeId;
	}

	public void setObserveId(Integer observeId) {
		this.observeId = observeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


}