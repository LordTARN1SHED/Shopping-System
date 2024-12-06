<template>
  <div class="registerBody">
    <div class="registerDiv">
      <div class="register-content">
        <h1 class="register-title">用户注册</h1>
        <el-form :model="registerForm" label-width="100px"
        :rules="rules" ref="registerForm">

          <el-form-item label="账号" prop="name">
            <el-input style="width: 200px" type="text" v-model="registerForm.name"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input style="width: 200px" type="password" v-model="registerForm.password"
                      show-password autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="邀请码" prop="invitation">
            <el-input style="width: 185px" type="text" v-model="registerForm.invitation"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="12">
              <el-button type="warning" @click="login" :disabled="login_disabled">登录</el-button>
            </el-col>
            <el-col :span="12">
              <el-button type="primary" @click="register" :disabled="register_disabled">注册</el-button>
            </el-col>
          </el-row>
          </el-form-item>
        </el-form>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "xRegister",
  data() {
    return {
      register_disabled: false,
      login_disabled: false,
      registerForm: {
        name: '',
        password: '',
        role:'',
        balance:'',
        invitation:''
      },
      rules: {
        name: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        invitation:[
          { required: true, message:'请输入邀请码' ,trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login(){
      this.login_disabled = true;
      this.$router.replace("/")
    },
    register() {
      console.log("push register")
      this.register_disabled = true;
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          // 前端格式验证成功，此处执行注册操作
          // 先提交请求查看邀请码对应权限
          console.log("valid")
          this.$axios.post(this.$backEnd+"/inv/register",{invitation:this.registerForm.invitation})
              .then(res=>res.data).then(res=>{
                //响应
                console.log("res-data="+res.data);
            if (res.code==200)
                {
                  this.registerForm.role = res.data.role;
                  alert(this.registerForm.role==0?"角色为管理员":"角色为用户");
                }
                else
                {
                  //查询失败
                  alert("邀请码无效，请重试！");
                  this.register_disabled = false;
                  this.$refs.registerForm.resetFields();
                  //注意，这里要重设为2
                  this.registerForm.role = 2;
                }
              })
              .catch(error=>{
                console.log(error);
              })
              .then(()=>{
              if (this.registerForm.role!=2){
              //有邀请码，继续完成注册过程
              // 用户注册，设置余额为1000
              if (this.registerForm.role==1) this.registerForm.balance=1000;
                if (this.registerForm.role==0) this.registerForm.balance=9999;
              this.$axios.post(this.$backEnd+"/xuser/save",this.registerForm).then(res1=>res1.data).then(res1=>{
                if (res1.code==200){
                  //注册成功
                  alert("注册成功，请登录!");
                  this.$router.replace("/");
                }
                else{
                  alert("注册失败，请重试！");
                  this.register_disabled = false;
                  this.$refs.registerForm.resetFields();
                }
              })
            }//role!=2
          })
        }//valid
        else {
          this.register_disabled = false;
          console.log('校验失败');
        }
      });
    }
  }
}
</script>

<style scoped>
.registerBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}
.registerDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: #fff;
  border-radius: 5%;
}
.register-title {
  margin: 20px 0;
  text-align: center;
}
.register-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>
