package com.hichat.mychat.repository.nativedb;

import com.hichat.mychat.model.entitie.MyUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MyUserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public MyUserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//   update

    public boolean updateUserInfoById(MyUser user){
        String sql = sqlUpdateBuilder(user);

        return jdbcTemplate.update(sql, toParams(user)) > 0;
    }

    private String sqlUpdateBuilder(MyUser user){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE my_user SET");

        if(user.getPublicName() != null)sqlBuilder.append(" public_name = ?,");
        if(user.getUsername()!=null)sqlBuilder.append(" username = ?,");
        if(user.getEmail()!=null)sqlBuilder.append(" email = ?,");
        if(user.getAge()!=null)sqlBuilder.append(" age = ?,");
        if(user.getDescription()!=null)sqlBuilder.append(" description = ?,");
        if(user.getUsersPhotoUrl()!=null)sqlBuilder.append(" users_photo_url = ?,");

        sqlBuilder.deleteCharAt(sqlBuilder.length()-1);

        sqlBuilder.append(" WHERE id = ?");

        return sqlBuilder.toString();
    }

    private Object[] toParams(MyUser user) {
        ArrayList<Object> params = new ArrayList<>();

        if (user.getPublicName() != null) params.add(user.getPublicName());
        if (user.getUsername() != null)  params.add(user.getUsername());
        if (user.getEmail() != null) params.add(user.getEmail());
        if (user.getAge() != null) params.add(user.getAge());
        if (user.getDescription() != null) params.add(user.getDescription());
        if (user.getUsersPhotoUrl() != null) params.add(user.getUsersPhotoUrl());

        params.add(user.getId());

        return params.toArray();
    }
}
