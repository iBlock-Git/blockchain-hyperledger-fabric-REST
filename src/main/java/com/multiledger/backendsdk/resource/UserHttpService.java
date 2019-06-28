package com.multiledger.backendsdk.resource;


import com.multiledger.backendsdk.document.UserContext;
import com.multiledger.backendsdk.network.LoadConnectionProfile;

import com.multiledger.backendsdk.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONObject;
import javax.ws.rs.core.Response;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.util.*;

@RestController
@RequestMapping(path = "/fabric/userservice")
@Api(value = "User Resource REST Endpoint", description = "Shows the user info")
public class UserHttpService {

    private static final Integer lock = 0;
    private HFCAClient hfcaClient=null;
    UserRepository userRepository;

    LinkedHashMap<String, UserContext> userStore = new LinkedHashMap<>();

    HFCAClient getHfcaClient(String org) {
        try {
            this.hfcaClient = HFCAClient.createNewInstance(LoadConnectionProfile.getCaInfo(org));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hfcaClient;
    }


    /**
     * Enroll the admin. This admin will be used as a registrar to register other users.
     *
     * @param name   - admin name
     * @param secret - admin secret
     * @return adminContext
     * @throws Exception
     */
    @PostMapping(path= "/enrollAdmin", consumes = "application/json", produces = "application/json")
    public ResponseEntity enrollAdmin(String name, String secret) throws Exception  {

       // System.out.println("Input" + request);

        JSONObject response = new JSONObject();


        //HFCA Client makes an enrol request to ca server.
        HFCAClient hfcaClient = getHfcaClient("Org1");
        Enrollment enrollment = hfcaClient.enroll(name, secret);

        userStore.put("Org1", new UserContext(name,LoadConnectionProfile.getOrgInfo("Org1").getName(),
                LoadConnectionProfile.getOrgInfo("Org1").getMspId()));


        userRepository.save(new UserContext(enrollment));


        return new ResponseEntity(name + " enrolled successfully", HttpStatus.OK);
    }

    /**
     * Register and enroll the user with organization MSP provider. User context saved in  /cred directory.
     * This is an admin function; admin should be enrolled before enrolling a user.
     *
     * @param userName
     * @param registrarAdmin - network admin
     * @return UserContext
     * @throws Exception
     */

//    @PostMapping(path= "/registerUser", consumes = "application/json", produces = "application/json")
//    public Response registerUser(EnrollRequestData request) {
//
//        JSONObject response = new JSONObject();
//        System.out.println("Input: " + request);
//        try {
//            CAUtility.enrollUser(request.getUserName(),
//                    request.getEnrollmentSecret(), request.getUserOrg());
//        } catch (EnrollmentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response.put("message", e.getMessage());
//            return Response.status(500).entity(response).build();
//        } catch (InvalidArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response.put("message", e.getMessage());
//            return Response.status(500).entity(response).build();
//        }
//        response.put("message", "Enrolled Successfully");
//        return Response.status(201).entity(response).build();
//
//    }

}
