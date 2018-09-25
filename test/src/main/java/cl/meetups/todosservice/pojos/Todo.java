package cl.meetups.todosservice.pojos;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Todos") //nombre de la conllection que se crea en mongo
@Builder
public class Todo {
	@Id // notacion para indicar a mongo que esta id sera unica
    @JsonIgnore // el json generado ignorara este field.
    @ToString.Exclude // se excluira del toString
    private ObjectId _id;
	
	private String action;
	
	private String description;
	
	private List<User> usuarios;
	
	public Optional<ObjectId> get_id() {
        return Optional.ofNullable(_id);
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
