# 123
- 12

```plantuml
@startuml
actor User

rectangle "Product Review System" {
    User --> (Rate Purchased Product)
    User --> (Write Review)
    User --> (View Other Users' Reviews)
    User --> (View Average Rating)
    
    (Rate Purchased Product) --> (Write Review) : Optional
    (View Other Users' Reviews) --> (View Average Rating)
}

@enduml
```
```plantuml
@startuml
start

:User submits order;
:Generate unique order number;
:Save order to database;

if (Payment completed?) then (yes)
  :Update order status to 'Paid';
else (no)
  :Update order status to 'Pending';
endif

:Show order confirmation to user;

:User views order history;
stop
@enduml
```

```plantuml
@startuml
actor User
participant "Frontend" as FE
participant "Backend" as BE
participant "Database" as DB

User -> FE: 浏览商品
FE -> BE: GET /api/products
BE -> DB: 查询商品列表
DB --> BE: 返回商品列表
BE --> FE: 返回商品列表

User -> FE: 查看商品详情
FE -> BE: GET /api/products/{id}
BE -> DB: 查询商品详情
DB --> BE: 返回商品详情
BE --> FE: 返回商品详情

User -> FE: 添加商品到购物篮
FE -> BE: POST /api/cart
BE -> DB: 添加商品到购物篮记录
DB --> BE: 更新购物篮
BE --> FE: 返回购物篮更新状态

User -> FE: 提交订单
FE -> BE: POST /api/orders
BE -> DB: 生成订单记录
DB --> BE: 保存订单记录
BE --> FE: 返回订单确认

@enduml
```

```plantuml
@startuml
start

:用户登录系统;
:浏览商品;
:选择需要购买的书籍;
:将书籍加入购物篮;

:进入购物篮页面;
:查看已添加的商品;

if (确认商品无误?) then (是)
  :提交订单;
  :系统生成订单;
  :提示用户付款信息;

  if (用户付款成功?) then (是)
    :订单状态更新为已支付;
  else (否)
    :订单状态更新为待支付;
  endif
else (否)
  :修改购物篮;
  :查看已添加的商品;
endif

stop
@enduml

```

```plantuml
@startuml
start

:管理员登录后台系统;
:查看商品列表;

if (选择操作?) then (新增)
  :新增商品;
  :系统保存商品信息;
else if (修改)
  :选择商品进行修改;
  :系统保存修改后的商品信息;
else if (删除)
  :选择商品进行删除;
  :系统删除商品信息;
endif

:系统记录操作;
:反馈操作结果给管理员;

stop
@enduml
```

```plantuml
@startuml
actor User

rectangle "User Management System" {
    User --> (Log in) : 用户名和密码
    User --> (Log out) : 退出系统
    User --> (Register) : 创建新账户
    (Log in) --> (Access Shopping Cart) : 成功登录后
    (Log in) --> (View Order History) : 成功登录后
    (Log in) --> (Review Products) : 成功登录后
}

@enduml
```

```plantuml
@startuml
actor User

rectangle "Product Management System" {
    User --> (Browse Catalog) : 查看所有书籍
    User --> (View Product Details) : 点击书籍查看详细信息
    (Browse Catalog) --> (View Product Details) : 查看商品详细信息
}

@enduml

```

```plantuml
@startuml
actor User

rectangle "Shopping Cart System" {
    User --> (Add Item) : 添加商品到购物篮
    User --> (Remove Item) : 从购物篮移除商品
    User --> (Empty Basket) : 清空购物篮
    User --> (View Basket) : 查看购物篮

    (Add Item) --> (Check Stock) : 验证库存
}

@enduml
```

```plantuml
@startuml
actor User

rectangle "Order Management System" {
    User --> (Submit Order) : 提交订单
    (Submit Order) --> (Generate Order Number) : 生成订单号
    (Submit Order) --> (Payment Status) : 提示用户付款状态

    User --> (View Order History) : 查看订单历史
    (View Order History) --> (View Order Details) : 查看订单详情
    (View Order History) --> (Check Order Status) : 检查订单状态 (已支付, 待支付)
}

@enduml


```

```plantuml

@startuml
actor User

rectangle "Product Review System" {
    User --> (Evaluate Item) : 评价商品
    User --> (View Comments) : 查看评论
}

@enduml

```

```plantuml
@startuml
start

:用户登录或注册;
:浏览商品;
:选择需要的书籍;
:将书籍加入购物篮;

:进入购物篮页面;
:查看已选择的商品;

if (确认商品无误?) then (是)
  :提交订单;
  :确认支付;
  :生成订单;
  :系统提示支付成功;
else (否)
  :修改购物篮内容;
  :重新查看已选择的商品;
endif

:查看订单历史;
:评价已购买商品;

stop
@enduml

```

```plantuml

@startuml
start

:管理员登录后台系统;

:查看所有商品;
if (选择操作?) then (新增商品)
  :添加新商品;
  :系统保存商品;
else if (修改商品)
  :选择商品进行修改;
  :系统保存修改后的商品信息;
else if (删除商品)
  :选择商品进行删除;
  :系统删除商品信息;
else (查看商品)
  :查看商品详情;
endif

:查看所有订单;
:查看用户评价;

:管理用户账户;
if (选择操作?) then (新增用户)
  :添加新用户;
  :系统保存用户信息;
else if (修改用户信息)
  :修改用户信息;
  :系统保存修改后的用户信息;
else if (删除用户)
  :删除用户;
  :系统删除用户信息;
else (查看用户)
  :查看用户详情;
endif

stop
@enduml


```

```plantuml
@startuml
' 使用 component 表示前端、后端和 API 层
component "Frontend (Vue.js)" as FE
component "Backend (Spring Boot)" as BE
component "API Layer" as API

FE --> API : Sends HTTP Requests
API --> BE : Forwards request to backend
BE --> API : Returns response to API layer
API --> FE : Sends response to frontend

@enduml


```

```plantuml
@startuml
node "Frontend (Vue.js)" {
  [Browser]
}

node "Backend (Spring Boot)" {
  [API Layer]
  [User Service]
  [Order Service]
  [Product Service]
}

node "Database" {
  [User Table]
  [Order Table]
  [Product Table]
}

Browser --> "API Layer" : HTTP Request
"API Layer" --> "User Service" : Manage Users
"API Layer" --> "Order Service" : Manage Orders
"API Layer" --> "Product Service" : Manage Products
"User Service" --> "User Table" : Query User Table
"Order Service" --> "Order Table" : Query Order Table
"Product Service" --> "Product Table" : Query Product Table

@enduml
