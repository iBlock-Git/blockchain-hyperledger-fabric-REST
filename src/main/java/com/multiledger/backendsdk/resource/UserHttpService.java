package com.multiledger.backendsdk.resource;


import com.multiledger.backendsdk.network.LoadConnectionProfile;
import com.multiledger.backendsdk.document.UserCredential;

import com.multiledger.backendsdk.repository.UserRepository;


import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric_ca.sdk.HFCAInfo;
import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;


import java.io.IOException;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.util.*;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserHttpService {

    private static final Logger logger = LoggerFactory.getLogger(UserHttpService.class);
    private static final Integer lock = 0;
    private HFCAClient hfcaClient1 = null;
    private HFCAClient hfcaClient = null;

    private static NetworkConfig networkConfig = null;

    UserRepository userRepository;
    HashMap<String, UserCredential> userStore = new HashMap<>();



    /**
     * Enroll the admin. This admin will be used as a registrar to register other users.
     *
     * @throws Exception
     */
    @PostMapping(path = "/enrollAdmin", consumes = "application/json", produces = "application/json")
    public ResponseEntity enrollAdmin(@RequestBody UserCredential ucr) throws Exception {


        File yamlConnectionFile = LoadConnectionProfile.getInstance().getTestNetworkConfigFileYAML();
        networkConfig = NetworkConfig.fromYamlFile(yamlConnectionFile);

        NetworkConfig.OrgInfo org = networkConfig.getOrganizationInfo("Org1");
        NetworkConfig.CAInfo caInfo = org.getCertificateAuthorities().get(0);

        hfcaClient = HFCAClient.createNewInstance(caInfo);

        //assertEquals(hfcaClient.getCAName(), caInfo.getCAName());
        //HFCAInfo info = hfcaClient.info(); //makes actual REST call.
        //assertEquals(caInfo.getCAName(), info.getCAName());

        Collection<NetworkConfig.UserInfo> registrars = caInfo.getRegistrars();
        // assertTrue(!registrars.isEmpty());


        NetworkConfig.UserInfo registrar = registrars.iterator().next();
        String admin = registrar.getName();
        registrar.setEnrollment(hfcaClient.enroll(admin, registrar.getEnrollSecret()));
        final String tlsKeyPEM = getPEMStringFromPrivateKey(registrar.getEnrollment().getKey());
        final String tlsCertPEM = registrar.getEnrollment().getCert();


        userRepository.save(new UserCredential(1, admin, tlsKeyPEM, tlsCertPEM));


        logger.info("Admin enrolled successfully");
        return new ResponseEntity(admin + " as network administartor enrolled successflly", HttpStatus.CREATED);

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
//    public ResponseEntity registerUser(@RequestBody UserCredential ucr) throws Exception {
//
//        userCredential.setName(ucr.getName());
//
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






    // Convert PrivateKey to String
    static String getPEMStringFromPrivateKey(PrivateKey privateKey) throws IOException {
        StringWriter pemStrWriter = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(pemStrWriter);

        pemWriter.writeObject(privateKey);

        pemWriter.close();

        return pemStrWriter.toString();
    }
}
