package com.pos.app.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import java.util.stream.Collectors;

public class TSVParser {

    public static Boolean checkTotalLines(MultipartFile file, Integer maxRows) throws IOException {
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines()
                .collect(Collectors.toList());
        return lines.size() > maxRows;
    }
}

