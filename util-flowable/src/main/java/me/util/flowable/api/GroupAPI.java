package me.util.flowable.api;

import me.util.flowable.model.Paging;
import me.util.flowable.model.group.Group;
import me.util.flowable.model.group.GroupRequest;
import me.util.flowable.model.group.Member;
import me.util.flowable.model.group.Membership;
import retrofit2.http.*;

import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface GroupAPI {

    @GET("identity/groups")
    Paging<Group> getGroups();

    @GET("identity/groups")
    Paging<Group> getGroups(@QueryMap Map<String, Object> params);

    @POST("identity/groups")
    Void createGroup(@Body GroupRequest groupRequest);

    @DELETE("identity/groups/{groupId}")
    Void deleteGroup(@Path("groupId") String groupId);

    @GET("identity/groups/{groupId}")
    Group getGroup(@Path("groupId") String groupId);

    @PUT("identity/groups/{groupId}")
    Void updateGroup(@Path("groupId") String groupId, @Body GroupRequest groupRequest);

    @POST("identity/groups/{groupId}/members")
    Membership addMember(@Path("groupId") String groupId, Member member);

    @DELETE("identity/groups/{groupId}/members/{userId}")
    Void deleteMember(@Path("groupId") String groupId, @Path("userId") String userId);
}
