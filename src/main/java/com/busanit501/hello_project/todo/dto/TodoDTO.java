package com.busanit501.hello_project.todo.dto;

import java.time.LocalDate;

public class TodoDTO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

    // 반자동으로 생성.
    // 1) 멤버 선택 후, 반자동 생성. 먼저 이것부터. 작업.
    // 2) lombok 라이브러리, 메모리에 자동생성

    public Long getTno() {
        return tno;
    }

    public void setTno(Long tno) {
        this.tno = tno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}';
    }

    public TodoDTO(Long tno, String title, LocalDate dueDate, boolean finished) {
        this.tno = tno;
        this.title = title;
        this.dueDate = dueDate;
        this.finished = finished;
    }

    public TodoDTO() {}
}
