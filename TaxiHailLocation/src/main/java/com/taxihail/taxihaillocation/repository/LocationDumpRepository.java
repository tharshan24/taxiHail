package com.taxihail.taxihaillocation.repository;

import com.taxihail.taxihaillocation.model.LocationDump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationDumpRepository extends JpaRepository<LocationDump, UUID> {
    @Modifying
    @Query("UPDATE LocationDump l SET l.status = 0 WHERE l.dumpId = :dumpId")
    void softDelete(@Param("dumpId") UUID dumpId);
}
