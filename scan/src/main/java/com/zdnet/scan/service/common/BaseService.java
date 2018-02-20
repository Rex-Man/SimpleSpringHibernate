package com.zdnet.scan.service.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zdnet.scan.dao.common.BaseDao;



public abstract class BaseService<T> {
	
	public abstract BaseDao<T> getRepository();
	
	@Transactional(propagation=Propagation.REQUIRED) 
	public List<T> saveEntity(Iterable<T> entities) {
		List<T> result = new ArrayList<T>();
		for (Iterator<T> iterator = entities.iterator(); iterator.hasNext();) {
			T entity = iterator.next();
			result.add(saveEntity(entity));
		}
		return result;
	}
	@Transactional(propagation=Propagation.REQUIRED) 
	public T saveEntity(T entity) {
		T result = getRepository().save(entity);
		return result;
	}
	public T getById(Long id)
	{
		return getRepository().get(id);
	}
	@Transactional(propagation=Propagation.REQUIRED) 
	public void deleteEntity(Long id) {
		getRepository().delete(id);
	}
	public List<T> getAll(){
		return getRepository().getAll();
	}
}
