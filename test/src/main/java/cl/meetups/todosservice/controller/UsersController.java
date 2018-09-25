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
import cl.meetups.todosservice.pojos.User;
import cl.meetups.todosservice.repository.TodosRepository;

@RestController
@RequestMapping("todos")
public class UsersController {
	@Autowired
	TodosRepository todosRepository;

	@GetMapping("/todos/{idT}/user/{idU}")
    public ResponseEntity getUser(@PathVariable String idT, @PathVariable int idU) {
        ResponseEntity response = null;
        User user = this.todosRepository.findById(idT).get().getUsuarios().get(idU);

        if (user != null)
            response = new ResponseEntity(user,HttpStatus.OK);
        else
            response = ResponseEntity.noContent().build();

        return response;
    }
	
	@GetMapping("/todos/{idT}/user/")
    public ResponseEntity getUsers(@PathVariable String idT) {
        ResponseEntity response = null;
        List<User> users = this.todosRepository.findById(idT).get().getUsuarios();
        if (!users.isEmpty())
            response = new ResponseEntity(users,HttpStatus.OK);
        else
            response = ResponseEntity.noContent().build();

        return response;
    }
	
	@DeleteMapping("/todos/{idT}/user/{idU}")
    public ResponseEntity deleteUser(@PathVariable String idT, @PathVariable int idU) {
        ResponseEntity response = null;
        try {
       	
        	Todo todo = this.todosRepository.findById(idT).get();
        	todo.getUsuarios().remove(idU);
        	
        	this.todosRepository.save(todo);

        	response = new ResponseEntity(HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
        	response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return response;
    }

	@PutMapping("/todos/{idT}/user/{idU}")
    public ResponseEntity putUser(@PathVariable String idT , @PathVariable int idU, @Valid @RequestBody User user) {
        ResponseEntity response = null;
        try {
        	Todo todo = this.todosRepository.findById(idT).get();
        	todo.getUsuarios().set(idU, user);
        	this.todosRepository.save(todo);
        	response = new ResponseEntity(HttpStatus.OK);
        }
        catch (IllegalArgumentException e)
        {
        	response = ResponseEntity.noContent().build();
        }

        return response;
    }
	
	@PostMapping("/todos/{idT}/user/")
    public ResponseEntity crearUser(@PathVariable String idT, @Valid @RequestBody User user) {
        ResponseEntity response = null;
        try {
        	Todo todo = this.todosRepository.findById(idT).get();
        	todo.getUsuarios().add(user);
        	this.todosRepository.save(todo);
        	response = new ResponseEntity(HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e)
        {
        	response = ResponseEntity.noContent().build();
        }

        return response;
    }
}
