package info.lmovse.service;

import info.lmovse.domain.Word;

/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
public interface IWordService {

    Word findWordByDictAndWordName(String wordName, Long dictId);

}
