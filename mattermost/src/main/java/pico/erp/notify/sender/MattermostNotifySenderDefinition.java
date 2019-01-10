package pico.erp.notify.sender;

import java.util.Map;
import lombok.Getter;
import lombok.val;
import net.bis5.mattermost.client4.ApiResponse;
import net.bis5.mattermost.model.Channel;
import net.bis5.mattermost.model.ChannelList;
import net.bis5.mattermost.model.ChannelType;
import net.bis5.mattermost.model.Post;
import net.bis5.mattermost.model.Team;
import net.bis5.mattermost.model.User;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.target.NotifyGroupData;
import pico.erp.notify.target.NotifyTargetData;

public class MattermostNotifySenderDefinition implements NotifySenderDefinition {

  @Getter
  private final NotifySenderId id = NotifySenderId.from("mattermost");

  @Getter
  private final String name = "Mattermost";

  private final String teamId;

  private final String senderId;

  private final MattermostClient client;

  private final Map<String, String> groupChannelNames;


  public MattermostNotifySenderDefinition(MattermostNotifySenderDefinitionConfig config) {
    client = new MattermostClient(config.getUrl());
    client.setAccessToken(config.getAccessToken());
    ApiResponse<Team> teamResponse = client.getTeamByName(config.getTeamName());
    senderId = getUserIdByEmail(config.getSenderEmail());
    teamId = teamResponse.readEntity().getId();
    groupChannelNames = config.getGroupChannelNames();
  }

  private String getUserIdByEmail(String email) {
    ApiResponse<User> userResponse = client.getUserByEmail(email);
    User user = userResponse.readEntity();
    if (!email.equals(user.getEmail())) {
      return null;
    }
    return user.getId();
  }

  @Override
  public boolean send(NotifyMessage message, NotifyTargetData target) {
    String email = target.getEmail();
    if (email == null || email.isEmpty()) {
      return false;
    }
    String userId = getUserIdByEmail(target.getEmail());
    if (userId == null) {
      return false;
    }
    ApiResponse<ChannelList> channels = client.getChannelsForTeamForUser(teamId, userId);
    ChannelList channelList = channels.readEntity();
    Channel channel = channelList.stream().filter(c -> c.getType() == ChannelType.Direct)
      .findAny()
      .orElse(null);

    if (channel != null) {
      Post post = new Post();
      post.setChannelId(channel.getId());
      post.setMessage(message.asMarkdown());
      client.createPost(post);
    } else {
      ApiResponse<Channel> directChannelResponse = client
        .createDirectChannel(senderId, userId);
      Channel directChannel = directChannelResponse.readEntity();
      Post post = new Post();
      post.setChannelId(directChannel.getId());
      post.setMessage(message.asMarkdown());
      client.createPost(post);
    }
    return true;
  }

  @Override
  public boolean send(NotifyMessage message, NotifyGroupData group) {
    val groupId = group.getId().getValue();
    val channelName =
      groupChannelNames.containsKey(groupId) ? groupChannelNames.get(groupId) : groupId;
    ApiResponse<Channel> channelResponse = client.getChannelByName(channelName, teamId);
    Channel channel = channelResponse.readEntity();
    if (!channelName.equals(channel.getName())) {
      return false;
    }
    Post post = new Post();
    post.setChannelId(channel.getId());
    post.setMessage(message.asMarkdown());
    client.createPost(post);
    return true;
  }

}
