package com.kakaopay.server.barcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, String> {

    Optional<Barcode> findByMemberId(String member_id);

}
