REGISTER FLOW :
 POST /api/auth/register

    Request Body:
         {
         "username": "string",
         "email": "string",
         "password": "string"
         }

    AuthController.register()
       ↓
    AuthService.register()
       ↓
    UserRepository.save(User)
       ↓
    Hibernate
       ↓
    users + user_roles tables

What happens :
Validates request
Encodes password
Assigns ROLE_USER
Saves user + role mapping


LOGIN FLOW :
 POST /api/auth/login

    Request Body:
    {
    "username": "string",
    "password": "string"
    }

    AuthController.login()
       ↓
    AuthenticationManager.authenticate()
       ↓
    DaoAuthenticationProvider
       ↓
    CustomUserDetailsService.loadUserByUsername()
       ↓
    UserRepository.findByUsername()
       ↓
    PasswordEncoder matches password
       ↓
    Authentication SUCCESS
       ↓
    JwtUtil.generateToken()
       ↓
    Response (JWT)

What happens:
Username + password validated
Spring Security checks credentials
JWT token generated and returned



ACCESS PROTECTED API:

GET /api/tasks
Authorization: Bearer <JWT>

    JwtAuthFilter
       ↓
    JwtUtil.validateToken()
       ↓
    CustomUserDetailsService.loadUserByUsername()
       ↓
    SecurityContext set
       ↓
    Controller allowed


What happens:
Token validated
User-authenticated
Roles checked
Request allowed


Admin manages a system for users and content.
Users can register, login, and access protected APIs using JWT authentication.
Roles (USER, ADMIN) determine access levels.
Entities:
User
 - id
 - username
 - email
 - password
 - roles (ManyToMany with Role)
Role
 - id
 - name (ROLE_USER, ROLE_ADMIN)
Task
    - id
    - title
    - description
    - assignedUser (ManyToOne with User)
APIs:
POST /api/auth/register - Register new user
POST /api/auth/login - Login and get JWT
GET /api/tasks - Get tasks (USER and ADMIN)
POST /api/tasks - Create task (ADMIN only)
PUT /api/tasks/{id} - Update task (ADMIN only)
DELETE /api/tasks/{id} - Delete task (ADMIN only)
Technologies:
Spring Boot
Spring Security
JWT (JSON Web Tokens)
Hibernate/JPA
MySQL or H2 Database
Security Config:
 - PasswordEncoder bean
 - AuthenticationManager bean
 - JWT filter in security chain
 - Role-based access control
    - CORS configuration
    - Exception handling for auth errors

This system ensures secure user management and content access based on roles using JWT authentication.
ER Diagram:
    Users ---< User_Roles >--- Roles
        |
        |
    Tasks
    |
    Assigned to Users
Relationships:
 - User to Role: Many-to-Many
 - User to Task: One-to-Many (a user can have multiple tasks)
    - Task to User: Many-to-One (a task is assigned to one user)
