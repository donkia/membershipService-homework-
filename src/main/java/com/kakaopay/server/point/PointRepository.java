package com.kakaopay.server.point;

import com.kakaopay.server.store.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    // 날짜별 포인트 내역 조회
    @Query("select p from Point p where p.barcodeId = :barcode and p.approvedAt between :startDate and :endDate order by p.approvedAt desc")
    List<Point> findPointByTermJPQL(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("barcode") String barcode);

}
