package com.app.urbfs.model;

public class HistoryModel {
    private String payname;
    private String payoutdate;
    private String amount;
    private String tds;
    private String admin;
    private String netpay;

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname;
    }

    public String getPayoutdate() {
        return payoutdate;
    }

    public void setPayoutdate(String payoutdate) {
        this.payoutdate = payoutdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getNetpay() {
        return netpay;
    }

    public void setNetpay(String netpay) {
        this.netpay = netpay;
    }
}
