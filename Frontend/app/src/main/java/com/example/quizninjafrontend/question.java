package com.example.quizninjafrontend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class question implements Serializable {

    String question_url;
    String solution_url;
    String course;
    String source;
    ArrayList<comment> comments;

    public question() {
    }


    public question(JSONObject json) throws JSONException {
        this.question_url = json.optString("question_url", "");
        this.solution_url = json.optString("solution_url", "");
        this.course = json.optString("course", "");
        this.source = json.optString("source", "");
        this.comments = new ArrayList<>();


        JSONArray commentsArray = json.optJSONArray("comments");
        if (commentsArray != null) {
            for (int i = 0; i < commentsArray.length(); i++) {

                    JSONObject commentJson = commentsArray.getJSONObject(i);
                    comment commentObject = new comment(commentJson.get("username").toString(), commentJson.get("text").toString());
                    comments.add(commentObject);

            }
        }
    }


    public question(String question_url, String solution_url, String course, String source, JSONArray arr_comments) {

        this.question_url = question_url;
        this.solution_url = solution_url;
        this.course = course;
        this.source = source;
        ArrayList<comment> temp = new ArrayList<>();

        if(arr_comments != null){
            for (int i = 0; i < arr_comments.length(); i++) {
                try {
                    JSONObject commentJson = arr_comments.getJSONObject(i);
                    comment commentObject = new comment(commentJson.get("username").toString(), commentJson.get("text").toString());

                    temp.add(commentObject);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        this.comments=temp;

    }

    public String getQuestion_url() {
        return question_url;
    }

    public void setQuestion_url(String question_url) {
        this.question_url = question_url;
    }

    public String getSolution_url() {
        return solution_url;
    }

    public void setSolution_url(String solution_url) {
        this.solution_url = solution_url;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<comment> comments) {
        this.comments = comments;
    }
}
