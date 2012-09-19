package com.wereach.vi.model;

import java.io.Serializable;

public abstract class ManagedEntity implements Serializable{
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
