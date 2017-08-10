package info.lmovse.service.impl;

import info.lmovse.domain.Word;
import info.lmovse.domain.WordRepository;
import info.lmovse.service.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmovse on 2017/8/7.
 * Tomorrow is a nice day.
 */
@Service
public class WordServiceImpl implements IWordService {

    @Autowired
    private WordRepository wordRepository;

//    @Autowired
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOps;

    @Override
    public Word findWordByDictAndWordName(String wordName, Long dictId) {
        // 只适用于测试，生产环境下要考虑到并发请求
//        if (!redisTemplate.hasKey("dict")) {
//            long count = wordRepository.count();
//            int pageAccounts = (int) Math.ceil(count * 1.0 / 300);
//            for (int i = 1; i <= pageAccounts; i++) {
//                threadPoolTaskExecutor.execute(new PutToRedisTask(i));
//            }
//        }

        if (StringUtils.isEmpty(wordName) || StringUtils.isEmpty(dictId)) {
            throw new RuntimeException("必要参数不能为空！");
        }

        Word word = null;

        // 判断 redis 中是否有对应的 key ，有的话直接取值
        if (hashOps.hasKey("dict", wordName)) {
            return (Word) hashOps.get("dict", wordName);
        }

        if (hashOps.hasKey("dict", wordName + 1)) {
            word = (Word) hashOps.get("dict", wordName + 1);
            word.setWordName(wordName);
            return word;
        }

        // redis 中没有对应的 key，查询数据库
        word = wordRepository.findWordByDictAndAndWordName(wordName, dictId);
        if (word == null) {
            word = wordRepository.findWordByDictAndAndWordName(wordName + 1, dictId);
            if (word == null) {
                throw new RuntimeException("暂无此记录！");
            }
        }
        hashOps.put("dict", wordName, word);
        return word;
    }

    /**
     * 由于 spring 事务管理是将 session 绑定到 transaction 中，因此当在一个事务中开启了一个新线程时， transaction
     * 并不能进行传递，所以在进行懒加载时就会报 noSession 错误，这里采取的一个办法是禁用了懒加载。
     */
    private class PutToRedisTask implements Runnable {
        private int page;

        PutToRedisTask(int page) {
            this.page = page;
        }

        @Override
        public void run() {
            Page<Word> page = wordRepository.findAll(new PageRequest(this.page, 300));
            Map<String, Object> wordMap = new HashMap<>();
            for (Word word : page.getContent()) {
                wordMap.put(word.getWordName(), word);
            }
            hashOps.putAll("dict", wordMap);
            wordMap = null;
        }
    }

}
