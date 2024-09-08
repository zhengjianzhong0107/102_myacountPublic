<template>
  <div>
    <div ref="editor"></div>
  </div>
</template>

<script>
  import E from "wangeditor"

  export default {
    name: "editor",
    model: {
      prop: "value",
      event: "change"
    },
    data() {
      return {
        editor: null,
        _info: ""
      }
    },
    props: {
      value: {
        type: String,
        default: ""
      },
    },
    methods: {
      init() {
        this.editor = new E(this.$refs.editor)
        this.initMenu(this.editor)
        this.initPic(this.editor)
        this.editor.config.onchange = html => {
          this._info = html // 绑定当前逐渐地值
					this.$emit("change", this._info); // 将内容同步到父组件中

				}
        this.editor.create()
        this.editor.txt.html(this.value)
      },
      initMenu(editor) {
        editor.config.zIndex = 100
        editor.config.menus = [
          "head",  // 标题
          "bold",  // 粗体
          "fontSize",  // 字号
          "fontName",  // 字体
          "foreColor",  // 文字颜色
          "backColor",  // 背景颜色
          "undo",  // 撤销
          "redo",  // 重复
          "emoticon",  // 表情
          "italic",  // 斜体
          "underline",  // 下划线
          "strikeThrough",  // 删除线
          "justify",  // 对齐方式
          "image",  // 插入图片
          "code",  // 插入代码
          "list",  // 列表
          "table",  // 表格
          "quote",  // 引用
          "link",  // 插入链接
          // 'video',  // 插入视频
        ]
      },
      initPic(editor) {
        editor.config.uploadImgMaxLength = 1
        editor.config.uploadFileName = "file" // 后端接受上传文件的参数名
        editor.config.showLinkImg = false
        // editor.config.uploadImgHeaders = {
        //   Authorization: this.$store.state.user.token
        // }
        // editor.config.uploadImgServer = process.env.VUE_APP_BACKEND_ROOT_URL_JAVA + "/api/file/admin/upload"

        editor.config.uploadImgHooks = {
          before: function (xhr, editor, files) {
            // 图片上传之前触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

            // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
            // return {
            //     prevent: true,
            //     msg: '放弃上传'
            // }
          },
          success: function (xhr, editor, result) {
            // 图片上传并返回结果，图片插入成功之后触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
          },
          fail: function (xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
          },
          error: function (xhr, editor) {
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
          },
          timeout: function (xhr, editor) {
            // 图片上传超时时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
          },

          // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
          // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
          customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

            debugger
            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            // var url = `${process.env.VUE_APP_BACKEND_ROOT_URL_JAVA}/api/file/picture/${result.name}`
            insertImg(url)

            // result 必须是一个 JSON 格式字符串！！！否则报错
          }
        }
      },

    },
    mounted() {
      this.init()
    },
    watch: {
      value: function (value) {
        if (value !== this.editor.txt.html()) {
          this.editor.txt.html(this.value)
        }
      },
    },
  }
</script>
