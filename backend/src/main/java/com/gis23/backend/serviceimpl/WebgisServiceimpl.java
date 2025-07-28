package com.gis23.backend.serviceimpl;

import com.gis23.backend.entity.TrackLine;
import com.gis23.backend.entity.TrackPoint;
import com.gis23.backend.mapper.WebgisMapper;
import com.gis23.backend.service.WebgisService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebgisServiceimpl implements WebgisService {
    @Autowired
    WebgisMapper webgisMapper;
    public static double calculateDistance(TrackPoint p1, TrackPoint p2) {
        double earthRadius = 6371; // 地球半径，单位：km

        double dLat = Math.toRadians(Double.parseDouble(p2.getLatitude())) - Math.toRadians(Double.parseDouble(p1.getLatitude()));
        double dLon = Math.toRadians(Double.parseDouble(p2.getLongitude())) - Math.toRadians(Double.parseDouble(p1.getLongitude()));

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(Double.parseDouble(p1.getLatitude()))) * Math.cos(Math.toRadians(Double.parseDouble(p2.getLatitude()))) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
    @Override
    public void LoadSaveToDB(String file_path) throws IOException{
        File file=new File(file_path);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line="";
        line= br.readLine();
        line= br.readLine();
        List<TrackPoint> trackPointList=new ArrayList<>();
        int current_track_id=-1;
        while (line!= null){
            String[] line_info=line.split(",");
            line= br.readLine();
            TrackPoint point=new TrackPoint();
            point.setTaxi_id(Integer.parseInt(line_info[0]));
            point.setDate(Timestamp.valueOf(line_info[1]));
            point.setLongitude(line_info[2]);
            point.setLatitude(line_info[3]);
            point.setDirection(Integer.parseInt(line_info[4]));
            point.setSpeed(Integer.parseInt(line_info[5]));
            point.setStatus(line_info[6]);
            point.setTrack_id(Integer.parseInt(line_info[line_info.length-1]));
            if(current_track_id!= point.getTrack_id()){
                current_track_id= point.getTrack_id();
                if(trackPointList.size()>1){
                    TrackLine new_trackLine=new TrackLine();
                    int list_length=trackPointList.size();
                    new_trackLine.setTrack_id(trackPointList.get(0).getTrack_id());
                    new_trackLine.setTaxi_id(trackPointList.get(0).getTaxi_id());
                    new_trackLine.setStart_time(trackPointList.get(0).getDate());
                    new_trackLine.setEnd_time(trackPointList.get(list_length-1).getDate());
                    if("空车".equals(trackPointList.get(0).getStatus())){
                        new_trackLine.setTrack_status(0);
                    }
                    else{
                        new_trackLine.setTrack_status(1);
                    }
                    double total_distance=0;
                    double total_speed=0;
                    StringBuilder sb=new StringBuilder();
                    sb.append("LINESTRING(");
                    for(int i=0;i<list_length;i++){
                        TrackPoint temp_point=trackPointList.get(i);
                        total_speed+=trackPointList.get(i).getSpeed();
                        sb.append(temp_point.getLongitude()).append(" ").append(temp_point.getLatitude()).append(",");
                    }
                    new_trackLine.setTrack_speed(total_speed/list_length);
                    sb.delete(sb.length()-1,sb.length());
                    sb.append(")");
                    new_trackLine.setTrack_geom(sb.toString());
                    for(int i=0;i<list_length-1;i++){
                        total_distance+=calculateDistance(trackPointList.get(i),trackPointList.get(i+1));
                    }
                    new_trackLine.setTrack_distance(total_distance);
                    webgisMapper.insertTrackLine(new_trackLine.getTaxi_id(),new_trackLine.getTrack_id(),new_trackLine.getStart_time(),new_trackLine.getEnd_time(),new_trackLine.getTrack_status(),new_trackLine.getTrack_distance(),new_trackLine.getTrack_speed(),new_trackLine.getTrack_geom());
                }
                trackPointList.clear();
                trackPointList.add(point);
            }
            else{
                trackPointList.add(point);
            }
        }
    }
    @Override
    public List<TrackLine> queryTrackLineByTime(Timestamp start_time,Timestamp end_time){
        return webgisMapper.queryTrackLineByTime(start_time,end_time);
    }
    @Override
    public String TrackLineListToJson(List<TrackLine> trackLines){
        StringBuilder sb=new StringBuilder();
        sb.append("{\"data\":[");
        int trackline_num=trackLines.size();
        for (int i=0;i<trackline_num;i++) {
            TrackLine this_trackline=trackLines.get(i);
            sb.append("{\"taxi_id\":").append(this_trackline.getTaxi_id()).append(",");
            sb.append("\"track_id\":").append(this_trackline.getTrack_id()).append(",");
            sb.append("\"start_time\":\"").append(this_trackline.getStart_time().toString()).append("\",");
            sb.append("\"end_time\":\"").append(this_trackline.getEnd_time().toString()).append("\",");
//            String polyline=this_trackline.getTrack_geom().substring(7,this_trackline.getTrack_geom().length()-1);
            sb.append("\"track_status\":").append(this_trackline.getTrack_status()).append(",");
            sb.append("\"track_distance\":").append(this_trackline.getTrack_distance()).append(",");
            sb.append("\"track_speed\":").append(this_trackline.getTrack_speed()).append(",");
            sb.append("\"track_geom\":\"").append(this_trackline.getTrack_geom()).append("\"},");

        }
        if(trackline_num!=0){
            sb.delete(sb.length()-1,sb.length());

        }
        sb.append("]}");
        return sb.toString();
    }
    @Override
    public List<TrackLine> queryTrackLineByPolygonSPWithin(String polygon){
        return webgisMapper.queryTrackLineByPolygonSPWithin(polygon);
    }
    public List<TrackLine> queryTrackLineByPolygonEPWithin(String polygon){
        return webgisMapper.queryTrackLineByPolygonEPWithin(polygon);
    }
    public List<TrackLine> queryTrackLineByPolygonWithin(String polygon){
        return webgisMapper.queryTrackLineByPolygonWithin(polygon);
    }
    public List<TrackLine> queryTrackLineByPolygonCrosses(String polygon){
        return webgisMapper.queryTrackLineByPolygonCrosses(polygon);
    }
    public List<TrackLine> queryTrackLineByTimeAndPolygonSPWithin(Timestamp start_time,Timestamp end_time,String polygon){
        return webgisMapper.queryTrackLineByTimeAndPolygonSPWithin(start_time,end_time,polygon);
    }
    public List<TrackLine> queryTrackLineByTimeAndPolygonEPWithin(Timestamp start_time,Timestamp end_time,String polygon){
        return webgisMapper.queryTrackLineByTimeAndPolygonEPWithin(start_time,end_time,polygon);
    }
    public List<TrackLine> queryTrackLineByTimeAndPolygonWithin(Timestamp start_time,Timestamp end_time,String polygon){
        return webgisMapper.queryTrackLineByTimeAndPolygonWithin(start_time,end_time,polygon);
    }
    public List<TrackLine> queryTrackLineByTimeAndPolygonCrosses(Timestamp start_time,Timestamp end_time,String polygon){
        return webgisMapper.queryTrackLineByTimeAndPolygonCrosses(start_time,end_time,polygon);
    }
    public List<TrackLine> queryAllTrackLine(){
        return webgisMapper.queryAllTrackLine();
    }
    public List<TrackLine> queryTrackLineBySemanticWithin(Timestamp start_time,Timestamp end_time,String point1,String point2){
        return webgisMapper.queryTrackLineBySemanticWithin(start_time,end_time,point1,point2);
    }
    public List<TrackLine> queryTrackLineBySemanticNearby(Timestamp start_time,Timestamp end_time,String point){
        return webgisMapper.queryTrackLineBySemanticNearby(start_time,end_time,point);
    }
}
