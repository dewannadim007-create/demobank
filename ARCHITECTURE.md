# Spring Boot Banking Application - Architecture Guide

## 📁 Project Structure Overview

```
src/main/java/com/example/app/
├── Application.java                 # Main Spring Boot entry point
├── controller/                      # REST Controllers (31 files)
├── model/                          # Entity/Domain classes (3 files)
├── services/                       # Business logic layer (3 files)
└── singleton/                      # Singleton patterns (1 file)
```

---

## 🗂️ Models (Entities)

### 1. **User.java**
Represents bank customers/users.

**Key Fields:**
- `username`, `password` - Authentication
- `fullName`, `email`, `phoneNumber` - Personal info
- `accountNumber`, `balance`, `accountType` - Banking details
- `userRole` - USER or ADMIN
- `isActive` - Account status

**MongoDB Collection:** `users`

**Validation:**
- Unique username and email
- Password minimum 6 characters
- Email format validation

---

### 2. **Transaction.java**
Represents all banking transactions.

**Key Fields:**
- `transactionId` - Unique transaction identifier
- `fromAccount`, `toAccount` - Source and destination
- `amount` - Transaction amount
- `transactionType` - DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT, RECHARGE, UTILITY
- `status` - PENDING, COMPLETED, FAILED
- `transactionDate` - Timestamp
- `balanceAfter` - Balance after transaction

**MongoDB Collection:** `transactions`

---

### 3. **Branch.java**
Represents bank branches.

**Key Fields:**
- `branchCode` - Unique branch identifier
- `branchName`, `address`, `city`, `state` - Location details
- `phoneNumber`, `email`, `managerName` - Contact info
- `ifscCode`, `swiftCode` - Banking codes
- `latitude`, `longitude` - GPS coordinates
- `openingHours`, `closingHours` - Operational hours

**MongoDB Collection:** `branches`

---

## 🔧 Services (Business Logic)

### 1. **UserService.java**

**Key Methods:**
```java
// CRUD Operations
createUser(User user)
getUserById(String id)
getUserByUsername(String username)
getUserByEmail(String email)
getUserByAccountNumber(String accountNumber)
getAllUsers()
getActiveUsers()
updateUser(User user)
deleteUser(String id) // Soft delete

// Banking Operations
updateBalance(String accountNumber, Double newBalance)

// Authentication
authenticateUser(String username, String password)
changePassword(String userId, String oldPassword, String newPassword)

// Validation
usernameExists(String username)
emailExists(String email)
```

**Usage Example:**
```java
@Autowired
private UserService userService;

// Create a new user
User user = new User("john_doe", "password123", "John Doe", "john@email.com", "1234567890");
user.setAccountNumber("ACC001");
user.setBalance(1000.0);
userService.createUser(user);

// Authenticate user
Optional<User> authenticatedUser = userService.authenticateUser("john_doe", "password123");
```

---

### 2. **TransactionService.java**

**Key Methods:**
```java
// CRUD Operations
createTransaction(Transaction transaction)
getTransactionById(String id)
getTransactionByTransactionId(String transactionId)
getTransactionsByAccount(String accountNumber)
getAllTransactions()
getTransactionsByType(String transactionType)
getTransactionsByStatus(String status)
getTransactionsByDateRange(String accountNumber, LocalDateTime start, LocalDateTime end)
updateTransactionStatus(String transactionId, String status)

// Banking Operations
processTransfer(String fromAccount, String toAccount, Double amount, String description)
processDeposit(String accountNumber, Double amount, String description)
processWithdrawal(String accountNumber, Double amount, String description)

// Utility
getRecentTransactions(String accountNumber, int limit)
```

**Usage Example:**
```java
@Autowired
private TransactionService transactionService;

// Transfer money
Transaction transfer = transactionService.processTransfer(
    "ACC001", // From account
    "ACC002", // To account
    500.0,    // Amount
    "Payment for services"
);

// Deposit money
Transaction deposit = transactionService.processDeposit(
    "ACC001",
    1000.0,
    "Salary deposit"
);

// Get transaction history
List<Transaction> transactions = transactionService.getTransactionsByAccount("ACC001");
```

---

### 3. **BranchService.java**

