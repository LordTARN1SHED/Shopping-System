<?php
// 连接数据库
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "weborder";

// 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);

// 检查连接
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 获取传入的参数
$orderid = isset($_GET['orderid']) ? (int)$_GET['orderid'] : 0;
$bookid = isset($_GET['bookid']) ? (int)$_GET['bookid'] : 0;

// 检查是否存在相同的 orderid 和 bookid
$checkSql = "SELECT * FROM review WHERE orderid = ? AND bookid = ?";
$checkStmt = $conn->prepare($checkSql);
$checkStmt->bind_param("ii", $orderid, $bookid);
$checkStmt->execute();
$checkResult = $checkStmt->get_result();

if ($checkResult->num_rows > 0) {
    // 如果评论已经存在，跳转回其他页面
    header("Location: buffer.php?status=exist");
    exit(); // 停止执行后续代码
}

$checkStmt->close();

// 处理表单提交
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $mark = isset($_POST['mark']) ? (int)$_POST['mark'] : 0;
    $content = isset($_POST['content']) ? $_POST['content'] : '';
    $imageUrl = '';

    // 上传图片处理
    if (isset($_FILES['image']) && $_FILES['image']['error'] == 0) {
        // 定义上传目录
        $uploadDir = 'uploads/';
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0777, true);
        }

        $imageName = basename($_FILES['image']['name']);
        $targetFilePath = $uploadDir . $imageName;
        $imageFileType = pathinfo($targetFilePath, PATHINFO_EXTENSION);

        // 检查文件类型
        $allowedTypes = array('jpg', 'png', 'jpeg', 'gif');
        if (in_array($imageFileType, $allowedTypes)) {
            // 移动文件到上传目录
            if (move_uploaded_file($_FILES['image']['tmp_name'], $targetFilePath)) {
                $imageUrl = realpath($targetFilePath); // 获取文件的绝对路径
            } else {
                echo "文件上传失败。";
            }
        } else {
            echo "仅允许上传 JPG, JPEG, PNG 和 GIF 格式的图片。";
        }
    }

     // 获取当前时间
     $currentTime = date("Y-m-d H:i:s");

     // 插入数据到数据库
     $sql = "INSERT INTO review (orderid, bookid, mark, content, reviewurl, time) 
             VALUES (?, ?, ?, ?, ?, ?)";
 
     $stmt = $conn->prepare($sql);
     $stmt->bind_param("iiisss", $orderid, $bookid, $mark, $content, $imageUrl, $currentTime);
 
     if ($stmt->execute()) {
        // 如果评论提交成功，跳转到其他页面，并附带成功信息
        header("Location: buffer.php?status=success");
        exit(); // 停止执行后续代码
    } else {
        // 如果提交失败，显示错误信息
        echo "评论提交失败: " . $conn->error;
    }
    
    $stmt->close();
    
}

$conn->close();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>提交评论</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            color: #555;
        }
        input[type="number"] {
            width: 100px; /* 调整分数输入框的宽度 */
        }
        textarea {
            width: 90%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            height: 150px; /* 固定高度 */
            resize: none; /* 禁止用户调整大小 */
        }
        input[type="file"] {
            margin-bottom: 20px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .back-button {
            background-color: #f44336;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            text-decoration: none;
            display: inline-block;
        }
        .back-button:hover {
            background-color: #e53935;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .stars {
            color: gold;
            font-size: 24px;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>提交您的评论</h2>

    <form action="review.php?orderid=<?php echo $orderid; ?>&bookid=<?php echo $bookid; ?>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="mark">评分 (1-5):</label>
            <input type="number" id="mark" name="mark" min="1" max="5" required>
            <div id="starDisplay" class="stars">★★★★★</div> <!-- 用于显示星星 -->
        </div>

        <div class="form-group">
            <label for="content">评论内容:</label>
            <textarea id="content" name="content" rows="4" cols="50"></textarea>
        </div>

        <div class="form-group">
            <label for="image">上传图片:</label>
            <input type="file" id="image" name="image" accept="image/*">
        </div>

        <input type="submit" value="提交评论">
    </form>

    <a href="buffer.php" class="back-button">返回</a>
</div>

<script>
    // 获取评分输入框和星星显示的div
    const markInput = document.getElementById('mark');
    const starDisplay = document.getElementById('starDisplay');

    // 当输入框的值发生变化时，更新星星的显示
    markInput.addEventListener('input', function() {
        let mark = parseInt(markInput.value);

        // 确保评分在1到5之间
        if (isNaN(mark) || mark < 1) {
            mark = 0; // 如果输入为空或小于1，显示0星
        } else if (mark > 5) {
            mark = 5; // 如果输入大于5，则最大为5
        }

        // 根据评分数量显示对应的星星
        let stars = '';
        for (let i = 0; i < mark; i++) {
            stars += '★'; // 添加金色星星
        }
        for (let i = mark; i < 5; i++) {
            stars += '☆'; // 补全剩余的灰色星星
        }

        // 更新星星显示
        starDisplay.innerHTML = stars;
    });

    // 初始化，确保页面加载时显示正确的星星数量
    markInput.dispatchEvent(new Event('input'));
</script>

</body>
</html>

