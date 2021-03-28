package com.example.stocks.repository;

import com.example.stocks.entity.DatabaseStock;
import com.example.stocks.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {

	List<Price> findAllByDatabaseStockOrderByCreatedDateAsc(DatabaseStock stock);

	Price findPriceById(Long id);

	List<Price> findAllByDatabaseStockIdOrderByCreatedDateAsc(Long id);
}
