package br.insper.sub.livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public RetornarLivroDTO salvarLivro(String token, List<String> roles, CadastrarLivroDTO dto) {
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apenas ADMIN pode criar livros.");
        }

        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setDescricao(dto.descricao());
        livro.setAutor(dto.autor());
        livro.setPaginas(dto.paginas());
        livro.setEmailCriador(dto.emailCriador());

        livro = livroRepository.save(livro);

        return new RetornarLivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getDescricao(),
                livro.getAutor(),
                livro.getPaginas(),
                livro.getEmailCriador()
        );
    }

    public void deletarLivro(String token, List<String> roles, String id) {
        if (!roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apenas ADMIN pode deletar livros.");
        }

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        livroRepository.delete(livro);
    }

    public List<Livro> listarLivros(String email, List<String> roles) {
        return livroRepository.findAll(); // qualquer usuário autenticado pode listar
    }
}