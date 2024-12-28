package com.hichat.mychat.service;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.exeption.InviteAlreadyExists;
import com.hichat.mychat.exeption.RequestNotFound;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.dto.ResToReqForFriendship;
import com.hichat.mychat.model.entitie.Friends;
import com.hichat.mychat.model.entitie.FriendsRequest;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.ResponseStatusEnum;
import com.hichat.mychat.repository.FriendsRepository;
import com.hichat.mychat.repository.FriendsRequestRepository;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.repository.nativedb.ChatNativeRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendsService {

    private final AuthenticatedMyUserService authService;
    private final MyUserRepository myUserRepository;
    private final FriendsRequestRepository friendsRequestRepository;
    private final FriendsRepository friendsRepository;
    private final ChatNativeRepository chatNativeRepository;

    public FriendsService(AuthenticatedMyUserService authService, MyUserRepository myUserRepository, FriendsRequestRepository friendsRequestRepository, FriendsRepository friendsRepository, ChatNativeRepository chatNativeRepository) {
        this.authService = authService;
        this.myUserRepository = myUserRepository;
        this.friendsRequestRepository = friendsRequestRepository;
        this.friendsRepository = friendsRepository;
        this.chatNativeRepository = chatNativeRepository;
    }

    public boolean requestForFriendshipById(int id) throws UserNotFoundException, InviteAlreadyExists {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser receiver = myUserRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));

        FriendsRequest friendsRequest = new FriendsRequest(authUser,receiver);

        if(friendsRequestRepository.existsByInviterAndReceiver(authUser,receiver)){
            throw new InviteAlreadyExists("Invite already exists");
        }
        friendsRequestRepository.save(friendsRequest);

        return true;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public String respondToRequest(ResToReqForFriendship response) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser inviter = myUserRepository.findById(response.getIdOfUser()).orElseThrow(()->new UsernameNotFoundException("User not found"));

        if(!friendsRequestRepository.existsByInviterAndReceiver(inviter,authUser)){
            throw new RequestNotFound("Request not found");
        }
        friendsRequestRepository.updateResponseStatusEnumByInviterAndReceiver(inviter,authUser,response.getResponseStatus());

        if(response.getResponseStatus()==ResponseStatusEnum.ACCEPT){
            friendsRepository.save(new Friends(authUser,inviter));
            if(!chatNativeRepository.isExistsByIds(authUser.getId(),inviter.getId())){
                chatNativeRepository.createChatTable(authUser.getId(),inviter.getId());
            }
            return "Successfully added to friends";
        }

        return "Successfully declined request to friends";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public boolean removeFromFriends(int id) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser inviter = myUserRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));

        friendsRepository.deleteByUserAndFriend(authUser,inviter);
        friendsRequestRepository.deleteByInviterAndReceiver(authUser,inviter);
        if(chatNativeRepository.isExistsByIds(authUser.getId(),inviter.getId())){
            chatNativeRepository.deleteChatTableByIds(authUser.getId(),id);
        }

        return true;
    }
}
