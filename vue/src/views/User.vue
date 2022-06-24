<template>
    <div style="padding: 10px">

        <!--    功能区域-->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="add">新增</el-button>

            <el-upload
                action="http://localhost:9090/user/import"
                :on-success="handleUploadSuccess"
                :show-file-list=false
                :limit="1"
                accept='.xlsx'
                style="display: inline-block; margin: 0 10px"
            >
                <el-button type="primary">导入</el-button>
            </el-upload>
            <el-button type="primary" @click="exportUser">导出</el-button>
        </div>

        <!--    搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入昵称" style="width: 20%" clearable
                      @keydown.enter.native="load"></el-input>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>

        <el-table
            v-loading="loading"
            :data="tableData"
            border
            stripe
            style="width: 100%">
            <el-table-column
                prop="id"
                label="ID"
                sortable
            >
            </el-table-column>
            <el-table-column
                prop="username"
                label="用户名">
            </el-table-column>
            <el-table-column
                prop="nickName"
                label="昵称">
            </el-table-column>
            <el-table-column
                prop="age"
                label="年龄">
            </el-table-column>
            <el-table-column
                prop="sex"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="address"
                label="班级">
            </el-table-column>
            <el-table-column label="操作" width="400">
                <template #default="scope">
                    <el-button size="mini" @click="details(scope.row.id)">详情</el-button>
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="详情" v-model="vis" width="50%">
            <el-card>
                <div v-html="detail.content" style="min-height: 100px"></div>
            </el-card>
        </el-dialog>

        <div style="margin: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>


        <el-dialog title="个人信息" v-model="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="form.nickName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="form.age" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="form.sex" label="男">男</el-radio>
                    <el-radio v-model="form.sex" label="女">女</el-radio>
                    <el-radio v-model="form.sex" label="未知">未知</el-radio>
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="form.address" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
            </template>
        </el-dialog>

        <el-dialog title="提交详情" v-model="dialogTableVisible">
            <el-table
                :data="personData"
                v-loading="loading"
                stripe
                style="width: 100%"
                height="200"
            >
                <el-table-column
                    width="50"
                    prop="id"
                    label="ID"
                >
                </el-table-column>
                <el-table-column
                    width="280"
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
                    width="200"
                    prop="time"
                    label="提交时间"
                >
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>


import request from "@/utils/request";

export default {
    name: 'Home',
    components: {},
    data() {
        return {
            loading: true,
            form: {},
            dialogVisible: false,
            dialogTableVisible: false,
            bookVis: false,
            search: '',
            currentPage: 1,
            pageSize: 10,
            total: 0,
            tableData: [],
            personData: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        details(row) {
            this.dialogTableVisible = true;
            this.detail = row;
            console.log(row);
            request.get("/user/personOrder", {
                params: {
                    id: row,
                }
            }).then(res => {
                console.log(res.data);
                this.personData = res.data
            })
        },

        handleChange(row) {
            request.put("/user/changeRole", row).then(res => {
                if (res.code === '0') {
                    this.$message.success("更新成功")
                    if (res.data) {
                        this.$router.push("/login")
                    }
                }
            })
        },

        load() {
            this.loading = true
            request.get("/user", {
                params: {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    search: this.search
                }
            }).then(res => {
                this.loading = false
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        handleUploadSuccess(res) {
            if (res.code === "0") {
                this.$message.success("导入成功")
                this.load()
            }
        },
        exportUser() {
            location.href = "http://" + window.server.filesUploadUrl + ":9090/user/export";
        },
        add() {
            this.dialogVisible = true
            this.form = {}
        },
        save() {
            if (this.form.id) {  // 更新
                request.put("/user", this.form).then(res => {
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
                request.post("/user", this.form).then(res => {
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
        },
        handleDelete(id) {
            console.log(id)
            request.delete("/user/" + id).then(res => {
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
        handleSizeChange(pageSize) {   // 改变当前每页的个数触发
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {  // 改变当前页码触发
            this.currentPage = pageNum
            this.load()
        }
    }
}
</script>
