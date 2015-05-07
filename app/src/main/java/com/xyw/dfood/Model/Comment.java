package com.xyw.dfood.Model;


import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private String detatils;
    private String date;
    private User user;
    private Food food;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDetatils() {
        return detatils;
    }

    public void setDetatils(String detatils) {
        this.detatils = detatils;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
	public Food getFood() {
		return food;
		}

	public void setFood(Food food) {
		this.food = food;
	}
}
