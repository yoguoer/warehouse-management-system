<template>
  <div>
    <div id="body">
      <div class="stars" ref="starsRef">
        <div class="star" v-for="(item, index) in starsCount" :key="index"></div>
      </div>
    </div>
    <welMsg />
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import welMsg from "./welMsg.vue"

export default {
  name: 'indexPage',
  data() {
    return {
    }
  },
  components: {
    welMsg
  },
  created() {

  },
  methods: {
    gotoLogin() {
      this.$router.push({ name: 'login' });
    }
  },
  setup() {
    let starsRef = ref(null);

    const starsCount = 800; //星星数量
    const distance = 900; //间距

    onMounted(() => {
      let starNodes = Array.from(starsRef.value.children);
      starNodes.forEach((item) => {
        let speed = 0.2 + Math.random() * 1;
        let thisDistance = distance + Math.random() * 300;
        item.style.transformOrigin = `0 0 ${thisDistance}px`;
        item.style.transform = `
        translate3d(0,0,-${thisDistance}px)
        rotateY(${Math.random() * 360}deg)
        rotateX(${Math.random() * -50}deg)
        scale(${speed},${speed})`;
      });
    });

    return {
      starsRef,
      starsCount,
    };
  },
}
</script>

<style lang="css" scoped>
* {
  /* CSS Reset */
  margin: 0;
  padding: 0;
}

#body {
  position: absolute;
  left: 0;
  top: 50px;
  overflow: hidden;
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  opacity: 0.8;
  background: radial-gradient(200% 100% at bottom center,
      #f7f7b6,
      #e96f92,
      #1b2947);
  background: radial-gradient(200% 105% at top center,
      #1b2947 10%,
      #75517d 40%,
      #e96f92 65%,
      #f7f7b6);
  background-attachment: fixed;
  overflow: hidden;
}

@keyframes rotate {
  0% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
  }

  100% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(-360deg);
  }
}

.stars {
  transform: perspective(500px);
  transform-style: preserve-3d;
  position: absolute;
  perspective-origin: 50% 100%;
  left: 45%;
  animation: rotate 90s infinite linear;
  bottom: 0;
}

.star {
  width: 2px;
  height: 2px;
  background: #f7f7b6;
  position: absolute;
  left: 0;
  top: 0;
  backface-visibility: hidden;
}

.enter-btn {
  cursor: pointer;
  top: 50%;
}

.enter-btn {
  position: absolute;
  z-index: 999;
  left: 50vw;
  top: 50vh;
}
</style>
