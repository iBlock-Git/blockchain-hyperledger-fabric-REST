package com.multiledger.backendsdk.network;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * Read Yaml file - Connection Profile
 */
public class ReadFile {

    private static ReadFile readFile;

    private ReadFile(){}

    /**
     * getReadFileObject return back singleton for SDK configuration.
     *
     * @return Global configuration
     */
    public static ReadFile getReadFileObject() {
        if (null == readFile) {
            readFile = new ReadFile();
        }
        return readFile;

    }


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



}
