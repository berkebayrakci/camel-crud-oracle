package com.example.demo.route;

import com.example.demo.service.dtos.InventoryDTO;
import com.example.demo.entity.Inventory;
import com.example.demo.service.InventoryService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRoute extends RouteBuilder {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").contextPath("/camel").apiContextPath("/api-doc")
                .apiProperty("api.title", "Inventory API").apiProperty("api.version", "1.0");

        rest("/inventories")
                .post("/add").type(InventoryDTO.class).to("direct:addInventory")
                .get("/all").to("direct:getAllInventories")
                .get("/{id}").to("direct:getInventoryById")
                .delete("/{id}").to("direct:deleteInventory")
                .put("/{id}").type(InventoryDTO.class).to("direct:updateInventory");

        from("direct:addInventory")
                .unmarshal().json(JsonLibrary.Jackson, InventoryDTO.class)
                .bean(inventoryService, "addInventory");

        from("direct:getAllInventories")
                .bean(inventoryService, "getAllInventories")
                .marshal().json(JsonLibrary.Jackson);

        from("direct:getInventoryById")
                .bean(inventoryService, "getInventoryById(${header.id})")
                .marshal().json(JsonLibrary.Jackson);

        from("direct:deleteInventory")
                .bean(inventoryService, "deleteInventory(${header.id})");

        from("direct:updateInventory")
                .unmarshal().json(JsonLibrary.Jackson, InventoryDTO.class)
                .bean(inventoryService, "updateInventory(${header.id}, ${body})")
                .marshal().json(JsonLibrary.Jackson);
    }
}