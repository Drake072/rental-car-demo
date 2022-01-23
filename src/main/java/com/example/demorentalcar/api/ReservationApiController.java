package com.example.demorentalcar.api;

import com.example.demorentalcar.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class ReservationApiController implements ReservationApi {

    @Autowired
    private ReservationApiService reservationApiService;

    @Override
    public Transaction reserveCar(String userId, Long carId) {
        return reservationApiService.reserveCar(userId, carId);
    }

    @Override
    public void returnCar(String userId, Long carId) {
        reservationApiService.returnCar(userId, carId);
    }
}
