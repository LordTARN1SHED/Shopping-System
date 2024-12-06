<template>
  <div>
    <div style="margin-bottom: 5px">
      <!--支付-->
      <el-button type="primary" @click="order">提交</el-button>
      <!--重置-->
      <el-button type="danger" @click="delFromCartAll">清空</el-button>
      <!--总价-->
      <el-tag
          style="float: right"
          :type="totalCost > user.balance ? 'danger' : 'primary'"
          disable-transitions>总价：{{totalCost.toFixed(2)}}</el-tag>
      <!--余额-->
      <el-tag
          style="float: right"
          :type="user.balance < 0 ? 'danger' : 'primary'"
          disable-transitions>余额：{{user.balance}}</el-tag>
  </div>
    <template v-if="totalCost > user.balance">
      <el-alert
          title="余额不足"
          type="warning">
      </el-alert>
    </template>
    <template v-if="tableData.length === 0">
      <el-alert
          title="购物车为空"
          type="warning">
      </el-alert>
    </template>
    <template v-if=" tableData.length !== 0 && totalCost === 0">
      <el-alert
          title="未选择商品，数量为0"
          type="warning">
      </el-alert>
    </template>
    <template v-for="(item, index) in tableData">
      <el-alert
          v-if="item.number > item.stock"
          :key="index"
          title="库存不足"
          type="warning">
      </el-alert>
    </template>




    <el-table :data="tableData"
            :header-cell-style="{background:'#b0b3b7',color:'#484444'}"
            border style="font-size: 20px;"
    >
    <el-table-column prop="id" label="编号" width="150">
    </el-table-column>
      <el-table-column label="图片" width="300">
        <template slot-scope="scope">
          <img :src="require('@/assets/' + scope.row.pictureurl)">
        </template>
      </el-table-column>
      <el-table-column prop="name" label="乐器" width="120">
      </el-table-column>
      <el-table-column prop="author" label="制造商" width="120">
      </el-table-column>
      <el-table-column prop="price" label="价格" width="100">
      </el-table-column>
      <el-table-column prop="number" label="数量" width="100">
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="100">
      </el-table-column>
      <el-table-column prop="operate" label="选项">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="addToCart(scope.row)">加购</el-button>
          <el-button size="small" type="warning" @click="subFromCart(scope.row)">减少</el-button>
          <el-button size="small" type="danger" @click="delFromCart(scope.row)">删除</el-button>
          <el-button size="small" type="success" @click="checkReview(scope.row.id)">查看评论</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        :before-close="cancel"
        center>
      <span style="display: flex; justify-content: center; align-items: center;">余额：{{user.balance}}</span><br>
      <span style="display: flex; justify-content: center; align-items: center;">支付：{{totalCost}}</span><br>
      <div style="display: flex; justify-content: center;"> <!-- 使用Flexbox布局 -->
        <el-radio-group v-model="shippingMethod">
          <el-radio label="0">空运</el-radio>
          <el-radio label="1">陆运</el-radio>
        </el-radio-group>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="letMeThink">我再想想</el-button>
        <el-button type="primary" @click="pay">确定支付</el-button>
      </span>
    </el-dialog>
</div>
</template>

