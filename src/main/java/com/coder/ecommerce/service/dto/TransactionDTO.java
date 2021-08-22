package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionid;


    private Long paymentId;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long transactionMethodId;

    private boolean is_transaction_completed;


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getTransactionMethodId() {
        return transactionMethodId;
    }

    public void setTransactionMethodId(Long transactionMethodId) {
        this.transactionMethodId = transactionMethodId;
    }

    public boolean isIs_transaction_completed() {
        return is_transaction_completed;
    }

    public void setIs_transaction_completed(boolean is_transaction_completed) {
        this.is_transaction_completed = is_transaction_completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", transactionid='" + getTransactionid() + "'" +
            ", paymentId=" + getPaymentId() +
            "}";
    }
}
