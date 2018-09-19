package com.cat.module.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EntitiesResponse<T> extends BaseResponse {

	private static final long serialVersionUID = 1L;

	private Entities<T> data;

	public Entities<T> getData() {
		return data;
	}

	public void setData(Entities<T> data) {
		this.data = data;
	}

	@JsonIgnore
	public List<T> getEnties() {
		return data == null ? null : data.getEntities();
	}

	public EntitiesResponse() {
		super();
	}

	public EntitiesResponse(List<T> entities) {
		super();
		data = new Entities<>();
		data.setEntities(entities);
	}

	public static class Entities<K> {

		private List<K> entities;

		public List<K> getEntities() {
			return entities;
		}

		public void setEntities(List<K> entities) {
			this.entities = entities;
		}

	}
}
