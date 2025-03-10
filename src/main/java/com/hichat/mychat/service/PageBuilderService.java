package com.hichat.mychat.service;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.exeption.UserOpenedHisPublicProfile;
import com.hichat.mychat.model.entitie.Friends;
import com.hichat.mychat.model.entitie.FriendsRequest;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.entitie.Message;
import com.hichat.mychat.model.enumclass.ResponseStatusEnum;
import com.hichat.mychat.repository.FriendsRepository;
import com.hichat.mychat.repository.FriendsRequestRepository;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.repository.nativedb.MessageJdbcRepository;
import com.hichat.mychat.repository.nativedb.ChatNativeRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageBuilderService {

    private final AuthenticatedMyUserService authService;
    private final MyUserRepository myUserRepository;
    private final MessageJdbcRepository usersMessageJdbcRepository;
    private final FriendsRequestRepository friendsRequestRepository;
    private final TableChatNameStorage tableChatNameStorage;
    private final FriendsRepository friendsRepository;
    private final ChatNativeRepository chatNativeRepository;

    public PageBuilderService(AuthenticatedMyUserService authService, MyUserRepository myUserRepository, MessageJdbcRepository usersMessageJdbcRepository, FriendsRequestRepository friendsRequestRepository, TableChatNameStorage tableChatNameStorage, FriendsRepository friendsRepository, ChatNativeRepository chatNativeRepository) {
        this.authService = authService;
        this.myUserRepository = myUserRepository;
        this.usersMessageJdbcRepository = usersMessageJdbcRepository;
        this.friendsRequestRepository = friendsRequestRepository;
        this.tableChatNameStorage = tableChatNameStorage;
        this.friendsRepository = friendsRepository;
        this.chatNativeRepository = chatNativeRepository;
    }

    public void buildProfilePage(Model model) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();

        List<MyUser> friendsList = getFriendsOfUser(authUser);

        model.addAttribute("user", authUser);
        model.addAttribute("friendsList", friendsList);
    }

    public void buildMyChatsPage(Model model) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();

        List<MyUser> friends = getFriendsOfUser(authUser);

        model.addAttribute("friendsList", friends);
    }

    public void buildChatWithPage(Model model, int id) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        int idOfReader = authUser.getId();

        MyUser userChatWith = myUserRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));

        tableChatNameStorage.setTableName(id, idOfReader);
        chatNativeRepository.existsOrThrow(authUser.getId(), userChatWith.getId());

        List<Message> messages = usersMessageJdbcRepository.findAll();

        model.addAttribute("user",userChatWith);
        model.addAttribute("messages", messages);

        model.addAttribute("id",authUser.getId());

        tableChatNameStorage.clear();
    }

    public void buildPublicProfile(int id, Model model) throws UserNotFoundException, UserOpenedHisPublicProfile {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser user = myUserRepository.findById(id).orElseThrow(()->new UserNotFoundException("user with such id dont exists"));

        if(authService.getCurrentUserAuthenticated().getId().equals(id)) {
            throw new UserOpenedHisPublicProfile("you requested your profile");
        }

        List<MyUser> friendsList = getFriendsOfUser(user);
        boolean isFriendshipRequested = friendsRequestRepository.existsByInviterAndReceiverAndStatus(user,authUser,ResponseStatusEnum.WAITING);
        if(!isFriendshipRequested) {
            isFriendshipRequested = friendsRequestRepository.existsByInviterAndReceiverAndStatus(user,authUser,ResponseStatusEnum.REJECT);
        }

        model.addAttribute("user", user);
        model.addAttribute("friendsList", friendsList);

        if(isFriendshipRequested){
            model.addAttribute("btnPlace", "<button class=\"acceptRequestToFriends\">Accept Request to friends</button>");
        }else if(!friendsList.contains(authUser)) {
            model.addAttribute("btnPlace", "<button class=\"addToFriends\">Add to Friends</button>");
        }else {
            model.addAttribute("btnPlace",
                    "<button class=\"removeFromFriends\">Remove From Friends</button>" +
                            "<button class=\"goToChat\">Chat</button>");
        }
    }

    public void buildRequestsForFriendShip(Model model) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();

        List<FriendsRequest> friendRequests = friendsRequestRepository.findAllByReceiverAndStatus(authUser, ResponseStatusEnum.WAITING);

        model.addAttribute("friendRequests", friendRequests);
    }

    private List<MyUser> getFriendsOfUser(MyUser authUser){
        List<Friends> friends = friendsRepository.findByFriendOrUser(authUser,authUser);
        return friends.stream().map(users->{
            if(users.getFriend().getId().equals(authUser.getId())){
                return users.getUser();
            }else{
                return users.getFriend();
            }
        }).collect(Collectors.toList());
    }


    public void buildUpdateProfilePage(Model model) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();

        model.addAttribute("user", authUser);
    }
}