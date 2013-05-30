package com.gto.aws.service;

import java.util.List;

public interface IGenericDAO<T> {

	public abstract T get(String id);

	public abstract T get(T id);

	public abstract T put(T object);

	public abstract void delete(String id);

	public abstract void delete(T id);

	public abstract T put(String id, T object);
	
	public abstract Iterable<String>  listKeys();

}