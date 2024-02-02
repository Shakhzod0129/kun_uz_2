package uz.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.kun.entity.EmailSendHistoryEntity;
import uz.kun.entity.SmsSendHistoryEntity;


import java.time.LocalDateTime;
import java.util.List;


public interface EmailSendHistoryRepository extends CrudRepository<EmailSendHistoryEntity, Integer>, PagingAndSortingRepository<EmailSendHistoryEntity,Integer> {


    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query("SELECT count (s) from EmailSendHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);

    List<EmailSendHistoryEntity> findAllByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<EmailSendHistoryEntity>  getByEmail(String phone);

    Page<EmailSendHistoryEntity> findAllBy(Pageable pageable);
}
