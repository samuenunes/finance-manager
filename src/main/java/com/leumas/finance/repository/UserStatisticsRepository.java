package com.leumas.finance.repository;

import com.leumas.finance.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    @Query("SELECT s FROM UserStatistics s LEFT JOIN FETCH s.incomesByCategory LEFT JOIN FETCH s.expensesByCategory WHERE s.userId = :userId AND s.year = :year AND s.month = :month")
    Optional<UserStatistics> findByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);

    @Query("SELECT s FROM UserStatistics s LEFT JOIN FETCH s.incomesByCategory LEFT JOIN FETCH s.expensesByCategory WHERE s.year = :year AND s.month = :month")
    Optional<UserStatistics> findByYearAndMonth(Integer year, Integer month);
}
