package com.sovos.status.pending.service;

import java.util.List;

import com.sovos.status.pending.models.ReceiptStatusPending;

public interface IReceiptStatusPendingService {
	
	public List<ReceiptStatusPending> executeProcess(Integer companyId);

}
