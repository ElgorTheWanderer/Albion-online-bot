package com.github.elgorthewanderer.albion_online_bot.service;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.springframework.stereotype.Component;

//вешаю на имплементацию бот сервиса аннотацию @Component
@Component
public class BotServiceImpl implements BotService {

    private GatewayDiscordClient client;

    @Override
    public void start(String token) {
        this.client = DiscordClientBuilder.create(token).build().login().block();

        this.client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    System.out.printf(
                            "Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.onDisconnect().block();
    }

    @Override
    public void stop() {
        this.client.logout().subscribe();
    }
}
