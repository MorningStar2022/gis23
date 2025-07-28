package com.gis23.backend.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class TrackPoint {
    int taxi_id;
    Timestamp date;
    String longitude;
    String latitude;
    int direction;
    int speed;
    String status;
    int track_id;
}
