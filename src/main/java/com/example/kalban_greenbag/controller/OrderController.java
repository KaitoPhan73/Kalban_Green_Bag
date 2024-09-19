package com.example.kalban_greenbag.controller;

import com.example.kalban_greenbag.constant.ConstAPI;
import com.example.kalban_greenbag.dto.request.order.AddOrderRequest;
import com.example.kalban_greenbag.dto.request.order.UpdateOrderRequest;
import com.example.kalban_greenbag.dto.response.order.OrderResponse;
import com.example.kalban_greenbag.exception.BaseException;
import com.example.kalban_greenbag.model.PagingModel;
import com.example.kalban_greenbag.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@Slf4j
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Operation(summary = "Create order", description = "API create order")
    @PostMapping(value = ConstAPI.OrderAPI.CREATE_ORDER)
    public OrderResponse createOrder(@Valid @RequestBody AddOrderRequest addOrderRequest) throws BaseException {
        return orderService.create(addOrderRequest);
    }

    @Operation(summary = "Update order", description = "API update order")
    @PatchMapping(value = ConstAPI.OrderAPI.UPDATE_ORDER)
    public OrderResponse updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrderRequest) throws BaseException {
        return orderService.update(updateOrderRequest);
    }

    @Operation(summary = "Change order status", description = "API change order status")
    @DeleteMapping(value = ConstAPI.OrderAPI.CHANGE_ORDER_STATUS + "{id}")
    public Boolean changeOrderStatus(@PathVariable("id") UUID id) throws BaseException {
        return orderService.changeStatus(id);
    }

    @Operation(summary = "Find order by ID", description = "API find order by ID")
    @GetMapping(value = ConstAPI.OrderAPI.GET_ORDER_BY_ID + "{id}")
    public OrderResponse findOrderById(@PathVariable("id") UUID id) throws BaseException {
        return orderService.findById(id);
    }

    @Operation(summary = "Get all orders", description = "API get all orders with pagination")
    @GetMapping(value = ConstAPI.OrderAPI.GET_ALL_ORDERS)
    public PagingModel<OrderResponse> getAllOrders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) throws BaseException {
        return orderService.getAll(page, limit);
    }

    @Operation(summary = "Get all active orders", description = "API get all active orders (status = true) with pagination")
    @GetMapping(value = ConstAPI.OrderAPI.GET_ALL_ACTIVE_ORDERS)
    public PagingModel<OrderResponse> getAllActiveOrders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) throws BaseException {
        return orderService.findAllByStatusTrue(page, limit);
    }
}