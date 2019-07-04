package com.multiledger.backendsdk.network;

import org.hyperledger.fabric.sdk.NetworkConfig;

import java.io.File;

/**
 * Singleton implementation
 * Load the network config from connection profile file.
 */
public class LoadConnectionProfile {

    private static LoadConnectionProfile loadConnectionProfile = null;


    /**
     * Returns the appropriate Network Config YAML file based on whether TLS is currently
     * enabled or not
     *
     * @return The appropriate Network Config YAML file
     */
    public File getTestNetworkConfigFileYAML() {
        String fname = "network-config.yaml";
        String pname = "src/main/resources/network_configs/";
        File ret = new File(pname, fname);
        return ret;
    }


    /**
     * Return class instance
     *
     * @return LoadConnectionProfile
     * @throws Exception
     */
    public static LoadConnectionProfile getInstance() throws Exception {

        if (null == loadConnectionProfile) {
            loadConnectionProfile = new LoadConnectionProfile();
        }

        return loadConnectionProfile;
    }

}

    /**
     * Get Certificate Authority config for an organization
     *
     * @param org
     * @return CAInfo
     * @throws Exception
     */
//    public static NetworkConfig.CAInfo getCaInfo(String org) throws Exception {
//        if (networkConfig == null) {
//            getInstance();
//        }
//        //assuming there is only one ca per organisation
//        return networkConfig.getOrganizationInfo(org).getCertificateAuthorities().get(0);
//    }



    /**
     * Get organization config
     *
     * @param org
     * @return OrgInfo
     * @throws Exception
     */
//    public static NetworkConfig.OrgInfo getOrgInfo(String org) throws Exception {
//        if (networkConfig == null) {
//            getInstance();
//        }
//        return networkConfig.getOrganizationInfo(org);
//    }






    /**
     * Return the complete configuartion info
     *
     * @return NetworkConfig
     * @throws Exception
     */
//    public static NetworkConfig getConfig() throws Exception {
//        if (networkConfig == null) {
//            getInstance();
//        }
//        return networkConfig;
//    }


