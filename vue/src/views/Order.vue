<template>

    <div style="padding: 10px">
        <!--    功能区域-->
        <div style="margin: 10px 0" v-if="name==='admin'">
            <el-button type="primary" @click="add" icon="el-icon-circle-plus-outline">新增</el-button>
            <el-upload
                action="http://localhost:9090/order/import"
                :on-success="handleUploadSuccess"
                :show-file-list=false
                :limit="1"
                accept='.xlsx'
                style="display: inline-block; margin: 0 10px"
            >
                <el-button type="primary" icon="el-icon-upload2">导入</el-button>
            </el-upload>
            <el-button type="primary" @click="exportOrder" icon="el-icon-download">导出</el-button>
        </div>

        <!--    搜索区域-->
        <el-input v-model="search" placeholder="请输入细分知识点" style="width: 20%"
                  clearable @keydown.enter.native="load"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>


        &nbsp;
        <el-dropdown>
            <el-button
                type='primary'
                icon="el-icon-sort"
            >
                难度选择<i class='el-icon-arrow-down el-icon--right'></i>
            </el-button>
            <template #dropdown split-button>
                <el-dropdown-menu slot="dropdown" type="primary" split-button>
                    <el-dropdown-item @click.native="getHardList(0)">难度 0</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList(1)">难度 1</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList(2)">难度 2</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList(3)">难度 3</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList(4)">难度 4</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        <el-dropdown>
            <el-button type="primary"
                       icon="el-icon-sort">
                大类知识点<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <template #dropdown :append-to-body="false">
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="getHardList('数学')">数学</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('数论')">数论</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('图论')">图论</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('算法')">算法</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('字符串')">字符串</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('博弈论')">博弈论</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('计算几何')">计算几何</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('数据结构')">数据结构</el-dropdown-item>
                    <el-dropdown-item>动态规划</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        <el-dropdown>
            <el-button type="primary" icon="el-icon-sort">
                掌握程度<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <template #dropdown :append-to-body="false">
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="getHardList('基础')">基础</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('分类基础')">分类基础</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('分类中级')">分类中级</el-dropdown-item>
                    <el-dropdown-item @click.native="getHardList('分类高级')">分类高级</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>


        <el-table
            v-loading="loading"
            :data="tableData"
            stripe
            style="width: 100%"
            max-height="790"
        >
            <el-table-column
                width="70"
                prop="id"
                label="ID"
                sortable
            >
            </el-table-column>
            <el-table-column
                width="300"
                prop="name"
                label="题目"
            >
            </el-table-column>

            <el-table-column
                prop="broadKnowledge"
                label="大类知识点">
            </el-table-column>
            <el-table-column
                prop="subdivisionKnowledge"
                label="细分知识点">
            </el-table-column>
            <el-table-column
                prop="hard"
                label="难度"
            >
            </el-table-column>
            <el-table-column
                prop="master"
                label="掌握程度">
            </el-table-column>

            <el-table-column label="操作" width="400" v-if="name==='admin'">
                <template #default="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>


            <el-table-column label="操作" v-if="name!=='admin'">
                <template #default="scope">
                    <el-popconfirm title="确定已解决吗？" @confirm="handleSave(scope.row.id)">
                        <template #reference>
                            <el-button plain size="mini" type="primary">解决</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>


        <el-dialog title="提示" v-model="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px">
                <el-form-item label="题目">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="大类知识点">
                    <el-input v-model="form.broadKnowledge" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="细分知识点">
                    <el-input v-model="form.subdivisionKnowledge" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="难度">
                    <el-input v-model="form.hard" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="掌握程度">
                    <el-input v-model="form.master" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
            </template>
        </el-dialog>


        <div style="margin: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
            >
            </el-pagination>
        </div>
    </div>
</template>

<style>
.el-dropdown {
    vertical-align: top;
}

.el-dropdown + .el-dropdown {
    margin-left: 15px;
}

