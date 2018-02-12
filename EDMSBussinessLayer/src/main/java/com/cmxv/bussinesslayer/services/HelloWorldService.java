package com.cmxv.bussinesslayer.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.RoleService;

@Path("/hello")
@Component
public class HelloWorldService {

    {
        System.out.println("init helloworld service");
    }
    private @Autowired
    RoleService roleService;

    @GET
    @Path("/{param}")

    public Response getMsg(@PathParam("param") String msg) {
        System.out.println(roleService);
        String output = "Jersey say : " + msg;
        String name = roleService.getRoleById(1).getRoleName();
        return Response.status(200).entity(output + " " + name).build();

    }

}
