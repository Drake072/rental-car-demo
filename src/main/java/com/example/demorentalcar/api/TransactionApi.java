package com.example.demorentalcar.api;

import com.example.demorentalcar.model.TransactionList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransactionApi {

    @Operation(summary = "Get all transactions", description = "Get all transactions of a service customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/transaction")
    TransactionList getAllTransaction(
            @Parameter(description = "User ID", required = true, in = ParameterIn.QUERY) @RequestParam("userId") String userId,
            @Parameter(description = "Transaction state filter", in = ParameterIn.QUERY, example = "OPEN") @RequestParam(value = "state", required = false) String state
    );

    @Operation(summary = "Get a transaction", description = "Get one transaction of a service customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/transaction/{transactionId}")
    TransactionList getTransaction(
            @Parameter(description = "User ID", required = true, in = ParameterIn.QUERY) @RequestParam("userId") String userId,
            @Parameter(description = "Transaction ID", required = true, in = ParameterIn.PATH) @PathVariable String transactionId);

}
