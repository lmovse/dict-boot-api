package info.lmovse.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by lmovse on 2017/8/4.
 * Tomorrow is a nice day.
 */
@Entity
@Table(name = "collosection")
public class Collosection implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Integer id;
    private String secheading;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "collosection_id")
    private Set<Collocate> collocates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecheading() {
        return secheading;
    }

    public void setSecheading(String secheading) {
        this.secheading = secheading;
    }

    public Set<Collocate> getCollocates() {
        return collocates;
    }

    public void setCollocates(Set<Collocate> collocates) {
        this.collocates = collocates;
    }

    @Override
    public String toString() {
        return "Collosection{" +
                "id=" + id +
                ", secheading='" + secheading + '\'' +
                ", collocates=" + collocates +
                '}';
    }
}