**Key Methods:**
```java
// CRUD Operations
createBranch(Branch branch)
getBranchById(String id)
getBranchByCode(String branchCode)
getAllBranches()
getActiveBranches()
getBranchesByCity(String city)
getBranchesByState(String state)
updateBranch(Branch branch)
deleteBranch(String id) // Soft delete

// Search Operations
searchBranchesByName(String searchTerm)
getBranchByIfscCode(String ifscCode)
getNearbyBranches(Double latitude, Double longitude, Double radiusInKm)

// Validation
branchCodeExists(String branchCode)
```

**Usage Example:**
```java
@Autowired
private BranchService branchService;

// Create a new branch
Branch branch = new Branch("BR001", "Main Branch", "123 Main St", "New York");
branch.setIfscCode("BANK0001234");
branch.setLatitude(40.7128);
branch.setLongitude(-74.0060);
branchService.createBranch(branch);

// Find nearby branches
List<Branch> nearbyBranches = branchService.getNearbyBranches(40.7128, -74.0060, 5.0);
```

---

## 🔒 Singleton

### **ConnectionSingleton.java**

Manages MongoDB connection using Singleton pattern.

**Key Methods:**
```java
getInstance() // Get singleton instance
getMongoClient() // Get MongoDB client
getDatabase() // Get database instance
isConnected() // Check connection status
getConnectionStatus() // Get status string
reconnect() // Reconnect to database
disconnect() // Close connection
getConnectionInfo() // Get connection details
```

**Usage Example:**
```java
@Autowired
private ConnectionSingleton connectionSingleton;

// Check connection status
if (connectionSingleton.isConnected()) {
    System.out.println("Database is connected");
}

// Get connection info
String info = connectionSingleton.getConnectionInfo();
```

---

## 🔄 How Controllers Use Services

**Example: LoginController using UserService**

```java
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.authenticateUser(
            request.getUsername(), 
            request.getPassword()
        );
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
```

**Example: SendMoneyController using TransactionService**

```java
@RestController
@RequestMapping("/api/money/send")
public class SendMoneyController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> sendMoney(@RequestBody TransferRequest request) {
        try {
            Transaction transaction = transactionService.processTransfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                request.getDescription()
            );
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
```

---

## 📊 Data Flow

```
User Request → Controller → Service → MongoDB → Service → Controller → Response
```

**Example: Money Transfer Flow**

1. User makes POST request to `/api/money/send`
2. `SendMoneyController` receives the request
3. Controller calls `TransactionService.processTransfer()`
4. Service validates accounts using `UserService`
5. Service checks balance
6. Service updates both user balances
7. Service creates transaction record
8. Transaction saved to MongoDB
9. Service returns transaction object
10. Controller sends response to user

---

## 🔑 Key Features

### **User Management**
- ✅ Registration and authentication
- ✅ Profile management
- ✅ Role-based access (USER, ADMIN)
- ✅ Password management
- ✅ Account activation/deactivation

### **Transaction Management**
- ✅ Money transfers between accounts
- ✅ Deposits and withdrawals
- ✅ Transaction history
- ✅ Date range filtering
- ✅ Transaction status tracking
- ✅ Automatic balance updates

### **Branch Management**
- ✅ Branch CRUD operations
- ✅ Location-based search
- ✅ IFSC code lookup
- ✅ City/State filtering
- ✅ Branch finder with GPS

---

## 🚀 Next Steps

1. **Add DTOs** - Create Data Transfer Objects for cleaner API contracts
2. **Add Repositories** - Create Spring Data MongoDB repositories
3. **Add Security** - Implement JWT authentication
4. **Add Exception Handling** - Create custom exceptions and global exception handler
5. **Add Validation** - Enhance input validation
6. **Add Logging** - Implement proper logging
7. **Add Tests** - Write unit and integration tests

---

## 📝 Notes

- All models use MongoDB `@Document` annotation
- Services use Spring `@Service` annotation for dependency injection
- Timestamps are automatically managed
- Soft deletes preserve data integrity
- All IDs are auto-generated by MongoDB
- Transaction IDs are generated using timestamp + random number

---

## 💡 Best Practices

1. **Always use Services in Controllers** - Never access MongoDB directly from controllers
2. **Handle Exceptions** - Wrap service calls in try-catch blocks
3. **Validate Input** - Use `@Valid` annotation with request bodies
4. **Use Optional** - Services return `Optional<T>` for single object queries
5. **Transaction Safety** - Consider using `@Transactional` for complex operations
6. **Security** - Never expose passwords in responses
7. **Logging** - Log important operations and errors

---

This architecture provides a solid foundation for your Spring Boot banking application!
