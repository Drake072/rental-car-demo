package com.example.demorentalcar.api;

import com.example.demorentalcar.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReservationApi {

    @Operation(summary = "Reserve a car", description = "Reserve a car by carId, open a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Car not available", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(path = "/reservation/car/{carId}")
    Transaction reserveCar(
            @Parameter(description = "user id", in = ParameterIn.QUERY) @RequestParam(name = "userId") String userId,
            @Parameter(description = "car id") @PathVariable Long carId);

    @Operation(summary = "Return a car", description = "Return a car by carId, close a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = {@Content(mediaType = "application/json")}),
    })
    @PatchMapping(path = "/reservation/car/{carId}")
    void returnCar(
            @Parameter(description = "user id", in = ParameterIn.QUERY) @RequestParam(name = "userId") String userId,
            @Parameter(description = "car id") @PathVariable Long carId
    );

}
