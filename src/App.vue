<template>
  <div class="page-common">
    <router-view></router-view>
  </div>
</template>
<style>
.page-common{
  padding: 0;
  margin: 0;
  position: absolute;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  background-color: #f4f7fb;
  overflow: auto;
}
</style>