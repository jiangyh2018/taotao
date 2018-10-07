package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**响应的json数据格式EasyUIResult
 * @author hzf
 *
 */
public class EasyUIDataGridResult implements Serializable{

	private Integer total;

	private List<?> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> list) {
		this.rows = list;
	}

	public EasyUIDataGridResult(Integer total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public EasyUIDataGridResult(Long total, List<?> rows) {
		this.total = total.intValue();
		this.rows = rows;
	}

	public EasyUIDataGridResult() {
	}

}
