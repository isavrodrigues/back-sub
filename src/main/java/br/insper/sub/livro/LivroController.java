package br.insper.sub.livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetornarLivroDTO criarLivro(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CadastrarLivroDTO dto
    ) {
        String token = jwt.getTokenValue();
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        return livroService.salvarLivro(token, roles, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String id
    ) {
        String token = jwt.getTokenValue();
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        livroService.deletarLivro(token, roles, id);
    }

    @GetMapping
    public List<Livro> listarLivros(
            @AuthenticationPrincipal Jwt jwt
    ) {
        String email = jwt.getClaimAsString("https://musica-insper.com/email");
        List<String> roles = jwt.getClaimAsStringList("https://musica-insper.com/roles");
        return livroService.listarLivros(email, roles);
    }
}