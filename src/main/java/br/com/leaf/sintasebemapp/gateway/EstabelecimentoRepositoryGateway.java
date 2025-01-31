package br.com.leaf.sintasebemapp.gateway;

import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;

public interface EstabelecimentoRepositoryGateway {

    Estabelecimento salvar(Estabelecimento estabelecimento);

    Estabelecimento obterEstabelecimentoPorCNPJ(String cnpj);
}
