package com.example.tcpclientserverquiz;

/**
 * Data Transfer Object (DTO) representing a single player's answer.
 * Encapsulates the player's nickname and their submitted answer text.
 * Used to safely pass data between the Producer and Consumer threads.
 */
public class AnswerPackage {
    private String nickname;
    private String answerText;
    private String ipAddress;

    public AnswerPackage(String nickname, String answerText, String ipAddress) {
        this.nickname = nickname;
        this.answerText = answerText;
        this.ipAddress = ipAddress;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return nickname + " (" + ipAddress + ") " + answerText;
    }
}
