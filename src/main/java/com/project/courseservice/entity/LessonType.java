package com.project.courseservice.entity;

public enum LessonType {
    LECTURE("Lecture"),
    SEMINAR("Seminar"),
    PRACTICE("Practice"),
    LAB("Lab");

    private final String type;

    LessonType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return type;
    }
}
