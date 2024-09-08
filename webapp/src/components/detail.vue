<template>
  <div class="newsedit-box">
    <el-form :model="form" label-width="150px" style="width: 85%">
      <el-row>
        <el-col :span="8">
          <el-form-item label="消费名称：">
            <el-input style="width: 220px" v-if="type=='bj'" v-model="form.label"></el-input>
            <el-input v-else v-model="form.label" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="消费金额：" label-width="150px">
            <el-input style="width: 220px" v-if="type=='bj'" v-model.number="form.count" type="number"></el-input>
            <el-input v-else v-model="form.count" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="消费类别：">
            <el-select
              v-if="type=='bj'"
              v-model="form.type"
              allow-create
              default-first-option
              filterable
            >
              <el-option
                v-for="item in types"
                :key="item"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
            <el-input v-else v-model="form.type" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="消费者：" label-width="150px">
            <el-select v-if="type=='bj'" v-model="form.custom">
              <el-option v-for="item in users" :key="item" :value="item"></el-option>
            </el-select>
            <el-input v-else v-model="form.custom" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="消费日期：">
            <el-date-picker v-if="type=='bj'" value-format='yyyy-MM-dd'
                            v-model="form.cusDate"></el-date-picker>
            <el-input v-else v-model="form.cusDate" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="是否已消费：">
            <el-switch
              v-if="type=='bj'"
              v-model="form.used"
              active-text="是"
              inactive-text="否"
            ></el-switch>
            <div v-else>{{ form.used ? "是" : "否" }}</div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="消费说明：">
        <editor
          v-if="type=='bj'"
          v-model="form.comment"
        ></editor>
        <div v-html="form.comment" v-else></div>
      </el-form-item>
      <el-form-item>
        <el-button v-if="type=='bj'" size="small" type="success" @click="submit()">提交</el-button>
        <el-button size="small" type="info" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import editor from "../components/common/editor.vue"

  export default {
    name: "detail",
    props: ["rows", "type"],
    data() {
      return {
        form: {},
        users: ["用户1", "用户2", "我自己"],
        types: [],
      }
    },
    components: {
      editor
    },
    methods: {
      cancel() {
        this.$emit("cancel")
      },
      submit() {
        this.$axiosJava.post(`api/home/${this.form.id ? "update" : "add"}`, this.form).then(res => {
          this.$message.success("成功")
          this.$emit("add")
        })
      },
      getTypes() {
        let url = `/static/api/home/type`
        this.$axios({
          method: "get",
          url: url
        }).then((res) => {
          this.types = res.data
        }).catch((error) => {
          this.$message.error("获取分类失败")
        })
      },
      getDetail() {
        if (this.rows.id) {
          let url = `api/home/detail`
          this.$axiosJava.get(url, {params: {id: this.rows.id}}).then((res) => {
            this.form = res.data
          }).catch((error) => {
            this.$message.error("获取数据失败")
          })
        } else {
          this.form = {
            used: true
          }
        }
      }
    },
    watch: {
      rows: {
        immediate: true,
        handler() {
          this.getDetail()
        },

      }
    },
    mounted() {
      if (this.type == "bj") {
        this.getTypes()
      }
    }
  }
</script>

<style scoped>
  .add-enclosure-btn {
    width: 73px;
    height: 35px;
    overflow: hidden;
    position: relative;
  }
</style>
