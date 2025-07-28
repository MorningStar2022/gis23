package com.gis23.backend.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class TrackLine {
    private int taxi_id;
    private int track_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private int track_status;
    private double track_distance;
    private double track_speed;
    private String track_geom;

}
