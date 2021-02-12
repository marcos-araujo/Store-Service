package com.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.store.controller.dto.InfoProviderDTO;
import com.store.controller.dto.InfoPurchaseDTO;
import com.store.controller.dto.PurchaseItemDTO;

import java.util.List;

@FeignClient("provider")
public interface ProviderClient {

	@RequestMapping("/info/{state}")
 	public InfoProviderDTO getInfoByState(@PathVariable String state);

	@RequestMapping(method = RequestMethod.POST, value = "/purchase")
	public InfoPurchaseDTO doPurchase(List<PurchaseItemDTO> items);

}