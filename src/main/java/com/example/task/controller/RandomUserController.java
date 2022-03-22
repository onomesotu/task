package com.example.task.controller;

import com.example.task.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/random_users")
public class RandomUserController {

  private final PersonService personService;

  public RandomUserController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public ResponseEntity<Void> getRandomUsers() {
    personService.fetchRandomPersons();
    return ResponseEntity.ok().build();
  }
}