<script>
export default {
  totalCost: 0,
  name: "ShoppingCart",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      tempData:[],
      tempId: 0,
      shippingMethod: 1,
      orderSub:{
        orderid:"",
        payornot: "",
        userid: "",
        time: "",
        ship: "",
        totalval: ""
      },
      centerDialogVisible: false,
    }
  },
  methods:{
    checkReview(id) {
      window.location.href = 'http://49.140.29.32:80/bookInfo.php?bookid=' + id;
    },

    //cancel
    cancel: function () {
      //save the order
      this.orderSub.time = new Date(+new Date()+8*3600*1000).toISOString().slice(0, 19).replace('T', ' ')
      console.log("time", this.orderSub.time)
      this.orderSub.payornot = 0
      this.orderSub.userid = this.user.id
      this.orderSub.totalval = this.totalCost
      this.orderSub.ship = this.shippingMethod
      console.log("ship", this.orderSub.ship)
      let promises = []
      let promise = this.$axios.post(this.$backEnd + '/orders/save', this.orderSub).then(res=>{
        console.log(res)
        this.loadGet()
      }).catch(error=>{
        console.error(error)
      })
      promises.push(promise)

      Promise.all(promises).then(() => {
        /*//iterate all the book and delete them from the table
        for (let i = 0; i < this.tableData.length; i++) {
          this.$axios.post(this.$backEnd + '/books/lessen/' + this.tableData[i].id + '/' + this.tableData[i].number).then(res => {
            console.log(res)
          })
              .catch(error => {
                console.error(error)
              })
        }*/

        //delete all the book from the cart
        this.$axios.post(this.$backEnd + '/cart/dump/' + this.user.id).then(res => {
          console.log(res)
          this.tableData = []
          this.loadGet()
          return
        })
            .catch(error => {
              console.error(error)
            })

        this.centerDialogVisible = false
        location.reload();
      })
    },

    //letMeThink
    letMeThink: function () {
      //save the order
      this.orderSub.time = new Date(+new Date()+8*3600*1000).toISOString().slice(0, 19).replace('T', ' ')
      console.log("time", this.orderSub.time)
      this.orderSub.payornot = 1
      this.orderSub.userid = this.user.id
      this.orderSub.totalval = this.totalCost
      this.orderSub.ship = this.shippingMethod
      console.log("ship", this.orderSub.ship)
      let promises = []
      let promise = this.$axios.post(this.$backEnd + '/orders/save', this.orderSub).then(res=>{
        console.log(res)
        this.loadGet()
      }).catch(error=>{
        console.error(error)
      })
      promises.push(promise)

      Promise.all(promises).then(() => {
        //delete all the book from the cart
        this.$axios.post(this.$backEnd + '/cart/dump/' + this.user.id).then(res => {
          console.log(res)
          this.tableData = []
          this.loadGet()
          return
        })
            .catch(error => {
              console.error(error)
            })

        this.centerDialogVisible = false
      })
    },

    //order
    order: function () {
      if(this.totalCost === 0) {
        return
      }
      if (this.totalCost > this.user.balance) {
        return
      }
      //check if every book is available, if not, return
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].number > this.tableData[i].stock) {
          return
        }
      }
      this.centerDialogVisible = true
    },

    //pay
    pay: function () {
      //save the order
      this.orderSub.time = new Date(+new Date()+8*3600*1000).toISOString().slice(0, 19).replace('T', ' ')
      console.log("time", this.orderSub.time)
      this.orderSub.payornot = 2
      this.orderSub.userid = this.user.id
      this.orderSub.totalval = this.totalCost
      this.orderSub.ship = this.shippingMethod
      console.log("ship", this.orderSub.ship)
      let promises = []
      let promise = this.$axios.post(this.$backEnd + '/orders/save', this.orderSub).then(res=>{
        console.log(res)
        this.loadGet()
      }).catch(error=>{
        console.error(error)
      })
      promises.push(promise)

      Promise.all(promises).then(() => {
        //iterate all the book and delete them from the table
        for (let i = 0; i < this.tableData.length; i++) {
          this.$axios.post(this.$backEnd + '/books/lessen/' + this.tableData[i].id + '/' + this.tableData[i].number).then(res => {
            console.log(res)
          })
              .catch(error => {
                console.error(error)
              })
        }

        //delete all the book from the cart
        this.$axios.post(this.$backEnd + '/cart/dump/' + this.user.id).then(res => {
          console.log(res)
          this.tableData = []
          this.loadGet()
          return
        })
            .catch(error => {
              console.error(error)
            })

        //update the balance
        this.$axios.post(this.$backEnd + '/xuser/costBalance', {
          "id": this.user.id,
          "cost": this.totalCost
        }).then(res => {
          console.log(res)
          //this.user.balance = this.user.balance - this.totalCost
          //notice that when all the books are paid, the totalCost will be 0
          console.log("this.user", this.user)
          console.log("this.totalCost", this.totalCost)
          this.user = res.data.data
          sessionStorage.setItem('CurUser', JSON.stringify(this.user))
          //update user
          this.$store.commit('setUser', this.user)
          return
        }).catch(error => {
          console.error(error)
        })

        this.centerDialogVisible = false
      })
    },

    //delFromCartAll
    delFromCartAll: function () {
      this.$axios.post(this.$backEnd + '/cart/dump/' + this.user.id ).then(res => {
        console.log(res)
        this.tableData = []
        this.loadGet()
        return
      })
          .catch(error => {
            console.error(error)
          })
    },
    //addToCart
    addToCart: function (row) {
          this.$axios.post(this.$backEnd + '/cart/add/' + this.user.id + '/' + row.id).then(res => {
            console.log(res)
            this.tableData = []
            this.loadGet()
            return
          })
              .catch(error => {
                console.error(error)
              })

    },
    subFromCart: async function (row) {
      try {
        await this.$axios.post(this.$backEnd + '/cart/sub/' + this.user.id + '/' + row.id);
      } catch (error) {
        console.error(error);
      }
      //check if the number is 0, if so, delete it
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].number <= 0) {
          try {
            await this.$axios.post(this.$backEnd + '/cart/del/' + this.user.id + '/' + this.tableData[i].id);
          } catch (error) {
            console.error(error);
          }
        }
      }
      this.tableData = [];
      this.loadGet();
    },

    //delFromCart
    delFromCart: function (row) {
      //弹窗确认是否删除
      this.$confirm('此操作将永久删除该乐器, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
            this.$axios.post(this.$backEnd + '/cart/del/' + this.user.id + '/' + row.id).then(res => {
              console.log(res)
              this.tableData = []
              this.loadGet()
              return
            })
                .catch(error => {
                  console.error(error)
                })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //GetBookList
    loadGet(){
      this.$axios.get(this.$backEnd+'/cart/list/'+this.user.id).then(res=>{
        this.tempData = res.data.data
        console.log("this.tempData",this.tempData)
        let promises = []
        //iterate tempData
        for(let i=0;i<this.tempData.length;i++){
          let promise = this.$axios.get(this.$backEnd+'/books/'+this.tempData[i].bookid).then(res=>{
            this.tableData.push({
              id: res.data.data.id,
              pictureurl: res.data.data.pictureurl,
              name: res.data.data.name,
              author: res.data.data.author,
              price: res.data.data.price,
              stock: res.data.data.number,
              number: this.tempData[i].number
            })
          })
          promises.push(promise)
        }
        // When all requests are finished, sort the data
        Promise.all(promises).then(() => {
          this.tableData.sort(function (a, b) {
            return a.id - b.id
          })
        })
      })
          .catch(error=>{
            console.error(error)
          })
    }

  },
  beforeMount(){
    this.loadGet()
  },
  //calculate total cost
  computed: {
    totalCost: function () {
      let total = 0
      for (let i = 0; i < this.tableData.length; i++) {
        total += this.tableData[i].price * this.tableData[i].number
      }
      return total
    }
  }

}
</script>

<style scoped>

</style>