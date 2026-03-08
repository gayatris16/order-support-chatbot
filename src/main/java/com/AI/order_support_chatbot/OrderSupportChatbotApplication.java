package com.AI.order_support_chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
		org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration.class
})
public class OrderSupportChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSupportChatbotApplication.class, args);
	}

	@Bean
	public OpenAiApi openAiApi() {
		return new OpenAiApi(
				"https://generativelanguage.googleapis.com/v1beta/openai",
				"AIzaSyAwUdmk_Tca5cKTVMMrol0Iv6GnbnVzZbs"
		);
	}

	@Bean
	public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
		return new OpenAiChatModel(openAiApi);
	}

	@Bean
	public ChatClient chatClient(OpenAiChatModel model) {
		return ChatClient.builder(model).build();
	}
}