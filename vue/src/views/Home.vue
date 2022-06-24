<template>
    <div style="padding: 10px">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div id="myChart" :style="{width: '600px', height: '500px'}"></div>
                </el-card>
            </el-col>

            <el-col :span="11">
                <el-card>
                    <div :style="{width: '600px', height: '500px'}">
                        <el-table
                            :data="tableData"
                            stripe
                            style="width: 100%">
                            <el-table-column
                                prop="hard"
                                label="难度"
                                width="100">
                            </el-table-column>
                            <el-table-column
                                prop="refer"
                                label="参考"
                                width="100">
                            </el-table-column>
                            <el-table-column
                                prop="master"
                                width="100"
                                label="掌握程度">
                            </el-table-column>
                            <el-table-column
                                prop="explain"
                                label="说明"
                            >
                            </el-table-column>
                        </el-table>

                        &nbsp;


                        <div slot="header" class="clearfix">
                            <span>说明：</span>
                        </div>
                        &nbsp;
                        <div class="text item" style="width: 550px">
                            大类知识点和细分知识点严格按照知识点归类，如果有多个按空格分割
                            难度如果无法把控可以在群里讨论（没有大的出入就可以）
                            题目链接不要附带个人信息，只需要未登录的单纯的url
                            区域赛铜奖难度指的是大概区域赛第三/四题的难度（假设四题拿铜），否则难度下调至 0-2。
                        </div>


                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<style>
.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: 480px;
}
</style>

<script>
import request from "@/utils/request";

export default {
    name: "Home",
    data() {
        return {
            tableData: [{
                hard: '0',
                refer: '入门',
                master: '基础',
                explain: '每个人必须熟练应用'
            }, {
                hard: '1',
                refer: '普及',
                master: '分类基础',
                explain: '学习该分类的熟练应用'
            }, {
                hard: '2',
                refer: '提高',
                master: '分类中级',
                explain: '学习该分类的掌握'
            }, {
                hard: '3',
                refer: '区域赛铜',
                master: '分类高级',
                explain: '学习该分类的知道'
            }, {
                hard: '4',
                refer: '区域赛银',
                master: '',
                explain: ''

            }]
        }
    },
    mounted() {
        this.drawLine();
    }
    ,
    methods: {
        drawLine() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$root.echarts.init(document.getElementById('myChart'))
            let option = {
                title: {
                    text: '各班级用户比例统计图',
                    subtext: '',
                    left: 'left'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    trigger: 'item',
                    left: 'center'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                series: [
                    {
                        name: '用户比例',
                        type: 'pie',
                        radius: [50, 150],
                        center: ['50%', '60%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: []
                    }
                ]
            }
            request.get("/user/count").then(res => {
                if (res.code === '0') {
                    res.data.forEach(item => {
                        option.series[0].data.push({ name: item.address, value: item.count })
                    })
                    // 绘制图表
                    myChart.setOption(option);
                }
            })

        }
    }
}
</script>

<style scoped>

</style>
