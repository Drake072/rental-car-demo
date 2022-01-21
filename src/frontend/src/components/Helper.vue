<template>
  <h1>{{msg}}</h1>
</template>

<script>
export default {
  name: "Helper",
  data() {
    return {
      msg: 'default msg'
    }
  },
  mounted() {
    fetch("/v1/")
      .then((response) => response.text())
    .then((data) => {
      this.msg = data;
    })
  }
}
</script>

<style scoped>

</style>