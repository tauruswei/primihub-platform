<template>
  <div class="log">
    <el-button size="small" type="danger" plain style="margin-bottom: 10px" @click="showErrorLog">ERROR ({{ errorLog.length }})</el-button>
    <div class="log-container">
      <template v-if="logData.length>0">
        <p v-for="(item,index) in logData" :id="(logData.length === index+1)?'scrollLog':''" :key="index" class="item">
          {{ item.log }}
        </p>
      </template>
      <template v-else>
        <p>暂无日志数据</p>
      </template>
    </div>
  </div>
</template>

<script>
import { getTaskLogInfo } from '@/api/task'

export default {
  name: 'Log',
  props: {
    taskState: {
      type: Number,
      default: 0
    },
    taskId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      ws: null,
      logData: [],
      query: '',
      start: '',
      errorLog: [],
      logType: ''
    }
  },
  async mounted() {
    await this.getTaskLogInfo()
    this.socketInit()
  },
  destroyed() {
    this.ws.close() // 离开路由之后断开websocket连接
  },
  methods: {
    socketInit() {
      const url = `ws://192.168.99.16:31191/loki/api/v1/tail?start=${this.start}&query=${this.query}`
      this.ws = new WebSocket(url)
      this.ws.onopen = this.open
      // 监听socket错误信息
      this.ws.onerror = this.error
      // 监听socket消息
      this.ws.onmessage = this.getMessage
      // 发送socket消息
      this.ws.onsend = this.send
      this.ws.onclose = this.close
    },
    open: function() {
      console.log('socket连接成功')
      // this.send(JSON.stringify(this.listQuery))
    },
    error: function() {
      console.log('连接错误')
    },
    close: function(e) {
      this.ws.close()
      if (this.taskState === 2) {
        this.socketInit()
      }
      console.log('socket已经关闭', e)
    },
    getMessage: function(msg) {
      if (msg.data.length > 0) {
        const data = JSON.parse(msg.data).streams
        const formatData = data.map(item => {
          const value = JSON.parse(item.values[0][1])
          if (value.log !== '\n') {
            value.log = value.time.split('T')[0] + ' ' + value.log
            return value
          }
        })
        this.logData = this.logData.concat(formatData)
        this.$nextTick(() => {
          this.scrollToTarget('scrollLog')
          // document.getElementById('scrollLog').scrollIntoView({ behavior: 'smooth', block: 'end' })
        })
        if (this.logType === 'error') {
          this.errorLog = this.logData
          this.$emit('error', this.logData)
        }
      }
    },
    send: function(order) {
      console.log(order)
      this.ws.send(order)
    },
    async getTaskLogInfo() {
      console.log(this.taskId)
      const taskId = this.taskId || this.$route.params.taskId
      const res = await getTaskLogInfo(taskId)
      if (res.code === 0) {
        const { container, job, taskIdName, start } = res.result
        this.start = start
        this.query = `{job ="${job}", container="${container}"}|="${taskIdName}"`
      }
    },
    scrollToTarget(target, block = 'end') {
      const element = document.getElementById(target)
      element.scrollIntoView({ behavior: 'smooth', block })
    },
    showErrorLog() {
      this.logType = 'error'
      this.logData = []
      this.query += `|="${this.logType}"`
      this.socketInit()
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .table,::v-deep .table  tr{
  background: transparent;
  color: #fff;
}
::v-deep .el-table--enable-row-hover .el-table__body tr:hover>td.el-table__cell{
  background-color: rgba($color: #000000, $alpha: 0.1);
}
::v-deep .el-table::before{
  height: 0;
}
::v-deep .el-table td.el-table__cell, .el-table th.el-table__cell.is-leaf{
  border: none;
}
.log-container{
  background-color: #112435;
  color: #fff;
  font-size: 14px;
  padding: 20px;
  overflow-y: scroll;
  overflow-x: hidden;
  width: 100%;
  .item{
    display: inline-block;
    margin-bottom: 10px;
    line-height: 1.5;
    word-break:break-all;
    .state-error{
      color: rgba(245, 108, 108);
    }
  }
}
</style>
