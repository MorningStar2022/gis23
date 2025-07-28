package com.gis23.backend.service;

import com.gis23.backend.entity.TrackLine;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public interface WebgisService {
    void LoadSaveToDB(String file_path) throws IOException;
    List<TrackLine> queryTrackLineByTime(Timestamp start_time,Timestamp end_time);
    String TrackLineListToJson(List<TrackLine> trackLines);
    List<TrackLine> queryTrackLineByPolygonSPWithin(String polygon);
    List<TrackLine> queryTrackLineByPolygonEPWithin(String polygon);
    List<TrackLine> queryTrackLineByPolygonWithin(String polygon);
    List<TrackLine> queryTrackLineByPolygonCrosses(String polygon);
    List<TrackLine> queryTrackLineByTimeAndPolygonSPWithin(Timestamp start_time,Timestamp end_time,String polygon);
    List<TrackLine> queryTrackLineByTimeAndPolygonEPWithin(Timestamp start_time,Timestamp end_time,String polygon);
    List<TrackLine> queryTrackLineByTimeAndPolygonWithin(Timestamp start_time,Timestamp end_time,String polygon);
    List<TrackLine> queryTrackLineByTimeAndPolygonCrosses(Timestamp start_time,Timestamp end_time,String polygon);
    List<TrackLine> queryAllTrackLine();
    List<TrackLine> queryTrackLineBySemanticWithin(Timestamp start_time,Timestamp end_time,String point1,String point2);
    List<TrackLine> queryTrackLineBySemanticNearby(Timestamp start_time,Timestamp end_time,String point);
}
