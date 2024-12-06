<?php
$servername = "localhost:3306";
$username = "laplace";
$password = "111111";
$dbname = "weborder";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("连接失败：" . $conn->connect_error);
} else {
    //echo "<div style=\"position:fixed;top:0px\">连接成功</div>";
}
