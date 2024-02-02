package uz.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.kun.entity.SmsSendHistoryEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface SmsSendHistoryRepository extends CrudRepository<SmsSendHistoryEntity, Integer>, PagingAndSortingRepository<SmsSendHistoryEntity,Integer> {

    List<SmsSendHistoryEntity> getByUserPhone(String phone);

    List<SmsSendHistoryEntity> findAllByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<SmsSendHistoryEntity> findAllBy(Pageable page);
}
