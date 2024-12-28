package com.hichat.mychat.config;

import com.hichat.mychat.config.storage.TableChatNameStorage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class TableNameInjector implements PhysicalNamingStrategy {

    private final TableChatNameStorage tableChatNameStorage;

    public TableNameInjector(TableChatNameStorage tableChatNameStorage) {
        this.tableChatNameStorage = tableChatNameStorage;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String newName = tableChatNameStorage.getTableName();

        if(newName != null){
            return Identifier.toIdentifier(newName);
        }

        return name;
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name;
    }
}
