<template>
<div >
  <el-form :inline="true" >

    <el-row  >
      <el-col :span="4" style="margin-bottom: 10px">
        <el-button type="primary" @click="add">新增</el-button>
        <el-popover
          placement="top"
          width="160"
          v-model="visible">
          <p>确定删除吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="bachdelete">确定</el-button>
          </div>
          <el-button type="danger" slot="reference">批量删除</el-button>
        </el-popover>
      </el-col>
      <el-col :span="5">
        <el-form-item label="姓名">
          <el-input  v-model="queryForm.name" clearable @keyup.enter.native="loadTable(1)" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="公司位置">
          <el-cascader :options="addresses"
                       clearable
                       v-model="queryForm.address">

            <template slot-scope="{ node, data }" >
              <span>{{ data.label }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
      </el-col>
      <el-col :span="8">
          <el-form-item label="入职日期">
            <el-date-picker
              v-model="queryForm.createdate"
              type="date"
              placeholder="选择日期"
              value-format=yyyy-MM-dd
              :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
        <el-button  type="primary" @click="loadTable(1)">查询</el-button>
        <el-button  type="info" @click="clearField">重置</el-button>
<!--          <el-input  v-model="queryForm.bumen" clearable ></el-input>-->
<!--        </el-form-item>-->
      </el-col>
    </el-row>
    </el-form>

  <!--表单显示-->
  <el-table
    ref="multipleTable"
    :data="page.content"
    stripe
    border
    tooltip-effect="dark"
    style="width: 100%"
    @selection-change="handleSelectionChange"
    :default-sort = "{prop: 'name',prop:'age',prop:'createdate', order: 'descending'}"
  >
    <el-table-column
      type="selection"
      width="50">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="100"
      sortable>
    </el-table-column>
    <el-table-column
      prop="sex"
      label="性别"
      width="80">
    </el-table-column>
    <el-table-column
      prop="age"
      label="年龄"
      width="80"
      sortable>
    </el-table-column>
    <el-table-column
      prop="createdate"
      label="入职日期"
      width="120"
      sortable>
    </el-table-column>
    <el-table-column
      prop="email"
      label="邮箱"
      width="120"
      :show-overflow-tooltip="true" >
    </el-table-column>
    <el-table-column
      prop="zhiwei"
      label="自我定位"
      width="250">
    </el-table-column>
    <el-table-column
      prop="plan"
      label="个人规划"
      width="250"
      :show-overflow-tooltip="true" >
    </el-table-column>
    <el-table-column
      prop="introduction"
      label="个人简介"
      width="200"
      :show-overflow-tooltip="true" >
    </el-table-column>
    <el-table-column
      prop="address"
      label="所在公司地址"
      width="200">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button type="primary" icon="el-icon-edit" size="small" circle @click="edit(scope.row)"></el-button>
        <el-popconfirm
          confirm-button-text='好的'
          cancel-button-text='不用了'
          @confirm="del(scope.row.id)"
          icon="el-icon-info"
          icon-color="red"
          title="确定删除吗？"
        >
          <el-button slot="reference" type="danger" icon="el-icon-delete" circle size="small"></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!--分页模块-->
  <el-row type="flex" justify="center" style="margin-top: 10px" >
    <span class="demonstration"></span>
    <el-pagination
      @prev-click="loadTable"
      @next-click="loadTable"
      @current-change="loadTable"
      :current-page="pageNum"
      :page-size="pageSize"
      layout="total, prev, pager, next,jumper"
      :total="page.totalElements">
    </el-pagination>
  </el-row>

  <!--添加用户信息用表格-->
  <el-dialog
    title="用户信息"
    :visible.sync="dialogVisible"
    width="35%">
    <el-form ref="form"
             :model="form"
             :rules="rulesRorm"
             label-width="100px"
             label-position="left"
             class="demo-ruleForm">

      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.sex" placeholder="请选择">
          <el-option
            v-for="item in sex"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age"
                  type="number"
                  onkeypress="return( /[\d]/.test(String.fromCharCode(event.keyCode) ) )"
        ></el-input>
      </el-form-item>
      <el-form-item label="入职日期" prop="day">
        <el-date-picker
          v-model="form.createdate"
          type="date"
          placeholder="选择日期"
          value-format=yyyy-MM-dd
          :picker-options="pickerOptions">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="tel">
        <el-input v-model="form.tel"></el-input>
      </el-form-item>
      <el-form-item label="个人规划" prop="texts">
        <el-input
          type="textarea"
          :rows="4"
          v-model="form.plan"></el-input>
      </el-form-item>
      <el-form-item label="自我定位" prop="zhiwei">
        <el-checkbox-group v-model="checkList" @change="handleChange3">
          <el-checkbox label="技术型"></el-checkbox>
          <el-checkbox label="业务型"></el-checkbox>
          <el-checkbox label="营销型"></el-checkbox>
          <el-checkbox label="管理型"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="个人简介" >
            <!--文件上传-->
        <el-upload
          drag
          ref="upload"
          action="http://localhost:8888/files/upload"
          :on-success="fileUploadSuccess"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>

      <el-form-item label="公司所在地址" >
        <el-cascader ref="cascader"
                     :options="addresses"
                     v-model="address"
                     @change="handleChange">
          <template slot-scope="{ node, data }" >
            <span>{{ data.label }}</span>
            <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
          </template>
        </el-cascader>
      </el-form-item>


    </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </span>
  </el-dialog>

</div>

</template>

<script>
    export default {
        name: "MainVue",
      data () {

        var isMobileNumber= (rule, value, callback) => {
          if (!this.form.tel) {
            return new Error("请输入电话号码");
          } else {
            console.log(value);
            const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
            const isPhone = reg.test(value);
            value = Number(value); //转换为数字
            if (typeof value === "number" && !isNaN(value)) {//判断是否为数字
              value = value.toString(); //转换成字符串
              if (value.length < 0 || value.length > 12 || !isPhone) { //判断是否为11位手机号
                callback(new Error("手机号码格式如:138xxxx8754"));
              } else {
                callback();
              }
            } else {
              callback(new Error("请输入电话号码"));
            }
          }
        };


          var checkAge = (rule, value, callback) => {
            if (!value) {
              return callback(new Error('年龄不能为空'));
            }
            setTimeout(() => {
              if (!value) {
                callback(new Error('请输入数字值'));
              } else {
                if (value < 18) {
                  callback(new Error('必须年满18岁'));
                } else if (value > 35){
                  callback(new Error('你年龄也太大了吧'));
                }else {
                  callback();
                }
              }
            }, 0);
          };

          return {
            visible: false,
            page: [],
            queryForm: {
              name: '',
              zhiwei:'',
              createdate:'',
              address:''
            },
            pageNum: 1,
            pageSize: 5,
            dialogVisible: false,
            form: {
              name:'',
              sex:'',
              age:'',
              createdate:'',
              email:'',
              tel:'',
              plan:'',
              zhiwei:'',
              address:''
            },
            flag: {},
            shuju: [],
            address:[],
            zhiwei:[],

            checkList:[],

            pickerOptions: {
              disabledDate(time) {
                return time.getTime() > Date.now();
              }
            },

            rulesRorm:{
              name: [
                { required: true, message: '请输入名称', trigger: 'blur' }
              ],
              day: [
                { type: 'date', required: true, message: '请选择入职日期', trigger: 'blur' }
              ],
              zhiwei: [
                {  required: true, message: '请至少选择一个个人定位', trigger: 'blur' },
                {required: true, message: '请至少选择一个个人定位', trigger: 'change'}
              ],
              age:[
                {validator: checkAge, trigger:'blur'}
                ],
              email: [
                { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' },
                {required: true, message:'请输入邮箱', trigger: 'blur'}
              ],
              tel: [
                { required: true, message: "请输入手机号码", trigger: "blur" },
                { validator: isMobileNumber, trigger: "blur" }
              ],
              texts:[
                {
                  min: 10,
                  max: 100,
                  message: '备注长度在 10 到 100 个字符',
                  trigger: 'blur',
                },
              ]
            },

            limitNum: 1,
            formLabelWidth: '80px',
            fileList: [],

            sex:[{
              value: '男'
            },{
              value: '女'
            }],

            options: [{
              value: '技术型',
            }, {
              value: '业务型',
            }, {
              value: '营销型',
            }, {
              value: '管理型',
            }],

            addresses: [{
              value:'湖南省',
              label:'湖南省',
              children:[{
                value:"长沙市",
                label:'长沙市',
                children: [{
                  value:'岳麓区',
                  label:'岳麓区',
                },{
                  value:'开福区',
                  label:'开福区',
                }]
              }]
            },{
              value: '北京市',
              label:'北京市',
              children: [{
                value: '北京市',
                label: '北京市',
                children: [{
                  value: '朝阳区',
                  label: '朝阳区',
                }]
              }]
            },{
              value: '上海市',
              label: '上海市',
              children: [{
                value: '上海市',
                label: '上海市',
                children: [{
                  value: '虹口区',
                  label: '虹口区',
                }]
              }]
            },{
              value: '深圳市',
              label: '深圳市',
              children: [{
                value: '深圳市',
                label: '深圳市',
                children: [{
                  value: '福田区',
                  label: '福田区',
                }]
              }]
            }]
          }
      }, created() {
        this.loadTable(this.pageNum);
      },
      methods: {
        handleChange(value) {
          this.form.address=value[0]+'|'+value[1]+'|'+value[2];
        },
        handleChange2(value) {
          this.queryForm.address=value[0]+'|'+value[1]+'|'+value[2];
        },
        handleChange3(value) {
          console.log(value.toString());
          this.zhiwei=value;
          this.form.zhiwei=value.toString();
        },
          //重置键
        clearField(){
            this.queryForm.name='';
            this.queryForm.zhiwei='';
            this.queryForm.createdate='';
            this.queryForm.address='';
            this.address='';
            // this.queryForm = Object.assign({}, undefined)
            this.loadTable(this.pageNum = 1)
        },
        //复选框传输对象
        handleSelectionChange(val) {
          this.multipleSelection = val;
          this.shuju=this.multipleSelection;
          console.log(this.shuju);
          return this.shuju;
        },
        //批量删除
        bachdelete(){
            var len = this.shuju.length;
            for (var i = 0; i < len; i++) {
              this.$http.delete(`api/user/${this.shuju[i].id}`)
            }
            this.visible = false;
            this.loadTable(1);
        },
        //加载页面
          loadTable(num) {
          this.pageNum = num;
          console.log(this.queryForm.createdate)
          let params = {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.queryForm.name,
            address: this.queryForm.address,
            createdate: this.queryForm.createdate,
          }
          this.$http.get('api/user/page',{params}).then(res => {
            console.log(params)
            this.page = res.data.data
            console.log(res.data);
          })
        },
        //点击添加用户按钮，弹出信息表单并清空数据
        add() {
          this.dialogVisible = true;
          this.form = {}
          this.address='';
          this.zhiwei='';
          this.checkList='';
          this.$refs['upload'].clearFiles();
        },
        //编辑按钮
        edit(row) {
          this.form = JSON.parse(JSON.stringify(row));
          this.zhiwei = this.checkList;
          this.$nextTick(() => {
            this.$refs['upload'].clearFiles();
          })
          this.dialogVisible = true;
        },
        save() {
          let data = JSON.stringify(this.form);
          // 修改信息
          if (this.form.id) {
            this.$http.put('api/user', data,
              {headers:{'Content-Type': 'application/json;charset=UTF-8'}}).then(res => {
                this.dialogVisible = false;
                this.loadTable(this.pageNum);
            })
          } else {
            // 新增信息
            this.$http.post('api/user', data,
              {headers:{'Content-Type': 'application/json;charset=UTF-8'}}).then(res => {
              this.dialogVisible = false;
              this.$message.success(res.data.msg)
              this.loadTable(this.pageNum)
            })
          }
        },
        //删除一条信息
        del(id) {//   '/api/user/' + {id}
          this.$http.delete(`/api/user/${id}`).then(res => {
            this.loadTable(this.pageNum);
          })
        },
        //上传文件
        fileUploadSuccess(response){
          console.log(response.name);
          this.form.introduction = response.data;
        }
      }
    }
</script>

