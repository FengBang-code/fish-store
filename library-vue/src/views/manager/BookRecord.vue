<template>
  <div class="divMain">
    <div class="div1" ref="circularAction"></div>
    <div class="div2" ref="barAction"></div>
    <div class="div3" ref="lineAction"></div>
  </div>
<!--  <div class="charts" ref="myCharts"></div>-->
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: "BookRecord",
  data() {
    return {
      lineData:{
        title: {
          text: '本周热销Top10'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        series: [
          // {
          //   name: '三国演绎',
          //   data: [10, 22, 28, 23, 19, 44, 13, 23],
          //   type: 'line'
          // },
          // {
          //   name: '水浒传',
          //   data: [25, 14, 23, 35, 10 , 42, 23, 16],
          //   type: 'line'
          // }
        ]
      },
      barData: {
        title: {
          text: '热销书类'
        },
        xAxis: {
          data: ['小说', '世界名著', '工具书', '教科书', '随笔']
        },
        yAxis: {},
        series: [
          {
            type: 'bar',
            data: [23, 24, 18, 25, 27]
          }
        ]
      },
      cirData: {
        title: {
          text: '售书金额(元)'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'horizontal',
          left: 'right'
        },
        series: [
          {
            // name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1022.3, name: '小说' },
              { value: 735, name: '世界名著' },
              { value: 580, name: '工具书' },
              { value: 484, name: '教科书' },
              { value: 300, name: '随笔' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
    }
  },
  methods: {
    getRecord() {
      this.$http.get("/api/order/getRecord").then(
          res => {
            // console.log(res.data.data)
            this.barData.series[0].data =  res.data.data.barList
            // console.log(this.barData.series[0].data)
            for(let i = 0; i < res.data.data.cirList.length; i++) {
                // this.cirData.series[i].value = res.data.data.cirList[i]
              this.cirData.series[0].data[i].value = res.data.data.cirList[i]
              // console.log(res.data.data.cirList[i])
              console.log("============")
              // console.log(this.cirData.series[0].data[i].value)
            }

            this.lineData.xAxis.data = res.data.data.times
            for(let i = 0; i < res.data.data.bookTopVOList.length; i++) {
              // console.log(res.data.data.bookTopVOList)

              this.lineData.legend.data.push(res.data.data.bookTopVOList[i].bookName)
              // console.log(this.lineData.legend.data)
              // this.lineData.series[i].name = res.data.data.bookTopVOList[i].bookName
              // this.lineData.series[i].data = res.data.data.bookTopVOList[i].bookNum
              this.lineVo = {
                name: null,
                data: null,
                type: 'line'
              }
              this.lineVo.name = res.data.data.bookTopVOList[i].bookName
              this.lineVo.data = res.data.data.bookTopVOList[i].bookNum
              // this.lineVo.type = 'line'
              // console.log(this.lineVo)
              this.lineData.series.push(this.lineVo)
              // this.cirData.series.push(this.lineVo)
              // console.log(this.lineData.series[0].name)
              // console.log(res.data.data.bookTopVOList[i].bookName)
              // this.lineData.series[i].type = 'line'
            }
            // console.log(this.lineData)
            let barEcharts = echarts.init(this.$refs.barAction)
            barEcharts.setOption(this.barData)
            let cirEcharts = echarts.init(this.$refs.circularAction)
            cirEcharts.setOption(this.cirData)
            let lineEcharts = echarts.init(this.$refs.lineAction)
            lineEcharts.setOption(this.lineData)
          }
      )
    },

  },
  mounted() {
    this.getRecord()
  }

}
</script>

<style scoped>
  .charts {
    width: 400px;
    height: 300px;
  }
  .divMain {
    width: 100%;
    height: 100%;
  }
  .div1 {
    width: 50%;
    height: 50%;
    /*background-color: green;*/
    float: left;
  }
  .div2 {
    width: 50%;
    height: 50%;
    /*background-color: red;*/
    float: left;
  }
  .div3 {
    width: 100%;
    height: 50%;
    /*background-color: yellow;*/
    float: left;
  }
</style>
