package info.lmovse;

import com.alibaba.fastjson.JSON;
import info.lmovse.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lmovse on 2017/8/4.
 * Tomorrow is a nice day.
 */
public class ImportWord {

    private SessionFactory sessionFactory;

    public void saveDict() throws IOException {
        File file = new File("G:\\dict.html");
        BufferedReader reader = new BufferedReader(new FileReader("G:\\dict.html"));
        String s;
        Dict dict = new Dict();
        dict.setDictName("朗文搭配");
        dict.setDictSize(file.length());
        dict.setGmtCreate(new Date());
        dict.setGmtModified(new Date());
        dict.setState(1);
        Set<Word> words = new HashSet<>();
        while ((s = reader.readLine()) != null) {
            Document document = Jsoup.parse(s);
            Word word = new Word();
            word.setGmtCreate(new Date());
            word.setGmtModified(new Date());
            if (!document.getElementsByClass("hwd").text().equals("love")) {
                continue;
            }
            word.setWordName(document.getElementsByClass("hwd").text());
            word.setPos(document.getElementsByClass("pos").text());
            word.setPron(document.getElementsByClass("pron").text());
            Elements senceElements = document.getElementsByClass("sense");
            Set<Sense> senses = new HashSet<>();
            for (Element senceElement : senceElements) {
                Sense sense = new Sense();
                sense.setDef(senceElement.getElementsByClass("def").text());
                Elements sectionElements = senceElement.getElementsByClass("collosection");
                Set<Collosection> collosections = new HashSet<>();
                for (Element sectionElement : sectionElements) {
                    Collosection collosection = new Collosection();
                    collosection.setSecheading(sectionElement.getElementsByClass("secheading").text());
                    Elements cateElements = sectionElement.getElementsByClass("collocate");
                    Set<Collocate> collocates = new HashSet<>();
                    for (Element cateElement : cateElements) {
                        Collocate collocate = new Collocate();
                        collocate.setCollexa(cateElement.getElementsByClass("collexa").text());
                        collocate.setColloc(cateElement.getElementsByClass("colloc").text());
                        collocate.setCollgloss(cateElement.getElementsByClass("collgloss").text());
                        collocates.add(collocate);
                    }
                    collosection.setCollocates(collocates);
                    collosections.add(collosection);
                }
                sense.setCollosections(collosections);
                senses.add(sense);
            }
            word.setSenses(senses);
            word.setDict(dict);
            words.add(word);
        }
        dict.setWordAmount(words.size());
        dict.setWords(words);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dict);
        transaction.commit();
        System.out.println("导出成功！");
        reader.close();
    }

    public void importNiuJin() throws IOException {
        File file = new File("G:\\niujiu.txt");
        BufferedReader reader = new BufferedReader(new FileReader("G:\\niujin.txt"));
        String s;
        Dict dict = new Dict();
        dict.setDictName("新牛津");
        dict.setDictSize(file.length());
        dict.setGmtCreate(new Date());
        dict.setGmtModified(new Date());
        dict.setState(1);
        Set<Word> words = new HashSet<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        while ((s = reader.readLine()) != null) {
            Document document = Jsoup.parse(s);
            Word word = new Word();
            word.setGmtCreate(new Date());
            word.setGmtModified(new Date());
            word.setWordName(document.getElementsByClass("words").text());
            word.setPron(document.getElementsByClass("words-yinbiao").text());
            Elements senceElements = document.getElementsByClass("cont-list");
            Set<Sense> senses = new HashSet<>();
            for (Element senceElement : senceElements) {
                Sense sense = new Sense();
                sense.setDef(senceElement.getElementsByAttributeValueContaining("class", "word-ext").text());
                Set<Collosection> collosections = new HashSet<>();

                Collosection collosection = new Collosection();
                collosection.setSecheading(senceElement.getElementsByAttributeValueContaining("class", "sense-pos").text());

                Elements cateElements = senceElement.getElementsByTag("dl");
                Set<Collocate> collocates = new HashSet<>();
                for (Element cateElement : cateElements) {
                    Collocate collocate = new Collocate();
                    collocate.setColloc(cateElement.getElementsByClass("sense-dt-def-ex").text());
                    collocate.setCollocTrans(cateElement.getElementsByClass("sense-dt-def-trans").text().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*"));
                    collocate.setCollexa(cateElement.getElementsByClass("cont-exam-ex").text());
                    collocate.setCollexaTrans(cateElement.getElementsByClass("cont-exam-trans").text().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*"));
                    collocates.add(collocate);
                }
                collosection.setCollocates(collocates);
                collosections.add(collosection);
                sense.setCollosections(collosections);
                senses.add(sense);
            }

            Elements phrasepaddingtop = document.getElementsByClass("phrasepaddingtop");
            Set<Phrase> phrases = new HashSet<>();
            for (Element element : phrasepaddingtop) {
                Phrase phrase = new Phrase();
                phrase.setPhrasePos(element.getElementsByClass("cont-list-pos").text());
                if (element.getElementsByTag("dl") != null
                        && !element.getElementsByTag("dl").isEmpty()) {
                    phrase.setPhraseEn(element.getElementsByTag("dl").get(0).child(0).text());
                }
                if (element.getElementsByClass("phrase-exam-trans") != null
                        && !element.getElementsByClass("phrase-exam-trans").isEmpty()) {
                    phrase.setPhraseCn(element.getElementsByClass("phrase-exam-trans").get(0).text());
                }
                if (element.getElementsByClass("cont-exam-ex") != null
                        && !element.getElementsByClass("cont-exam-ex").isEmpty()) {
                    phrase.setPhraseEx(element.getElementsByClass("cont-exam-ex").get(0).text());
                }
                if (element.getElementsByClass("cont-exam-trans") != null
                        && !element.getElementsByClass("cont-exam-trans").isEmpty()) {
                    phrase.setPhraseExTrans(element.getElementsByClass("cont-exam-trans").get(0).text());
                }
                phrase.setWord(word);
                phrases.add(phrase);
            }
            word.setPhrases(phrases);
            word.setSenses(senses);
            word.setEtymology(document.getElementsByClass("body-ciyuan").text());
            word.setDict(dict);
            words.add(word);
        }
        dict.setWordAmount(words.size());
        dict.setWords(words);
        try {
            session.save(dict);
        } catch (Exception e) {
            transaction.rollback();
        }
        transaction.commit();
        System.out.println("导出成功！");
        reader.close();
    }

    public void testJsonDict() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object o = session.createQuery("from Word where wordName = 'advice'").list().get(1);
        String s = JSON.toJSONString(o);
        System.out.println(s);
        transaction.commit();
    }

}
