package com.coder.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transactionid", nullable = false)
    private String transactionid;

    @NotNull
    @Column(name = "transaction_method", nullable = false)
    private String transaction_method;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private Payment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public Transaction transactionid(String transactionid) {
        this.transactionid = transactionid;
        return this;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTransaction_method() {
        return transaction_method;
    }

    public Transaction transaction_method(String transaction_method) {
        this.transaction_method = transaction_method;
        return this;
    }

    public void setTransaction_method(String transaction_method) {
        this.transaction_method = transaction_method;
    }

    public Payment getPayment() {
        return payment;
    }

    public Transaction payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", transactionid='" + getTransactionid() + "'" +
            ", transaction_method='" + getTransaction_method() + "'" +
            "}";
    }
}
