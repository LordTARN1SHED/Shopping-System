<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Other Page</title>
    <script>
    // 获取 URL 中的查询参数
    function getQueryParam(param) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    // 检查 status 参数并显示弹窗，之后立即跳转
    window.onload = function() {
        const status = getQueryParam('status');
        const redirectUrl = "specified_page.php"; // 跳转的页面

        if (status === 'success') {
            alert('评论提交成功！');
            window.location.href = redirectUrl; // 立即跳转
        } else if (status === 'failed') {
            alert('评论提交失败，请重试。');
            window.location.href = redirectUrl; // 立即跳转
        } else if (status === 'exist') {
            alert('已经评论过了，无法重复评论。');
            window.location.href = redirectUrl; // 立即跳转
        } else {
            // 如果没有 status 参数，直接跳转到指定页面
            window.location.href = redirectUrl;
        }
    }
</script>

</head>
<body>

</body>
</html>
