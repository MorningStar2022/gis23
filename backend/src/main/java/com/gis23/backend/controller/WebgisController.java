package com.gis23.backend.controller;

import com.gis23.backend.entity.TrackLine;
import com.gis23.backend.service.WebgisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("web")
@ResponseBody
@CrossOrigin(origins = "*")
@Api("基于出租车轨迹数据的时空查询实现")
public class WebgisController {
    @Autowired WebgisService webgisService;
    @ApiOperation("通过读取本地文件，将轨迹数据存储到数据库中")
    @RequestMapping("LoadSaveToDB")
    String LoadSaveToDB(@RequestParam(value = "file_path") String file_path){
        try {
            webgisService.LoadSaveToDB(file_path);
            return "success";
        }catch (IOException e){
            return "文件读写报错";
        }


    }
    @ApiOperation("对轨迹数据进行时间查询")
    @RequestMapping("queryTrackLineByTime")
    String queryTrackLineByTime(@RequestParam(value = "start_time") String start_time_str,
                                @RequestParam(value = "end_time") String end_time_str){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        List<TrackLine> res_trackline=webgisService.queryTrackLineByTime(start_time,end_time);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @ApiOperation("对轨迹数据进行空间查询")
    @RequestMapping("queryTrackLineByPolygonSPWithin")
    String queryTrackLineByPolygonSPWithin(@RequestParam(value = "polygon") String polygon){
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByPolygonSPWithin(query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByPolygonEPWithin")
    String queryTrackLineByPolygonEPWithin(@RequestParam(value = "polygon") String polygon){
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByPolygonEPWithin(query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByPolygonWithin")
    String queryTrackLineByPolygonWithin(@RequestParam(value = "polygon") String polygon){
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByPolygonWithin(query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByPolygonCrosses")
    String queryTrackLineByPolygonCrosses(@RequestParam(value = "polygon") String polygon){
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByPolygonCrosses(query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByTimeAndPolygonSPWithin")
    String queryTrackLineByTimeAndPolygonSPWithin(@RequestParam(value = "start_time") String start_time_str,
                                          @RequestParam(value = "end_time") String end_time_str,
                                          @RequestParam(value = "polygon") String polygon){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByTimeAndPolygonSPWithin(start_time,end_time,query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByTimeAndPolygonEPWithin")
    String queryTrackLineByTimeAndPolygonEPWithin(@RequestParam(value = "start_time") String start_time_str,
                                          @RequestParam(value = "end_time") String end_time_str,
                                          @RequestParam(value = "polygon") String polygon){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByTimeAndPolygonEPWithin(start_time,end_time,query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByTimeAndPolygonWithin")
    String queryTrackLineByTimeAndPolygonWithin(@RequestParam(value = "start_time") String start_time_str,
                                                  @RequestParam(value = "end_time") String end_time_str,
                                                  @RequestParam(value = "polygon") String polygon){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByTimeAndPolygonWithin(start_time,end_time,query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineByTimeAndPolygonCrosses")
    String queryTrackLineByTimeAndPolygonCrosses(@RequestParam(value = "start_time") String start_time_str,
                                                  @RequestParam(value = "end_time") String end_time_str,
                                                  @RequestParam(value = "polygon") String polygon){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb=new StringBuilder();
        sb.append("POLYGON((").append(polygon.replace("_"," ")).append("))");
        String query_polygon=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineByTimeAndPolygonCrosses(start_time,end_time,query_polygon);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryAllTrackLine")
    String queryAllTrackLine(){
        List<TrackLine> res_trackline=webgisService.queryAllTrackLine();
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineBySemanticWithin")
    String queryTrackLineBySemanticWithin(@RequestParam(value = "start_time") String start_time_str,
                                    @RequestParam(value = "end_time") String end_time_str,
                                    @RequestParam(value = "point1") String point1,
                                    @RequestParam(value = "point2") String point2){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb1=new StringBuilder();
        sb1.append("POINT(").append(point1.replace(","," ")).append(")");
        String query_point1=sb1.toString();
        StringBuilder sb2=new StringBuilder();
        sb2.append("POINT(").append(point2.replace(","," ")).append(")");
        String query_point2=sb2.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineBySemanticWithin(start_time,end_time,query_point1,query_point2);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
    @RequestMapping("queryTrackLineBySemanticNearby")
    String queryTrackLineBySemanticNearby(@RequestParam(value = "start_time") String start_time_str,
                                         @RequestParam(value = "end_time") String end_time_str,
                                         @RequestParam(value = "point") String point){
        Timestamp start_time=Timestamp.valueOf(start_time_str.replace("_"," "));
        Timestamp end_time=Timestamp.valueOf(end_time_str.replace("_"," "));
        StringBuilder sb=new StringBuilder();
        sb.append("POINT(").append(point.replace(","," ")).append(")");
        String query_point=sb.toString();
        List<TrackLine> res_trackline=webgisService.queryTrackLineBySemanticNearby(start_time,end_time,query_point);
        String return_json=webgisService.TrackLineListToJson(res_trackline);
        return return_json;
    }
}
