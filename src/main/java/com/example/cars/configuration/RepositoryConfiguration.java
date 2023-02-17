package com.example.cars.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
public class RepositoryConfiguration extends ElasticsearchConfiguration {
    @Value( "${elasticsearch.host}" )
    private String host;
    @Value( "${elasticsearch.port}" )
    private String port;
    @Value( "${elasticsearch.username}" )
    private String username;
    @Value( "${elasticsearch.password}" )
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder().
        connectedTo(host + ":" + port).
                usingSsl(sslContext()).
                withBasicAuth(username, password).
//                withDefaultHeaders(compatibilityHeaders()).
                build();
    }

    private SSLContext sslContext() {
        try {
            File crtFile = new File("http_ca.crt");
            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(crtFile));

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("server", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            return sslContext;
        } catch ( CertificateException | KeyStoreException |IOException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

//    private HttpHeaders compatibilityHeaders() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7");
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7");
//        return httpHeaders;
//    }

}
