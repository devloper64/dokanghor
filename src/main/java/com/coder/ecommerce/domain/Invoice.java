package com.coder.ecommerce.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "invoice_number", nullable = false)
    private String invoice_number;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private String to;

    @NotNull
    @Column(name = "item_list", nullable = false)
    private String item_list;

    @NotNull
    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "vat")
    private Double vat;

    @NotNull
    @Column(name = "total", nullable = false)
    private Double total;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private ZonedDateTime invoice_date;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public Invoice invoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
        return this;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getTo() {
        return to;
    }

    public Invoice to(String to) {
        this.to = to;
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getItem_list() {
        return item_list;
    }

    public Invoice item_list(String item_list) {
        this.item_list = item_list;
        return this;
    }

    public void setItem_list(String item_list) {
        this.item_list = item_list;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Invoice subtotal(Double subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public Invoice discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVat() {
        return vat;
    }

    public Invoice vat(Double vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTotal() {
        return total;
    }

    public Invoice total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ZonedDateTime getInvoice_date() {
        return invoice_date;
    }

    public Invoice invoice_date(ZonedDateTime invoice_date) {
        this.invoice_date = invoice_date;
        return this;
    }

    public void setInvoice_date(ZonedDateTime invoice_date) {
        this.invoice_date = invoice_date;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Invoice transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", invoice_number='" + getInvoice_number() + "'" +
            ", to='" + getTo() + "'" +
            ", item_list='" + getItem_list() + "'" +
            ", subtotal=" + getSubtotal() +
            ", discount=" + getDiscount() +
            ", vat=" + getVat() +
            ", total=" + getTotal() +
            ", invoice_date='" + getInvoice_date() + "'" +
            "}";
    }
}
