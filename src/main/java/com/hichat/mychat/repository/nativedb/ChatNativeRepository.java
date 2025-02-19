package com.hichat.mychat.repository.nativedb;

import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.exeption.ChatNotFound;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class ChatNativeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final TableChatNameStorage tableChatNameStorage;

    public ChatNativeRepository(TableChatNameStorage tableChatNameStorage) {
        this.tableChatNameStorage = tableChatNameStorage;
    }

    public void createChatTable(@NonNull Integer user1_id, @NonNull  Integer user2_id) {
        String tableName = tableChatNameStorage.setTableName(user1_id, user2_id);

        String query = "CREATE TABLE " + tableName + " (" +
                "id SERIAL PRIMARY KEY, " +
                "content_type TEXT NOT NULL, " +
                "message TEXT NOT NULL, " +
                "time_stamp TIMESTAMP NOT NULL, " +
                "is_read BOOLEAN DEFAULT FALSE NOT NULL, " +
                "id_of_sender BIGINT NOT NULL);";

        entityManager.createNativeQuery(query).executeUpdate();
    }

    public void deleteChatTableByIds(@NonNull Integer user1_id, @NonNull Integer user2_id) {
        String query = "DROP TABLE " + tableChatNameStorage.setTableName(user1_id, user2_id);

        entityManager.createNativeQuery(query).executeUpdate();
    }

    public boolean isExistsByIds(@NonNull Integer user1_id, @NonNull Integer user2_id) {
        String tableName = tableChatNameStorage.setTableName(user1_id, user2_id);
        String query = "SELECT COUNT(*) " +
                "FROM information_schema.tables " +
                "WHERE table_schema = 'public' " +
                "AND table_name = '"+tableName+"'";

        Long count = (Long) entityManager.createNativeQuery(query).getSingleResult();

        return count > 0;
    }

    public void existsOrThrow(int id1, int id2) {
        if(!isExistsByIds(id1, id2)){
            throw new ChatNotFound("chat with these users not exists");
        }
    }

    public boolean isExistsByName(String chatName) {
        String query = "SELECT COUNT(*) " +
                "FROM information_schema.tables " +
                "WHERE table_schema = 'public' " +
                "AND table_name = '"+chatName+"'";

        Long count = (Long) entityManager.createNativeQuery(query).getSingleResult();

        return count > 0;
    }
}

