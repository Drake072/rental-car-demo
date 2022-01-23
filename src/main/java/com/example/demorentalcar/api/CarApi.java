package com.example.demorentalcar.api;

import com.example.demorentalcar.model.CarInfoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface CarApi {

    @Operation(summary = "Get cars by status and model", description = "Get cars that matches the status and model parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/car")
    CarInfoList getCarsByStatusAndModel(
            @Parameter(description = "car status", in = ParameterIn.QUERY, example = "IN_STOCK") @RequestParam(name = "status", required = false) String status,
            @Parameter(description = "car model", in = ParameterIn.QUERY, example = "CAMRY") @RequestParam(name = "model", required = false) String model);


    @Operation(summary = "Get all available car model", description = "Get available car models in stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping(path = "/carModel")
    CarInfoList getCarModels();
}
