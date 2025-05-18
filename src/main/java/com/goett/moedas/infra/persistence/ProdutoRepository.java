package com.goett.moedas.infra.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.goett.moedas.infra.entity.ProdutoEntity;
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findByNome(String nome);

    List<ProdutoEntity> findAll();
}
