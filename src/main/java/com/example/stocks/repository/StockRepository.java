package com.example.stocks.repository;

import com.example.stocks.entity.DatabaseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<DatabaseStock, Long> {

	Optional<DatabaseStock> findDatabaseStockById(Long id);

	List<DatabaseStock> findAllByOrderByCreatedDateDesc();





}
