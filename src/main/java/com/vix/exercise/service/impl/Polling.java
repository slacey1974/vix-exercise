package com.vix.exercise.service.impl;

import java.time.LocalDate;


public class Polling {
    private int id;
    private String status;
    private LocalDate modifiedDate;

    public Polling() {}
    public Polling (int id, String status) { this.id = id; this.status = status;}
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getModifiedDate() { return this.modifiedDate; }
    public void setModifiedDate(LocalDate ModifiedDate) { this.modifiedDate = modifiedDate; }

}
