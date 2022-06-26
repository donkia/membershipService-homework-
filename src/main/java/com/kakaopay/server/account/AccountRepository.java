package com.kakaopay.server.account;

import com.kakaopay.server.store.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountByBarcode(String barcode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findAccountByStoreCategoryAndBarcode(StoreCategory category, String barcode);
}
