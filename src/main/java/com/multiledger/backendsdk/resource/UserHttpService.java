package com.multiledger.backendsdk.resource;


import com.multiledger.backendsdk.network.LoadConnectionProfile;
import com.multiledger.backendsdk.document.UserCredential;

import com.multiledger.backendsdk.network.ReadFile;
import com.multiledger.backendsdk.repository.UserRepository;
import io.swagger.annotations.Api;


import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric.sdk.NetworkConfig;


import java.util.*;


public class UserHttpService {

    private static final Logger logger = LoggerFactory.getLogger(UserHttpService.class);
    private static final Integer lock = 0;
    private HFCAClient hfcaClient = null;
    private static NetworkConfig networkConfig;


    UserRepository userRepository;

    UserCredential userCredential = new UserCredential();

    LinkedHashMap<String, UserCredential> userStore = new LinkedHashMap<>();

    HFCAClient getHfcaClient(String org) {
        try {
            this.hfcaClient = HFCAClient.createNewInstance(LoadConnectionProfile.getCaInfo(org));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hfcaClient;
    }




    /**
     * 1. Enroll admin given username and secret (registrar). Read from yaml file
     * 2. Register user given username, affiliation, organization
     * 3. Enroll user given username and admin registrar
     *
     *
     *
      */




    /**
     * Enroll the admin. This admin will be used as a registrar to register other users.
     *
     * @throws Exception
     */
    @PostMapping(path = "/enrollAdmin", consumes = "application/json", produces = "application/json")
    public ResponseEntity enrollAdmin(@RequestBody UserCredential ucr) throws Exception {

        userCredential.setName(ucr.getName());
        userCredential.setPassword(ucr.getPassword());
        userCredential.setAffiliation(ucr.getAffiliation());

        if (userStore.containsKey(userCredential.getAffiliation())) {
            // username already exist
            logger.info("Admin is already enrolled. Therefore skipping...admin enrollment");
            return new ResponseEntity("Admin is already enrolled.", HttpStatus.ALREADY_REPORTED);
        } else {
            userStore.put(userCredential.getAffiliation(), userCredential);
        }

        //HFCA Client makes an enrol request to ca server.
        HFCAClient hfcaClient = getHfcaClient(userCredential.getAffiliation());
        Enrollment enrollment = hfcaClient.enroll(userCredential.getName(), userCredential.getPassword());
        userCredential.setEnrollment(enrollment);
        userRepository.save(userCredential);

        logger.info("Admin enrolled successfully");
        return new ResponseEntity("Admin enrolled successflly", HttpStatus.CREATED);

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

    @PostMapping(path= "/registerUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity registerUser(@RequestBody UserCredential ucr) throws Exception {

        userCredential.setName(ucr.getName());


        JSONObject response = new JSONObject();
        System.out.println("Input: " + request);
        try {
            CAUtility.enrollUser(request.getUserName(),
                    request.getEnrollmentSecret(), request.getUserOrg());
        } catch (EnrollmentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.put("message", e.getMessage());
            return Response.status(500).entity(response).build();
        } catch (InvalidArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.put("message", e.getMessage());
            return Response.status(500).entity(response).build();
        }
        response.put("message", "Enrolled Successfully");
        return Response.status(201).entity(response).build();

    }

}
