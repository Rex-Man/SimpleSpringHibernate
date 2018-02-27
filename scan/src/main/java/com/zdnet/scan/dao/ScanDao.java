package com.zdnet.scan.dao;



import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.zdnet.scan.dao.common.BaseDao;
import com.zdnet.scan.model.Page;
import com.zdnet.scan.model.Scan;

@Repository
public class ScanDao extends BaseDao<Scan> {

	public Page<Scan> getScanForPage(int currentPage,int pageSize)
	{
		DetachedCriteria detCri = DetachedCriteria.forClass(Scan.class);
		detCri.addOrder(Order.desc("id"));
		return getListForPage(currentPage,pageSize,detCri);
	}
	public boolean getScanByScanCode(String scanCode)
	{
		String hql="FROM Scan scan WHERE scan.scanCode=?";
		Query query=getSessionFactory().getCurrentSession().createQuery(hql);
		Object result=query.setString(0, scanCode).uniqueResult();
		if(result!=null)
		{
			return true;
		}else{
			return false;
		}
	}
	
}
