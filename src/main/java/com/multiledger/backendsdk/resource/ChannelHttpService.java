package com.multiledger.backendsdk.resource;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;




//@RestController
//@RequestMapping(path = "/fabric/channelservice")
public class ChannelHttpService {

//    @PostMapping(path= "/channel", consumes = "application/json", produces = "application/json")
//    public Response createChannel(CreateChannelRequestData request) {
//
//        JSONObject response = new JSONObject();
//        String channelName = "";
//        System.out.println("Input " + request);
//        try {
//
//            channelName = ChannelUtility.createChannel(
//                    request.getChannelName(), request.getChannelPath(),
//                    request.getOrgName());
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response.put("message", e.getMessage());
//            return Response.status(500).entity(response).build();
//        }
//        response.put("message", "Channel" + " " + channelName + " "
//                + "created Successfully");
//        return Response.status(201).entity(response).build();
//    }
//
//
//
//
//    @PostMapping(path= "/channel/{channelname}/peers", consumes = "application/json", produces = "application/json")
//    public Response joinChannel(@PathParam("channelname") String channelname,
//                                JoinChannelRequestData request) {
//        JSONObject response = new JSONObject();
//        boolean flag = false;
//        try {
//            flag = ChannelUtility.joinChannel(channelname, request.getPeers(),
//                    request.getUserOrg());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response.put("message", e.getMessage());
//            return Response.status(500).entity(response).build();
//        }
//        if (flag) {
//
//            response.put("message", "peer joined channel successfully");
//        } else {
//            response.put("message", "peer joined channel failed");
//        }
//
//        return Response.status(201).entity(response).build();
//
//    }


}
