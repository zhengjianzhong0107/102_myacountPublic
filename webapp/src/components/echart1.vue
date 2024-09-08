<template>
  <div id="chart1"></div>
</template>

<script>

  export default {
    name: "echart1",
    methods: {
      query(params) {
        let url = "api/home/sum"
        this.$axiosJava
          .post(url, params)
          .then(res => {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById("chart1"))
            myChart.clear()
            let sum = res.data.map((item) => {
              return item.value
            }).reduce((sum, item) => {
              return sum + item
            })
            myChart.setOption({
              title: {
                text: "消费饼状图",
              },
              series: [{
                name: "访问来源",
                type: "pie",
                radius: "55%",
                data: res.data.map(item => {
                  return {
                    value: item.value,
                    name: item.name + "： " + (item.value / sum * 100).toFixed(2) + "%",
                  }
                })
              }]
            })
          })
      }
    }
  }
</script>

<style scoped>
  #chart1 {
    width: 100%;
    height: calc(50vh - 150px);
  }
</style>
