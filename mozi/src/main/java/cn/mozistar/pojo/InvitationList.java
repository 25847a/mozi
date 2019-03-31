package cn.mozistar.pojo;

/**
 * 邀请关注没有注册时的列表
 * @author Admin
 *
 */
public class InvitationList {
    private Integer id;

	private Integer userId;

	private String phone;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

}