package info.lmovse.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lmovse on 2017/8/4.
 * Tomorrow is a nice day.
 */
@Entity
@Table(name = "collocate")
public class Collocate implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Integer id;
    private String collgloss;

    private String colloc;

    @Column(name = "colloc_trans")
    private String collocTrans;

    private String collexa;

    @Column(name = "collexa_trans")
    private String collexaTrans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColloc() {
        return colloc;
    }

    public void setColloc(String colloc) {
        this.colloc = colloc;
    }

    public String getCollgloss() {
        return collgloss;
    }

    public void setCollgloss(String collgloss) {
        this.collgloss = collgloss;
    }

    public String getCollexa() {
        return collexa;
    }

    public void setCollexa(String collexa) {
        this.collexa = collexa;
    }

    public String getCollocTrans() {
        return collocTrans;
    }

    public void setCollocTrans(String collocTrans) {
        this.collocTrans = collocTrans;
    }

    public String getCollexaTrans() {
        return collexaTrans;
    }

    public void setCollexaTrans(String collexaTrans) {
        this.collexaTrans = collexaTrans;
    }

    @Override
    public String toString() {
        return "Collocate{" +
                "id=" + id +
                ", collgloss='" + collgloss + '\'' +
                ", colloc='" + colloc + '\'' +
                ", collocTrans='" + collocTrans + '\'' +
                ", collexa='" + collexa + '\'' +
                ", collexaTrans='" + collexaTrans + '\'' +
                '}';
    }

}
