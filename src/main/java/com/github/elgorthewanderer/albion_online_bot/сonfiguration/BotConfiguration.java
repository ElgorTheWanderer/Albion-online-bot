package com.github.elgorthewanderer.albion_online_bot.сonfiguration;

import com.github.elgorthewanderer.albion_online_bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    @Value("${DISCORD_BOT_TOKEN}")
    private String token;

    // создал переменную интерефейса ботсервиса
    BotService botService;

    // создал конструктор для инжекта этого сервиса, повесь на конструктор аннотацию @Autowired
    @Autowired
    public BotConfiguration(BotService botService) {
        this.botService = botService;
    }

    @Bean
    public BotService botService (){
        botService.start(token);
        return botService;
    }
}
