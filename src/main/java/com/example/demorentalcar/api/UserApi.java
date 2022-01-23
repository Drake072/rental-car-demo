package com.example.demorentalcar.api;

import com.example.demorentalcar.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserApi {

    @Operation(summary = "Register a new user", description = "Register with Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration"),
            @ApiResponse(responseCode = "400", description = "Missing email", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping(path = "/user", consumes = {"application/json"})
    User register(
            @Parameter(description = "New car rental service user", required = true) @Valid @RequestBody User user
    );

    @Operation(summary = "User login", description = "Get user information by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Missing email", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Invalid email", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/user")
    User findByEmail(
            @Parameter(description = "User email", required = true) @RequestHeader(name = "email") String email
    );

    @Operation(summary = "Get user info", description = "Get user information by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Missing email", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Invalid email", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(path = "/user/{userId}")
    User findById(
            @Parameter(description = "User ID", required = true) @PathVariable("userId") String userId
    );

    @Operation(summary = "Delete User", description = "Get user information by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping(path = "/user/{userId}")
    void deleteUserById(
            @Parameter(description = "User ID", required = true) @PathVariable("userId") String id,
            @Parameter(description = "API Key", required = true) @RequestHeader(name = "xx-api_key") String apiKey
    );

    @Operation(summary = "Update User friendly name", description = "Update user account friendly name setting")
    @PatchMapping(path = "/user/{userId}")
    void updateUserFriendlyName(
            @Parameter(description = "New car rental service user", required = true) @Valid @RequestBody User user,
            @Parameter(description = "User ID", required = true) @PathVariable("userId") String userId
    );
}
