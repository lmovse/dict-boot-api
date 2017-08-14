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
@Table(name = "sense")
public class Sense implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @JSONField(serialize = false)
    private Integer id;
    private String def;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sense_id")
    private Set<Collosection> collosections;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public Set<Collosection> getCollosections() {
        return collosections;
    }

    public void setCollosections(Set<Collosection> collosections) {
        this.collosections = collosections;
    }

    @Override
    public String toString() {
        return "Sense{" +
                "id=" + id +
                ", def='" + def + '\'' +
                ", collosections=" + collosections +
                '}';
    }
}
