package com.tivizado.tivizado.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favourites")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Long showid;
    private Integer userid;
    private Number score;
    private Number rank;
    private Number cap;
    private Boolean finish;

    public Favourite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getShowid() {
        return showid;
    }

    public void setShowid(Long showid) {
        this.showid = showid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Number getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = score;
    }

    public Number getRank() {
        return rank;
    }

    public void setRank(Number rank) {
        this.rank = rank;
    }

    public Number getCap() {
        return cap;
    }

    public void setCap(Number cap) {
        this.cap = cap;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

   

}
