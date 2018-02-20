package com.zdnet.scan.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.zdnet.scan.dao.common.BaseDao;
import com.zdnet.scan.model.Page;
import com.zdnet.scan.model.Scan;

@Repository
public class ScanDao { //extends BaseDao<Scan> {

	public Page<Scan> getScanForPage(int currentPage,int pageSize)
	{
		DetachedCriteria detCri = DetachedCriteria.forClass(Scan.class);
		//return getListForPage(currentPage,pageSize,detCri);
		return null;
		
	}
}
