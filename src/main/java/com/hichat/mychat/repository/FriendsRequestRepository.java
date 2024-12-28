package com.hichat.mychat.repository;

import com.hichat.mychat.model.entitie.FriendsRequest;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.ResponseStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRequestRepository extends JpaRepository<FriendsRequest,Integer> {

     <S extends FriendsRequest> S save(S entity);

     List<FriendsRequest> findAllByInviter(MyUser inviter);

     boolean existsByInviterAndReceiver(MyUser inviter, MyUser receiver);

     List<FriendsRequest> findAllByReceiverAndStatus(MyUser receiver, ResponseStatusEnum status);


     @Modifying
     @Query("UPDATE FriendsRequest fr SET fr.status = :status WHERE fr.inviter = :inviter AND fr.receiver = :receiver")
     int updateResponseStatusEnumByInviterAndReceiver(@Param("inviter") MyUser inviter,
                                                      @Param("receiver") MyUser receiver,
                                                      @Param("status") ResponseStatusEnum status);

     @Modifying
     @Query("DELETE FROM FriendsRequest f WHERE (f.inviter = :inviter AND f.receiver = :receiver) OR (f.inviter = :receiver AND f.receiver = :inviter)")
     int deleteByInviterAndReceiver(MyUser inviter, MyUser receiver);

     boolean existsByInviterAndReceiverAndStatus(MyUser inviter, MyUser receiver, ResponseStatusEnum status);

     List<FriendsRequest> findByInviterAndReceiver(MyUser inviter, MyUser receiver);
}
