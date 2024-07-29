package domain.model;

import java.util.Date;

public class ElectricBill {
    private String idClient;
    private String fullName;
    private String person;
    private String monthlyElectricly;
    private Date time;
    private Integer qty;
    private Double unitPrice;
    private Double quota;
    private Double total;

    public ElectricBill(String idClient, String fullName, String person, String monthlyElectricly, Date time,
            Integer qty, Double unitPrice, Double quota) {
        this.idClient = idClient;
        this.fullName = fullName;
        this.person = person;
        this.monthlyElectricly = monthlyElectricly;
        this.time = time;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.quota = quota;
    }

   

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getMonthlyElectricly() {
        return monthlyElectricly;
    }

    public void setMonthlyElectricly(String monthlyElectricly) {
        this.monthlyElectricly = monthlyElectricly;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        if (qty <= quota) {
            return qty * unitPrice;
        } else {
            return quota * unitPrice + (qty - quota) * unitPrice * 2.5;
        }
    }

}
