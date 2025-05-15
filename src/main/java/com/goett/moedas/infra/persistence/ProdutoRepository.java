package com.goett.moedas.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goett.moedas.infra.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
