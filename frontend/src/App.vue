<template>
  <navigation />
  <div class="container">
    <router-view/>
  </div>
</template>

<script>

import Navigation from './components/NavBar.vue'

export default {
  components: {
    Navigation,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.container {
  margin-top: 4rem;
}

</style>
