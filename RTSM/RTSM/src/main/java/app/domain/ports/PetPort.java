package app.domain.ports;

import app.domain.model.Pet;
import app.domain.model.Person;

import java.util.List;

public interface PetPort {

   // Busxa mascota por el id
    public Pet findById(long id) throws Exception;

    // Busca mascotas por el dueño
    public List<Pet> findByOwner(Person owner) throws Exception;

    // Guarda nueva mascota
    public void save(Pet pet) throws Exception;

    //Actualiza mascota que ya existe
    public void update(Pet pet) throws Exception;
}
