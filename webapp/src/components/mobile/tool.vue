<template>
  <div style="width: 80%">
    <el-row>
      <el-col :span="6">
        <el-select size="mini" v-model="params.type">
          <el-option
            :key="item.value"
            :label="item.label"
            :value="item.value"
            v-for="item in searchType"
          ></el-option>
        </el-select>
      </el-col>
      <el-col :span="14">
        <el-input size="mini" placeholder="请输入关键字" v-model="keyword" clearable="clearable"
                  @clear="params.keyword=keyword;search()"></el-input>
      </el-col>
      <el-col :span="2">
        <el-button size="mini" type="primary" @click="params.keyword=keyword;search()">搜索</el-button>
      </el-col>
    </el-row>
    <el-date-picker
      style="width: 100%"
      @change="search"
      size="mini"
      v-model="params.date"
      type="daterange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期">
    </el-date-picker>
    <el-button-group>
      <el-button size="mini" type="success" @click="$emit('getLastCount')">获取平账日</el-button>
      <el-button size="mini" type="primary" @click="$emit('add')">添加记录</el-button>
      <el-button size="mini" type="danger" @click="$emit('delete')">批量删除</el-button>
    </el-button-group>

    <el-switch
      @change="search"
      size="mini"
      v-model="params.used"
      active-text="已销费"
      inactive-text="全部">
    </el-switch>
  </div>
</template>

<script>
  export default {
    name: "tool",
    data() {
      return {
        keyword: "",
        params: {
          used: false,
          type: "all",
          keyword: "",
        },
        searchType: [
          {
            label: "全部",
            value: "all"
          },
          {
            label: "标题",
            value: "label"
          },
          {
            label: "分类",
            value: "type"
          },
          {
            label: "消费者",
            value: "custom"
          },
        ],
      }
    },
    methods: {
      setDate(date) {
        this.$set(this.params, "date", date)
      },
      search() {
        this.$emit("search", this.params)
      }
    },
    mounted() {
      this.search()
    }
  }
</script>

<style scoped>

</style>
