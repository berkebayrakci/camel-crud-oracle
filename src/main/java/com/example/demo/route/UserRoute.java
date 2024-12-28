package com.example.demo.route;

import com.example.demo.service.dtos.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    @Autowired
    private UserService userService;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").contextPath("/camel").apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "1.0");

        rest("/users")
                .post("/add").type(User.class).to("direct:addUser")
                .get("/all").to("direct:getAllUsers")
                .get("/{id}").to("direct:getUserById")
                .delete("/{id}").to("direct:deleteUser")
                .put("/{id}").type(UserDTO.class).to("direct:updateUser");

        from("direct:start")
                .convertBodyTo(UserDTO.class)
                .to("log:converted");

        from("direct:addUser")
                .unmarshal().json(JsonLibrary.Jackson, User.class)
                .bean(userService, "addUser");

        from("direct:getAllUsers")
                .bean(userService, "getAllUsers")
                .marshal().json(JsonLibrary.Jackson);

        from("direct:getUserById")
                .bean(userService, "getUserById(${header.id})")
                .marshal().json(JsonLibrary.Jackson);

        from("direct:deleteUser")
                .bean(userService, "deleteUser(${header.id})");

        from("direct:updateUser")
                .unmarshal().json(JsonLibrary.Jackson, UserDTO.class)
                .bean(userService, "updateUser(${header.id}, ${body})")
                .marshal().json(JsonLibrary.Jackson);
    }
}