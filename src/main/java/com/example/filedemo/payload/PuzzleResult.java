package com.example.filedemo.payload;

public class PuzzleResult {
    private String fileName;
    private String fileDownloadUri;
    private String puzzleAnswer;

    public PuzzleResult(String fileName, String fileDownloadUri, String puzzleAnswer) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.puzzleAnswer = puzzleAnswer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getPuzzleAnswer() {
        return puzzleAnswer;
    }

    public void setPuzzleAnswer(String puzzleAnswer) {
        this.puzzleAnswer = puzzleAnswer;
    }
}
