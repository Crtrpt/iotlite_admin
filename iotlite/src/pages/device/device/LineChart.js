import { Line, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  name:"LineChart",
  extends: Line,
  mixins: [reactiveProp],
  props: ['options'],
  mounted () {

    const canvas = this.$refs.canvas;

    const gradient = canvas.getContext('2d').createLinearGradient(0, 0, 0, 150);

    gradient.addColorStop(0, '#e95f2b90')
    gradient.addColorStop(0.5, '#e95f2b50');
    gradient.addColorStop(1, '#e95f2b00');
    this.chartData.datasets[0].backgroundColor=gradient
    this.renderChart(this.chartData, {
      responsive: true,
      maintainAspectRatio: false,
      scaleOverride : true,
      scaleSteps : 10,
      scaleStepWidth : 10,
      scaleStartValue : 0
    })

    setInterval(() => {

      this.$data._chart.update();
    }, 1000);
  }
}