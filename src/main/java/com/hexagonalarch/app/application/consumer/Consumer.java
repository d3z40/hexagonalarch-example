package com.hexagonalarch.app.application.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hexagonalarch.app.application.adapter.LocalDateDeserializer;
import com.hexagonalarch.app.domain.Animal;
import com.hexagonalarch.app.domain.service.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;

@Component
public class Consumer {

    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    public Consumer(AnimalService animalService) {
        this.animalService = animalService;
        this.modelMapper = new ModelMapper();
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void listener(@Payload String msg) {
        System.out.println(msg);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting()
                .create();

        var animal = gson.fromJson(msg, Animal.class);

        var response = animalService.createAnimal(animal);
        System.out.println(URI.create("v1/animals/" + response.toString()));
    }
}
