package info.lmovse.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query("select w from Word w where w.wordName=:wordName and w.dict.id=:dictId")
    Word findWordByDictAndAndWordName(@Param("wordName") String wordName, @Param("dictId") Long dictId);

}
