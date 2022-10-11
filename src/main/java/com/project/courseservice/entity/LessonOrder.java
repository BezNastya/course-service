package com.project.courseservice.entity;

public enum LessonOrder {
    FIRST("08:30-09:50"),
    SECOND("10:00-11:20"),
    THIRD("11:40-13:00"),
    FORTH("13:30-14:50"),
    FIFTH("15:00-16:20"),
    SIXTH("16:30-17:50"),
    SEVENTH("18:00-19:20");

    private final String order;

    LessonOrder(final String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }


    @Override
    public String toString() {
        return order;
    }
}
