package com.example.tcpclientserverquiz;

/**
 * Data model representing a single answer from a client.
 */
public class AnswerPackage {
    private String nickname;
    private String answerText;

    public AnswerPackage(String nickname, String answerText) {
        this.nickname = nickname;
        this.answerText = answerText;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAnswer() {
        return answerText;
    }

    @Override
    public String toString() {
        return nickname + ": " + answerText;
    }
}
