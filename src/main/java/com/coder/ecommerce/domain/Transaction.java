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
public class Transaction extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transactionid", nullable = false)
    private String transactionid;


    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @NotNull
    @Column(name = "is_transaction_completed", nullable = false)
    private boolean is_transaction_completed;


    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private TransactionMethod transactionMethod;


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


    public boolean isIs_transaction_completed() {
        return is_transaction_completed;
    }

    public void setIs_transaction_completed(boolean is_transaction_completed) {
        this.is_transaction_completed = is_transaction_completed;
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


    public TransactionMethod getTransactionMethod() {
        return transactionMethod;
    }

    public void setTransactionMethod(TransactionMethod transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public Transaction transactionMethod(TransactionMethod transactionMethod){
        this.transactionMethod=transactionMethod;
        return this;
    }

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
            "}";
    }
}
