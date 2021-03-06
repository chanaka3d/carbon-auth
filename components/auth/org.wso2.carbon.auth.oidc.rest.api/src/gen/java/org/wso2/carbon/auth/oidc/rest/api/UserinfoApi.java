package org.wso2.carbon.auth.oidc.rest.api;


import io.swagger.annotations.ApiParam;

import org.wso2.carbon.auth.oidc.rest.api.dto.ErrorDTO;
import org.wso2.carbon.auth.oidc.rest.api.factories.UserinfoApiServiceFactory;

import org.wso2.msf4j.Microservice;
import org.wso2.msf4j.Request;
import org.wso2.msf4j.formparam.FileInfo;
import org.wso2.msf4j.formparam.FormDataParam;
import org.osgi.service.component.annotations.Component;

import java.io.InputStream;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Component(
    name = "org.wso2.carbon.auth.oidc.rest.api.UserinfoApi",
    service = Microservice.class,
    immediate = true
)
@Path("/api/auth/oidc/v1.[\\d]+/userinfo")


@ApplicationPath("/userinfo")
@io.swagger.annotations.Api(description = "the userinfo API")
public class UserinfoApi implements Microservice  {
   private final UserinfoApiService delegate = UserinfoApiServiceFactory.getUserinfoApi();

    @OPTIONS
    @GET
    
    @Consumes({ "application/x-www-form-urlencoded" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = void.class, tags={ "OAuth Userinfo", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. Successful response from userinfo endpoint. ", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized. Error response from userinfo endpoint due to client authentication failure. ", response = void.class) })
    public Response userinfoGet(@ApiParam(value = "Authentication scheme header" ,required=true)@HeaderParam("Authorization") String authorization
 ,@Context Request request)
    throws NotFoundException {
        
        return delegate.userinfoGet(authorization,request);
    }
}
