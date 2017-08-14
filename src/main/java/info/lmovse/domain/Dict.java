package info.lmovse.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by lmovse on 2017/8/4.
 * Tomorrow is a nice day.
 */
@Entity
@Table(name = "dict")
public class Dict {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Long id;

    @Column(name = "dict_name")
    private String dictName;

    @Column(name = "dict_size")
    private Long dictSize;

    @Column(name = "dict_version")
    private Integer dictVersion;

    @Column(name = "word_amount")
    private Integer wordAmount;

    @Column(name = "cover_url")
    private String coverUrl;
    private Integer state;

    @Column(name = "gmt_create")
    @JSONField(serialize = false)
    private Date gmtCreate;

    @Column(name = "gmt_modified")
    @JSONField(serialize = false)
    private Date gmtModified;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dict_id")
    private Set<Word> words;

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Long getDictSize() {
        return dictSize;
    }

    public void setDictSize(Long dictSize) {
        this.dictSize = dictSize;
    }

    public Integer getDictVersion() {
        return dictVersion;
    }

    public void setDictVersion(Integer dictVersion) {
        this.dictVersion = dictVersion;
    }

    public Integer getWordAmount() {
        return wordAmount;
    }

    public void setWordAmount(Integer wordAmount) {
        this.wordAmount = wordAmount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
