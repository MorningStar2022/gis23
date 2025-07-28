<!-- <script setup>
import {
    Document,
    Menu as IconMenu,
} from '@element-plus/icons-vue'

</script> -->
<script>
import querySider from './querySider.vue';
export default {
    data() {
        return {
            map: null,
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath)
            this.$store.commit('setQueryMethod', key)

        }
    }
}

</script>
<template>
    <el-menu default-active="0" class="el-menu-vertical-demo" @select="handleSelect">
        <el-menu-item index="0">
            <el-icon><icon-menu /></el-icon>
            <span>时间查询</span>
        </el-menu-item>
        <el-menu-item index="1">
            <el-icon>
                <document />
            </el-icon>
            <span>空间查询</span>
        </el-menu-item>
    </el-menu>
</template>
