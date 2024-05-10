package inf.gontijo.atividadeaulausuario.controller;
import inf.gontijo.atividadeaulausuario.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        usuarioRepository.saveAll(List.of(
                new Usuario("Ana", "ana@gmail.com", "123*"),
                new Usuario("Marcos", "marcos@gmail.com", "456!"),
                new Usuario("Pedro", "pedro@gmail.com", "789!"),
                new Usuario("Maria", "maria@gmail.com", "891*")
        ));
    }

    @PostMapping("/usuarios")
    ResponseEntity<Usuario> add(@RequestBody Usuario usuario) {
        if(!usuario.getNome().isEmpty() && !usuario.getEmail().isEmpty() && !usuario.getSenha().isEmpty()) {
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/usuarios")
    Iterable<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @PutMapping("/usuarios/{id}")
    ResponseEntity<Usuario> update(@PathVariable int id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(existingUsuario -> {
                    usuario.setId(existingUsuario.getId());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario)));
    }

    @DeleteMapping("/usuarios/{id}")
    ResponseEntity<Usuario> delete(@PathVariable int id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
