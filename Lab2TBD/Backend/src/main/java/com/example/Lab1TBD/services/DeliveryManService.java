package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;
import com.example.Lab1TBD.persistence.repositories.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryManService {

    @Autowired
    private DeliveryManRepository deliveryManRepository;

    // DEFAULT ------------------------------------------------------------------------------------

    public List<DeliveryManEntity> getAllDeliveryMen(){
        return deliveryManRepository.findAllDeliveryMen();
    }

    public DeliveryManEntity getDeliveryManById(Long id) {
        return deliveryManRepository.findDeliveryManById(id);
    }

    public void saveDeliveryMan(Long userId, Long establishmentId) {
        deliveryManRepository.saveDeliveryMan(userId, establishmentId);
    }

    public void updateDeliveryMan(DeliveryManEntity deliveryMan) {
        deliveryManRepository.updateDeliveryMan(deliveryMan);
    }

    public void deleteDeliveryManById(Long id) {
        deliveryManRepository.deleteDeliveryManById(id);
    }

    // SEARCH -------------------------------------------------------------------------------------
    public DeliveryManEntity getDeliveryManByClientId(Long clientId) {
        DeliveryManEntity deliveryMan = deliveryManRepository.findDeliveryManByClientId(clientId);
        if (deliveryMan == null) {
            System.out.println("No se encontró un DeliveryMan para client_id: " + clientId);
        } else {
            System.out.println("DeliveryMan encontrado: " + deliveryMan);
        }
        return deliveryMan;
    }


    public List<DeliveryManEntity> getDeliveryMenByEstablishmentId(Long establishmentId){
        return deliveryManRepository.findDeliveryManByEstablishmentId(establishmentId);
    }
}
