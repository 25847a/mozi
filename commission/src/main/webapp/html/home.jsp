<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>首页</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${site }css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${site }css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="${site }css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${site }css/home.css" rel="stylesheet">
    <script>
    	var site="${site}";
    </script>
</head>
  <div class="gray-bg">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row row-number">
                <div class="col-sm-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <p>今日预约人数</p>
                            <p class="number">25000</p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <p>今日持卡人数</p>
                            <p class="number">24800</p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <p>今日采浆人数</p>
                            <p class="number">24600</p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <p>总采浆量（t）</p>
                            <h2>52.68</h2>
                            <h5>完成率60%</h5>
                            <div class="progress progress-mini">
                                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <!--各浆站采浆情况-->
                <div class="col-md-6">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div id="statics" style="width:100%;height:600px;"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div id="pine_chart" style="width:100%;height:350px;"></div>
                            <div id="region" style="width:100%;height:250px;"></div>
                        </div>
                    </div>
                </div>
                <!--检疫期报废率，不同权限用户界面，默认隐藏-->
                <div class="col-md-3" style="display: none">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="rate">检疫期报废率(近6个月)</div>
                            <div class="rate">平均报废率：3%，增长50%</div>
                            <div id="scrap_rate" style="width:100%;height:520px;"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="ibox">
                        <div class="ibox-content" style="padding: 0;background: #F3F3F4">
                            <div class="line_chart_box" style="padding: 15px 20px 20px">
                                <p>各桨站采浆情况</p>
                                <div id="line_chart" style="width:100%;height:322px;"></div>
                            </div>
                            <div class="blood_amount">
                                <div>
                                    <p>近半年采浆量</p>
                                    <h3>86.55</h3>
                                </div>
                                <div>
                                    <p>同期采浆量</p>
                                    <h3>86.55</h3>
                                </div>
                                <div>同比增长：60%</div>
                            </div>
                            <div id="weather"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="tab_box">
                                <a href="javascript:;" class="on">检疫期报废率</a>
                                <a href="javascript:;">老浆员复采率</a>
                            </div>
                            <div id="line_container1" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </div>
                <!--新老浆员发展情况，不同权限用户界面，默认隐藏-->
                <div class="col-md-6" style="display: none">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="tab_box">
                                <a href="javascript:;" class="on">新浆员发展情况</a>
                                <a href="javascript:;">老浆员发展情况</a>
                            </div>
                            <div id="development_situation" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="title">新浆员发展情况</div>
                            <div id="line_container2" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${site }js/jquery.min.js"></script>
    <script src="${site }js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${site }js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${site }js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="${site }js/plugins/echarts/echarts.min.js"></script>
    <script src="${site }js/content.min.js?v=1.0.0"></script>
    <script src="${site }js/home/home.js"></script>
</body>
</html>