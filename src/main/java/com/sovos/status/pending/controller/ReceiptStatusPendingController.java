package com.sovos.status.pending.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovos.status.pending.models.ReceiptStatusPending;
import com.sovos.status.pending.service.impl.ReceiptStatusPendingServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/app/v1")
public class ReceiptStatusPendingController {
	
	@Autowired
	private ReceiptStatusPendingServiceImpl service;
	
	@ApiOperation(value = "Generate the process to set status to receipts with status pending by companyId",
			notes = "Find the documents with status pending on receiptstatuspending table",
			tags = { "process/by-companyId" })
	@PutMapping(path = "/process/{companyId}")
	public List<ReceiptStatusPending> updatingProcessByCompanyId(@PathVariable("companyId") Integer companyId){
		
		List<ReceiptStatusPending> list = service.executeProcess(companyId);
		
		return list;
	}
	
	@ApiOperation(value = "Generate the process to set status to receipts with status pending to all companies",
			notes = "Find the documents with status pending on receiptstatuspending table",
			tags = { "process/all-companies" })
	@PutMapping(path = "/process/all-companies")
	public List<ReceiptStatusPending> updatingProcessByAllCompanies(){
		
		List<ReceiptStatusPending> list = service.executeProcess(null);
		
		return list;
	}

}
