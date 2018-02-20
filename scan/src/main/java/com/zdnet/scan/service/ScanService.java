package com.zdnet.scan.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zdnet.scan.dao.ScanDao;
import com.zdnet.scan.dao.common.BaseDao;
import com.zdnet.scan.model.Page;
import com.zdnet.scan.model.Scan;
import com.zdnet.scan.service.common.BaseService;

@Service
public class ScanService{ //extends BaseService<Scan>{

	@Resource
	ScanDao scanDao;
//	@Override
//	public BaseDao<Scan> getRepository() {
//		// TODO Auto-generated method stub
//		return scanDao;
//	}
	
	public Page<Scan> getScanForPage(int currentPage,int pageSize)
	{
		return scanDao.getScanForPage(currentPage, pageSize);
	}
	

}
