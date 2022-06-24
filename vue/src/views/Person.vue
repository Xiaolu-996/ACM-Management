<template>
    <div style="padding: 10px">
        <el-row :gutter="20">
            <el-col :span="9">
                <el-card style="height: 600px;width: 470px">
                    <div style=" font-weight: bold; color: dodgerblue">个人信息</div>
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item style="text-align: center" label-width="0">
                            <el-upload
                                class="avatar-uploader"
                                action="http://localhost:9090/files/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                            >
                                <img v-if="form.avatar" :src="form.avatar" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="用户名">
                            <el-input v-model="form.username" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="昵称">
                            <el-input v-model="form.nickName"></el-input>
                        </el-form-item>
                        <el-form-item label="年龄">
                            <el-input v-model="form.age"></el-input>
                        </el-form-item>
                        <el-form-item label="性别">
                            <el-input v-model="form.sex"></el-input>
                        </el-form-item>
                        <el-form-item label="班级">
                            <el-input v-model="form.address"></el-input>
                        </el-form-item>
                        <!--        <el-form-item label="密码">-->
                        <!--          <el-input v-model="form.password" show-password></el-input>-->
                        <!--        </el-form-item>-->
                    </el-form>
                    <div style="text-align: center">
                        <el-button type="primary" @click="update">保存</el-button>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="10" v-if="name!=='admin'">

                <el-card style="height: 600px;width: 800px">

                    <div>
                        <div style=" font-weight: bold; color: dodgerblue">已提交题目</div>
                        <el-table
                            :data="personData"
                            height="510"
                            stripe
                            style="width: 100%"
                        >
                            <el-table-column
                                width="50"
                                prop="id"
                                label="ID"
                            >
                            </el-table-column>
                            <el-table-column
                                width="300"
                                prop="name"
                                label="题目"
                            >
                            </el-table-column>
                            <el-table-column
                                width="100"
                                prop="broadKnowledge"
                                label="大类知识点">
                            </el-table-column>
                            <el-table-column
                                prop="subdivisionKnowledge"
                                label="细分知识点">
                            </el-table-column>
                            <el-table-column
                                prop="time"
                                label="提交时间"
                            >
                            </el-table-column>
                        </el-table>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>

</template>

<script>
import request from "@/utils/request";

export default {
    name: "Person",
    data() {
        return {
            name: localStorage.getItem('username'),
            uid: localStorage.getItem('uid'),
            form: {},
            personData: [],
        }
    },
    created() {
        let str = sessionStorage.getItem("user") || "{}"
        this.form = JSON.parse(str)
        let uid = this.uid;
        console.log("uid: " + localStorage.getItem('uid'));
        console.log("name: " + localStorage.getItem('username'));
        this.details(uid)
    },
    methods: {

        details(uid) {
            request.get("/user/personOrder", {
                params: {
                    id: uid,
                }
            }).then(res => {
                console.log(res.data);
                this.personData = res.data
            })
        },

        handleAvatarSuccess(res) {
            this.form.avatar = res.data
            this.$message.success("上传成功")
            // this.update()
        },
        update() {
            request.put("/user", this.form).then(res => {
                console.log(res)
                if (res.code === '0') {
                    this.$message({
                                      type: "success",
                                      message: "更新成功"
                                  })
                    sessionStorage.setItem("user", JSON.stringify(this.form))
                    // 触发Layout更新用户信息
                    this.$emit("userInfo")
                } else {
                    this.$message({
                                      type: "error",
                                      message: res.msg
                                  })
                }
            })
        }
    }
}
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
