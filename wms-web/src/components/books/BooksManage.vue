<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="bookName" placeholder="乐器查找" style="width: 100px;" suffix-icon="el-icon-search"
                @keyup.enter.native="queryBookName"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="queryBookName">查询</el-button>
      <el-input v-model="authorName" placeholder="制造商查找" style="width: 100px;margin-left: 5px;" suffix-icon="el-icon-search"
                @keyup.enter.native="queryAuthorName"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="queryAuthorName">查询</el-button>
      <el-button type="success" style="margin-left: 50px;" @click="addBook">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#b0b3b7',color:'#484444'}"
              border style="font-size: 14px;"
    >
      <el-table-column prop="id" label="ID" width="40">
      </el-table-column>
      <el-table-column label="图片" width="300">
        <template slot-scope="scope">
          <img :src="require('@/assets/' + scope.row.pictureurl)">
        </template>
      </el-table-column>
      <el-table-column prop="name" label="乐器" width="200">
      </el-table-column>
      <el-table-column prop="author" label="制造商" width="120">
      </el-table-column>
      <el-table-column prop="price" label="价格" width="100">
      </el-table-column>
      <el-table-column prop="number" label="数量" width="100">
      </el-table-column>
      <el-table-column prop="operate" label="选项">
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="editBook(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="delBook(scope.row)">删除</el-button>
          <el-button size="small" type="success" @click="checkReview(scope.row.id)">查看评论</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑乐器" :visible.sync="editDialogVisible" width="30%">
      <el-form :model="bookForm" label-width="80px">
        <el-form-item label="乐器">
          <el-input v-model="bookForm.name"></el-input>
        </el-form-item>
        <el-form-item label="制造商">
          <el-input v-model="bookForm.author"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="bookForm.price"></el-input>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="bookForm.number"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>


    <el-dialog title="添加乐器" :visible.sync="addDialogVisible" width="40%">
      <el-form :model="bookForm" label-width="80px">
        <el-form-item label="图片">
          <el-upload
              class="upload-demo"
              drag
              action="http://49.140.29.32:8090/books/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeUpload">
            <img v-if="this.imageUrl" :src="this.imageUrl" class="avatar">
            <template v-else>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </template>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="乐器">
          <el-input v-model="bookForm.name"></el-input>
        </el-form-item>
        <el-form-item label="制造商">
          <el-input v-model="bookForm.author"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="bookForm.price"></el-input>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="bookForm.number"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAdd">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "BooksManage",
  data() {
    return {
      centerDialogVisible: false,
      addDialogVisible: false,
      editDialogVisible: false,
      tableData: [],
      bookName: "",
      authorName: "",
      imageUrl: "",
      backPictureUrl: "",
      file: "",
      bookForm:{
        id: "",
        name: "",
        author: "",
        price: "",
        number: "",
        pictureurl: ""
      }
    }
  },
  methods:{
    checkReview(id) {
      window.location.href = 'http://49.140.29.32:80/bookInfo.php?bookid=' + id;
    },

    // 文件上传成功时的钩子
    handleAvatarSuccess(res, file){
      this.file = file
      this.imageUrl = URL.createObjectURL(file.raw)
      this.backPictureUrl = res.data
      console.log(file)
      console.log("this.imageUrl", this.imageUrl)
      console.log("res", res.data)
    },
    beforeUpload(file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 1;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 1MB!');
      }
      return isJPG && isLt2M;

    },

    //saveAdd
    saveAdd(){
      this.bookForm.pictureurl = this.backPictureUrl
      this.$axios.post(this.$backEnd+'/books/save', this.bookForm).then(res=>{
        console.log(res)
        this.loadGet()
        this.addDialogVisible=false
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //saveEdit
    saveEdit(){
      this.$axios.post(this.$backEnd+'/books/update',this.bookForm).then(res=>{
        console.log(res)
        this.loadGet()
        this.editDialogVisible=false
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //addBook
    addBook(){
      this.addDialogVisible=true
      this.bookForm= {
        id: "",
        name: "",
        author: "",
        price: "",
        number: ""
      }
    },
    //editBook
    editBook(row){
      this.editDialogVisible=true
      this.bookForm= {
        id: row.id,
        name: row.name,
        author: row.author,
        price: row.price,
        number: row.number
      }
    },
    //delBook
    delBook(row){
      //弹窗确认是否删除
      this.$confirm('此操作将永久删除该乐器, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
            this.$axios.post(this.$backEnd+'/books/delete',{
              id:row.id
            }).then(res=>{
              console.log(res)
              this.loadGet()
            })
                .catch(error=>{
                  console.error(error)
                })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //subNum
    subNum(row){
      this.$axios.post(this.$backEnd+'/books/subNum',{
        id:row.id,
        num:1
      }).then(res=>{
        console.log(res)
        this.loadGet()
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //addNum
    addNum(row){
      this.$axios.post(this.$backEnd+'/books/addNum',{
          id:row.id,
          num:1
      }).then(res=>{
        console.log(res)
        this.loadGet()
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //queryAuthorName
    queryAuthorName(){
      this.$axios.get(this.$backEnd+'/books/blurryFindByAuthor',{
        params:{
          author:this.authorName
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.data
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //queryBookName
    queryBookName(){
      this.$axios.get(this.$backEnd+'/books/blurryFindByName',{
        params:{
          name:this.bookName
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.data
      })
          .catch(error=>{
            console.error(error)
          })
    },
    //GetBookList
    loadGet(){
      this.$axios.get(this.$backEnd+'/books/list').then(res=>{
        console.log(res)
        this.tableData=res.data.data
        console.log("this.tableData", this.tableData)
      })
    }
  },

  beforeMount(){
    this.loadGet()
  }
}
</script>

<style scoped>

</style>