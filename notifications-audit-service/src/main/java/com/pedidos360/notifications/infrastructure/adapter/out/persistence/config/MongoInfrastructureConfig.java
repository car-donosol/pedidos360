package com.pedidos360.notifications.infrastructure.adapter.out.persistence.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoInfrastructureConfig extends AbstractMongoClientConfiguration {

    // 1. Extraemos la URI de tu archivo properties que ya comprobamos que funciona
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    // 2. Definimos explícitamente el nombre de la base de datos
    @Override
    protected String getDatabaseName() {
        return "pedidos360_audit";
    }

    // 3. Sobrescribimos el Bean de conexión. Spring Boot usará este en lugar del suyo.
    @Override
    public MongoClient mongoClient() {
        System.out.println("\n========== BYPASS DE INFRAESTRUCTURA ==========");
        System.out.println("Forzando conexión manual a Atlas...");
        System.out.println("===============================================\n");
        
        return MongoClients.create(mongoUri);
    }
}