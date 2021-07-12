package com.mehdi.service;

import com.mehdi.model.Statistic;
import com.mehdi.service.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class process the files and move them to the right place once they're processed...
 */
public class Processor {

    private String inputFolder;
    private String processedFolder;
    private String unsupportedFolder;
    private static final int THREAD_POOL_SIZE = 10;
    private ParserFactory factory = new ParserFactory();

    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public Processor(String inputFolder, String processedFolder, String unsupportedFolder) {
        this.inputFolder = inputFolder;
        this.processedFolder = processedFolder;
        this.unsupportedFolder = unsupportedFolder;

        init();
    }

    /**
     * The observer thread for watching the new file adding process is instantiate here by {@link Processor#folderObserver()} method
     * We also process the existing files if any by {@link Processor#processedFolder} method
     */
    private void init() {
        new Thread(() -> folderObserver()).start();
        new Thread(() -> processFiles(new File(inputFolder).listFiles())).start();
    }

    /**
     * Watch for any file creation within the [input] folder and forward them for processing!
     */
    private void folderObserver() {

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(inputFolder);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;

            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    processFiles(new File(inputFolder, event.context().toString()));
                }
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * It processes the given files using 10 worker-thread
     *
     * @param files
     */
    private void processFiles(File... files) {
        List<Future<FileStatistic>> futures = new ArrayList<>();
        for (File fx : files) {
            Future<FileStatistic> future = executor.submit(() -> {
                Parser parser = factory.getParser(fx);
                Statistic statistic = parser.getStatistic();
                return new FileStatistic(fx, statistic);
            });
            futures.add(future);

        }

        for (Future<FileStatistic> fu : futures) {
            try {
                FileStatistic fs = fu.get();
                moveToProcessedFolder(fs.getFile());
                showTheResult(fs);
            } catch (IllegalArgumentException | ExecutionException e) {
                String path = e.getCause().getMessage();
                if (new File(path).exists())
                    moveToUnsupportedFolder(new File(path));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void moveToUnsupportedFolder(File file) {
        file.renameTo(new File(unsupportedFolder, file.getName()));
    }

    private void moveToProcessedFolder(File file) {
        file.renameTo(new File(processedFolder, file.getName()));
    }

    private void showTheResult(FileStatistic fileStatistic) {
        File file = fileStatistic.getFile();
        Statistic statistic = fileStatistic.getStatistic();
        StringBuilder sb = new StringBuilder(String.format("File [%s] processed..\n\n", file.getName()));
        sb
                .append(String.format("Number of words: [%d]\n", statistic.getNumOfWords()))
                .append(String.format("Number of repeated [.] characters: [%d]\n", statistic.getNumOfDots()))
                .append(String.format("Most repeated word: [%s]\n", statistic.getMostUsedWord()))
                .append("****************************************");

        System.out.println(sb);
    }

    class FileStatistic {
        private File file;
        private Statistic statistic;

        public FileStatistic(File file, Statistic statistic) {
            this.file = file;
            this.statistic = statistic;
        }

        public File getFile() {
            return file;
        }

        public Statistic getStatistic() {
            return statistic;
        }
    }

}
