package com.goett.moedas.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.goett.moedas.service.CambioService;

@Component
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true", matchIfMissing = false)
public class AtualizacaoCambioJob {

    private final CambioService cambioService;

    public AtualizacaoCambioJob(CambioService cambioService) {
        this.cambioService = cambioService;
    }

   //@Scheduled(cron = "0 * * * * *") // Executa no segundo 0 de cada minuto
    @Scheduled(cron = "0 0/5 * * * *")
    public void atualizarTaxas() {
        cambioService.atualizarTaxas();
    }
}
