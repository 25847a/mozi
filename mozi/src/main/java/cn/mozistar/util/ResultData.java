package cn.mozistar.util;

/**
 * 执行结果
 * 
 * @author 帝血弑天——DDM——
 *
 */
public class ResultData<T> extends ResultBase {

	private Object data;// 数据

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
