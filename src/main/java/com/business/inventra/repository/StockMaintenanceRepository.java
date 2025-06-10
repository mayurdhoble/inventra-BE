package com.business.inventra.repository;

import com.business.inventra.model.StockMaintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockMaintenanceRepository extends JpaRepository<StockMaintenance, String> {
    List<StockMaintenance> findBypCode(String pCode);
    List<StockMaintenance> findByBranchId(String branchId);
    List<StockMaintenance> findBypCodeAndBranchId(String pCode, String branchId);
    @Query("SELECT s FROM StockMaintenance s WHERE s.pCode = :pCode AND s.branchId = :branchId AND s.pUnit = :pUnit AND s.orgId = :orgId")
    Optional<StockMaintenance> findByPCodeAndBranchIdAndPUnitAndOrgId(
            @Param("pCode") String pCode,
            @Param("branchId") String branchId,
            @Param("pUnit") String pUnit,
            @Param("orgId") String orgId);

    @Query("SELECT s FROM StockMaintenance s WHERE s.pCode = :pCode AND s.branchId = :branchId AND s.pUnit = :pUnit")
    Optional<StockMaintenance> findByPCodeAndBranchIdAndPUnit(
            @Param("pCode") String pCode,
            @Param("branchId") String branchId,
            @Param("pUnit") String pUnit);

    List<StockMaintenance> findByCurrentStockLessThanEqualAndThresholdLevelIsNotNull(BigDecimal threshold);

    @Query("SELECT s FROM StockMaintenance s WHERE s.orgId = :orgId")
    Page<StockMaintenance> findByOrgId(@Param("orgId") String orgId, Pageable pageable);

    @Query("SELECT s FROM StockMaintenance s WHERE s.orgId = :orgId AND s.branchId IN :branchIds")
    Page<StockMaintenance> findByOrgIdAndBranchIdIn(
            @Param("orgId") String orgId,
            @Param("branchIds") List<String> branchIds,
            Pageable pageable);

    @Query("SELECT s FROM StockMaintenance s WHERE s.orgId = :orgId AND s.branchId = :branchId")
    Page<StockMaintenance> findByOrgIdAndBranchId(
            @Param("orgId") String orgId,
            @Param("branchId") String branchId,
            Pageable pageable);
} 