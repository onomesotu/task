package com.example.task.controller;

import com.example.task.model.PersonResponse;
import com.example.task.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/persons")
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;

  @GetMapping
  public ResponseEntity<PersonResponse> getPersons() {
    final var persons =  personService.getPersons();
    return ResponseEntity.ok().body(PersonResponse.builder().persons(persons).build());
  }
}
