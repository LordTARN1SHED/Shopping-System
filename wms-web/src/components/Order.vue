<template>
  <div>
    <div>
      <!--余额-->
      <el-tag
          style="float: right"
          :type="user.balance < 0 ? 'danger' : 'primary'"
          disable-transitions>余额：{{user.balance}}</el-tag>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#b0b3b7',color:'#484444'}"
              :span-method="spanMethod"
              border
    >
      <el-table-column prop="orderid" label="订单号" width="110">
      </el-table-column>
      <el-table-column prop="bookid" label="乐器编号" width="110">
      </el-table-column>
      <el-table-column prop="name" label="乐器" width="135">
      </el-table-column>
      <el-table-column prop="author" label="制造商" width="135">
      </el-table-column>
      <el-table-column prop="price" label="单价(元)" width="90">
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="80">
      </el-table-column>
      <el-table-column prop="num" label="购买数量" width="70">
      </el-table-column>
      <el-table-column label="评价" width="90">
        <template slot-scope="scope">
          <el-button v-if = "scope.row.payornot === 2" size="small" type="success" @click="writeReview(scope.row)">评论</el-button>
          <el-tag v-else size="small" type="warning">无法评论</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="totalval" label="合计(元)" width="100">
      </el-table-column>
      <el-table-column prop="time" label="提交时间" width="150">
      </el-table-column>
      <el-table-column prop="ship" label="运输" width="70">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.ship === 0 ? 'warning' : 'success'"
              disable-transitions>{{scope.row.ship === 0 ? '空运' : '陆运'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="payornot" label="支付状态" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.payornot === 0 ? 'danger' : (scope.row.payornot === 1 ? 'warning' : 'success')"
              disable-transitions>{{scope.row.payornot === 0 ? '已取消' : (scope.row.payornot === 1 ? '待支付' : '已支付')}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <template v-if = "scope.row.payornot === 1">
            <el-button size="small" type="success" @click="prepare(scope.$index)">支付</el-button><br><br>
            <el-button size="small" type="danger" @click="cancel(scope.$index)">取消</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <template v-if="dialogType === 'balanceInsufficient'">
        <el-tag type="danger" style="display: flex; justify-content: center; align-items: center;">余额不足</el-tag>
      </template>
      <template v-else-if="dialogType === 'outOfStock'">
        <el-tag type="danger" style="display: flex; justify-content: center; align-items: center;">库存不足</el-tag>
      </template>
      <template v-else-if="dialogType === 'balanceAndCost'">
        <span style="display: flex; justify-content: center; align-items: center;">余额：{{user.balance}}</span><br>
        <span style="display: flex; justify-content: center; align-items: center;">支付：{{totalCost}}</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="pay">确定支付</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Order",
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      tempData1:[],
      tempId: 0,
      dialogType: '',
      totalCost: 0,
      currentRowIndex: 0,
      centerDialogVisible: false,
    }
  },
  methods:{
    //review
    writeReview: function (row) {
      window.location.href = 'http://49.140.29.32:80/review.php?orderid=' + row.orderid + '&bookid=' +row.bookid;
    },

    //prepare
    prepare: function (index) {
      this.currentRowIndex = index
      if(this.tableData[index].totalval > this.user.balance) {
        this.dialogType = 'balanceInsufficient'
        this.centerDialogVisible = true
        return
      }
      let flag = true
      for(let i = index; i < this.tableData.length; i++) {
        if(this.tableData[i].orderid === this.tableData[index].orderid){
          if (this.tableData[i].num > this.tableData[i].stock) {
            flag = false
            break
          }
        }
        else break
      }
      if(flag === false) {
        this.dialogType = "outOfStock"
        this.centerDialogVisible = true
        return
      }

      this.dialogType = "balanceAndCost"
      this.totalCost = this.tableData[index].totalval
      this.centerDialogVisible = true
      return
    },

    //cancel
    cancel: function (index) {
      //update the order
      this.$axios.post(this.$backEnd + '/orders/update/' + this.tableData[index].orderid + '/' + 0).then(res => {
        console.log(res)
        this.tableData[index].payornot = 0
        return
      }).catch(error => {
        console.error(error)
      })

    },

    //pay
    pay: function () {
      let index = this.currentRowIndex
      //iterate all the book and delete them from the table
      for (let i = index; i < this.tableData.length; i++) {
        if(this.tableData[i].orderid === this.tableData[index].orderid) {
          this.$axios.post(this.$backEnd + '/books/lessen/' + this.tableData[i].bookid + '/' + this.tableData[i].num).then(res => {
            console.log(res)
            this.tableData[i].stock -= this.tableData[i].num
            this.tableData[i].time = new Date(+new Date()+8*3600*1000).toISOString().slice(0, 19).replace('T', ' ')
          }).catch(error => {
            console.error(error)
          })
        }
      }

      //update the balance
      this.$axios.post(this.$backEnd + '/xuser/costBalance',{
        "id": this.user.id,
        "cost": this.totalCost
      }).then(res => {
        console.log(res)
        console.log("this.user",this.user)
        console.log("this.totalCost",this.totalCost)
        this.user = res.data.data
        sessionStorage.setItem('CurUser', JSON.stringify(this.user))
        //update user
        this.$store.commit('setUser', this.user)
        return
      }).catch(error => {
        console.error(error)
      })

      //update the order
      this.$axios.post(this.$backEnd + '/orders/update/' + this.tableData[index].orderid + '/' + 2).then(res => {
        console.log(res)
        this.tableData[index].payornot = 2
        return
      }).catch(error => {
        console.error(error)
      })

      this.centerDialogVisible = false
    },

    //spanMethod
    spanMethod: function({ rowIndex, columnIndex }) {
      if (columnIndex === 0 || (8 <= columnIndex && columnIndex <= 12 )) {
        let standard = this.tableData[rowIndex].orderid
        if (rowIndex === 0 || standard !== this.tableData[rowIndex - 1].orderid) {
          let span = 1
          for (let i = rowIndex + 1; i < this.tableData.length; i++) {
            if (this.tableData[i].orderid == standard) span++
            else break
          }
          return {
            rowspan: span,
            colspan: 1
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }
      else {
        return {
          rowspan: 1,
          colspan: 1
        }
      }
    },

    //GetOrderList
    loadGet(){
      this.$axios.get(this.$backEnd+'/orders/list/'+this.user.id).then(res=>{
        this.tempData1 = res.data.data
        console.log("this.tempData1",this.tempData1)
        let promises = []

        //iterate tempData
        for(let i=0; i<this.tempData1.length; i++){
          let promise = this.$axios.get(this.$backEnd+'/orders/orderbook/'+this.tempData1[i].orderid).then(res=>{
            let tempData2 = res.data.data
            console.log("tempData2",tempData2)

            let bookPromises = tempData2.map(bookData => {
              return this.$axios.get(this.$backEnd + '/books/' + bookData.bookid);
            });

            return Promise.all(bookPromises)
                .then(bookResponses => {
                  bookResponses.forEach((bookRes) => {
                    // 在tempData2中查找与orderid和bookid对应的num值
                    const matchedNum = tempData2.find(data => data.orderid === this.tempData1[i].orderid
                        && data.bookid === bookRes.data.data.id);

                    this.tableData.push({
                      orderid: this.tempData1[i].orderid,
                      bookid: bookRes.data.data.id,
                      name: bookRes.data.data.name,
                      author: bookRes.data.data.author,
                      price: bookRes.data.data.price,
                      stock: bookRes.data.data.number,
                      num: matchedNum.num,
                      totalval: this.tempData1[i].totalval,
                      time: this.tempData1[i].time,
                      ship: this.tempData1[i].ship,
                      payornot: this.tempData1[i].payornot
                    });
                  });
                });
          });
          promises.push(promise)
        }
        // When all requests are finished, sort the data
        Promise.all(promises).then(() => {
          this.tableData.sort(function (a, b) {
            if(a.orderid === b.orderid)
            {
              return a.bookid - b.bookid
            }
            return a.orderid - b.orderid
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
  }

}
</script>

<style scoped>

</style>
