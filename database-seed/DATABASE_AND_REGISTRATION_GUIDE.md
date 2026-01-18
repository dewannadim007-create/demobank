# 📊 Complete Database & Registration Guide

## 🗄️ All Collections (Tables) Needed

### ✅ **REQUIRED Collections:**

| # | Collection Name | Purpose | Must Pre-populate? |
|---|----------------|---------|-------------------|
| 1 | **`users`** | All user accounts (customers + admin) | ✅ **YES** - Need existing bank accounts |
| 2 | **`transactions`** | All banking transactions | ❌ No - Auto-created |
| 3 | **`branches`** | Branch/ATM locations | ✅ **YES** - For branch locator |

### ⚠️ **OPTIONAL Collection:**

| # | Collection Name | Purpose | Must Pre-populate? |
|---|----------------|---------|-------------------|
| 4 | **`utility`** | Utility bill accounts (gas/electricity/recharge) | ⚠️ Optional - For utility payments |

---

## 🔐 Registration Logic - CRITICAL UNDERSTANDING

### **YES! Users MUST Have Existing Bank Account Before Registration!**

### 📋 **Registration Flow:**

```
Step 1: User fills registration form
  ├─ Name
  ├─ NID/Passport
  ├─ Date of Birth
  ├─ Bank Account Number (CRITICAL!)
  ├─ Mobile Number
  ├─ Email
  └─ Password (exactly 8 characters)

Step 2: System validates all fields
  ├─ Name: Not empty
  ├─ NID: Not empty
  ├─ DOB: Must be in the past
  ├─ Account: Not empty
  ├─ Mobile: Not empty
  ├─ Email: Must be @gmail.com/@yahoo.com/@outlook.com/@hotmail.com
  └─ Password: EXACTLY 8 characters (not 7, not 9!)

Step 3: Check if bank account exists in database ⚠️ CRITICAL
  ├─ Query: users.account = entered account number
  ├─ If account NOT FOUND → Error: "You Have No Account In The Bank"
  └─ If account FOUND → Continue to Step 4

Step 4: Check if mobile/account already registered
  ├─ Query: users.mobile = entered mobile
  ├─ If mobile exists with SAME account → Error: "Account Already Exist"
  └─ If new user → Continue to Step 5

Step 5: Create online banking access
  ├─ Save user to database
  ├─ Create wallet with initial balance = 0
  └─ Redirect to login
```

---

## 🎯 **Why You Need Pre-populated Bank Accounts**

### **The Two-Step Process:**

1. **Physical Bank Account** (Already in database)
   - Customer visits physical bank branch
   - Opens a traditional bank account
   - Gets account number (e.g., "ACC001")
   - **This account is already in the `users` collection**

2. **Online Banking Registration** (What your app does)
   - Customer uses your web app
   - Enters their existing bank account number
   - SystemVerifies: **Does this account exist?**
   - If YES → Creates online/mobile wallet access
   - If NO → Shows error: "You Have No Account In The Bank"

---

## 📦 **What You Need in MongoDB Before Users Can Register:**

### **Sample Pre-existing Bank Account in `users` collection:**

```json
{
  "name": "John Doe",
  "account": "ACC001",  // ← This MUST exist
  "nid": "1234567890123",
  "DOB": "1995-05-15",
  "email": "john@gmail.com",
  "mobile": "",  // ← Empty, will be filled during registration
  "password": "",  // ← Empty, will be filled during registration
  "balance": 50000,  // ← Pre-existing bank balance
  "userRole": "USER"
}
```

### **After Online Registration:**

Same document becomes:
```json
{
  "name": "John Doe",
  "account": "ACC001",
  "nid": "1234567890123",
  "DOB": "1995-05-15",
  "email": "john@gmail.com",
  "mobile": "01700000002",  // ← NOW FILLED
  "password": "john1234",   // ← NOW FILLED (8 chars)
  "balance": 50000,
  "userRole": "USER",
  "walletBalance": 0  // ← NEW: Online wallet created
}
```

---

## ⚙️ **How Registration Actually Works:**

### **Code Flow (from RegistrationController.java):**

```java
// Line 92: Check if account exists
boolean haveAccount = userService.checkAccount(account);

if (haveAccount) {  // ← Account MUST exist in database
    // Line 95: Check if already registered
    boolean exist = UserService.existingAccount(mobile, account, mongoTemplate);
    
    if (!exist) {  // ← Not already registered
        // Line 98: Register user
        boolean isRegistered = userService.registration(user);
        
        if (isRegistered) {
            // Line 101: Create online wallet
            UserService.createOnlineBankingAccount(account, mobile, 0, mongoTemplate);
            return "redirect:/login";
        }
    } else {
        error = "Account Already Exist";
    }
} else {
    // LINE 114-115: CRITICAL ERROR MESSAGE
    error = "You Have No Account In The Bank";
}
```

---

## 🚨 **Common Registration Errors & Fixes:**

| Error Message | Cause | Solution |
|--------------|-------|----------|
| **"You Have No Account In The Bank"** | Account number not in database | Add account to `users` collection first |
| **"Account Already Exist"** | Mobile already registered with this account | Use different mobile or account |
| **"Please fill all fields correctly"** | Validation failed | Check: Email domain, Password = 8 chars, DOB in past |
| Password validation fails | Password not exactly 8 chars | Make it EXACTLY 8 (not 7, not 9!) |

---

## ✅ **Testing Registration - Step by Step:**

### **1. Pre-populate Database with Bank Account:**

```json
// Insert into users collection
{
  "name": "Test User",
  "account": "TEST001",
  "nid": "9876543210123",
  "DOB": "1990-01-01",
  "balance": 10000,
  "userRole": "USER",
  "email": "",
  "mobile": "",
  "password": ""
}
```

### **2. Register via Web:**

- Go to: http://localhost:5896/register
- Fill form:
  - Name: Test User
  - NID: 9876543210123
  - DOB: 1990-01-01
  - Account: **TEST001** ← This MUST match database
  - Mobile: 01700000099
  - Email: test@gmail.com
  - Password: test1234 (exactly 8)

### **3. Success Response:**

```
"Registration Done. Proceeding To Login"
```

### **4. You can now login with:**

- Mobile: 01700000099
- Password: test1234

---

## 🎯 **Summary:**

1. ✅ **4 Collections**: users (required), transactions, branches (required), utility (optional)
2. ✅ **Registration Requires**: Pre-existing bank account in database
3. ✅ **Logic**: Account verification → Duplicate check → Create online access
4. ✅ **Password Rule**: EXACTLY 8 characters (very specific!)
5. ✅ **Email Rule**: Must be @gmail/@yahoo/@outlook/@hotmail

**Your sample data already has this setup!** Users in `users.json` can register for online banking! 🎉
