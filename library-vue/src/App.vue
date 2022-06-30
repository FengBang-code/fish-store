<template>
  <div id="app">
<!--    <Home></Home>-->
<!--    <Login></Login>-->
    <router-view/>
  </div>
</template>

<script>
import Login from './views/login/Login'
import Home from './views/Main'
export default {
  name: 'App',
  components: {
    Login,Home
  }
}
</script>

<style>
  html {
    width: 100%;
    height: 100%;
    margin:0;
    padding: 0;
  }
  body {
    width: 100%;
    height: 100%;
    margin:0;
    padding: 0;
  }
  #app {
    width: 100%;
    height: 100%;
  }
</style>
