<template>
  <div>
    <div id="chart3"></div>
    <div style="margin-bottom: 10px">平账：
      <el-button @click="balance" type="primary" size="mini">一键添加</el-button>
    </div>
    <div v-for="(item,key) in message" :key="key">
      <strong>{{key}}</strong>: {{item}}
    </div>
  </div>
</template>

<script>
  export default {
    name: "echart3",
    data() {
      return {
        message: {},
      }
    },
    methods: {
      balance() {
        let list = []

        for (let i in this.message) {
          let form = {
            "label": "自动平账",
            "custom": i,
            "count": this.message[i],
            "comment": "string",
            "type": "平账",
            "cusDate": new Date(),
            "used": true,
            "pic": null,
          }
          list.push(
            new Promise((resolve => {
              this.$axiosJava.post(`api/home/add`, form).then(res => {
                resolve()
              })
            }))
          )
        }
        Promise.all(list).then(res => {
          this.$message.success("成功")
          this.$emit("balance")
        })
      },
      getMessage(map) {
        let message = {}
        let total = 0
        let count = 0
        for (let i in map) {
          total += map[i]
          count++
        }
        let ave = total / count
        for (let i in map) {
          message[i] = ave - map[i]
        }
        this.message = message
      },
      query(params) {
        let url = "api/home/vs"
        this.$axiosJava
          .post(url, params)
          .then(res => {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById("chart3"))
            myChart.clear()

            let totalLabel = "总数"
            let users = []
            let result = {
              [totalLabel]: {}
            }
            let labels = [totalLabel]

            for (let i in res.data) {
              let item = res.data[i]
              if (!result.hasOwnProperty(item.name)) {
                result[item.name] = {}
                result[item.name][item.custom] = item.value
                labels.push(item.name)
              } else {
                result[item.name][item.custom] = item.value
              }
              if (users.indexOf(item.custom) < 0) {
                users.push(item.custom)
              }
              if (result[totalLabel].hasOwnProperty(item.custom))
                result[totalLabel][item.custom] += item.value
              else
                result[totalLabel][item.custom] = item.value
            }

            this.getMessage(result[totalLabel])

            this.substrate = ((result[totalLabel][users[0]] - result[totalLabel][users[1]]) / 2).toFixed(2)

            myChart.setOption({
              title: {
                text: "消费对比图（单位:元）",
              },
              tooltip: {
                trigger: "axis",
                axisPointer: {
                  type: "shadow"
                }
              },
              legend: {
                data: users,
                right: "10"
              },
              xAxis: [
                {
                  type: "category",
                  axisTick: {show: false},
                  data: labels
                }
              ],
              yAxis: [
                {
                  type: "value"
                }
              ],
              series: users.map(user => {
                return {
                  name: user,
                  type: "bar",
                  barGap: 0,
                  label: {
                    show: true,
                    position: "inside"
                  },
                  emphasis: {
                    focus: "series"
                  },
                  data: labels.map(type => {
                    return Number((result[type][user] || 0).toFixed(2))
                  })
                }
              })
            })
          })
          .catch(error => {
          })
      }
    }
  }
</script>

<style scoped>
  #chart3 {
    width: 100%;
    height: calc(50vh - 150px);
  }
</style>
