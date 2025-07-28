package com.gis23.backend.mapper;

import com.gis23.backend.entity.TrackLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WebgisMapper {
    void insertTrackLine(
            @Param("taxi_id") int taxi_id,
            @Param("track_id") int track_id,
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("track_status") int track_status,
            @Param("track_distance") double track_distance,
            @Param("track_speed") double track_speed,
            @Param("track_geom") String track_geom
            );
    List<TrackLine> queryTrackLineByTime(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time);
    List<TrackLine> queryTrackLineByPolygonSPWithin(
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByPolygonEPWithin(
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByPolygonWithin(
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByPolygonCrosses(
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByTimeAndPolygonSPWithin(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByTimeAndPolygonEPWithin(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByTimeAndPolygonWithin(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("polygon") String polygon
    );
    List<TrackLine> queryTrackLineByTimeAndPolygonCrosses(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("polygon") String polygon
    );
    List<TrackLine> queryAllTrackLine(
    );
    List<TrackLine> queryTrackLineBySemanticWithin(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("point1") String point1,
            @Param("point2") String point2
    );
    List<TrackLine> queryTrackLineBySemanticNearby(
            @Param("start_time") Timestamp start_time,
            @Param("end_time") Timestamp end_time,
            @Param("point") String point
    );

}
