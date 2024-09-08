<template>
  <div class="header">
    <img :src="'./static/images/logo.png'" />
    <div class="title">个人记账系统</div>
    <div class="user">
      <i class="el-icon-user-solid" @click="visible2 = true"
        >{{ loginUser.name }}|</i
      >
      <a class="warning hearder-logout" @click="logout()">注销注</a>
    </div>
    <!-- 修改基本信息框 -->
    <el-dialog title="基本信息" :visible.sync="visible2" width="40%" center>
      <el-form
        style="margin-left: 70px"
        label-position="left"
        :model="infoForm"
        ref="infoForm"
        label-width="110px"
        hide-required-asterisk
      >
        <el-form-item label="用户名" prop="name" style="width: 380px;">
          <el-input v-model="infoForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="width: 380px;">
          <el-input v-model="infoForm.password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateInfo()">修 改</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Cookie from "js-cookie";
export default {
  name: "headerTop",
  data() {
    return {
      visible2: false,
      infoForm: JSON.parse(Cookie.get("token")),
      loginUser: JSON.parse(Cookie.get("token")),
      showDetail: false
      //        activeIndex: this.$route.path
    };
  },
  methods: {
    logout: function() {
      Cookie.remove("token");
      location.reload();
    },
    edit(scope) {
      this.currentRow = scope.row;
      //this.detailType = "bj";
      this.showDetail = true;
    },
    updateInfo() {
      // this.$axios.post("api/user/update", this.infoForm).then(res => {
      //   Cookie.set("token", JSON.stringify(this.infoForm), { expires: 100 });
      //   this.$message({
      //     showClose: true,
      //     message: "修改成功",
      //     type: "success"
      //   });
      //   this.visible2 = false;
      //   this.$router.push("/");
      // });
      this.$axiosJava.post(`api/user/update`, this.infoForm).then(res => {
        if (res.data) {
          Cookie.set("token", JSON.stringify(this.infoForm), { expires: 100 });
          this.$message({
            showClose: true,
            message: "修改成功",
            type: "success"
          });
          this.visible2 = false;
          location.reload();
        } else {
          this.$message.error("用户名密码错误");
        }
      });
    }
  }
};
</script>

<style scoped>
.header {
  width: 100%;
  height: 60px;
  line-height: 60px;
  background-color: cornflowerblue;
  text-align: left;
  display: flex;
}

.header img {
  width: 42px;
  margin: 9px;
}

.header .title {
  font-size: 18px;
  color: white;
  font-weight: bolder;
  width: calc(100% - 145px);
}

.header .user {
  padding-right: 50px;
  cursor: pointer;
  font-size: 14px;
  color: white;
  font-weight: bolder;
}
</style>
