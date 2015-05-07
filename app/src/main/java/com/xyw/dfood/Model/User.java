package com.xyw.dfood.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	private int id;
	private String name;
	private String passWord;
	private String imageUrl;
    @JsonIgnore
	private List<Comment> comments=new ArrayList<Comment>();
    @JsonIgnore
    private List<Food> foods=new ArrayList<Food>();
    @JsonIgnore
	private List<Food> conllect_food=new ArrayList<Food>();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String user_name) {
		this.name = user_name;
	}
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    public List<Food> getConllect_food() {
        return conllect_food;
    }

    public void setConllect_food(List<Food> conllect_food) {
        this.conllect_food = conllect_food;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
