package br.insper.sub.livro;

public record CadastrarLivroDTO(
        String titulo,
        String descricao,
        String autor,
        Integer paginas,
        String emailCriador
) {}