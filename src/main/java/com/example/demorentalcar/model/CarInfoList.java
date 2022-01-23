package com.example.demorentalcar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInfoList {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Car> cars;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> models;

}
