package com.example.task.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.task.model.Person;
import com.example.task.model.PersonResponse;
import com.example.task.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Person controller - ")
class PersonControllerTest {

  private static final String END_POINT = "/v1/persons";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PersonRepository personRepository;

  @Test
  @DisplayName("should return all persons")
  void shouldReturnAllPersons() throws Exception {

    final var firstPerson = Person.builder()
        .first("john")
        .last("doe")
        .password("johndoe")
        .email("john email")
        .build();

    final var secondPerson = Person.builder()
        .first("jane")
        .last("doe")
        .password("janedoe")
        .email("jane email")
        .build();

    // Prepare dummy person data
    // Given
    final var personData = List.of(
        firstPerson, secondPerson
    );

    personRepository.saveAll(personData);

    // When
    final var mvcResult =
        mockMvc
            .perform(get(END_POINT).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    final var response =
        objectMapper.readValue(
            mvcResult.getResponse()
                .getContentAsString(), PersonResponse.class);

    final var persons = response.getPersons();

    // Then
    assertThat(persons).hasSize(2);
    assertThat(persons).extracting("first", "last", "email", "password")
        .contains(
            tuple("john", "doe", "john email", "johndoe"),
            tuple("jane", "doe", "jane email", "janedoe")
        );

  }
}
