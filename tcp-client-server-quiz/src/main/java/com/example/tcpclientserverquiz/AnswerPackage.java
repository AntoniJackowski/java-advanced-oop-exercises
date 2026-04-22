package com.example.tcpclientserverquiz;

/**
 * Data Transfer Object (DTO) representing a single player's answer.
 * Encapsulates the player's nickname and their submitted answer text.
 * Used to safely pass data between the Producer and Consumer threads.
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

    public String getAnswerText() {
        return answerText;
    }

    @Override
    public String toString() {
        return nickname + ": " + answerText;
    }
}
