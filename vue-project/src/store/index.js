import { createStore } from 'vuex'

const store = createStore({
    state() {
        return {
            queryMethod: 0, // 查询方式 0: 时间查询 1: 空间查询
            polygon: [],
            isDraw: false,
            queryPolygon: [],
        }
    },
    mutations: {
        setPolygon(state, polygon) {
            state.polygon = polygon
        },
        setQueryMethod(state, queryMethod) {
            state.queryMethod = queryMethod
        },
        setIsDraw(state, isDraw) {
            state.isDraw = isDraw
        },
        setQueryPolygon(state, queryPolygon) {
            state.queryPolygon = queryPolygon
        }
    }
})

export default store

