package com.zdnet.scan.dao.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.zdnet.scan.model.Page;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;

public class BaseDao<T> {

	//private HibernateTemplate ht = null;
	
	@Resource
    private SessionFactory sessionFactory;
	
    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	
    public SessionFactory getSessionFactory(){
    	return sessionFactory;
    }
    @Resource
    HibernateTemplate hibernateTemplate;
    
    public  HibernateTemplate getHibernateTemplate()   
    {   
    	
//        if (ht == null)   
//        {   
//              ht = new HibernateTemplate(sessionFactory);
//              ht.setFlushMode(FlushMode);
//        }   
        return hibernateTemplate;   
    }   
    public Class<T> getTClass() {  
    	Class<T> classT= (Class<T>) ((ParameterizedType) getClass()  
                .getGenericSuperclass()).getActualTypeArguments()[0];  
    	return classT;
    } 
    
	public T save(T t) {
		   
		   Serializable id=getHibernateTemplate().save(t);
	       return get(id);
	}
	
	public void saveOrUpdate(T t) {
		   getHibernateTemplate().saveOrUpdate(t);
	}
    
	
	public void delete(Serializable id) {
        T t = get(id);
        if (t != null)
        	getHibernateTemplate().delete(t);
        else
            new RuntimeException("No Data").printStackTrace();
    }
	
	public void update(T t) {
		getHibernateTemplate().update(t);
	}
	
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
	        return (T) getHibernateTemplate().get(getTClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
	        return getHibernateTemplate().loadAll(getTClass());
	}
	
	public List findByPage(final String hql, final Object[] values,   
	         final int offset, final int pageSize)   
	    {   
	        List list = (List) getHibernateTemplate().execute(new   
	        HibernateCallback()   
	            {   
	                public Object doInHibernate(Session session)   
	                    throws HibernateException   
	                {   
	                    Query query = session.createQuery(hql);   
	                    for (int i = 0 ; i < values.length ; i++)   
	                    {   
	                        query.setParameter( i, values);   
	                    }   
	                    List result = query.setFirstResult(offset)   
	                                       .setMaxResults(pageSize)   
	                                       .list();   
	                    return result;   
	                }   
	            });   
	        return list;   
	    }  
	
	public List findByPage(final String hql,   
	         final int offset, final int pageSize)   
	    {   
	        
	        List list = (List) getHibernateTemplate().execute(new   
	        HibernateCallback()   
	            {   
	                public Object doInHibernate(Session session)   
	                    throws HibernateException 
	                {   
	                    List result = session.createQuery(hql)   
	                                         .setFirstResult(offset)   
	                                         .setMaxResults(pageSize)   
	                                         .list();   
	                    return result;   
	                }   
	            });   
	        return list;   
	    }  
	protected Page<T> getListForPage(int currentPage, int pageSize,DetachedCriteria detCri) { 
		Page<T> page=new Page<T>();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try {
		        Criteria criteria=detCri.getExecutableCriteria(session);
	            criteria.setProjection(Projections.rowCount());
	            int totalRecord=Integer.valueOf(criteria.uniqueResult().toString());
		       
		       	      
		        criteria.setProjection(null);
		        List<T> results=criteria.setFirstResult((pageSize)*(currentPage-1))
						 .setMaxResults(pageSize)
						 .addOrder(Order.desc("id"))
						 .setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE)
						 .list();
		        page.setCurrentPage(currentPage);
		        page.setPageSize(pageSize);
		        page.setTotalRecord(totalRecord);
		        page.setList(results);
		} catch (Exception e) {
		        e.printStackTrace();
		}
		finally{
		        session.close();		        
		}
		return page;
	}
	protected List<T> getListForCriteria(DetachedCriteria  criteria) { 
		
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}
}
