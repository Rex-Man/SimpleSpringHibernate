package com.zdnet.scan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdnet.scan.controller.common.ResponseEntity;
import com.zdnet.scan.model.Page;
import com.zdnet.scan.model.Scan;
import com.zdnet.scan.service.ScanService;

@Controller
@RequestMapping("scanController")
public class ScanController {

	@Resource
	private ScanService scanService;
	
	@RequestMapping(value="getScanList",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllScanListByPage(int currentPage, int pageSize)
	{
		ResponseEntity responseEntity=new ResponseEntity();
		try{
		      Page<Scan> pageScan=scanService.getScanForPage(currentPage, pageSize);
		      responseEntity.setData(pageScan);
		}
		catch(Exception e)
		{
			responseEntity.setData("fail");
			responseEntity.setErrorCode("500");
			responseEntity.setErrorMessage(e.getMessage());
		}
		return responseEntity;
	}
	@RequestMapping(value="saveScan",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity saveScanInfo(Scan scan)
	{
		ResponseEntity responseEntity=new ResponseEntity();
		try{
		   //Scan scanSaved=scanService.saveEntity(scan);
		   responseEntity.setData("success");
		}catch(Exception e)
		{
			responseEntity.setData("fail");
			responseEntity.setErrorCode("500");
			responseEntity.setErrorMessage(e.getMessage());
		}
		return responseEntity;
		
	
	}
}
