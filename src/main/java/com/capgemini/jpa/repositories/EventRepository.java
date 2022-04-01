package com.capgemini.jpa.repositories;

import com.capgemini.jpa.entities.Event;
import com.capgemini.jpa.entities.RequestEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTimeBetweenAndAnalysisRequiredIs(LocalDateTime start, LocalDateTime end, boolean analysisRequired, Pageable pageable);

    @Modifying
    @Query("delete from Event where time < :givenDate")
    void deleteInBulkBeforeDate(@Param("givenDate") LocalDateTime givenDate);

    @Query("select new com.capgemini.jpa.repositories.ServerStatistic(e.server, count(e)) from Event e group by e.server")
    List<ServerStatistic> getServerStatistics();

    @Modifying
    @Query(value = "update #{#entityName} e set e.analysisRequired = true where e.duration > :threshold and type(e) = :type")
    void updateInBulkToBeAnalyzedByType(@Param("type") Class<? extends Event> type, @Param("threshold") int threshold);
}
