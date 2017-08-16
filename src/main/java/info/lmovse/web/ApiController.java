package info.lmovse.web;

import info.lmovse.domain.Word;
import info.lmovse.service.IWordService;
import info.lmovse.util.Result;
import info.lmovse.util.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lmovse on 2017/8/5.
 * Tomorrow is a nice day.
 */
@Controller
public class ApiController {
    @Resource
    private IWordService wordService;

    @RequestMapping(value = "/q", produces="application/json; charset=utf-8")
    @ResponseBody
    public Result getWord(String q) {
        Word word = wordService.findWordByDictAndWordName(q, 23L);
        return ResultFactory.getSuccesResult(word);
    }

    /**
     * 跳转到页面查词界面
     * @return 页面资源
     */
    @RequestMapping("/")
    public String queryIndex() {
        return "index";
    }

}
