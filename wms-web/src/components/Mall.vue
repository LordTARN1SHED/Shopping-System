<template>
  <div>
  <div style="margin-bottom: 5px">
    <el-input v-model="bookName" placeholder="乐器查找" style="width: 200px;" suffix-icon="el-icon-search"
      @keyup.enter.native="queryBookName"></el-input>
    <el-button type="primary" style="margin-left: 5px;" @click="queryBookName">查询</el-button>
    <el-input v-model="authorName" placeholder="制造商查找" style="width: 200px;margin-left: 5px;" suffix-icon="el-icon-search"
      @keyup.enter.native="queryAuthorName"></el-input>
    <el-button type="primary" style="margin-left: 5px;" @click="queryAuthorName">查询</el-button>
  </div>
  <el-table :data="tableData"
    :header-cell-style="{background:'#b0b3b7',color:'#484444'}"
    border style="font-size: 14px;"
  >
    <el-table-column prop="id" label="编号" width="150" sortable>
    </el-table-column>
    <el-table-column label="图片" width="300">
      <template slot-scope="scope">
        <img :src="require('@/assets/' + scope.row.pictureurl)">
      </template>
    </el-table-column>
    <el-table-column prop="name" label="乐器" width="200" sortable>
    </el-table-column>
    <el-table-column prop="author" label="制造商" width="120" sortable>
    </el-table-column>
    <el-table-column prop="price" label="价格" width="100" sortable>
    </el-table-column>
    <el-table-column prop="number" label="库存" width="100" sortable>
    </el-table-column>
    <el-table-column prop="operate" label="选项">
      <template slot-scope="scope">
        <el-button size="small" type="success" @click="addToCart(scope.row)">加购</el-button>
        <el-button size="small" type="success" @click="checkReview(scope.row.id)">查看评论</el-button>
      </template>
    </el-table-column>
  </el-table>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        :before-close="reload"
        center>
      <el-tag
          :type="this.flag === 0 ? 'warning' : 'danger'"
          disable-transitions>{{this.flag === 0 ? '库存不足' : '商品已经下架'}}
      </el-tag>
    </el-dialog>
  </div>
</template>

<script>
export default {name: "Mall", data() {
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      flag: 2,
      bookName: "",
      authorName: "",
      centerDialogVisible: false,
    }
  },
  methods:{
    checkReview(id) {
      window.location.href = 'http://49.140.29.32:80/bookInfo.php?bookid=' + id;
    },

    //reload
    reload: function () {
      location.reload();
    },

    //addToCart
    addToCart: async function (row) {
      try {
        const res = await this.$axios.get(this.$backEnd + '/books/' + row.id);

        if (res.data.data.number <= 0) {
          this.flag = 0;
          this.centerDialogVisible = true;
          return;
        }

        this.flag = 2;
        row.number = res.data.data.number;

        const uniqueRes = await this.$axios.post(this.$backEnd + '/cart/unique', {
          "userid": this.user.id,
          "bookid": row.id
        });

        if (uniqueRes.data.data == 0) {
          const newRes = await this.$axios.post(this.$backEnd + '/cart/new/' + this.user.id + '/' + row.id);
          console.log(newRes);
          return;
        } else {
          const addRes = await this.$axios.post(this.$backEnd + '/cart/add/' + this.user.id + '/' + row.id);
          console.log(addRes);
          return;
        }
      } catch (error) {
        this.flag = 1;
        this.centerDialogVisible = true;
        console.error(error);
      }
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
      })
          .catch(error=>{
            console.error(error)
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