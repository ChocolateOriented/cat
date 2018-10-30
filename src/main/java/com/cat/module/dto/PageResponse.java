package com.cat.module.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageResponse<T> extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Page<T> data;

	public PageResponse() {
		
	}

	public PageResponse(List<T> entities, int pageNum, int pageSize, long total) {
		this.data = new Page<T>();
		data.setEntities(entities);
		data.setPageNum(pageNum);
		data.setPageSize(pageSize);
		data.setTotal(total);
	}

	public Page<T> getData() {
		return data;
	}

	public void setData(Page<T> data) {
		this.data = data;
	}

	@JsonIgnore
	public List<T> getEntities() {
		return data == null ? null : data.getEntities();
	}

	public void setEntities(List<T> entities) {
		if (data == null) {
			data = new Page<T>();
		}
		
		data.setEntities(entities);
	}

	@JsonIgnore
	public Integer getPageNum() {
		return data == null ? null : data.getPageNum();
	}

	public void setPageNum(int pageNum) {
		if (data == null) {
			data = new Page<T>();
		}
		
		data.setPageNum(pageNum);
	}

	@JsonIgnore
	public Integer getPageSize() {
		return data == null ? null : data.getPageSize();
	}

	public void setPageSize(int pageSize) {
		if (data == null) {
			data = new Page<T>();
		}
		
		data.setPageSize(pageSize);
	}

	@JsonIgnore
	public Long getTotal() {
		return data == null ? null : data.getTotal();
	}

	public void setTotal(long total) {
		if (data == null) {
			data = new Page<T>();
		}
		
		data.setTotal(total);
	}

	public static class Page<K> {
		
		private List<K> entities;

		private Integer pageNum;

		private Integer pageSize;

		private Long total;

		public List<K> getEntities() {
			return entities;
		}

		public void setEntities(List<K> entities) {
			this.entities = entities;
		}

		public Integer getPageNum() {
			return pageNum;
		}

		public void setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

		public boolean isEmpty() {
			return total == 0 || entities == null || entities.isEmpty();
		}

		/**
		 * equals {@link Page#setEntities(List)}
		 * <p>just use for jackson unserialize</p>
		 * @param entities
		 */
		public void setResults(List<K> entities) {
			this.entities = entities;
		}

		/**
		 * equals {@link Page#setPageNum(Integer)}
		 * <p>just use for jackson unserialize</p>
		 * @param entities
		 */
		public void setPage(Integer pageNum) {
			this.pageNum = pageNum;
		}

		/**
		 * equals {@link Page#setPageSize(Integer)}
		 * <p>just use for jackson unserialize</p>
		 * @param entities
		 */
		public void setPagesize(Integer pageSize) {
			this.pageSize = pageSize;
		}
	}

}
