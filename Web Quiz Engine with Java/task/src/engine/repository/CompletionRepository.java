package engine.repository;

import engine.entity.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<QuizCompletion, Long> {

    Page<QuizCompletion> findAllByUserName(String userName, Pageable pageable);


}
