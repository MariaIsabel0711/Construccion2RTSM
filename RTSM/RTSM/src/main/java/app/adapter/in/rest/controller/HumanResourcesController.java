package app.adapter.in.rest.controller;

import app.adapter.in.builder.UserBuilder;
import app.adapter.in.rest.request.UserRequest;
import app.adapter.in.rest.request.UserUpdateRequest;
import app.application.usecases.HumanResourcesUseCase;
import app.domain.model.User;
import app.domain.model.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class HumanResourcesController {

    private final HumanResourcesUseCase hrUseCase;
    private final UserBuilder userBuilder;

    public HumanResourcesController(HumanResourcesUseCase hrUseCase, UserBuilder userBuilder) {
        this.hrUseCase = hrUseCase;
        this.userBuilder = userBuilder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createEmployee(@RequestBody UserRequest req) throws Exception {
        Role role;
        try {
            role = Role.valueOf(req.getRole().toUpperCase());
        } catch (Exception e) {
            role = Role.PERSONAL_ADMINISTRATIVO;
        }


        User user = userBuilder.build(
                req.getFullName(),
                req.getDocument(),          
                req.getEmail(),
                req.getPhoneNumber(),
                req.getDateOfBirth(),       
                req.getAddress(),
                req.getGender(),
                req.getUserName(),
                req.getPassword(),
                role
        );

        switch (role) {
            case MEDICO -> hrUseCase.createDoctor(user);
            case ENFERMERA -> hrUseCase.createNurse(user);
            case PERSONAL_ADMINISTRATIVO -> hrUseCase.createAdministrativeStaff(user);
            case SOPORTE_INFORMACION -> hrUseCase.createInformationSupport(user);
            case RECURSOS_HUMANOS -> hrUseCase.createAdministrativeStaff(user);
            default -> hrUseCase.createAdministrativeStaff(user);
        }

        return ResponseEntity.created(URI.create("/api/users/" + user.getDocument())).body(user);
    }

    @GetMapping("/users/{document}")
    public ResponseEntity<User> getEmployee(@PathVariable String document) {
        User u = hrUseCase.findUserByDocument(document);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @PutMapping("/users/{document}")
    public ResponseEntity<User> updateEmployee(@PathVariable String document, @RequestBody UserUpdateRequest req) throws Exception {
        User existing = hrUseCase.findUserByDocument(document);
        if (existing == null) return ResponseEntity.notFound().build();

        userBuilder.applyPersonalDataUpdates(
                existing,
                blankToNull(req.getFullName()),
                blankToNull(req.getEmail()),
                blankToNull(req.getPhoneNumber()),
                blankToNull(req.getAddress()),
                blankToNull(req.getDateOfBirth()),
                blankToNull(req.getGender())
        );
        hrUseCase.updateUserPersonalData(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/users/{document}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String document) throws Exception {
        hrUseCase.deleteUser(document);
        return ResponseEntity.noContent().build();
    }

    private String blankToNull(String v) { return (v == null || v.isBlank()) ? null : v; }
}
