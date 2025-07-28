
<script>
import axios from "axios";
timeToTime: [new Date(2018, 11, 10, 15, 25),
            new Date(2018, 11, 10, 16, 25)]
export default {
    data() {
        return {
            draw: null,
            
        }
    },
    methods: {
        formatDate(date) {
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hour = date.getHours().toString().padStart(2, '0');
            const minute = date.getMinutes().toString().padStart(2, '0');
            const second = date.getSeconds().toString().padStart(2, '0');
            return `${year}-${month}-${day}_${hour}:${minute}:${second}`;
        },
        queryByTime() {
            axios.post("http://localhost:8082/web/queryTrackLineByTime",
                {
                    start_time: this.formatDate(this.timeToTime[0]),
                    end_time: this.formatDate(this.timeToTime[1]),
                }, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }
            ).then((res) => {
                let tmp = []
                for (let i = 0; i < res.data.data.length; i++) {
                    tmp.push([])
                    res.data.data[i].track_geom.split(",").map(item => {
                        tmp[i].push([Number(item.split(" ")[0]), Number(item.split(" ")[1])])
                    })
                }
                this.$store.commit('setPolygon', tmp)
            })
        },
        queryByPolygon() {
            let tmp = ""
            console.log(this.$store.state.queryPolygon[0])
            this.$store.state.queryPolygon[0].map(item => {
                tmp += item[0].toString() + "_" + item[1].toString() + ","
            })
            tmp = tmp.slice(0, -1)
            axios.post("http://localhost:8082/web/queryTrackLineByPolygon",
                {
                    polygon: tmp
                },
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }).then((res) => {
                    let tmp = []
                    for (let i = 0; i < res.data.data.length; i++) {
                        tmp.push([])
                        res.data.data[i].track_geom.split(",").map(item => {
                            tmp[i].push([Number(item.split(" ")[0]), Number(item.split(" ")[1])])
                        })
                    }
                    this.$store.commit('setPolygon', tmp)
                })
        },
        drawPolygon() {
            this.$store.commit('setIsDraw', true)
        }
    },
    computed: {
        queryMethod() {
            return Number(this.$store.state.queryMethod);
        },
    },
    watch: {
        timeToTime(val) {
            console.log(this.formatDate(val[0]));
            console.log(this.formatDate(val[1]));
        },
        // queryMethod(val) {
        //     if (val === 0) {
        //         this.queryByTime();
        //     }
        // }
    }
}

</script>
<template>
    <div v-if="queryMethod === 0" class="queryByTime">
        <div class="queryByTime-container">
            <div class="queryByTime-container-title">
                <span>时间查询</span>
            </div>
            <el-date-picker v-model="timeToTime" type="datetimerange" range-separator="至" start-placeholder="开始日期"
                end-placeholder="结束日期">
            </el-date-picker>
        </div>
        <div>
            <el-button type="primary" @click="queryByTime">查询</el-button>
        </div>
    </div>
    <div v-else-if="queryMethod === 1" class="queryByPolygon">
        <div class="queryByPolygon-container">
            <div class="queryByPolygon-container-title">
                <span>空间查询</span>
            </div>
            <div>
                <el-button type="primary" @click="drawPolygon">绘制</el-button>
                <el-button type="primary" @click="queryByPolygon">查询</el-button>
            </div>
        </div>
    </div>
</template>
