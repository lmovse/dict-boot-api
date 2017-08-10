package info.lmovse.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lmovse on 2017/8/5.
 * Tomorrow is a nice day.
 */
@Entity
@Table(name = "phrase")
public class Phrase {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_id")
    @JSONField(serialize = false)
    private Word word;

    @Column(name = "phrase_pos")
    private String phrasePos;

    @Column(name = "phrase_en")
    private String phraseEn;

    @Column(name = "phrase_cn")
    private String phraseCn;

    @Column(name = "phrase_ex")
    private String phraseEx;

    @Column(name = "phrase_ex_trans")
    private String phraseExTrans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getPhraseEn() {
        return phraseEn;
    }

    public void setPhraseEn(String phraseEn) {
        this.phraseEn = phraseEn;
    }

    public String getPhraseCn() {
        return phraseCn;
    }

    public void setPhraseCn(String phraseCn) {
        this.phraseCn = phraseCn;
    }

    public String getPhrasePos() {
        return phrasePos;
    }

    public void setPhrasePos(String phrasePos) {
        this.phrasePos = phrasePos;
    }

    public String getPhraseEx() {
        return phraseEx;
    }

    public void setPhraseEx(String phraseEx) {
        this.phraseEx = phraseEx;
    }

    public String getPhraseExTrans() {
        return phraseExTrans;
    }

    public void setPhraseExTrans(String phraseExTrans) {
        this.phraseExTrans = phraseExTrans;
    }
}
