<script setup>
import olmap from './components/olmap.vue'
import menuSider from "./components/menuSider.vue";
import querySider from './components/querySider.vue';
</script>

<template>
  <el-container class="el-container">
    <el-aside class="el-aside-1">
      <menuSider />
    </el-aside>
    <el-main class="el-main">
      <olmap />
    </el-main>
    <el-aside class="el-aside-2">
      <querySider />
    </el-aside>
  </el-container>
</template>

<style>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

#app {
  max-width: 100%;
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

.el-container {
  width: 100%;
  height: 100%;
}

.el-main {
  height: 100%;
  width: 100%;
  padding: 0;
}

.el-aside-1 {
  height: 100%;
  width: auto;
}

.el-aside-2 {
  height: 100%;
  width: auto;
}
</style>
