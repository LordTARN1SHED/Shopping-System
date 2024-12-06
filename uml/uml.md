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
:选择需要购买的商品;
:将商品加入购物篮;

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
actor Developer

participant "Version Control System (Git)" as VCS
participant "CI Tool (IntelliJ IDEA)" as CI
participant "Testing Server" as TS
participant "Deployment Server" as DS

Developer -> VCS : Push Code
VCS -> CI : Trigger Build
CI -> CI : Compile and Build
CI -> CI : Run Unit Tests
alt Tests Passed
    CI -> TS : Deploy to Testing Server
    TS -> CI : Run Integration Tests
    alt Integration Tests Passed
        CI -> DS : Deploy to Production
        DS -> CI : Deployment Confirmation
    else Integration Tests Failed
        CI -> Developer : Report Test Failures
    end
else Tests Failed
    CI -> Developer : Report Build/Test Failures
end
Developer -> VCS : Check Results

@enduml


