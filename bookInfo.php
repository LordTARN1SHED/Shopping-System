<?php
$bookid = $_GET['bookid'];
?>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>homepage</title>
    <link rel="stylesheet" href="common control.css">
    <style>
        .nav {
            float: left;
            width: 100%
        }

        .header {
            background-color: rgb(176, 179, 183);
        }

        .navbox {
            display: block;
            float: left;
            width: 25%;
            height: 50px;
            text-decoration: none;
            font-size: 30px;
            font-weight: 700;
            color: #fac03d;
            line-height: 50px;
            background-color: rgb(84, 92, 100);
            /* background-color: #2c2f3b; */
            transition: 0.5s;
        }

        .navbox:hover {
            /* background-color: rgba(255, 234, 0, 0.779); */
            background-color: #fac03d;
            color: rgb(84, 92, 100);
        }
    </style>
</head>

<body>
    <div class="header">
        <!-- <button style="position:absolute;margin-top:20px;" onclick="window.location.href='quit.php'">退出登录</button> -->
        <h1 align="center" style="line-height: 70px;">
            <?php
            include_once("connectDB.php");
            $sql = "select * from books where id = $bookid";
            $result = mysqli_query($conn, $sql);
            $row = mysqli_fetch_array($result);
            echo $row['name'];
            ?></h1>
        <p align="center" style="line-height: 0px;color: crimson; font-weight: 700;">评论详情</p>
    </div>
    <div class="headerNav">
        <div class="wrap">
            <div style="height: 50px;">
                <div class="nav" height="50px">
                    <a href="http://localhost:8080/Home" class="navbox" align="center">首页</a>
                    <a href="http://localhost:8080/Mall" class="navbox" align="center">商城</a>
                    <a href="#" class="navbox" align="center">购物车</a>
                    <a href="#" class="navbox" align="center">订单</a>
                </div>
            </div>
        </div>
    </div>
    <div class="content">
        <div class="wrap" style="border-radius: 10px;">
            <div style="background-color: #f5f5f5;height:10px;width:100%;"></div>
            <div class="Grid">
                <?php
                include_once("connectDB.php");
                $sql = "SELECT table1.*, 
                orders.time AS order_time,  -- 将 orders 表的 time 列重命名为 order_time
                table1.time AS review_time, -- 将 review 表的 time 列重命名为 review_time
                xuser.*
                FROM (
                    SELECT * FROM review WHERE bookid = '$bookid'
                ) AS table1
                JOIN orders ON orders.orderid = table1.orderid
                JOIN xuser ON xuser.id = orders.userid";
                $result = mysqli_query($conn, $sql);
                while ($row = mysqli_fetch_array($result)) {
                    echo "<div><b style=\"font-size: 20px;\">" . $row['name'] . "</b> " . $row['time'] . "</div>";
                    echo "<div>";
                    $temp = 0;
                    while ($temp != 5) {
                        $temp++;
                        echo "<span style=\"height: 30px;width: 30px;position: relative;float: left;\">
                        <svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 1000 1000\" style=\"\">
                            <path fill=";
                        if ($temp <= $row['mark']) {
                            echo "\"gold\"";
                        } else {
                            echo "\"white\"";
                        }
                        echo "d=\"M283.84 867.84 512 747.776l228.16 119.936a6.4 6.4 0 0 0 9.28-6.72l-43.52-254.08 184.512-179.904a6.4 6.4 0 0 0-3.52-10.88l-255.104-37.12L517.76 147.904a6.4 6.4 0 0 0-11.52 0L392.192 379.072l-255.104 37.12a6.4 6.4 0 0 0-3.52 10.88L318.08 606.976l-43.584 254.08a6.4 6.4 0 0 0 9.28 6.72z\">
                            </path>
                        </svg>
                    </span>";
                    }
                    echo "</div>";
                    if($row['reviewurl'] !=null) echo "<div><img style=\"width:100px;height:100px;\" src=\"" . $row['reviewurl'] . "\"></img></div>";
                    echo "<div><span style=\"font-size:30px;\">" . $row['content'] . "</span></div>";
                    echo "<div style=\"background-color: #f5f5f5;height:10px;\"></div>";
                }
                echo "<div style=\"text-align:center;height:50px;font-size:20px;lineheight:50px;color:red;\">没有更多了</div>";
                $conn->close();
                ?>
            </div>
        </div>
    </div>

    <script src="js/jquery.js"></script>
    <script src="js/control.js"></script>
</body>

</html>