package com.goett.moedas.service;

import com.goett.model.ConversaoRequest;
import com.goett.model.ConversaoResponse;
import com.goett.moedas.infra.entity.*;
import com.goett.moedas.infra.persistence.*;
import com.goett.moedas.strategy.ConversaoStrategy;
import com.goett.moedas.strategy.ConversaoStrategyRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CambioServiceTest {

    @Mock
    private TaxaCambioRepository taxaCambioRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ReinoRepository reinoRepository;

    @Mock
    private MoedaRepository moedaRepository;

    @Mock
    private ConversaoStrategyRegistry strategyRegistry;

    @Mock
    private ConversaoStrategy strategy;

    @InjectMocks
    private CambioService cambioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTaxa_Sucesso() {
        TaxaCambioEntity taxa = new TaxaCambioEntity();
        taxa.setTaxa(BigDecimal.valueOf(2.5));

        when(taxaCambioRepository.findByMoedaOrigemNomeAndMoedaDestinoNomeAndProdutoNome("OURO_REAL", "TIBAR", "MADEIRA"))
            .thenReturn(List.of(taxa));

        Optional<TaxaCambioEntity> result = cambioService.obterTaxa("OURO_REAL", "TIBAR", "MADEIRA");

        assertTrue(result.isPresent());
        assertEquals(BigDecimal.valueOf(2.5), result.get().getTaxa());
    }

    @Test
    void testObterTaxa_NaoEncontrada() {
        when(taxaCambioRepository.findByMoedaOrigemNomeAndMoedaDestinoNomeAndProdutoNome("OURO_REAL", "TIBAR", "ferro"))
            .thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class,
            () -> cambioService.obterTaxa("OURO_REAL", "TIBAR", "ferro"));
    }

    @Test
    void testConverterMoeda() {
        ConversaoRequest request = new ConversaoRequest()
                .moedaOrigem("OURO_REAL")
                .moedaDestino("TIBAR")
                .produto("MADEIRA")
                .valor(100.0);

        when(strategyRegistry.getStrategy("MADEIRA")).thenReturn(strategy);
        when(strategy.converter(BigDecimal.valueOf(100.0), BigDecimal.valueOf(2.0)))
            .thenReturn(BigDecimal.valueOf(200.0));

        ConversaoResponse response = cambioService.converterMoeda(request, BigDecimal.valueOf(2.0));

        assertEquals("OURO_REAL", response.getMoedaOrigem());
        assertEquals("TIBAR", response.getMoedaDestino());
        assertEquals("MADEIRA", response.getProduto());
        assertEquals(200.0, response.getValorConvertido());
        assertEquals(2.0, response.getTaxaAplicada());
        assertNotNull(response.getDataHora());
    }

    @Test
    void testSalvarTransacao_Sucesso() {
        ConversaoRequest request = new ConversaoRequest()
                .moedaOrigem("OURO_REAL")
                .moedaDestino("TIBAR")
                .produto("MADEIRA")
                .valor(100.0);

        ConversaoResponse response = new ConversaoResponse()
                .valorConvertido(200.0);

        TaxaCambioEntity taxa = new TaxaCambioEntity();
        taxa.setTaxa(BigDecimal.valueOf(2.0));

        when(produtoRepository.findByNome("MADEIRA")).thenReturn(Optional.of(new ProdutoEntity()));
        when(reinoRepository.findByNome("SRM")).thenReturn(Optional.of(new ReinoEntity()));
        when(moedaRepository.findByNome("OURO_REAL")).thenReturn(Optional.of(new MoedaEntity()));
        when(moedaRepository.findByNome("TIBAR")).thenReturn(Optional.of(new MoedaEntity()));
        when(transacaoRepository.save(any())).thenReturn(new TransacaoEntity());

        boolean result = cambioService.salvarTransacao(request, taxa, response);
        assertTrue(result);
    }

    @Test
    void testSalvarTransacao_Falha() {
        ConversaoRequest request = new ConversaoRequest()
                .moedaOrigem("OURO_REAL")
                .moedaDestino("TIBAR")
                .produto("MADEIRA")
                .valor(100.0);

        ConversaoResponse response = new ConversaoResponse()
                .valorConvertido(200.0);

        TaxaCambioEntity taxa = new TaxaCambioEntity();
        taxa.setTaxa(BigDecimal.valueOf(2.0));

        // Simulando ausência de moeda
        when(produtoRepository.findByNome("MADEIRA")).thenReturn(Optional.empty());

        boolean result = cambioService.salvarTransacao(request, taxa, response);
        assertFalse(result);
    }
}
