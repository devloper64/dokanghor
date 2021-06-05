package com.coder.ecommerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.coder.ecommerce.domain.Transaction} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.TransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transactionid;

    private StringFilter transaction_method;

    private LongFilter paymentId;

    public TransactionCriteria() {
    }

    public TransactionCriteria(TransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transactionid = other.transactionid == null ? null : other.transactionid.copy();
        this.transaction_method = other.transaction_method == null ? null : other.transaction_method.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
    }

    @Override
    public TransactionCriteria copy() {
        return new TransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(StringFilter transactionid) {
        this.transactionid = transactionid;
    }

    public StringFilter getTransaction_method() {
        return transaction_method;
    }

    public void setTransaction_method(StringFilter transaction_method) {
        this.transaction_method = transaction_method;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionCriteria that = (TransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionid, that.transactionid) &&
            Objects.equals(transaction_method, that.transaction_method) &&
            Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionid,
        transaction_method,
        paymentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionid != null ? "transactionid=" + transactionid + ", " : "") +
                (transaction_method != null ? "transaction_method=" + transaction_method + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            "}";
    }

}
