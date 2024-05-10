package inf.gontijo.atividadeaulausuario.controller;
import inf.gontijo.atividadeaulausuario.model.Usuario;
import org.springframework.data.repository.CrudRepository;

interface UsuarioRepository extends CrudRepository<Usuario, Integer> {}
