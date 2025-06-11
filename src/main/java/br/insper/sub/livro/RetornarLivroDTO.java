package br.insper.sub.livro;

public record RetornarLivroDTO(
        String id,
        String titulo,
        String descricao,
        String autor,
        Integer paginas,
        String emailCriador
) {}