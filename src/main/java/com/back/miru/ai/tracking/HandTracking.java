package com.back.miru.ai.tracking;

import com.back.miru.ai.transfer.TransformPainting;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class HandTracking {
    static int lastNum = 0;

    public static void main(String[] args) throws Exception {
//        tracking("ororwnstlr2");
        tracking("ty");
    }

    public static void tracking(String id) throws Exception {
        System.out.println("tracking");
        String root = "src/main/java/com/back/miru/ai/tracking/";

        String[] command = new String[2];
        command[0] = "python3";
        command[1] = root + "handtracking.py";

        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        System.out.println(commandLine);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
//        System.out.println("output: " + outputStream);

        String[] word = new String(outputStream.toByteArray(), StandardCharsets.UTF_8).split("\n");
        if (word[2].equals("MJ\r")) {
            int styleNum = (int) (Math.random() * 21 + 1);
            System.out.println("---SUCCESS--- Detectived HandTracking");
            // 중복 검사
            while (lastNum == styleNum) styleNum = (int) (Math.random() * 21 + 1);
            TransformPainting.transform(String.valueOf(styleNum),
                    "",
                    "src/main/java/com/back/miru/ai/transfer/c.jpg",
                    id);
            lastNum = styleNum;
        }
    }
}
