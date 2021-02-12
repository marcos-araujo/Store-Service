package com.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.store.client.ProviderClient;
import com.store.client.TransporterClient;
import com.store.controller.dto.*;
import com.store.model.CompraStatus;
import com.store.model.Purchase;
import com.store.repository.PurchaseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PurchaseService {

    @Autowired
    private ProviderClient providerClient;

    @Autowired
    private TransporterClient transporterClient;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseService.class);

    @HystrixCommand(threadPoolKey = "getByIdThreadPool")
    public Purchase getById(Long id) {
        return purchaseRepository.findById(id).orElse(new Purchase());
    }

    @HystrixCommand(fallbackMethod = "doPurchaseFallback", threadPoolKey = "doPurchaseThreadPool")
    public Purchase doPurchase(PurchaseDTO purchaseDTO) {

        Purchase purchaseDB = new Purchase();
        purchaseDB.setState(CompraStatus.RECEIVED);
        purchaseDB.setDestinationAddress(purchaseDTO.getAddress().toString());
        purchaseRepository.save(purchaseDB);
        purchaseDTO.setPurchaseId(purchaseDB.getId());

        LOG.info("Searching for provider information from {}", purchaseDTO.getAddress().getState());
        InfoProviderDTO info = providerClient.getInfoByState(purchaseDTO.getAddress().getState());

        LOG.info("Making an purchase");
        InfoPurchaseDTO purchase = providerClient.doPurchase(purchaseDTO.getItems());

        purchaseDB.setState(CompraStatus.ORDER_PLACED);
        purchaseDB.setOrderId(purchase.getId());
        purchaseDB.setPreparationTime(purchase.getPreparationTime());

        purchaseRepository.save(purchaseDB);

        InfoDeliveryDTO deliveryDTO = new InfoDeliveryDTO();
        deliveryDTO.setOrderId(purchase.getId());
        deliveryDTO.setDeliveryDate(LocalDate.now().plusDays(purchase.getPreparationTime()));
        deliveryDTO.setOriginAddress(info.getAddress());
        deliveryDTO.setDestinationAddress(purchaseDTO.getAddress().toString());
        VoucherDTO voucher = transporterClient.book(deliveryDTO);

        purchaseDB.setState(CompraStatus.DELIVERY_RESERVATION_MADE);
        purchaseDB.setVoucher(voucher.getNumber());
        purchaseRepository.save(purchaseDB);

        return purchaseDB;

    }

    public Purchase doPurchaseFallback(PurchaseDTO purchaseDTO) {

        if(purchaseDTO.getPurchaseId() != null){
            Purchase compraSalva = purchaseRepository.findById(purchaseDTO.getPurchaseId()).get();
        }

        Purchase purchaseFallback = new Purchase();
        purchaseFallback.setDestinationAddress(purchaseDTO.getAddress().toString());
        return purchaseFallback;

    }

}