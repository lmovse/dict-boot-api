package info.lmovse.web;

import info.lmovse.domain.Word;
import info.lmovse.service.IWordService;
import info.lmovse.util.Result;
import info.lmovse.util.ResultFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lmovse on 2017/8/5.
 * Tomorrow is a nice day.
 */
@RestController
public class ApiController {
    @Resource
    private IWordService wordService;

    @RequestMapping(value = "/q")
    public Result getWord(String q, HttpServletResponse response) {
        Word word = wordService.findWordByDictAndWordName(q, 23L);
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        return ResultFactory.getSuccesResult(word);
    }

}
