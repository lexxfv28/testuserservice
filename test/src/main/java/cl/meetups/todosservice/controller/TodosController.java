package cl.meetups.todosservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.meetups.todosservice.pojos.Todo;
import cl.meetups.todosservice.repository.TodosRepository;

@RestController
@RequestMapping("todos")
public class TodosController {
	@Autowired
	TodosRepository todosRepository;
	
	@GetMapping("/todos/todos/{id}")
    public ResponseEntity getTodo(@PathVariable String id) {
        ResponseEntity response = null;
        Optional todo = this.todosRepository.findById(id);
        if (todo.isPresent())
            response = new ResponseEntity(todo.get(),HttpStatus.OK);
        else
            response = ResponseEntity.noContent().build();

        return response;
    }
	
	@GetMapping("/todos/todos/")
    public ResponseEntity getTodos() {
        ResponseEntity response = null;
        List<Todo> todos = this.todosRepository.findAll();
        if (!todos.isEmpty())
            response = new ResponseEntity(todos,HttpStatus.OK);
        else
            response = ResponseEntity.noContent().build();

        return response;
    }
	
	@DeleteMapping("/todos/todos/{id}")
    public ResponseEntity deleteTodo(@PathVariable String id) {
        ResponseEntity response = null;
        try {
        	this.todosRepository.deleteById(id);
        	response = new ResponseEntity(HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
        	response = ResponseEntity.noContent().build();
        }

        return response;
    }

	@PutMapping("/todos/todos/{id}")
    public ResponseEntity putTodo(@PathVariable ObjectId id, @Valid @RequestBody Todo todo) {
        ResponseEntity response = null;
        try {
        	todo.set_id(id);
        	this.todosRepository.save(todo);
        	response = new ResponseEntity(HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
        	response = ResponseEntity.noContent().build();
        }

        return response;
    }
	
	@PostMapping("/todos/todos/")
    public ResponseEntity crearTodo(@Valid @RequestBody Todo todo) {
        ResponseEntity response = null;
        try {
        	todo.set_id(ObjectId.get());
        	Todo t = this.todosRepository.save(todo);
        	response = new ResponseEntity(t.get_id(),HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
        	response = ResponseEntity.noContent().build();
        }

        return response;
    }
	
}
