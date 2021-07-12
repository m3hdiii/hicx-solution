package com.mehdi.ui;

import com.mehdi.service.Processor;

import java.io.File;

public class MainClass {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("You need to pass the [ROOT] path.\nThe ROOT folder is the parent folder for [processed, input and unsupported] folders..\n");
            System.exit(1);
        }

        String errFormat = "The system cannot create the [%s] directory, try to check your given path!";
        File baseFolder = new File(args[0]);
        directoryCoordinator(baseFolder, String.format(errFormat, "ROOT"));

        String subFolderName = "input";
        File input = new File(args[0] , subFolderName);
        directoryCoordinator(input, String.format(errFormat, subFolderName));

        subFolderName = "processed";
        File processed = new File(args[0] , subFolderName);
        directoryCoordinator(processed, String.format(errFormat, subFolderName));

        subFolderName = "unsupported";
        File unsupported = new File(args[0] , subFolderName);
        directoryCoordinator(unsupported, String.format(errFormat, subFolderName));

        new Processor(input.getAbsolutePath(), processed.getAbsolutePath(), unsupported.getAbsolutePath());
    }

    private static void directoryCoordinator(File file, String errorCreating) {
        if (!file.exists()) {
            boolean dirCreated = file.mkdir();
            if (!dirCreated) {
                System.err.println(errorCreating + "\n");
                System.exit(1);
            }
        }
    }
}
