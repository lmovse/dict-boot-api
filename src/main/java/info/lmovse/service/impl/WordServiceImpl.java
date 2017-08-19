package info.lmovse.service.impl;

import info.lmovse.domain.Word;
import info.lmovse.repository.WordRepository;
import info.lmovse.service.IWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
@Service
public class WordServiceImpl implements IWordService {
    private Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOps;

    @Override
    public Word findWordByDictAndWordName(String wordName, Long dictId) {
        if (StringUtils.isEmpty(wordName) || StringUtils.isEmpty(dictId)) {
            throw new RuntimeException("必要参数不能为空！");
        }

        // 从 redis 中查询，有数据的话更新查询次数后返回
        Word word = null;
        word = getWordFromRedis(wordName);
        if (word != null) {
            updateQueryCounts(wordName, word);
            return word;
        }

        // redis 中没有缓存该单词，查询数据库
        word = wordRepository.findWordByDictAndAndWordName(wordName, dictId);
        if (word == null) {
            word = wordRepository.findWordByDictAndAndWordName(wordName + 1, dictId);
            if (word == null) {
                throw new RuntimeException("暂无此记录！");
            }
        }

        // 更新查询次数并放入 redis 中
        wordRepository.save(word);
        putToRedis(wordName, word);
        return word;
    }

    // 保存到 redis 中
    private void putToRedis(String wordName, Word word) {
        try {
            hashOps.put("dict", wordName, word);
        } catch (Exception e) {
            logger.error("缓存失败！失败详情：", e.getMessage());
        }
    }

    // 从 redis 中获取值
    private Word getWordFromRedis(String wordName) {
        Word word = null;
        try {
            if (hashOps.hasKey("dict", wordName)) {
                word = (Word) hashOps.get("dict", wordName);
            } else if (hashOps.hasKey("dict", wordName + 1)) {
                word = (Word) hashOps.get("dict", wordName + 1);
                word.setWordName(wordName);
            }
        } catch (Exception e) {
            logger.error("查询缓存失败！失败详情：", e.getMessage());
        }
        return word;
    }

    // 更新查询次数
    private void updateQueryCounts(String wordName, Word wordR) {
        wordR.setQueryAccount(wordR.getQueryAccount() + 1);
        putToRedis(wordName, wordR);
        if (wordR.getQueryAccount() % 10 == 0) {
            wordRepository.save(wordR);
        }
    }

}
