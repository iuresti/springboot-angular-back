package com.iuresti.learning.backendclients.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "folios")
public class Folio implements Serializable {

    private static final long serialVersionUID = 4076353971093765533L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String observation;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"folios", "hibernateLazyInitializer", "handler"})
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "folio_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FolioItem> items;

    public Folio() {
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<FolioItem> getItems() {
        return items;
    }

    public void setItems(List<FolioItem> items) {
        this.items = items;
    }

    public Double getTotal() {
        double total = 0;

        for (FolioItem item : items) {
            total += item.calculateAmount();
        }

        return total;
    }
}
