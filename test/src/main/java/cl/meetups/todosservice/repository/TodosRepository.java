package cl.meetups.todosservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.meetups.todosservice.pojos.Todo;


public interface TodosRepository extends MongoRepository<Todo, String>{

}
