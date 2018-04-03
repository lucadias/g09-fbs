/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.appe.fbs.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luca_
 */
@Entity
@Table(name = "Article")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
    , @NamedQuery(name = "Article.findByIdArticle", query = "SELECT a FROM Article a WHERE a.idArticle = :idArticle")
    , @NamedQuery(name = "Article.findByName", query = "SELECT a FROM Article a WHERE a.name = :name")
    , @NamedQuery(name = "Article.findByArticlenumber", query = "SELECT a FROM Article a WHERE a.articlenumber = :articlenumber")
    , @NamedQuery(name = "Article.findByDescription", query = "SELECT a FROM Article a WHERE a.description = :description")
    , @NamedQuery(name = "Article.findByInStock", query = "SELECT a FROM Article a WHERE a.inStock = :inStock")
    , @NamedQuery(name = "Article.findByPrice", query = "SELECT a FROM Article a WHERE a.price = :price")
    , @NamedQuery(name = "Article.findByMinInStock", query = "SELECT a FROM Article a WHERE a.minInStock = :minInStock")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArticle")
    private Integer idArticle;
    @Column(name = "Name")
    private String name;
    @Column(name = "Articlenumber")
    private Integer articlenumber;
    @Column(name = "Description")
    private String description;
    @Column(name = "InStock")
    private Integer inStock;
    @Column(name = "Price")
    private Long price;
    @Column(name = "MinInStock")
    private Integer minInStock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleidArticle")
    private Collection<Reorder> reorderCollection;

    public Article() {
    }

    public Article(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArticlenumber() {
        return articlenumber;
    }

    public void setArticlenumber(Integer articlenumber) {
        this.articlenumber = articlenumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getMinInStock() {
        return minInStock;
    }

    public void setMinInStock(Integer minInStock) {
        this.minInStock = minInStock;
    }

    @XmlTransient
    public Collection<Reorder> getReorderCollection() {
        return reorderCollection;
    }

    public void setReorderCollection(Collection<Reorder> reorderCollection) {
        this.reorderCollection = reorderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticle != null ? idArticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.idArticle == null && other.idArticle != null) || (this.idArticle != null && !this.idArticle.equals(other.idArticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.appe.fbs.model.entities.Article[ idArticle=" + idArticle + " ]";
    }
    
}
