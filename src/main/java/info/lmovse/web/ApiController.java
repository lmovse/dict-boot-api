package info.lmovse.web;

import info.lmovse.domain.Word;
import info.lmovse.service.IWordService;
import info.lmovse.util.Result;
import info.lmovse.util.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lmovse on 2017/8/5.
 * Tomorrow is a nice day.
 */
@Controller
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Resource
    private IWordService wordService;

    @RequestMapping(value = "/q", produces="application/json; charset=utf-8")
    @ResponseBody
    public Result getWord(@RequestParam String q, @RequestParam Long dictId) {
        Word word = wordService.findWordByDictAndWordName(q, dictId);
        return ResultFactory.getSuccesResult(word);
    }

    /**
     * 跳转到页面查词界面
     * @return 页面资源
     */
    @RequestMapping("/")
    public String quryIndex() {
        return "index";
    }

    /**
     * 统一异常处理方法
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        logger.error("未捕捉异常，异常详情：{}", e.getMessage(), e);
        return ResultFactory.getFailResult(e.getMessage());
    }

}
