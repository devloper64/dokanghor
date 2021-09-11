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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.coder.ecommerce.domain.Invoice} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoice_number;

    private StringFilter to;

    private StringFilter item_list;

    private DoubleFilter subtotal;

    private DoubleFilter discount;

    private DoubleFilter vat;

    private DoubleFilter total;

    private ZonedDateTimeFilter invoice_date;

    private LongFilter transactionId;

    public InvoiceCriteria() {
    }

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.invoice_number = other.invoice_number == null ? null : other.invoice_number.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.item_list = other.item_list == null ? null : other.item_list.copy();
        this.subtotal = other.subtotal == null ? null : other.subtotal.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.vat = other.vat == null ? null : other.vat.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.invoice_date = other.invoice_date == null ? null : other.invoice_date.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(StringFilter invoice_number) {
        this.invoice_number = invoice_number;
    }

    public StringFilter getTo() {
        return to;
    }

    public void setTo(StringFilter to) {
        this.to = to;
    }

    public StringFilter getItem_list() {
        return item_list;
    }

    public void setItem_list(StringFilter item_list) {
        this.item_list = item_list;
    }

    public DoubleFilter getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(DoubleFilter subtotal) {
        this.subtotal = subtotal;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
    }

    public DoubleFilter getVat() {
        return vat;
    }

    public void setVat(DoubleFilter vat) {
        this.vat = vat;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public ZonedDateTimeFilter getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(ZonedDateTimeFilter invoice_date) {
        this.invoice_date = invoice_date;
    }

    public LongFilter getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(LongFilter transactionId) {
        this.transactionId = transactionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoice_number, that.invoice_number) &&
            Objects.equals(to, that.to) &&
            Objects.equals(item_list, that.item_list) &&
            Objects.equals(subtotal, that.subtotal) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(vat, that.vat) &&
            Objects.equals(total, that.total) &&
            Objects.equals(invoice_date, that.invoice_date) &&
            Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoice_number,
        to,
        item_list,
        subtotal,
        discount,
        vat,
        total,
        invoice_date,
        transactionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoice_number != null ? "invoice_number=" + invoice_number + ", " : "") +
                (to != null ? "to=" + to + ", " : "") +
                (item_list != null ? "item_list=" + item_list + ", " : "") +
                (subtotal != null ? "subtotal=" + subtotal + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (vat != null ? "vat=" + vat + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (invoice_date != null ? "invoice_date=" + invoice_date + ", " : "") +
                (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
            "}";
    }

}
