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


    private LongFilter paymentId;

    private LongFilter transactionMethodId;

    private BooleanFilter is_transaction_completed;

    public TransactionCriteria() {
    }

    public TransactionCriteria(TransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transactionid = other.transactionid == null ? null : other.transactionid.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.transactionMethodId = other.transactionMethodId == null ? null : other.transactionMethodId.copy();
        this.is_transaction_completed = other.is_transaction_completed == null ? null : other.is_transaction_completed.copy();
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


    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getTransactionMethodId() {
        return transactionMethodId;
    }

    public void setTransactionMethodId(LongFilter transactionMethodId) {
        this.transactionMethodId = transactionMethodId;
    }

    public BooleanFilter getIs_transaction_completed() {
        return is_transaction_completed;
    }

    public void setIs_transaction_completed(BooleanFilter is_transaction_completed) {
        this.is_transaction_completed = is_transaction_completed;
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
                Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(transactionMethodId, that.transactionMethodId)&&
                Objects.equals(is_transaction_completed, that.is_transaction_completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            transactionid,
            paymentId,
            transactionMethodId,
            is_transaction_completed
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (transactionid != null ? "transactionid=" + transactionid + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (transactionMethodId != null ? "transactionMethodId=" + transactionMethodId + ", " : "") +
            (is_transaction_completed != null ? "is_transaction_completed=" + is_transaction_completed + ", " : "") +
            "}";
    }

}
