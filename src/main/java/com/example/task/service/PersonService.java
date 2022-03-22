package com.example.task.service;

import com.example.task.client.PersonClient;
import com.example.task.model.Person;
import com.example.task.model.RandomUsers;
import com.example.task.repository.PersonRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
  private final PersonRepository personRepository;
  private final PersonClient personClient;

  @SneakyThrows
  public void fetchRandomPersons() {
    RandomUsers randomUsers = personClient.fetchRandomPersons();

    List<Person> persons = randomUsers.getResults().stream()
        .map(randomUser -> Person.builder()
            .first(randomUser.getName().getFirst())
            .last(randomUser.getName().getLast())
            .email(randomUser.getEmail())
            .password(randomUser.getLogin().getPassword())
            .build())
        .collect(Collectors.toList());

    personRepository.saveAll(persons);
  }

  public List<Person> getPersons() {
    return personRepository.findAll();
  }

}
