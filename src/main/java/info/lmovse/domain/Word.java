package info.lmovse.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by lmovse on 2017/8/4.
 * Tomorrow is a nice day.
 */
@Entity
@Table(name = "word")
public class Word implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Integer id;

    @Column(name = "word_name")
    private String wordName;
    private String pron;
    private String pos;
    private String etymology;

    @Column(name = "query_account")
    private Long queryAccount;

    @Column(name = "gmt_create")
    @JSONField(serialize = false)
    private Date gmtCreate;

    @Column(name = "gmt_modified")
    @JSONField(serialize = false)
    private Date gmtModified;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_id")
    private Set<Sense> senses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_id")
    private Set<Phrase> phrases;

    @ManyToOne
    @JoinColumn(name = "dict_id")
    @JSONField(serialize = false)
    private Dict dict;

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String query) {
        this.wordName = query;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Set<Sense> getSenses() {
        return senses;
    }

    public void setSenses(Set<Sense> senses) {
        this.senses = senses;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getEtymology() {
        return etymology;
    }

    public void setEtymology(String etymology) {
        this.etymology = etymology;
    }

    public Set<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(Set<Phrase> phrases) {
        this.phrases = phrases;
    }

    public Long getQueryAccount() {
        return queryAccount;
    }

    public void setQueryAccount(Long queryAccount) {
        this.queryAccount = queryAccount;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", wordName='" + wordName + '\'' +
                ", pron='" + pron + '\'' +
                ", pos='" + pos + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", senses=" + senses +
                '}';
    }
}
