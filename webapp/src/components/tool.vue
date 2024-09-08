<template>
  <el-row :gutter="20">
    <el-col :span="2">
      <el-select size="mini" v-model="params.type">
        <el-option
          :key="item.value"
          :label="item.label"
          :value="item.value"
          v-for="item in searchType"
        ></el-option>
      </el-select>
    </el-col>
    <el-col :span="2">
      <el-input
        size="mini"
        placeholder="请输入关键字"
        v-model="keyword"
        clearable="clearable"
        @clear="
          params.keyword = keyword;
          search();
        "
      ></el-input>
    </el-col>
    <el-col :span="2">
      <el-button
        size="mini"
        type="primary"
        @click="
          params.keyword = keyword;
          search();
        "
        >搜索</el-button
      >
    </el-col>
    <el-col :span="6">
      <el-date-picker
        @change="search"
        size="mini"
        v-model="params.date"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      >
      </el-date-picker>
    </el-col>
    <el-col :span="2">
      <el-button class="upload-btn" size="medium" type="info">
        导入csv
        <input
          id="uploadFilePicture"
          ref="addEnclosure"
          class="add-enclosure-file"
          name="uploadFilePicture"
          type="file"
          @change="upload"
      /></el-button>
    </el-col>
    <el-col :span="2">
      <el-button size="mini" type="success" @click="$emit('getLastCount')"
        >获取平账日</el-button
      >
    </el-col>
    <el-col :span="2">
      <el-button size="mini" type="primary" @click="$emit('add')"
        >添加记录</el-button
      >
    </el-col>
    <el-col :span="2">
      <el-button size="mini" type="primary" @click="$emit('export')"
        >导出</el-button
      >
    </el-col>
    <el-col :span="2">
      <el-button size="mini" type="danger" @click="$emit('delete')"
        >批量删除</el-button
      >
    </el-col>
    <el-col :span="2">
      <el-switch
        @change="search"
        size="mini"
        v-model="params.used"
        active-text="已销费"
        inactive-text="全部"
      >
      </el-switch>
    </el-col>
  </el-row>
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
        keyword: ""
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
        }
      ]
    };
  },
  methods: {
    deal(path) {
      let url = "api/home/deal?path=" + path;
      this.$axiosJava
        .get(url)
        .then(res => {
          this.search();
          this.$message.success("处理成功");
        })
        .catch(e => {
          this.$message.error("处理失败");
        });
    },
    //上传文件
    upload(e) {
      let files = e.target.files[0];
      let filextension = files.name.substring(
        files.name.lastIndexOf("."),
        files.name.length
      );
      let config = {
        headers: { "Content-Type": "multipart/form-data" }
      };
      let uploadUrl = "api/home/upload";
      let param = new FormData(); //创建form对象
      param.append("file", files); //通过append向form对象添加数据
      this.$axiosJava
        .post(uploadUrl, param, config)
        .then(res => {
          this.deal(res.data.name);
          this.$message.success("上传成功");
        })
        .catch(error => {
          this.$message({
            type: "error",
            message: "上传失败"
          });
        });
    },
    setDate(date) {
      this.$set(this.params, "date", date);
    },
    search() {
      this.$emit("search", this.params);
    }
  },
  mounted() {
    this.search();
  }
};
</script>

<style scoped>
.upload-btn {
  width: 73px;
  height: 28px;
  overflow: hidden;
  position: relative;
  text-align: center;
  line-height: 28px;
  padding: 0;
}

.upload-btn .add-enclosure-file {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  opacity: 0;
}
</style>
