package info.lmovse;

import info.lmovse.domain.Word;
import info.lmovse.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmovse on 2017/8/10.
 * Tomorrow is a nice day.
 */
public class ImportWordToRedis extends DictBootApiApplicationTests {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOps;

    public void importWord() {
        long count = wordRepository.count();
        int pageAccounts = (int) Math.ceil(count * 1.0 / 300);
        for (int i = 1; i <= pageAccounts; i++) {
            threadPoolTaskExecutor.execute(new PutToRedisTask(i));
        }
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
