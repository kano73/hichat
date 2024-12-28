package com.hichat.mychat.config.storage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
public class TableChatNameStorage {

    private static final ThreadLocal<String> tableName = new ThreadLocal<>();

    public String setTableName(Integer user1_id, Integer user2_id) {
        String dynamicName = Math.max(user2_id, user1_id) == user1_id
                ? "chat_" + user2_id + "_" + user1_id
                : "chat_" + user1_id + "_" + user2_id;
        tableName.set(dynamicName);

        return getTableName();
    }

    public String getTableName() {
        return tableName.get();
    }

    public void clear() {
        tableName.remove();
    }
}