.el-icon-arrow-down {
    font-size: 12px;
}
</style>


<script>


import request from "@/utils/request";

export default {
    components: {},
    data() {
        return {
            user: {},
            userOrder: {},
            loading: true,
            form: {},
            dialogVisible: false,
            search: '',
            currentPage: 1,
            pageSize: 10,
            total: 0,
            tableData: [],
            name: localStorage.getItem('username'),
            uid: localStorage.getItem('uid')
            //    pageControl: true,
        }
    },
    created() {
        let userStr = sessionStorage.getItem("user") || "{}"
        this.user = JSON.parse(userStr)
        // 请求服务端，确认当前登录用户的 合法信息
        request.get("/user/" + this.user.id).then(res => {
            if (res.code === '0') {
                this.user = res.data
            }
        })

        this.load()
    },
    methods: {
        load() {
            //   this.pageControl = true
            console.log("search: " + this.search);
            this.loading = true
            request.get("/order", {
                params: {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    search: this.search,
                }
            }).then(res => {
                this.loading = false
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },

        getHardList(search) {
            console.log("数据测试" + search);
            request.get("/order", {
                       params: {
                           pageNum: this.currentPage,
                           pageSize: this.pageSize,
                           search: search
                       }
                   })
                   .then(res => {
                       this.tableData = res.data
                       this.tableData = res.data.records
                       this.total = res.data.total
                       this.search = search
                       //  this.pageControl = false;
                   })
        },

        handleSizeChange(pageSize) {   // 改变当前每页的个数触发
            this.pageSize = pageSize
            this.load()
        },

        handleCurrentChange(pageNum) {  // 改变当前页码触发
            this.currentPage = pageNum
            this.load()
        },



        add() {
            this.dialogVisible = true
            this.form = {}
            if (this.$refs['upload']) {
                this.$refs['upload'].clearFiles()  // 清除历史文件列表
            }
        },

        save() {
            if (this.form.id) {  // 更新
                request.put("/order", this.form).then(res => {
                    console.log(res)
                    if (res.code === '0') {
                        this.$message({
                                          type: "success",
                                          message: "更新成功"
                                      })
                    } else {
                        this.$message({
                                          type: "error",
                                          message: res.msg
                                      })
                    }
                    this.load() // 刷新表格的数据
                    this.dialogVisible = false  // 关闭弹窗
                })
            } else {  // 新增
                request.post("/order", this.form).then(res => {
                    console.log(res)
                    if (res.code === '0') {
                        this.$message({
                                          type: "success",
                                          message: "新增成功"
                                      })
                    } else {
                        this.$message({
                                          type: "error",
                                          message: res.msg
                                      })
                    }

                    this.load() // 刷新表格的数据
                    this.dialogVisible = false  // 关闭弹窗
                })
            }
        },

        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row))
            this.dialogVisible = true
            this.$nextTick(() => {
                if (this.$refs['upload']) {
                    this.$refs['upload'].clearFiles()  // 清除历史文件列表
                }
            })
        },

        handleDelete(id) {
            console.log(id)
            request.delete("/order/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                                      type: "success",
                                      message: "删除成功"
                                  })
                } else {
                    this.$message({
                                      type: "error",
                                      message: res.msg
                                  })
                }
                this.load()  // 删除之后重新加载表格的数据
            })
        },

        handleSave(id) {
            console.log("oid: " + id);
            request.post("/user/insert/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                                      type: "success",
                                      message: "成功解决"
                                  })
                } else {
                    this.$message({
                                      type: "error",
                                      message: "请勿重复提交"
                                  })
                }
            })
        },


        exportOrder() {
            location.href = "http://" + window.server.filesUploadUrl + ":9090/order/export";
        },

        handleUploadSuccess(res) {
            if (res.code === "0") {
                this.$message.success("导入成功")
                this.load()
            }
        },
    }
}
</script>

