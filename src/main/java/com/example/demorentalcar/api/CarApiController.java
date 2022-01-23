package com.example.demorentalcar.api;

import com.example.demorentalcar.model.CarInfoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "v1")
@RestController
public class CarApiController implements CarApi{

    @Autowired
    private CarApiService carApiService;

    @Override
    public CarInfoList getCarsByStatusAndModel(String status, String model) {
        return new CarInfoList(carApiService.getCars(status, model), null);
    }

    @Override
    public CarInfoList getCarModels() {
        return new CarInfoList(null, carApiService.getCarModels());
    }
}
