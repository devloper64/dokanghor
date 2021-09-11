package com.coder.ecommerce.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Invoice} entity.
 */
public class InvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String invoice_number;

    @NotNull
    private String to;

    @NotNull
    private String item_list;

    @NotNull
    private Double subtotal;

    @NotNull
    private Double discount;

    private Double vat;

    @NotNull
    private Double total;

    private ZonedDateTime invoice_date;


    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getItem_list() {
        return item_list;
    }

    public void setItem_list(String item_list) {
        this.item_list = item_list;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ZonedDateTime getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(ZonedDateTime invoice_date) {
        this.invoice_date = invoice_date;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDTO)) {
            return false;
        }

        return id != null && id.equals(((InvoiceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDTO{" +
            "id=" + getId() +
            ", invoice_number='" + getInvoice_number() + "'" +
            ", to='" + getTo() + "'" +
            ", item_list='" + getItem_list() + "'" +
            ", subtotal=" + getSubtotal() +
            ", discount=" + getDiscount() +
            ", vat=" + getVat() +
            ", total=" + getTotal() +
            ", invoice_date='" + getInvoice_date() + "'" +
            ", transactionId=" + getTransactionId() +
            "}";
    }
}
