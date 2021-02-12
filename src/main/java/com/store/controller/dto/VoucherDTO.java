package com.store.controller.dto;

import java.time.LocalDate;

public class VoucherDTO {

    private Long number;

    private LocalDate deliveryEstimation;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public LocalDate getDeliveryEstimation() {
		return deliveryEstimation;
	}

	public void setDeliveryEstimation(LocalDate deliveryEstimation) {
		this.deliveryEstimation = deliveryEstimation;
	}

}