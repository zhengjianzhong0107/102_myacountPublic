<template>
  <div id="chart2"></div>
</template>

<script>
  export default {
    name: "echart2",
    methods: {
      reList(list) {
        let result = [0]
        let sum = 0
        for (let i in list) {
          result.push(sum)
          sum += list[i].value
        }
        return result
      },
      query(params){
        let url = "api/home/sum"
        this.$axiosJava
          .post(url,params)
          .then(res => {
            let myChart = echarts.init(document.getElementById("chart2"))
            myChart.clear()

            myChart.setOption({
              title: {
                text: "消费柱状图（单位:元）",
              },
              tooltip: {
                trigger: "axis",
                axisPointer: {
                  type: "shadow"
                },
                formatter: function (params) {
                  var tar = params[1]
                  return tar.name + "：<br/>" + tar.seriesName + " : " + tar.value
                }
              },
              grid: {
                left: "5%",
                right: "5%",
                bottom: "1%",
                containLabel: true
              },
              xAxis: {
                type: "category",
                splitLine: {show: false},
                data: ["总量", ...[...res.data.map(item => {
                  return item.name
                })]]
              },
              yAxis: {
                type: "value"
              },
              series: [
                {
                  name: "辅助",
                  type: "bar",
                  stack: "总量",
                  itemStyle: {
                    barBorderColor: "rgba(0,0,0,0)",
                    color: "rgba(0,0,0,0)"
                  },
                  emphasis: {
                    itemStyle: {
                      barBorderColor: "rgba(0,0,0,0)",
                      color: "rgba(0,0,0,0)"
                    }
                  },
                  data: this.reList(res.data)
                },
                {
                  name: "总花费",
                  type: "bar",
                  stack: "总量",
                  label: {
                    show: true,
                    position: "inside"
                  },
                  data: [res.data.map(item => {
                    return item.value
                  }).reduce((sum, item) => {
                    return sum + item
                  }).toFixed(2), ...[...res.data.map(item => {
                    return item.value.toFixed(2)
                  })]]
                }
              ]
            })
          })
      }
    }
  }
</script>

<style scoped>
  #chart2 {
    width: 100%;
    height: calc(50vh - 150px);
  }
</style>
