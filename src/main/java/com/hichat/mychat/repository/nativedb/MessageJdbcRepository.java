package com.hichat.mychat.repository.nativedb;

import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.model.entitie.Message;
import com.hichat.mychat.model.enumclass.DataType;
import com.hichat.mychat.repository.MyUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TableChatNameStorage tableChatNameStorage;
    private final MyUserRepository myUserRepository;
    private String tableName;

    public MessageJdbcRepository(JdbcTemplate jdbcTemplate, TableChatNameStorage tableChatNameStorage, MyUserRepository myUserRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableChatNameStorage = tableChatNameStorage;
        this.myUserRepository = myUserRepository;
    }

//    select

    public List<Message> findAll() {
        setTableName(tableChatNameStorage.getTableName());
        String sql = "SELECT id, content_type, message, time_stamp, is_read, id_of_sender FROM " + tableName;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setContentType(DataType.valueOf(rs.getString("content_type")));
            message.setMessage(rs.getString("message"));
            message.setTimeStamp(rs.getTimestamp("time_stamp").toLocalDateTime());
            message.setRead(rs.getBoolean("is_read"));
            message.setSender(myUserRepository.findById(rs.getInt("id_of_sender")).orElse(null));
            return message;
        });
    }

//    insert

    public boolean save(Message message) {
        setTableName(tableChatNameStorage.getTableName());

        String sql = String.format(
                "INSERT INTO %s (message, content_type, time_stamp, is_read, id_of_sender) VALUES (?, ?, ?,  ?, ?)", tableName
        );

        return jdbcTemplate.update(sql, toParams(message)) > 0;

    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private Object[] toParams(Message message) {
        ArrayList<Object> params = new ArrayList<>();

        message.setRead(false);
        message.setTimeStamp(LocalDateTime.now());

        params.add(message.getMessage());
        params.add(message.getContentType().toString());
        params.add(message.getTimeStamp());
        params.add(message.isRead());
        params.add(message.getSender().getId());

        return params.toArray();
    }
}
