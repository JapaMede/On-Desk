package com.example.ondesk.Service;

import android.os.AsyncTask;

import com.example.ondesk.model.CEP;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class HttpService extends AsyncTask <Void, Void, CEP> {

    private final String cep;

    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder result = new StringBuilder();

        if (this.cep != null && this.cep.length() == 8) {
            try {
                URL url = new URL("http://viacep.com.br/ws/" + this.cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
                String linha;
                while ((linha = rd.readLine()) != null) {
                    result.append(linha);
                }
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Gson().fromJson(result.toString(), CEP.class);

    }

    public HttpService(String cep) {
        this.cep = cep;
    }
}
