package com.tradematch.repository;

import com.tradematch.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT AVG(t.executionPrice) FROM Trade t WHERE t.symbol = :symbol")
    Double getAverageExecutionPrice(@Param("symbol") String symbol);
}