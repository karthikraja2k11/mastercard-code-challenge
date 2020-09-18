package io.swagger.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-18T13:22:20.760Z[GMT]")
@Controller
public class ConnectedApiController implements ConnectedApi {

    private static final Logger logger = LoggerFactory.getLogger(ConnectedApiController.class);

    @Autowired
    private ConnectedService connectedService;
    
    public ResponseEntity<String> connected(
    		@NotNull @ApiParam(value = "Origin place to check the connection route from", required = true) 
    			@Valid @RequestParam(value = "origin", required = true) String origin,
    		@NotNull @ApiParam(value = "Destination place to check the connection route to", required = true) 
    			@Valid @RequestParam(value = "destination", required = true) String destination) {
    	    	
    	String connected = "no";
    	try {
    		boolean isConnected = connectedService.areConnected(origin, destination);
    		if(isConnected) {
    			connected = "yes";
    		}
    	} catch (Exception ex) {
    		logger.error(ex.getMessage(), ex);
    	}
    	return new ResponseEntity<String>(connected, HttpStatus.OK);
    }

}
