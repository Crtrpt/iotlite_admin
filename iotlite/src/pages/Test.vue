<template>
  <div class="Test">
    <div v-for="(item, i) in frame" :key="i" class="frame">
      <div class="info">
        <div class="seg">
          <div class="name">SN</div>
          <div class="data">10101</div>
        </div>
        <div class="seg">
          <div class="name">时间</div>
          <div class="data">2020年10月13日17:52:07</div>
        </div>
        <div class="seg">
          <div class="name">来源</div>
          <div class="data">2020年10月13日17:52:07</div>
        </div>
        <div class="seg">
          <div class="name">目标地市</div>
          <div class="data">2020年10月13日17:52:07</div>
        </div>
        <div class="seg">
          <div class="name">协议名称</div>
          <div class="data">2020年10月13日17:52:07</div>
        </div>
        <div class="seg">
          <div class="name">帧描述</div>
          <div class="data">报警</div>
        </div>
        <div class="seg">
          <div class="name">帧长度</div>
          <div class="data">98</div>
        </div>
      </div>
      <div class="detail">
        <div v-for="p in item" :key="i + p" class="seg val">
          <div class="name">{{ p.name }}</div>
          <div class="data">{{ p.data }}</div>
          <div class="data scale ascii">{{ p.ascii }}</div>
          <div class="data scale bin">{{ p.bin }}</div>
          <!-- <div class="data scale dec" > {{p.dec}} </div> -->
          <div class="data scale hex">{{ p.hex }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Test',
  data() {
    return {
      base: 'bin11',
      frame: [
        [
          {
            name: '帧头',
            data: 'FD',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '版本',
            data: 'v01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '长度',
            data: '12',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'imei',
            data: '30001111111111',
            ascii: '111111111',
            dec: '',
            bin: '0001 00002 0003 0004',
            hex: '01 02 01 03',
            width: 15,
          },
          {
            name: '报警',
            data: '电量低',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'CSQ',
            data: '30',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'time',
            data: '0001:01:01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '校验',
            data: '正确',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
        ],

        [
          {
            name: '帧头',
            data: 'FD',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '版本',
            data: 'v01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '长度',
            data: '12',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'imei',
            data: '30001111111111',
            ascii: '111111111',
            dec: '',
            bin: '0001 00002 0003 0004',
            hex: '01 02 01 03',
            width: 15,
          },
          {
            name: '报警',
            data: '电量低',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'CSQ',
            data: '30',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'time',
            data: '0001:01:01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '校验',
            data: '正确',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
        ],

        [
          {
            name: '帧头',
            data: 'FD',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '版本',
            data: 'v01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '长度',
            data: '12',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'imei',
            data: '30001111111111',
            ascii: '111111111',
            dec: '',
            bin: '0001 00002 0003 0004',
            hex: '01 02 01 03',
            width: 15,
          },
          {
            name: '报警',
            data: '电量低',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'CSQ',
            data: '30',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: 'time',
            data: '0001:01:01',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
          {
            name: '校验',
            data: '正确',
            ascii: '111111111',
            dec: '',
            bin: '0001',
            hex: '01 02 01 03',
            width: 1,
          },
        ],
      ],
    };
  },
};
</script>

<style scoped>
.frame {
  margin-top: 10px;
  border: 1px solid grey;
  display: flex;
  flex-direction: column;
}
.frame:hover .detail {
  display: flex;
}
.info {
  display: flex;
}
.detail {
  /* display: flex; */
  display: none;
}

.frame:hover {
  background-color: lightblue;
  border-top: 5px solid gray;
}

.seg {
  border-right: 1px solid grey;
  display: flex;
  flex-direction: column;
}
.seg .scale {
  display: none;
}
.frame:hover .scale {
  display: inline;
}
.name {
  text-align: center;
}
.data {
  border-top: 1px solid grey;
  border-bottom: 1px solid grey;
  padding: 1px 5px;
}
</style>
