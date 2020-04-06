package com.example.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentClient {

	private DataWarehouseService dataWareHouseService;

	@Autowired
	public PaymentClient(DataWarehouseService dataWareHouseService) {
		this.dataWareHouseService = dataWareHouseService;
	}

	public PaymentStatus createPayment() {
		dataWareHouseService.callDataWareHouse();
		return PaymentStatus.SUCCESS;
	}

}
