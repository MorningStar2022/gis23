

<script>
import { Feature, Map, View } from "ol";
import TileLayer from "ol/layer/Tile";
import { XYZ } from "ol/source";
import * as layer from "ol/layer";
import * as source from "ol/source";
import { LineString } from "ol/geom";
import { Stroke, Style } from "ol/style";
import { Draw } from "ol/interaction";
export default {
    data() {
        return {
            map: null,
            draw: null,
            vectorLayer: null,
            vectorSource: null,
            drawFeature: null,
        };
    },
    methods: {
        createMap() {
            this.vectorSource = new source.Vector();
            this.vectorLayer = new layer.Vector({
                source: this.vectorSource,
                style: new Style({
                    stroke: new Stroke({
                        color: 'blue',
                        width: 2,
                    }),
                }),
            });
            this.map = new Map({
                target: "map_container",
                layers: [
                    new TileLayer({
                        source: new XYZ({
                            url: "http://t4.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=49ea1deec0ffd88ef13a3f69987e9a63"
                        })
                    }),
                    this.vectorLayer
                ],
                view: new View({
                    center: [114.3643, 30.5360],
                    projection: "EPSG:4326",
                    zoom: 12,
                    maxZoom: 20,
                }),
            });
        },
        addPolyline() {
            this.vectorSource.clear();
            this.$store.state.polygon.map((item) => {
                const lineString = new LineString(item);
                const feature = new Feature(lineString);
                this.vectorSource.addFeature(feature);
            })
        },
        drawPolygon() {
            this.vectorSource.clear();
            this.draw = new Draw({
                source: this.vectorSource,
                type: 'Polygon',
            });
            this.draw.on('drawend', (event) => {
                // 获取新绘制的多边形的坐标
                this.drawFeature = event.feature;
                const coordinates = event.feature.getGeometry().getCoordinates();
                this.$store.commit("setQueryPolygon", coordinates);
                console.log(coordinates);
                // 移除 draw 交互来停止绘制
                this.map.removeInteraction(this.draw);
            });
            this.map.addInteraction(this.draw);
            this.$store.commit("setIsDraw", false);
        }
    },
    mounted() {
        this.createMap();
    },
    computed: {
        polygon() {
            return this.$store.state.polygon
        },
        isDraw() {
            return this.$store.state.isDraw
        }
    },
    watch: {
        polygon(val) {
            console.log(val)
            this.addPolyline()
        },
        isDraw(val) {
            if (val) {
                this.drawPolygon()
            }
        }
    }
};


</script>

<style scoped>
#map_container {
    width: 100%;
    height: 100%;
}
</style>
<template>
    <div id="map_container"></div>
</template>
