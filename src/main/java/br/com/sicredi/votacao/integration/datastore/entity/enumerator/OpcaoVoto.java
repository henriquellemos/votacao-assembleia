package br.com.sicredi.votacao.integration.datastore.entity.enumerator;

import br.com.sicredi.votacao.core.exception.ServicoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum OpcaoVoto {
    SIM("SIM"), NAO("NAO");

    private final String nome;

    public static OpcaoVoto transformarOpcao(String opcao) {

        for (OpcaoVoto opcaoVoto : OpcaoVoto.values()) {
            if (opcaoVoto.nome.equalsIgnoreCase(opcao))
                return opcaoVoto;
        }

        log.error("Opção de voto '{}' não encontrada.", opcao);
        throw new ServicoException("Opção de voto '" + opcao + "' não encontrada.");
    }
}
