# Web Order System (Agile Development)

## Project Overview

The Web Order System is an online music instrument order management system designed to provide users with a convenient platform for browsing, ordering, and managing musical instruments. The main functionalities include:

- Add Item
- View Order History
- Remove Item
- Evaluate Item
- Empty Basket
- Web Order Shopper
- View Comments
- Submit Order
- Log in
- Log out
- Browse Catalog
- Database Server

## Team Roles and Responsibilities

| Role                     | Responsibilities                                             | Team Members         |
| ------------------------ | ------------------------------------------------------------ | -------------------- |
| Project Manager/Agile Coach | Project management, Agile process coordination, progress tracking | Feng Zhexi           |
| Product Owner             | Requirements gathering and definition, prioritization, ensuring business needs are met | Feng Zhexi           |
| Frontend Developer        | UI design and implementation, frontend-backend interaction | Song Yuhao, Li Jiqun, Feng Zhexi |
| Backend Developer         | Server-side logic, API development, database interaction     | Song Yuhao, Li Jiqun, Feng Zhexi |
| Full Stack Developer      | Assist with both frontend and backend development, integration and optimization | Song Yuhao, Zhang Yitian |
| QA Engineer               | Functionality, performance, security, and user experience testing | Ma Jiasi             |

## Technology Stack

- Frontend: Vue.js
- Backend: Node.js
- Database: MySQL
- Server: AWS or Azure
- Version Control: Git (GitHub)
- Project Management: Git (GitHub)
- CI/CD: IntelliJ IDEA Ultimate Edition 2024.1.4

## Agile Iteration Plan

The project is divided into two 2-week iterations over 4 weeks:

### Iteration 1 (Weeks 1-2): Core Functionality Development

**Goals:**
- Build basic system architecture
- Implement user authentication and shopping functionalities

**Key Features:**
- Log in / Log out
- Browse Catalog
- Add Item
- Remove Item
- Empty Basket
- Database basic design and implementation

**Acceptance Criteria:**
- Successful user registration, login, logout
- Catalog browsing, shopping cart management (add, remove, clear items)
- Basic database operations (CRUD)

### Iteration 2 (Weeks 3-4): Advanced Functionality and System Optimization

**Goals:**
- Implement advanced features and optimize user experience

**Key Features:**
- Submit Order
- View Order History
- Evaluate Item
- View Comments
- Web Order Shopper functionality
- System integration and testing
- Deployment and go-live

**Acceptance Criteria:**
- Order submission and order history functionality
- Product review and comment functionality
- Web Order Shopper works as expected
- Successful system deployment with no major defects

## Project Timeline

| Week  | Task                                                      | Responsible          |
| ----- | --------------------------------------------------------- | -------------------- |
| Week 1 | Requirements analysis, user story writing, system architecture design, database design | Full Team            |
| Week 2 | Iteration 1 development and testing (Core functionality)  | Frontend and Backend Developers, Full Stack Developer |
| Week 3 | Iteration 2 development and testing (Advanced functionality) | Frontend and Backend Developers, Full Stack Developer |
| Week 4 | System integration, optimization, full testing, deployment, user acceptance | QA Engineer          |

## Detailed Tasks for Each Iteration

### Iteration 1 Tasks

- **User Authentication (Log in/Log out)**: 
  - Frontend: Design login page
  - Backend: Develop authentication API (Register, Log in, Log out)
  - Full Stack: Integrate frontend and backend for smooth login process
  
- **Browse Catalog**: 
  - Frontend: Implement product catalog page
  - Backend: Develop product management API (Get product list, details)
  
- **Shopping Cart Functionality**: 
  - Frontend: Implement shopping cart page (add/remove items)
  - Backend: Develop shopping cart management API (Add, remove, clear items)

- **Database Design**: 
  - Design tables for users, products, orders, shopping carts
  - Implement basic database operations (CRUD)

### Iteration 2 Tasks

- **Submit Order**:
  - Frontend: Implement order confirmation page
  - Backend: Develop order processing API (Create order, payment interface)

- **View Order History**:
  - Frontend: Implement order history page
  - Backend: Develop order query API (Retrieve orders by user ID)

- **Evaluate Item & View Comments**:
  - Frontend: Implement product review and comment page
  - Backend: Develop review and comment API (Submit review, retrieve comments)

- **Web Order Shopper**: 
  - Optimize shopping flow to enhance user experience

- **System Testing & Optimization**: 
  - Write and execute test cases (Functional and integration tests)
  - Bug fixing and system performance optimization

- **Deployment & Go-Live**: 
  - Configure server, deploy application
  - Final checks before going live

## Risk Management

- **Technical Risk**: Incorrect tech stack choice may lead to development difficulties or delays.  
  *Mitigation*: Evaluate technologies early to ensure they fit project needs.

- **Schedule Risk**: Development tasks may exceed estimated time.  
  *Mitigation*: Daily standups to track progress and adjust priorities as needed.

- **Personnel Risk**: Team members may leave or fail to meet deadlines.  
  *Mitigation*: Cross-train team members to reduce dependency on individual roles.

- **Requirement Change Risk**: Frequent changes in requirements may affect progress.  
  *Mitigation*: Use agile methods to adapt to changes and quickly prioritize new requirements.

## Quality Assurance

- **Continuous Integration (CI)**: Use CI tools like Jenkins or GitHub Actions to automate builds and tests.
- **Code Review**: Regular code reviews to ensure quality and consistency.
- **Automated Testing**: Write automated tests to cover major functionalities and ensure system stability.
- **User Feedback**: Gather feedback through agile iterations and make improvements accordingly.

## Delivery and Acceptance

- **Iteration Delivery**: Deliver functional modules at the end of each iteration to ensure continuous value delivery.
- **Final Acceptance**: Conduct comprehensive user acceptance testing after deployment to ensure the system meets business requirements and user expectations.

## Conclusion

The Web Order System will be delivered within 4 weeks using an agile development approach. By focusing on core functionalities and advanced features, and responding quickly to changing requirements, the system will meet user needs and provide a seamless experience for managing online music instrument orders.
