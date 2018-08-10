package com.panzhiev.leogaming.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzhiev.leogaming.R;
import com.panzhiev.leogaming.repository.pojo.ResponseAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class RepositoryProvider {

    private final String jsonPath = "res/raw/dashboard.json";

    public ResponseAccount getAccountInfo() {

        InputStream is = RepositoryProvider.class.getClassLoader().getResourceAsStream(jsonPath);

        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        return gson.fromJson(writer.toString(), ResponseAccount.class);
    }
}
