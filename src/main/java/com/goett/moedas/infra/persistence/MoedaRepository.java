package com.goett.moedas.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goett.moedas.infra.entity.Moeda;

@Repository
public interface MoedaRepository extends JpaRepository<Moeda, Long> {}